package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.PacijentService;
import MRSISA.Klinicki.centar.service.ZahtevZRService;

@RestController
@RequestMapping("/pacijent")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private AdminKCSerivce adminKCService;

	@Autowired
	private AdminKService adminService;

	@Autowired
	private ZahtevZRService zzrService;

	@Autowired
	private LekarService lekarService;
	
	

	@GetMapping("/all")
	public ResponseEntity<List<PacijentDTO>> getAllPacijenti() {
		List<Pacijent> pacijenti = pacijentService.findAll();
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent p : pacijenti)
			pacijentiDTO.add(new PacijentDTO(p));

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}

	@GetMapping("/allAkcive")
	public ResponseEntity<List<PacijentDTO>> getAllActive() {
		List<Pacijent> pacijenti = pacijentService.findAll();
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent p : pacijenti) {
			if (p.getStanjePacijenta() == StanjePacijenta.AKTIVAN) {
				pacijentiDTO.add(new PacijentDTO(p));
			}
		}
		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}

	@GetMapping("/page/{stranica}/{koliko}")
	public ResponseEntity<List<PacijentDTO>> getPacijentPage(@PathVariable Integer stranica,
			@PathVariable Integer koliko) {
		Pageable prvihDeset = PageRequest.of(stranica, koliko,
				Sort.by("stanjePacijenta").descending().and(Sort.by("id")));
		Page<Pacijent> pacijenti = null;
		try {
			pacijenti = pacijentService.findAll(prvihDeset);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();

		for (Pacijent p : pacijenti) {
			if (p.getStanjePacijenta() == StanjePacijenta.AKTIVAN) {
				pacijentiDTO.add(new PacijentDTO(p));
			}

		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);

	}

	@PostMapping("/register")
	public ResponseEntity<PacijentDTO> register(@RequestBody PacijentDTO pacijentDTO) {
		if (!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!jedinstvenEmail(pacijentDTO)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(pacijentDTO)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		if (!jedinstveniBrOsig(pacijentDTO)) {
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}

		Pacijent novi = new Pacijent(0, pacijentDTO.getIme(), pacijentDTO.getPrezime(), pacijentDTO.getJmbg(),
				pacijentDTO.getEmail(), pacijentDTO.getLozinka(), null, pacijentDTO.getPol(), pacijentDTO.getGrad(),
				pacijentDTO.getDrzava(), pacijentDTO.getAdresa(), pacijentDTO.getBrojTelefona(),
				pacijentDTO.getJedinstveniBrOsig());
		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		SimpleMailMessage msg = new SimpleMailMessage();
		for (AdministratorKlinickogCentra adm : admini) {
			msg.setTo(adm.getEmail());
			msg.setSubject("Registracija korisnika");
			msg.setText(pacijentDTO.toString());
			try {
				javaMailSender.send(msg);
			} catch (MailAuthenticationException e) {
				//e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (MailException e) {
				// e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		ZahtevZaRegistraciju zzr = new ZahtevZaRegistraciju(0, StanjeZahteva.NA_CEKANJU, novi, new KlinickiCentar(1));
		zzrService.save(zzr);
		return new ResponseEntity<>(new PacijentDTO(), HttpStatus.CREATED);

	}

	@PostMapping("/add")
	public ResponseEntity<PacijentDTO> addPacijent(@RequestBody PacijentDTO pacijentDTO) {
		
		if (!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (!jedinstvenEmail(pacijentDTO)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(pacijentDTO)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		if (!jedinstveniBrOsig(pacijentDTO)) {
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}
		Pacijent pacijent = new Pacijent();
		pacijent.setId(pacijentDTO.getId());
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setJmbg(pacijentDTO.getJmbg());
		pacijent.setPol(pacijentDTO.getPol());
		pacijent.setStanjePacijenta(StanjePacijenta.NA_CEKANJU);
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		pacijent.setJedinstveniBrOsig(pacijentDTO.getJedinstveniBrOsig());

		pacijent = pacijentService.addPacijent(pacijent);

		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePacijent(@PathVariable Integer id) {
		Pacijent pacijent = pacijentService.findOne(id);

		if (pacijent != null) {
			pacijentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getUpdate/{id}")
	public ResponseEntity<PacijentDTO> getUpdate(@PathVariable Integer id) {
		Pacijent pacijent = pacijentService.findOne(id);
		if (pacijent != null)
			return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getOnePacijent/{id}")
	public ResponseEntity<PacijentDTO> getPacijent(@PathVariable Integer id) {
		Pacijent pacijent = pacijentService.findOne(id);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<PacijentDTO> updatePacijent(@RequestBody PacijentDTO pacijentDTO) {
		if (!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Pacijent pacijent = pacijentService.findOne(pacijentDTO.getId());
		if (pacijent == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (!pacijent.getEmail().equals(pacijentDTO.getEmail())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setPol(pacijentDTO.getPol());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		pacijent.setJedinstveniBrOsig(pacijentDTO.getJedinstveniBrOsig());

		System.out.println(pacijent);
		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}

	@PostMapping("/search/{kriterijum}/{vrednost}")
	public ResponseEntity<List<PacijentDTO>> searchPacijent(@PathVariable String kriterijum,
			@PathVariable String vrednost) {
		List<PacijentDTO> retVal = new ArrayList<>();
		for (Pacijent pacijent : pacijentService.findAll()) {
			if (kriterijum.equalsIgnoreCase("ime")) {
				if (pacijent.getIme().equalsIgnoreCase(vrednost)) {
					retVal.add(new PacijentDTO(pacijent));
				}
			} else if (kriterijum.equalsIgnoreCase("prezime")) {
				if (pacijent.getPrezime().equalsIgnoreCase(vrednost)) {
					retVal.add(new PacijentDTO(pacijent));
				}
			} else if (kriterijum.equalsIgnoreCase("Jedin. br. pacijenta")) {
				if (pacijent.getJedinstveniBrOsig().equalsIgnoreCase(vrednost)) {
					retVal.add(new PacijentDTO(pacijent));
				}
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@PostMapping("/filter/{grad}")
	public ResponseEntity<List<PacijentDTO>> getFilter(@PathVariable String grad) {
		List<PacijentDTO> retVal = new ArrayList<>();

		for (Pacijent p : pacijentService.findAll()) {
			if (p.getGrad().equalsIgnoreCase(grad)) {
				retVal.add(new PacijentDTO(p));
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	private boolean jedinstvenEmail(PacijentDTO pacijentDTO) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN)
					&& p.getEmail().equals(pacijentDTO.getEmail())) {
				return false;
			}
		}

		List<Lekar> lekari = lekarService.findAll();
		for (Lekar l : lekari) {
			if (l.getEmail().equals(pacijentDTO.getEmail())) {
				return false;
			}
		}

		List<AdministratorKlinike> adminiKlinika = adminService.findAll();
		for (AdministratorKlinike ak : adminiKlinika) {
			if (ak.getEmail().equals(pacijentDTO.getEmail())) {
				return false;
			}
		}

		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		for (AdministratorKlinickogCentra adm : admini) {
			if (adm.getEmail().equals(pacijentDTO.getEmail())) {
				return false;
			}
		}
		return true;
	}

	private boolean jedinstvenJmbg(PacijentDTO pacijentDTO) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN) && p.getJmbg().equals(pacijentDTO.getJmbg())) {

				return false;

			}
		}

		List<AdministratorKlinike> adminiKlinika = adminService.findAll();
		for (AdministratorKlinike ak : adminiKlinika) {
			if (ak.getJmbg().equals(pacijentDTO.getJmbg())) {
				return false;
			}
		}

		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		for (AdministratorKlinickogCentra adm : admini) {
			if (adm.getJmbg().equals(pacijentDTO.getJmbg())) {
				return false;
			}
		}
		return true;
	}

	private boolean jedinstveniBrOsig(PacijentDTO pacijentDTO) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN)
					&& p.getJedinstveniBrOsig().equals(pacijentDTO.getJedinstveniBrOsig())) {

				return false;

			}
		}
		return true;
	}

}
