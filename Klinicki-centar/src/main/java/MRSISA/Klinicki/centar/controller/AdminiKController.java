package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
public class AdminiKController {

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

	@GetMapping("/adminK/getAll")
	public ResponseEntity<List<AdminKDTO>> getAll() {
		List<AdminKDTO> admini = new ArrayList<AdminKDTO>();
		List<AdministratorKlinike> adminiKlinike = adminService.findAll();
		for (AdministratorKlinike administratorKlinike : adminiKlinike) {
			admini.add(new AdminKDTO(administratorKlinike));
		}

		return new ResponseEntity<>(admini, HttpStatus.OK);
	}

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
		admin.setIme(adminKDTO.getIme());
		admin.setPrezime(adminKDTO.getPrezime());
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
			}else {
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
