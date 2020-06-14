package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
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
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.TipKorisnika;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.dto.PrvoLogovanjeDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.ConfirmationTokenService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
public class AdminiKController {

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

	/*
	 * Funckija koja vraca sve administratore klinike
	 */
	@GetMapping("/adminK/getAll")
	public ResponseEntity<List<AdminKDTO>> getAll() {
		List<AdminKDTO> admini = new ArrayList<AdminKDTO>();
		List<AdministratorKlinike> adminiKlinike = adminService.findAll();
		for (AdministratorKlinike administratorKlinike : adminiKlinike) {
			admini.add(new AdminKDTO(administratorKlinike));
		}

		return new ResponseEntity<>(admini, HttpStatus.OK);
	}

	/*
	 * Funkcija za dodavanje administratora klinike
	 */
	@PostMapping("/adminK/add")
	public ResponseEntity<Object> add(@RequestBody AdminKDTO adminDTO) {
		adminDTO.setLozinka("Password1");

		if (!adminDTO.proveraPolja()) {
			return new ResponseEntity<>("Neispravni podaci", HttpStatus.BAD_REQUEST);
		}
		if (!jedinstvenEmail(adminDTO, true)) {
			return new ResponseEntity<>("Email nije jedinstven, već postoji u bazi", HttpStatus.BAD_REQUEST);
		}
		if (!jedinstvenJmbg(adminDTO, true)) {
			return new ResponseEntity<>("JMBG nije jedinstven, već postoji u bazi", HttpStatus.BAD_REQUEST);
		}
		adminDTO.setLozinka("XAEA12");
		AdministratorKlinike admin = new AdministratorKlinike();
		admin.setLozinka(adminDTO.getLozinka());
		admin.setIme(adminDTO.getIme());
		admin.setPrezime(adminDTO.getPrezime());
		admin.setJmbg(adminDTO.getJmbg());
		admin.setTipKorisnika(TipKorisnika.ADMINISTRATOR_KLINIKE);
		admin.setEmail(adminDTO.getEmail());

		admin = adminService.save(admin);

		/// Slanje email-a sa kodom za aktivaciju naloga
		ConfirmationToken confirmationToken = new ConfirmationToken(adminDTO.getJmbg());
		confirmationToken = tokenService.save(confirmationToken);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(adminDTO.getEmail());
		msg.setSubject("Uspešna registracija na klinički centar!");
		msg.setText("Da bi ste aktivirali nalog, kliknite na link: "
				+ "http://localhost:8080/klinicki-centar/aktivacija.html?token="
				+ confirmationToken.getConfirmationToken());
//		mrsisa2020-t19.herokuapp.com

		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new AdminKDTO(admin), HttpStatus.OK);
	}
	
	
	/*
	 * Funkcija koja postavlja sifru pri provoj prijavi na sistem
	 */
	@PutMapping("/adminK/prvaSifra")
	public ResponseEntity<Object> prvaSifra(@RequestBody PrvoLogovanjeDTO prvoLogovanje) {
		Pattern regPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,256}$");
		AdministratorKlinike admin = adminService.findOne(prvoLogovanje.getId());
		if (!regPass.matcher(prvoLogovanje.getSifra()).matches()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (admin != null) {
			admin.setLozinka(prvoLogovanje.getSifra());
			admin = adminService.save(admin);
			return new ResponseEntity<>(new AdminKDTO(admin), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	/*
	 * Funkcija za izmenu administratora klinike
	 */
	@PutMapping("/adminK/update")
	public ResponseEntity<AdminKDTO> updateAdmina(@RequestBody AdminKDTO adminKDTO) {

		if (!adminKDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!jedinstvenEmail(adminKDTO, false)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(adminKDTO, false)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		AdministratorKlinike admin = adminService.findOne(adminKDTO.getId());
		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		admin.setEmail(adminKDTO.getEmail());
		admin.setIme(adminKDTO.getIme().substring(0, 1).toUpperCase() + adminKDTO.getIme().substring(1).toLowerCase());
		admin.setPrezime(adminKDTO.getPrezime().substring(0, 1).toUpperCase()
				+ adminKDTO.getPrezime().substring(1).toLowerCase());
		admin.setLozinka(adminKDTO.getLozinka());
		admin.setJmbg(adminKDTO.getJmbg());

		admin = adminService.save(admin);

		return new ResponseEntity<>(new AdminKDTO(admin), HttpStatus.OK);

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
			if (!dodavanje) {
				if (ak.getEmail().equals(osoba.getEmail()) && !ak.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (ak.getEmail().equals(osoba.getEmail())) {
					return false;
				}
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
			if (!dodavanje) {
				if (ak.getJmbg().equals(osoba.getJmbg()) && !ak.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (ak.getJmbg().equals(osoba.getJmbg())) {
					return false;
				}
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
			if (l.getJmbg().equals(osoba.getJmbg())) {
				return false;
			}
		}
		return true;
	}
}
