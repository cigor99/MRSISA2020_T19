package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Ocena;
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.OcenjivanjePregledaDTO;
import MRSISA.Klinicki.centar.dto.OperacijaDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.dto.PregledDTO;
import MRSISA.Klinicki.centar.dto.PregledDetaljiDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;
import MRSISA.Klinicki.centar.service.PregledService;
import MRSISA.Klinicki.centar.service.ZahtevZRService;

/*Kontroler u kom se nalaze metode vezane za pacijenta*/
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

	@Autowired
	private MedicinskaSestraSerive medSesService;
	
	@Autowired
	HttpServletRequest request;
	

	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private KlinikaService klinikaService;

	/*
	 * Get zahtev Vraca sve pacijente iz baze
	 */
	@GetMapping("/all")
	public ResponseEntity<List<PacijentDTO>> getAllPacijenti() {
		List<Pacijent> pacijenti = pacijentService.findAll();
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent p : pacijenti)
			pacijentiDTO.add(new PacijentDTO(p));

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}

	/*
	 * Get zahtev Vraca sve pacijente iz baze ciji je nalog aktiviram
	 */
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

	/*
	 * Get zahtev Vraca odredjen broj pacijenata
	 */
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

	/*
	 * Post zahtev Funkcija za slanje zahteva za registraciju pacijenata Proverava
	 * da li su uneseni podaci ispravni Proverava da li su podaci koji trebaju biti
	 * jedinstveni, zaista jedinstveni Ako je sve to ispravno salje se mejl
	 * administratorima da je stigao nov zahtev Kreira se nov zahtev
	 */
	@PostMapping("/register")
	public ResponseEntity<PacijentDTO> register(@RequestBody PacijentDTO pacijentDTO) {
		if (!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!jedinstvenEmail(pacijentDTO, true)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(pacijentDTO, true)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		if (!jedinstveniBrOsig(pacijentDTO, true)) {
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}

		Pacijent novi = new Pacijent(0,
				pacijentDTO.getIme().substring(0, 1).toUpperCase() + pacijentDTO.getIme().substring(1).toLowerCase(),
				pacijentDTO.getPrezime().substring(0, 1).toUpperCase()
						+ pacijentDTO.getPrezime().substring(1).toLowerCase(),
				pacijentDTO.getJmbg(), pacijentDTO.getEmail(), pacijentDTO.getLozinka(), null, pacijentDTO.getPol(),
				pacijentDTO.getGrad(), pacijentDTO.getDrzava(), pacijentDTO.getAdresa(), pacijentDTO.getBrojTelefona(),
				pacijentDTO.getJedinstveniBrOsig());
		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		SimpleMailMessage msg = new SimpleMailMessage();
		for (AdministratorKlinickogCentra adm : admini) {
			if (!adm.getEmail().equals("super")) {
				msg.setTo(adm.getEmail());
				msg.setSubject("Registracija korisnika");
				msg.setText(pacijentDTO.toString());

				try {
					javaMailSender.send(msg);
				} catch (MailAuthenticationException e) {
					// e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (MailException e) {
					// e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		ZahtevZaRegistraciju zzr = new ZahtevZaRegistraciju(0, StanjeZahteva.NA_CEKANJU, novi, new KlinickiCentar(1));
		zzrService.save(zzr);
		return new ResponseEntity<>(new PacijentDTO(), HttpStatus.CREATED);

	}

	/*
	 * Post zahtev Funkcija za dodavanje novih pacijenata Proverava da li su uneseni
	 * podaci ispravni Proverava da li su podaci koji trebaju biti jedinstveni,
	 * zaista jedinstveni Ako je to ispravno dodaje se nov pacijent u bazu
	 */
	@PostMapping("/add")
	public ResponseEntity<PacijentDTO> addPacijent(@RequestBody PacijentDTO pacijentDTO) {

		if (!pacijentDTO.proveraPolja()) {
			System.out.println("provera POLJA");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (!jedinstvenEmail(pacijentDTO, false)) {
			System.out.println("provera jedinstvenEmail");
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(pacijentDTO, false)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		if (!jedinstveniBrOsig(pacijentDTO, false)) {
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}
		Pacijent pacijent = new Pacijent();
		pacijent.setId(pacijentDTO.getId());
		pacijent.setIme(
				pacijentDTO.getIme().substring(0, 1).toUpperCase() + pacijentDTO.getIme().substring(1).toLowerCase());
		pacijent.setPrezime(pacijentDTO.getPrezime().substring(0, 1).toUpperCase()
				+ pacijentDTO.getPrezime().substring(1).toLowerCase());
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

	/*
	 * Delete zahtev Proba da se obrise pacijent sa datim id-jem Ako je uspesno
	 * pacijent se brise iz baze Ako je neuspesno vraca se error 404
	 */
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

	/*
	 * Get zahtev Vraca se pacijent sa datim id-jem Ako je uspesno vraca se pacijent
	 * iz baze Ako je neuspesno vraca se error 404
	 */
	@GetMapping("/getUpdate/{id}")
	public ResponseEntity<PacijentDTO> getUpdate(@PathVariable Integer id) {
		Pacijent pacijent = pacijentService.findOne(id);
		if (pacijent != null)
			return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * Get zahtev Vraca se pacijent sa datim id-jem Ako je uspesno vraca se pacijent
	 * iz baze Ako je neuspesno vraca se error 400
	 */
	@GetMapping("/getOnePacijent/{id}")
	public ResponseEntity<PacijentDTO> getPacijent(@PathVariable Integer id) {
		Pacijent pacijent = pacijentService.findOne(id);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
		}
	}

	/*
	 * Post zahtev Funkcija za izmenu podataka pacijenta Vrsi se provera polja Ako
	 * su polja ispravna trazi se pacijent u bazi Ako se pacijent ne nadje vraca se
	 * error 400 Vrsi se provera da li pacijent menja svoj email Ako da vraca se
	 * error 403 Izmene se cuvaju u bazi
	 */
	@PutMapping("/update")
	public ResponseEntity<PacijentDTO> updatePacijent(@RequestBody PacijentDTO pacijentDTO) {
		if (!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (!jedinstvenEmail(pacijentDTO, false)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (!jedinstvenJmbg(pacijentDTO, false)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		if (!jedinstveniBrOsig(pacijentDTO, false)) {
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}

		Pacijent pacijent = pacijentService.findOne(pacijentDTO.getId());
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (!pacijent.getEmail().equals(pacijentDTO.getEmail())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if (!pacijent.getJmbg().equals(pacijentDTO.getJmbg())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if (!pacijent.getJedinstveniBrOsig().equals(pacijentDTO.getJedinstveniBrOsig())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		pacijent.setIme(
				pacijentDTO.getIme().substring(0, 1).toUpperCase() + pacijentDTO.getIme().substring(1).toLowerCase());
		pacijent.setPrezime(pacijentDTO.getPrezime().substring(0, 1).toUpperCase()
				+ pacijentDTO.getPrezime().substring(1).toLowerCase());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setPol(pacijentDTO.getPol());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		// pacijent.setJedinstveniBrOsig(pacijentDTO.getJedinstveniBrOsig());

		System.out.println(pacijent);
		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}

	/*
	 * Post zahtev Funkcija koja sluzi za pretragu pacijenata u zavisnosti od
	 * kriterijuma pretrage i vrednosti traze se pacijenti iz baze Vraca se lista
	 * pronadjenih pacijenata koja moze biti prazna
	 */
	@PostMapping("/search/{kriterijum}/{vrednost}")
	public ResponseEntity<List<PacijentDTO>> searchPacijent(@PathVariable String kriterijum,
			@PathVariable String vrednost) {
		List<PacijentDTO> retVal = new ArrayList<>();
		for (Pacijent pacijent : pacijentService.findAll()) {
			if (kriterijum.equalsIgnoreCase("ime")) {
				if (pacijent.getIme().toLowerCase().contains((vrednost.toLowerCase()))) {
					retVal.add(new PacijentDTO(pacijent));
				}
			} else if (kriterijum.equalsIgnoreCase("prezime")) {
				if (pacijent.getPrezime().toLowerCase().contains((vrednost.toLowerCase()))) {
					retVal.add(new PacijentDTO(pacijent));
				}
			} else if (kriterijum.equalsIgnoreCase("Jedin. br. pacijenta")) {
				if (pacijent.getJedinstveniBrOsig().toLowerCase().contains((vrednost.toLowerCase()))) {
					retVal.add(new PacijentDTO(pacijent));
				}
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	/*
	 * Post zahtev Funkcija koja sluzi za filtriranje pacijenata u zavisnosti od
	 * grada traze se pacijenti iz baze Vraca se lista pronadjenih pacijenata koja
	 * moze biti prazna
	 */
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
	
	/*
	 * Funkcija koja vraca istoriju pregleda za pacijenta
	 */
	@GetMapping("/istorijaPregleda")
	public ResponseEntity<Set<PregledDTO>> getIstorijaPregleda(){
		Set<PregledDTO> istorija = new HashSet<>();
		Object current = request.getSession().getAttribute("current");
		PacijentDTO curr = null;
		try {
			curr = (PacijentDTO) current;
		}catch (ClassCastException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pacijent p = pacijentService.findOne(curr.getId());
		for(Pregled pregled : p.getIstorijaPregleda()) {
			istorija.add(new PregledDTO(pregled));
		}
		
		return new ResponseEntity<>(istorija, HttpStatus.OK);
		
	}
	
	/*
	 * Funkcija koja vraca istoriju operacija za pacijenta
	 */
	@GetMapping("/istorijaOperacija")
	public ResponseEntity<Set<OperacijaDTO>> getIstorijaOperacija(){
		Set<OperacijaDTO> istorija = new HashSet<>();
		Object current = request.getSession().getAttribute("current");
		PacijentDTO curr = null;
		Set<String> lekari = new HashSet<>();
		try {
			curr = (PacijentDTO) current;
		}catch (ClassCastException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pacijent p = pacijentService.findOne(curr.getId());
		for(Operacija operacija : p.getIstorijaOperacija()) {
			istorija.add(new OperacijaDTO(operacija));
		}
		
		
		return new ResponseEntity<>(istorija, HttpStatus.OK);
		
	}
	
	@GetMapping("/getPregledByID/{idPregleda}")
	public ResponseEntity<PregledDetaljiDTO> getPregledByID(@PathVariable String idPregleda){
		Pregled p = pregledService.findOne(Integer.parseInt(idPregleda));
		if(p != null) {
			return new ResponseEntity<PregledDetaljiDTO>(new PregledDetaljiDTO(p), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("ocenjivanjePregleda")
	public ResponseEntity<String> ocenjivanjePregleda(@RequestBody OcenjivanjePregledaDTO ocene){
		Integer lekarId = Integer.parseInt(ocene.getIdLekar());
		Integer klinikaId = Integer.parseInt(ocene.getIdKlinika());
		Lekar l = lekarService.findOne(lekarId);
		if(l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Klinika k = klinikaService.findOne(klinikaId);
		if(k == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Ocena ocenaLekara = kojaOcena(ocene.getOcenaLekar());
		if(ocenaLekara != null) {
			List<Ocena> oceneLekara = l.getOcene();
			oceneLekara.add(ocenaLekara);
			l.setProsecnaOcena(l.izracunajProsecnuOcenu());
			lekarService.save(l);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Ocena ocenaKlinike = kojaOcena(ocene.getOcenaKlinika());
		if(ocenaKlinike != null) {
			List<Ocena> oceneKlinike = k.getOcene();
			oceneKlinike.add(ocenaKlinike);
			k.setProsecnaOcena(k.izracunajProsecnuOcenu());
			klinikaService.save(k);
			
			return new ResponseEntity<String>("Uspesno ocenjeno", HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	private Ocena kojaOcena(String ocena) {
		switch (ocena) {
		case "5":
			return Ocena.PET;
		case "4":
			return Ocena.CETIRI;
		case "3":
			return Ocena.TRI;
		case "2":
			return Ocena.DVA;
		case "1":
			return Ocena.JEDAN;
		default:
			return null;
		}
	}

	/* Funkcija koja proverava da li postoji dati email u bazi */
	private boolean jedinstvenEmail(Osoba osoba, Boolean dodavanje) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			//System.out.println(p);
			if (!dodavanje) {
				if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN) && p.getEmail().equals(osoba.getEmail())
						&& !p.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN) && p.getEmail().equals(osoba.getEmail())) {
					return false;
				}
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
			if (m.getEmail().equals(osoba.getEmail())) {
				return false;
			}
		}

		return true;
	}

	/* Funkcija koja proverava da li postoji dati jmbg u bazi */
	private boolean jedinstvenJmbg(Osoba osoba, Boolean dodavanje) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if (!dodavanje) {
				if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN) && p.getJmbg().equals(osoba.getJmbg())
						&& !p.getId().equals(osoba.getId())) {

					return false;
				}
			} else {
				if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN) && p.getJmbg().equals(osoba.getJmbg())) {

					return false;
				}
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
			if (l.getJmbg().equals(osoba.getJmbg())) {
				return false;
			}
		}
		return true;
	}

	/* Funkcija koja proverava da li postoji dati broj osiguranja u bazi */
	private boolean jedinstveniBrOsig(PacijentDTO pacijentDTO, Boolean dodavanje) {
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if (!dodavanje) {
				if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN)
						&& p.getJedinstveniBrOsig().equals(pacijentDTO.getJedinstveniBrOsig())
						&& !p.getId().equals(pacijentDTO.getId())) {

					return false;

				}
			} else {
				if (!p.getStanjePacijenta().equals(StanjePacijenta.ODBIJEN)
						&& p.getJedinstveniBrOsig().equals(pacijentDTO.getJedinstveniBrOsig())) {

					return false;
				}
			}
		}
		return true;
	}
	
	

	
	

}
