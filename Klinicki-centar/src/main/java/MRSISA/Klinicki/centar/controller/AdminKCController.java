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
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.dto.PrvoLogovanjeDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.ConfirmationTokenService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;
import antlr.ASdebug.ASDebugStream;

@RestController
public class AdminKCController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ConfirmationTokenService tokenService;

	@Autowired
	private AdminKCSerivce adminKCService;

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private AdminKService adminService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private MedicinskaSestraSerive medSesService;

	@Autowired
	private KlinikaService klinikaService;

	@GetMapping("/adminKC/getAll")
	public ResponseEntity<List<AdminKCDTO>> getAllAdmini() {
		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		List<AdminKCDTO> ret = new ArrayList<AdminKCDTO>();
		for (AdministratorKlinickogCentra ad : admini) {
			ret.add(new AdminKCDTO(ad));
		}
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@PostMapping("/adminKC/add")
	public ResponseEntity<AdminKCDTO> addAdmin(@RequestBody AdminKCDTO adminKCDTO) {
		adminKCDTO.setLozinka("Password1");

		if (!adminKCDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!jedinstvenEmail(adminKCDTO, true)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(adminKCDTO, true)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		AdministratorKlinickogCentra admin = new AdministratorKlinickogCentra();
		admin.setEmail(adminKCDTO.getEmail());
		admin.setIme(
				adminKCDTO.getIme().substring(0, 1).toUpperCase() + adminKCDTO.getIme().substring(1).toLowerCase());
		admin.setPrezime(adminKCDTO.getPrezime().substring(0, 1).toUpperCase()
				+ adminKCDTO.getPrezime().substring(1).toLowerCase());
		admin.setJmbg(adminKCDTO.getJmbg());
		admin.setLozinka("XAEA12");

		admin = adminKCService.addAdmin(admin);

		ConfirmationToken confirmationToken = new ConfirmationToken(adminKCDTO.getJmbg());
		confirmationToken = tokenService.save(confirmationToken);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(adminKCDTO.getEmail());
		msg.setSubject("Uspešna registracija na klinički centar!");
		msg.setText("Da bi ste aktivirali nalog, kliknite na link: "
				+ "http://mrsisa2020-t19.herokuapp.com/klinicki-centar/aktivacija.html?token="
				+ confirmationToken.getConfirmationToken());

		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);
	}

	@DeleteMapping("/adminKC/delete/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id) {
		AdministratorKlinickogCentra admin = adminKCService.findOne(id);
		if (admin != null) {
			adminKCService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/adminKC/getupdate/{id}")
	public ResponseEntity<AdminKCDTO> getUpdate(@PathVariable Integer id) {
		AdministratorKlinickogCentra admin = adminKCService.findOne(id);
		if (admin != null) {
			return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/adminKC/prvaSifra")
	public ResponseEntity<AdminKCDTO> prvaSifra(@RequestBody PrvoLogovanjeDTO prvoLogovanje) {
		Pattern regPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,256}$");
		AdministratorKlinickogCentra admin = adminKCService.findOne(prvoLogovanje.getId());
		if (!regPass.matcher(prvoLogovanje.getSifra()).matches()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (admin != null) {
			admin.setLozinka(prvoLogovanje.getSifra());
			admin = adminKCService.save(admin);
			return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/adminKC/update")
	public ResponseEntity<AdminKCDTO> updateAdmina(@RequestBody AdminKCDTO adminKCDTO) {

		if (!adminKCDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!jedinstvenEmail(adminKCDTO, false)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(adminKCDTO, false)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		AdministratorKlinickogCentra admin = adminKCService.findOne(adminKCDTO.getId());
		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		admin.setEmail(adminKCDTO.getEmail());
		admin.setIme(
				adminKCDTO.getIme().substring(0, 1).toUpperCase() + adminKCDTO.getIme().substring(1).toLowerCase());
		admin.setPrezime(adminKCDTO.getPrezime().substring(0, 1).toUpperCase()
				+ adminKCDTO.getPrezime().substring(1).toLowerCase());
		admin.setLozinka(adminKCDTO.getLozinka());
		admin.setJmbg(adminKCDTO.getJmbg());

		admin = adminKCService.save(admin);

		return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);

	}

	@PutMapping("/superadmin/update")
	public ResponseEntity<AdminKCDTO> updateSuperAdmina(@RequestBody AdminKCDTO adminKCDTO) {

		if (adminKCDTO.getEmail().length() > 128) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (adminKCDTO.getEmail().equals("") || adminKCDTO.getEmail() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AdministratorKlinickogCentra admin = adminKCService.findOne(adminKCDTO.getId());
		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		admin.setEmail(adminKCDTO.getEmail());
		admin.setIme(adminKCDTO.getIme());
		admin.setPrezime(adminKCDTO.getPrezime());
		admin.setLozinka(adminKCDTO.getLozinka());
		admin.setJmbg(adminKCDTO.getJmbg());

		admin = adminKCService.save(admin);

		return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);

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
			if (!dodavanje) {
				if (adm.getEmail().equals(osoba.getEmail()) && !adm.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (adm.getEmail().equals(osoba.getEmail())) {
					return false;
				}
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
			if (!dodavanje) {
				if (adm.getJmbg().equals(osoba.getJmbg()) && !adm.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (adm.getJmbg().equals(osoba.getJmbg())) {
					return false;
				}
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
