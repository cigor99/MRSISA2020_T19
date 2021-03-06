package MRSISA.Klinicki.centar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.Cena;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.domain.ZahtevZaGodisnjiOdmor;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.Admin_klinikaDTO;
import MRSISA.Klinicki.centar.dto.KlinikaDTO;
import MRSISA.Klinicki.centar.dto.LekDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.dto.PretragaKlinikaDTO;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.OperacijaService;
import MRSISA.Klinicki.centar.service.PregledService;
import MRSISA.Klinicki.centar.service.TipPregledaService;

@RestController
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private AdminKService adminService;

	@Autowired
	private TipPregledaService tipPregledaService;

	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/klinika/all")
	public ResponseEntity<List<KlinikaDTO>> getAllKlinike() {

		List<Klinika> klinike = klinikaService.findAll();

		List<KlinikaDTO> klinikeDTO = new ArrayList<KlinikaDTO>();
		for (Klinika k : klinike) {
			klinikeDTO.add(new KlinikaDTO(k));
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}

	@GetMapping("/klinika/page")
	public ResponseEntity<List<KlinikaDTO>> getKlinikaPage() {
		// AKO TREBA DA SE MENJA MOZE SE PROSLEDITI Pageable pageable
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Klinika> klinike = klinikaService.findAll(prvihDeset);

		List<KlinikaDTO> klinikeDTO = new ArrayList<KlinikaDTO>();
		for (Klinika k : klinike) {
			klinikeDTO.add(new KlinikaDTO(k));
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}

	@PostMapping("/klinika/add")
	public ResponseEntity<KlinikaDTO> addKlinika(@RequestBody KlinikaDTO klinikaDTO) {
		System.out.println("USAO");
		System.out.println(klinikaDTO);
		Klinika klinika = new Klinika();
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setId(klinikaDTO.getId());
		System.out.println(klinika.getNaziv());
		klinika = klinikaService.addKlinika(klinika);

		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}

	@DeleteMapping("/klinika/delete/{id}")
	public ResponseEntity<Void> deleteKlinika(@PathVariable Integer id) {
		System.out.println("CONTROLER");
		System.out.println(id);
		Klinika klinika = klinikaService.findOne(id);
		System.out.println("VRATIO se");
		System.out.println(klinika != null);

		if (klinika != null) {
			klinikaService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/klinika/getUpdate/{id}")
	public ResponseEntity<KlinikaDTO> getUpdate(@PathVariable Integer id) {
		Klinika klinika = klinikaService.findOne(id);
		if (klinika != null) {

			return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/klinika/update")
	public ResponseEntity<KlinikaDTO> updateklinika(@RequestBody KlinikaDTO klinikaDTO) {
		Klinika klinika = klinikaService.findOne(klinikaDTO.getId());

		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setNaziv(klinikaDTO.getNaziv());

		klinika = klinikaService.save(klinika);

		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}
	
	@GetMapping("/klinika/prihodi/{datum1}/{datum2}")
	public ResponseEntity<Double> getPrihodi(@PathVariable String datum1, @PathVariable String datum2) throws ParseException {
		int id = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if(tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			id = admink.getKlinikaID();			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(datum1);
		Date date2 = sdf.parse(datum2);
		double ukupno = 0;
		Klinika klinika = klinikaService.findOne(id);
		if (klinika != null) {
			for(Pregled p : pregledService.findAll()) {
				if(p.getIzvestajiPregleda().size()>0 && p.getSala() != null && p.getSala().getKlinika().getId().equals(id)) {
					if(p.getDatum().compareTo(date1)>=0 && p.getDatum().compareTo(date2)<= 0) {
						ukupno += p.getTipPregleda().getCena().getIznos();	
					}
				}
			}
			return new ResponseEntity<>(ukupno, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@PutMapping("/klinika/addAdmin")
	public ResponseEntity<Admin_klinikaDTO> addAdmin(@RequestBody Admin_klinikaDTO admin_klinika) {
		System.out.println("ADMIN_KLINIKA");
		System.out.println(admin_klinika.getAdminID());
		System.out.println(admin_klinika.getKlinikaID());
		System.out.println("ADMIN_KLINIKA");
		AdministratorKlinike admin = adminService.findOne(admin_klinika.getAdminID());
		Klinika klinika = klinikaService.findOne(admin_klinika.getKlinikaID());
		System.out.println(admin);
		System.out.println(klinika);
		if (klinika != null && admin != null) {
			klinika.getAdministratori().add(admin);
			admin.setKlinika(klinika);
			klinika = klinikaService.save(klinika);
			admin = adminService.save(admin);
			return new ResponseEntity<>(new Admin_klinikaDTO(admin.getId(), klinika.getId()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/klinika/search")
	public ResponseEntity<List<KlinikaDTO>> searchKlinika(@RequestBody String pretraga) {
		List<KlinikaDTO> retVal = new ArrayList<>();
		System.out.println(pretraga);
		System.out.println("======================");
		for (Klinika k : klinikaService.findAll()) {
			System.out.println(k.getNaziv());
			if (k.getNaziv().contains(pretraga)) {
				KlinikaDTO dto = new KlinikaDTO(k);
				retVal.add(dto);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@GetMapping("/klinika/getKlinika/{id}")
	public ResponseEntity<KlinikaDTO> getKlinika(@PathVariable Integer id) {
		Klinika klinika = klinikaService.findOne(id);
		if (klinika == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
		}
	}
	
	/*
	 * Funckija koja vraca id trenutno ulogovanog administratora klinike
	 */
	@GetMapping("/klinika/getCurrent")
	public ResponseEntity<Integer> getCurrent() {
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
		int id = admink.getKlinikaID();
		// Klinika klinika = klinikaService.findOne(id);

		return new ResponseEntity<>(id, HttpStatus.OK);

	}

	/*
	 * Funckija koja vraca cenu po tipu pregleda
	 */
	@GetMapping("/klinika/getCenaTipaPoID/{idKlinike}/{idTipa}")
	public ResponseEntity<Double> getCenaTipaPoID(@PathVariable Integer idKlinike, @PathVariable Integer idTipa) {
		Klinika klinika = klinikaService.findOne(idKlinike);
		try {
			for (Cena c : klinika.getCenovnik().getCene()) {
				if (c.getTipPregleda().getId().equals(idTipa)) {
					return new ResponseEntity<>(c.getIznos(), HttpStatus.OK);
				}
			}
		} catch (NullPointerException e) {
			return new ResponseEntity<>(3.0, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * Funckija kojom pacijent vrsi pretragu klinika
	 */
	@PostMapping("/klinika/searchPacijentoviParametri")
	public ResponseEntity<Set<KlinikaDTO>> searchKlinikaPacijentoviParametri(@RequestBody PretragaKlinikaDTO pretraga) {
		Set<KlinikaDTO> retVal = new HashSet<>();
		TipPregleda tip = tipPregledaService.findOne(pretraga.tip);
		List<Pregled> pregledi = pregledService.findAll();
		for (Klinika k : klinikaService.findAll()) {
			if (k.getProsecnaOcena() < pretraga.ocena) {
				continue;
			}
			for (Lekar l : k.getLekari()) {
				if (l.getTipoviPregleda().contains(tip)) {
					if(!proveriZauzetosti(l, pretraga.datum)) {
						KlinikaDTO dto = new KlinikaDTO(k);
						if (!retVal.contains(dto)) {
							retVal.add(dto);
						}
					}
				}
			}

		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	/*
	 * Funkcija koja vrsi roveru da li je lekar zauzet u tom danu
	 */
	private boolean proveriZauzetosti(Lekar l, Date pretraga) {
		Klinika klinika = l.getKlinika();
		Date pocetak = new Date();
		Date kraj = new Date();
		pocetak.setTime(pretraga.getTime());
		kraj.setTime(pretraga.getTime());
		final long ONE_MINUTE_IN_MILLIS = 60000;
		
		String pocetakString = klinika.getPocetakRadnogVremena();
		String[] pocetakTokens = pocetakString.split(":");
		int pocetakSat = Integer.parseInt(pocetakTokens[0]);
		int pocetakMinut = Integer.parseInt(pocetakTokens[1]);
		pocetakMinut += pocetakSat * 60;
		pocetak.setTime(pocetak.getTime() + pocetakMinut * ONE_MINUTE_IN_MILLIS);
		
		String krajString = klinika.getKrajRadnogVremena();
		String[] krajTokens = krajString.split(":");
		int krajSat = Integer.parseInt(krajTokens[0]);
		int krajMinut = Integer.parseInt(krajTokens[1]);
		krajMinut += krajSat * 60;
		kraj.setTime(kraj.getTime() + krajMinut * ONE_MINUTE_IN_MILLIS);
		
		Date porednjenje = new Date();
		while (pocetak.before(kraj)) {
			boolean uslov = true;
			for (Pregled pregled : l.getPregledi()) {
				porednjenje.setTime(
						pregled.getDatum().getTime() + pregled.getTipPregleda().getTrajanje() * ONE_MINUTE_IN_MILLIS);

				if (pocetak.after(pregled.getDatum()) && pocetak.before(porednjenje)
						|| pocetak.equals(pregled.getDatum()) || pocetak.equals(porednjenje)) {
					// pocetak = new Date(krajPregleda.getTime()+15*ONE_MINUTE_IN_MILLIS);
					// continue;
					uslov = false;
				}

			}

			for (Operacija operacija : l.getOperacije()) {
				porednjenje.setTime(operacija.getDatum().getTime() + operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS);

				if (pocetak.after(operacija.getDatum()) && pocetak.before(porednjenje)
						|| pocetak.equals(operacija.getDatum()) || pocetak.equals(porednjenje)) {
					uslov = false;
				}
			}

			for (ZahtevZaGodisnjiOdmor zahtev : l.getZahteviZaGodisnji()) {

				if (zahtev.getStanjeZahteva().equals(StanjeZahteva.PRIHVACEN)) {
					if (pocetak.after(zahtev.getPocetniDatum()) && pocetak.before(zahtev.getKrajnjiDatum())
							|| zahtev.getKrajnjiDatum().equals(pocetak) || zahtev.getPocetniDatum().equals(pocetak)) {
						uslov = false;
					}
				}
			}

			if (uslov) {
				return false;
			} else {
				pocetak.setTime(pocetak.getTime() + 15 * ONE_MINUTE_IN_MILLIS);
			}

		}
		return true;
	}
}
