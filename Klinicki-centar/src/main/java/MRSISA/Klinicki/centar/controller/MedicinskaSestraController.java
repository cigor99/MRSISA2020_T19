package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.ConfirmationToken;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.MedicinskaSestraDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.dto.PrvoLogovanjeDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.ConfirmationTokenService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;
import MRSISA.Klinicki.centar.service.ZahtevZRService;

@RestController
public class MedicinskaSestraController {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ConfirmationTokenService tokenService;

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private AdminKCSerivce adminKCService;

	@Autowired
	private AdminKService adminService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private MedicinskaSestraSerive medSesService;

	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	HttpServletRequest request;
	

	@GetMapping("/medicinskaSestra/all")
	public ResponseEntity<List<MedicinskaSestraDTO>> getAllMS() {
		List<MedicinskaSestra> sestre = medSesService.findAll();
		List<MedicinskaSestraDTO> sestreDTO = new ArrayList<MedicinskaSestraDTO>();
		for (MedicinskaSestra ms : sestre) {
			sestreDTO.add(new MedicinskaSestraDTO(ms));
		}
		return new ResponseEntity<>(sestreDTO, HttpStatus.OK);
	}

	@GetMapping("/medicinskaSestra/page")
	public ResponseEntity<List<MedicinskaSestraDTO>> getMSPage() {
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<MedicinskaSestra> sestre = medSesService.findAll(prvihDeset);
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");		
		int klinika = admink.getKlinikaID();
		List<MedicinskaSestraDTO> sestreDTO = new ArrayList<MedicinskaSestraDTO>();
		for (MedicinskaSestra ms : sestre) {
			if(ms.getKlinika().getId().equals(klinika)) {
				sestreDTO.add(new MedicinskaSestraDTO(ms));
			}
		}
		return new ResponseEntity<>(sestreDTO, HttpStatus.OK);
	}

