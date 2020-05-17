package MRSISA.Klinicki.centar.controller;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.ConfirmationToken;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.MedicinskaSestraDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.dto.PrvoLogovanjeDTO;
import MRSISA.Klinicki.centar.dto.SalaDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.ConfirmationTokenService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
public class LekarController {
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
	

	@GetMapping("/lekar/all")
	public ResponseEntity<List<LekarDTO>> getAllLekari() {
		List<Lekar> lekari = lekarService.findAll();
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		for (Lekar l : lekari) {
			lekariDTO.add(new LekarDTO(l));
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	@GetMapping("/lekar/page")
	public ResponseEntity<List<LekarDTO>> getLekarPage() {
		Pageable prvihDeset = PageRequest.of(0, 10);
		System.out.println(request.getSession().getAttribute("current"));
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");		
		System.out.println(admink.getEmail());
		System.out.println(admink.getKlinikaID());
		int klinika = admink.getKlinikaID();
		Page<Lekar> lekari = lekarService.findAll(prvihDeset);
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		for (Lekar l : lekari) {
			if(l.getKlinika().getId().equals(klinika)) {
				lekariDTO.add(new LekarDTO(l));
			}
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	@PostMapping("/lekar/add")
	public ResponseEntity<LekarDTO> addLekar(@RequestBody LekarDTO lekarDTO) {
		lekarDTO.setLozinka("Password1");

		if (!lekarDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (!jedinstvenEmail(lekarDTO, true)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(lekarDTO, true)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		Lekar lekar = new Lekar();
		String email = lekarDTO.getEmail();
		for (Lekar l : lekarService.getLekari()) {
			if (email.equals(l.getEmail())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		lekar.setEmail(email);
		lekar.setLozinka("XAEA12");
		lekar.setJmbg(lekarDTO.getJmbg());
		lekar.setIme(lekarDTO.getIme().substring(0, 1).toUpperCase() + lekarDTO.getIme().substring(1).toLowerCase());
		lekar.setPrezime(lekarDTO.getPrezime().substring(0, 1).toUpperCase() + lekarDTO.getPrezime().substring(1).toLowerCase());
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");		
		int id = admink.getKlinikaID();
		Klinika klinika = klinikaService.findOne(id);
		lekar.setKlinika(klinika);
		lekar = lekarService.addLekar(lekar);
		
		/// Slanje email-a sa kodom za aktivaciju naloga
		ConfirmationToken confirmationToken = new ConfirmationToken(lekarDTO.getJmbg());
		confirmationToken = tokenService.save(confirmationToken);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(lekarDTO.getEmail());
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
		
		
		
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/lekar/prvaSifra")
	public ResponseEntity<LekarDTO> prvaSifra(@RequestBody PrvoLogovanjeDTO prvoLogovanje) {
		Pattern regPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,256}$");
		Lekar lekar = lekarService.findOne(prvoLogovanje.getId());
		if (!regPass.matcher(prvoLogovanje.getSifra()).matches()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (lekar != null) {
			lekar.setLozinka(prvoLogovanje.getSifra());
			lekar = lekarService.save(lekar);
			return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	@PostMapping("/lekar/search")
	public ResponseEntity<List<LekarDTO>> searchLekar(@RequestBody String pretraga) {
		List<LekarDTO> retVal = new ArrayList<LekarDTO>();
		System.out.println(pretraga);

		for (Lekar l : lekarService.findAll()) {
			System.out.println(l.getIme());
			if (l.getIme().toLowerCase().contains(pretraga) || l.getPrezime().toLowerCase().contains(pretraga)
					|| l.getEmail().toLowerCase().contains(pretraga)) {
				System.out.println(l.getPrezime());
				LekarDTO lekar = new LekarDTO(l);
				retVal.add(lekar);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);

	}

	@DeleteMapping("/lekar/delete/{id}")
	public ResponseEntity<Void> deleteSala(@PathVariable Integer id) {
		Lekar lekar = lekarService.findOne(id);
		if (lekar != null) {
			lekarService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/lekar/getOneLekar/{id}")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable Integer id) {
		Lekar lekar = lekarService.findOne(id);
		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
		}
	}

	@PutMapping("/lekar/update")
	public ResponseEntity<LekarDTO> updateLekara(@RequestBody LekarDTO lekarDTO) {

		System.out.println(lekarDTO);
		if (!lekarDTO.proveraPolja()) {
			System.out.println("USAO!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}

		if (!jedinstvenEmail(lekarDTO, false)) {
			System.out.println("USAO11!");
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(lekarDTO, false)) {
			System.out.println("USAO22!");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		Lekar lekar = lekarService.findOne(lekarDTO.getId());
		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
//		lekar.setEmail(lekarDTO.getEmail());
		lekar.setIme(lekarDTO.getIme().substring(0, 1).toUpperCase() + lekarDTO.getIme().substring(1).toLowerCase());
		lekar.setPrezime(lekarDTO.getPrezime().substring(0, 1).toUpperCase() + lekarDTO.getPrezime().substring(1).toLowerCase());
		lekar.setLozinka(lekarDTO.getLozinka());
//		lekar.setJmbg(lekarDTO.getJmbg());

		lekar = lekarService.save(lekar);

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);

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
			if(!dodavanje) {
				if (l.getEmail().equals(osoba.getEmail()) && !l.getId().equals(osoba.getId())) {
					return false;
				}
			}else {
				if (l.getEmail().equals(osoba.getEmail())) {
					return false;
				}
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
			if (m.getEmail().equals(osoba.getEmail())) {
				return false;
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
			if (m.getJmbg().equals(osoba.getJmbg())) {
				return false;
			}
		}

		List<Lekar> lekari = lekarService.findAll();
		for (Lekar l : lekari) {
			if(!dodavanje) {
			if (l.getJmbg().equals(osoba.getJmbg()) && !l.getId().equals(osoba.getId())) {
				return false;
			}
			}else {
				if (l.getJmbg().equals(osoba.getJmbg())) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * @GetMapping("/lekar/getUpdate/{id}") public ResponseEntity<LekarDTO>
	 * getUpdate(@PathVariable Integer id){ Lekar lekar = lekarService.findOne(id);
	 * if(lekar != null) { return new ResponseEntity<>(new
	 * LekarDTO(lekar),HttpStatus.OK); }else { return new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND); } }
	 * 
	 * @PutMapping("/lekar/update") public ResponseEntity<LekarDTO>
	 * updateSala(@RequestBody LekarDTO lekarDTO){ Lekar lekar =
	 * lekarService.findOne(lekarDTO.getId()); if(lekar == null) { return new
	 * ResponseEntity<>(HttpStatus.BAD_REQUEST); }
	 * lekar.setEmail(lekarDTO.getEmail()); lekar.setLozinka(lekarDTO.getLozinka());
	 * lekar.setIme(lekarDTO.getIme()); lekar.setPrezime(lekarDTO.getPrezime()); ;
	 * return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK); }
	 */

	/*
	 * @GetMapping("/dobaviKlinike") public ResponseEntity<List<Klinika>>
	 * dobaviKlinike() { ArrayList<Klinika> klinike = new ArrayList<Klinika>();
	 * Klinika k1 = new Klinika(1, "klinika 1", "adresa klinike 1", "opis", null,
	 * null, null, null); Klinika k2 = new Klinika(2, "klinika 2",
	 * "adresa klinike 2", "opis", null, null, null, null); klinike.add(k1);
	 * klinike.add(k2);
	 * 
	 * return new ResponseEntity<List<Klinika>>(klinike, HttpStatus.OK); }
	 */

	/*
	 * @PostMapping("/dodajNovogLekara") public ResponseEntity
	 * dodajLekara(@RequestBody Lekar lekar) throws ClassNotFoundException,
	 * SQLException { System.out.println(lekar); lekarService.dodajLekara(lekar);
	 * //System.out.println(lekar.getEmail()); return new
	 * ResponseEntity(HttpStatus.OK); }
	 */

}