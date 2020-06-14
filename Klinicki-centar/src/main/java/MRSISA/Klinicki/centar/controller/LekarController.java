package MRSISA.Klinicki.centar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.domain.ZahtevZaGodisnjiOdmor;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.OperacijaDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.dto.PretragaKlinikaDTO;
import MRSISA.Klinicki.centar.dto.PrvoLogovanjeDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.ConfirmationTokenService;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;
import MRSISA.Klinicki.centar.service.PregledService;
import MRSISA.Klinicki.centar.service.TipPregledaService;

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

	@Autowired
	private PregledService pregledService;

	@Autowired
	private TipPregledaService tipPregledaService;

	@GetMapping("/lekar/all")
	public ResponseEntity<List<LekarDTO>> getAllLekari() {
		List<Lekar> lekari = lekarService.findAll();
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		for (Lekar l : lekari) {
			lekariDTO.add(new LekarDTO(l));
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	// Funkcija vraca sve lekara slobodne za izabranu operaciju
	@PostMapping("/lekar/allOperacija")
	public ResponseEntity<Object> getAll(@RequestBody OperacijaDTO operacijaDTo) {
		List<Lekar> lekari = lekarService.findAll();
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		final long ONE_MINUTE_IN_MILLIS = 60000;
		boolean uslov = true;

		for (Lekar l : lekari) {
			System.out.println(l.getId());
			uslov = true;
			// Proverava da li u zadatom terminu lekar ima pregled
			for (Pregled pregled : l.getPregledi()) {

				Date zahtevDatum = null;
				try {
					zahtevDatum = sdf.parse(operacijaDTo.getDatumivreme());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date kraj = new Date(
						pregled.getDatum().getTime() + (pregled.getTipPregleda().getTrajanje() * ONE_MINUTE_IN_MILLIS));

				// Proverava da li lekar data ime neki pregled
				if (zahtevDatum.after(pregled.getDatum()) && zahtevDatum.before(kraj)
						|| zahtevDatum.equals(pregled.getDatum()) || zahtevDatum.equals(kraj)) {
					uslov = false;
				}
			}

			// Proverava da li u zadatom terminu lekar ima operaciju
			for (Operacija operacija : l.getOperacije()) {
				System.out.println(operacija.getId());
				Date zahtevDatum = null;
				Date operDatum = null;
				try {
					zahtevDatum = sdf.parse(operacijaDTo.getDatumivreme());
					operDatum = sdf.parse(operacija.getDatum().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date kraj = new Date(operacija.getDatum().getTime() + (operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS));
				System.out.println(kraj);
				System.out.println(zahtevDatum);
				System.out.println(operDatum);
				if (zahtevDatum.after(operDatum) && zahtevDatum.before(kraj) || zahtevDatum.equals(operDatum)
						|| zahtevDatum.equals(kraj)) {
					uslov = false;
				}
			}

			for (ZahtevZaGodisnjiOdmor zahtev : l.getZahteviZaGodisnji()) {
				Date zahtevDatum = null;
				try {
					zahtevDatum = sdf.parse(operacijaDTo.getDatumivreme());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (zahtev.getStanjeZahteva().equals(StanjeZahteva.PRIHVACEN)) {
					if (zahtevDatum.after(zahtev.getPocetniDatum()) && zahtevDatum.before(zahtev.getKrajnjiDatum())
							|| zahtev.getKrajnjiDatum().equals(zahtevDatum)
							|| zahtev.getPocetniDatum().equals(zahtevDatum)) {
						uslov = false;
					}
				}

			}
			if (uslov) {
				lekariDTO.add(new LekarDTO(l));
			}
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	// Pretraga lekara uz proveru da li je slobodan za termin operacije
	@PostMapping("/lekar/searchOperacija/{pretraga}")
	public ResponseEntity<Object> search(@PathVariable String pretraga, @RequestBody OperacijaDTO operacijaDTo) {
		List<LekarDTO> retVal = new ArrayList<LekarDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		final long ONE_MINUTE_IN_MILLIS = 60000;
		boolean uslov = true;
		for (Lekar l : lekarService.findAll()) {
			uslov = true;
			if (l.getIme().toLowerCase().contains(pretraga.toLowerCase())
					|| l.getPrezime().toLowerCase().contains(pretraga.toLowerCase())
					|| l.getEmail().toLowerCase().contains(pretraga.toLowerCase())) {

				for (Pregled pregled : l.getPregledi()) {

					Date zahtevDatum = null;
					try {
						zahtevDatum = sdf.parse(operacijaDTo.getDatumivreme());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date kraj = new Date(pregled.getDatum().getTime()
							+ (pregled.getTipPregleda().getTrajanje() * ONE_MINUTE_IN_MILLIS));
					System.out.println(pregled.getTipPregleda().getTrajanje());
					System.out.println(kraj);

					// Proverava da li lekar data ime neki pregled
					if (zahtevDatum.after(pregled.getDatum()) && zahtevDatum.before(kraj)
							|| zahtevDatum.equals(pregled.getDatum()) || zahtevDatum.equals(kraj)) {
						uslov = false;
					}
				}
				// Proverava da li u zadatom terminu lekar ima operaciju
				for (Operacija operacija : l.getOperacije()) {
					System.out.println(operacija.getId());
					Date zahtevDatum = null;
					Date operDatum = null;
					try {
						zahtevDatum = sdf.parse(operacijaDTo.getDatumivreme());
						operDatum = sdf.parse(operacija.getDatum().toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date kraj = new Date(
							operacija.getDatum().getTime() + (operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					System.out.println(kraj);
					System.out.println(zahtevDatum);
					System.out.println(operDatum);
					if (zahtevDatum.after(operDatum) && zahtevDatum.before(kraj) || zahtevDatum.equals(operDatum)
							|| zahtevDatum.equals(kraj)) {
						uslov = false;
					}
				}
				for (ZahtevZaGodisnjiOdmor zahtev : l.getZahteviZaGodisnji()) {
					Date zahtevDatum = null;
					try {
						zahtevDatum = sdf.parse(operacijaDTo.getDatumivreme());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (zahtev.getStanjeZahteva().equals(StanjeZahteva.PRIHVACEN)) {
						if (zahtevDatum.after(zahtev.getPocetniDatum()) && zahtevDatum.before(zahtev.getKrajnjiDatum())
								|| zahtev.getKrajnjiDatum().equals(zahtevDatum)
								|| zahtev.getPocetniDatum().equals(zahtevDatum)) {
							uslov = false;
						}
					}

				}
				if (uslov) {

					LekarDTO lekar = new LekarDTO(l);
					retVal.add(lekar);
				}
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);

	}
	/*
	 * Funckija koja vraca odredjeni broj lekara
	 */

	@GetMapping("/lekar/page")
	public ResponseEntity<List<LekarDTO>> getLekarPage() {
		Pageable prvihDeset = PageRequest.of(0, 10);
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
		int klinika = admink.getKlinikaID();
		Page<Lekar> lekari = lekarService.findAll(prvihDeset);
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		for (Lekar l : lekari) {
			if (l.getKlinika().getId().equals(klinika)) {
				lekariDTO.add(new LekarDTO(l));
			}
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	
	/*
	 * Funckija za dodavanje lekara
	 */
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
		lekar.setPrezime(
				lekarDTO.getPrezime().substring(0, 1).toUpperCase() + lekarDTO.getPrezime().substring(1).toLowerCase());
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
				+ "http://mrsisa2020-t19.herokuapp.com/klinicki-centar/aktivacija.html?token="
				+ confirmationToken.getConfirmationToken());

		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}
	
	/*
	 * Funkcija koja postavlja sifru pri prvoj prijavi na sistem 
	 */
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

	
	/*
	 * Funkcija za pretragu lekara
	 */
	@PostMapping("/lekar/search")
	public ResponseEntity<List<LekarDTO>> searchLekar(@RequestBody String pretraga) {
		List<LekarDTO> retVal = new ArrayList<LekarDTO>();


		for (Lekar l : lekarService.findAll()) {
			if (l.getIme().toLowerCase().contains(pretraga.toLowerCase())
					|| l.getPrezime().toLowerCase().contains(pretraga.toLowerCase())
					|| l.getEmail().toLowerCase().contains(pretraga.toLowerCase())) {
				LekarDTO lekar = new LekarDTO(l);
				retVal.add(lekar);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);

	}
	
	/*
	 * Funkcija za brisanje lekara
	 */
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
	
	/*
	 * Funkcija koja dobavlja jednog lekara
	 */
	@GetMapping("/lekar/getOneLekar/{id}")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable Integer id) {
		Lekar lekar = lekarService.findOne(id);
		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
		}
	}

	/*
	 * Funkcija za izmenu lekara
	 */
	@PutMapping("/lekar/update")
	public ResponseEntity<LekarDTO> updateLekara(@RequestBody LekarDTO lekarDTO) {

//		System.out.println(lekarDTO);
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

		lekar.setIme(lekarDTO.getIme().substring(0, 1).toUpperCase() + lekarDTO.getIme().substring(1).toLowerCase());
		lekar.setPrezime(
				lekarDTO.getPrezime().substring(0, 1).toUpperCase() + lekarDTO.getPrezime().substring(1).toLowerCase());
		lekar.setLozinka(lekarDTO.getLozinka());

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
			if (!dodavanje) {
				if (l.getEmail().equals(osoba.getEmail()) && !l.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
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
			if (!dodavanje) {
				if (l.getJmbg().equals(osoba.getJmbg()) && !l.getId().equals(osoba.getId())) {
					return false;
				}
			} else {
				if (l.getJmbg().equals(osoba.getJmbg())) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Funkcija koja vracalistu lekara za prikaz pacijentu
	 */
	@GetMapping("/lekar/pageForPacijent/{stranica}/{koliko}/{klinika}")
	public ResponseEntity<List<LekarDTO>> getLekariOdDo(@PathVariable Integer stranica, @PathVariable Integer koliko,
			@PathVariable Integer klinika) {
		Pageable prvihDeset = PageRequest.of(stranica, koliko);
		// , Sort.by("stanjePacijenta").descending().and(Sort.by("id")));
		Page<Lekar> lekari = null;
		try {
			lekari = lekarService.findAll(prvihDeset);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		List<LekarDTO> lekariDTO = new ArrayList<>();

		for (Lekar l : lekari) {
			if (l.getKlinika().getId().equals(klinika)) {
				lekariDTO.add(new LekarDTO(l));
			}
		}

		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);

	}

	
	/*
	 * Funkcija za pretragu lekara 
	 */
	@PostMapping("/lekar/searchPacijentoviParametriDva/{klinika}")
	public ResponseEntity<Set<LekarDTO>> searchPacijentoviParametri2(@PathVariable int klinika, @RequestBody PretragaKlinikaDTO pretraga) {
		Set<LekarDTO> retVal = new HashSet<>();
		TipPregleda tip = tipPregledaService.findOne(pretraga.tip);
		List<Pregled> pregledi = pregledService.findAll();
		for (Lekar l : lekarService.findAll()) {
			if (l.getProsecnaOcena() < pretraga.ocena || !l.getKlinika().getId().equals(klinika)) {
				continue;
			}

			if (l.getTipoviPregleda().contains(tip)) {
				
				for (Pregled p : pregledi) {
					if ((p.getLekar().getId().equals(l.getId()))) {
						if (!proveriZauzetost(p.getDatum(), pretraga.datum)) {
							LekarDTO dto = new LekarDTO(l);
							if (!retVal.contains(dto)) {
								retVal.add(dto);
							}
						}
					} else {
						LekarDTO dto = new LekarDTO(l);
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
	 * Funkcija koja vraca sve lekare odredjene klinike
	 */
	@PostMapping("/lekar/all/{klinika}")
	public ResponseEntity<Set<LekarDTO>> getAllLekariInKlinika(@PathVariable Integer klinika) {
		List<Lekar> lekari = lekarService.findAll();
		Set<LekarDTO> lekariDTO = new HashSet<LekarDTO>();
		List<Pregled> pregledi = pregledService.findAll();
		for (Lekar l : lekari) {
			if (l.getKlinika().getId().equals(klinika)) {
				lekariDTO.add(new LekarDTO(l));
			}
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	private boolean proveriZauzetost(Date pregled, Date pretraga) {
		if (pregled.getYear() == pretraga.getYear()) {
			if (pregled.getMonth() == pretraga.getMonth()) {
				if (pregled.getDay() == pretraga.getDay()) {
					return true;
				}
			}
		}
		return false;
	}

}