	@PutMapping("/medicinskaSestra/update")
	public ResponseEntity<MedicinskaSestraDTO> updateSestra(@RequestBody MedicinskaSestraDTO medSes) {

		if (!medSes.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!jedinstvenEmail(medSes, false)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(medSes, false)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		MedicinskaSestra sestra = medSesService.findOne(medSes.getId());
		if (sestra == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
//		sestra.setEmail(medSes.getEmail());
		sestra.setIme(medSes.getIme().substring(0, 1).toUpperCase() + medSes.getIme().substring(1).toLowerCase());
		sestra.setPrezime(medSes.getPrezime().substring(0, 1).toUpperCase() + medSes.getPrezime().substring(1).toLowerCase());
		sestra.setLozinka(medSes.getLozinka());
//		sestra.setJmbg(medSes.getJmbg());

		sestra = medSesService.save(sestra);

		return new ResponseEntity<>(new MedicinskaSestraDTO(sestra), HttpStatus.OK);

	}

	@PostMapping("/medicinskaSestra/add")
	public ResponseEntity<MedicinskaSestraDTO> addLekar(@RequestBody MedicinskaSestraDTO sestraDTO) {
		sestraDTO.setLozinka("Password1");

		if (!sestraDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!jedinstvenEmail(sestraDTO, true)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(sestraDTO, true)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		MedicinskaSestra sestra = new MedicinskaSestra();
		String email = sestraDTO.getEmail();
		for (MedicinskaSestra ms : medSesService.getSestre()) {
			if (email.equals(ms.getEmail())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		sestra.setEmail(email);
		sestra.setLozinka("XAEA12");
		sestra.setJmbg(sestraDTO.getJmbg());
		sestra.setIme(sestraDTO.getIme().substring(0, 1).toUpperCase() + sestraDTO.getIme().substring(1).toLowerCase());
		sestra.setPrezime(sestraDTO.getPrezime().substring(0, 1).toUpperCase() + sestraDTO.getPrezime().substring(1).toLowerCase());
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");		
		int id = admink.getKlinikaID();
		Klinika klinika = klinikaService.findOne(id);
		sestra.setKlinika(klinika);
		sestra = medSesService.addSestra(sestra);
		
		ConfirmationToken confirmationToken = new ConfirmationToken(sestraDTO.getJmbg());
		confirmationToken = tokenService.save(confirmationToken);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(sestraDTO.getEmail());
		msg.setSubject("Uspešna registracija na klinički centar!");
		msg.setText("Da bi ste aktivirali nalog, kliknite na link: "
				+ "http://localhost:8080/klinicki-centar/aktivacija.html?token="
				+ confirmationToken.getConfirmationToken());

		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		
		return new ResponseEntity<>(new MedicinskaSestraDTO(sestra), HttpStatus.CREATED);
	}
	
	/*
	 * Funkcija za postavljanje sifre prilikom prve prijave na sistem
	 */
	@PutMapping("/medicinskaSestra/prvaSifra")
	public ResponseEntity<MedicinskaSestraDTO> prvaSifra(@RequestBody PrvoLogovanjeDTO prvoLogovanje) {
		Pattern regPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,256}$");
		MedicinskaSestra sestra = medSesService.findOne(prvoLogovanje.getId());
		if (!regPass.matcher(prvoLogovanje.getSifra()).matches()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (sestra != null) {
			sestra.setLozinka(prvoLogovanje.getSifra());
			sestra = medSesService.save(sestra);
			return new ResponseEntity<>(new MedicinskaSestraDTO(sestra), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	@DeleteMapping("/medicinskaSestra/delete/{id}")
	public ResponseEntity<Void> deleteSala(@PathVariable Integer id) {
		MedicinskaSestra sestra = medSesService.findOne(id);
		if (sestra != null) {
			medSesService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/medicinskaSestra/getOneMS/{id}")
	public ResponseEntity<MedicinskaSestraDTO> getMS(@PathVariable Integer id) {
		MedicinskaSestra sestra = medSesService.findOne(id);
		if (sestra == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new MedicinskaSestraDTO(sestra), HttpStatus.OK);
		}
	}

	@PostMapping("/medicinskaSestra/search")
	public ResponseEntity<List<MedicinskaSestraDTO>> searchLekar(@RequestBody String pretraga) {
		List<MedicinskaSestraDTO> retVal = new ArrayList<MedicinskaSestraDTO>();
		System.out.println(pretraga);
		pretraga = pretraga.toLowerCase();
		for (MedicinskaSestra ms : medSesService.findAll()) {
			System.out.println(ms.getIme());
			if (ms.getIme().toLowerCase().contains(pretraga) || ms.getPrezime().toLowerCase().contains(pretraga)
					|| ms.getEmail().toLowerCase().contains(pretraga)) {
				System.out.println(ms.getPrezime());
				MedicinskaSestraDTO sestra = new MedicinskaSestraDTO(ms);
				retVal.add(sestra);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);

	}

	/* Funkcija koja proverava da li postoji dati email u bazi */
	private boolean jedinstvenEmail(Osoba osoba, boolean dodavanje) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {

			if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN) && p.getEmail().equals(osoba.getEmail())) {
				return false;
			}
		}

		List<Lekar> lekari = lekarService.findAll();
		for (Lekar l : lekari) {
			if (l.getEmail().equals(osoba.getEmail())) {
				return false;
			}
		}

		List<AdministratorKlinike> adminiKlinika = adminService.findAll();
		for (AdministratorKlinike ak : adminiKlinika) {
			if (ak.getEmail().equals(osoba.getEmail())) {
				return false;
			}
		}

		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		for (AdministratorKlinickogCentra adm : admini) {
			if (adm.getEmail().equals(osoba.getEmail())) {
				return false;
			}
		}

		List<MedicinskaSestra> sestre = medSesService.findAll();
		for (MedicinskaSestra m : sestre) {
			if (!dodavanje) {
				if (m.getEmail().equals(osoba.getEmail()) && !m.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (m.getEmail().equals(osoba.getEmail())) {
					return false;
				}
			}
		}

		return true;
	}

	/* Funkcija koja proverava da li postoji dati jmbg u bazi */
	private boolean jedinstvenJmbg(Osoba osoba, boolean dodavanje) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN) && p.getJmbg().equals(osoba.getJmbg())) {

				return false;

			}
		}

		List<AdministratorKlinike> adminiKlinika = adminService.findAll();
		for (AdministratorKlinike ak : adminiKlinika) {
			if (ak.getJmbg().equals(osoba.getJmbg())) {
				return false;
			}
		}

		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		for (AdministratorKlinickogCentra adm : admini) {
			if (adm.getJmbg().equals(osoba.getJmbg())) {
				return false;
			}
		}

		List<MedicinskaSestra> sestre = medSesService.findAll();
		for (MedicinskaSestra m : sestre) {
			if (!dodavanje) {
				if (m.getJmbg().equals(osoba.getJmbg()) && !m.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (m.getJmbg().equals(osoba.getJmbg())) {
					return false;
				}
			}
		}

		List<Lekar> lekari = lekarService.findAll();
		for (Lekar l : lekari) {
			if (l.getJmbg().equals(osoba.getJmbg())) {
				return false;
			}
		}
		return true;
	}

}
