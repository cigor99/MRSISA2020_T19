package MRSISA.Klinicki.centar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Pacijent;

import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.TipKorisnika;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.domain.ZahtevZaGodisnjiOdmor;
import MRSISA.Klinicki.centar.domain.ZahtevZaPregled;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.dto.PregledDTO;
import MRSISA.Klinicki.centar.dto.SalaDTO;
import MRSISA.Klinicki.centar.dto.SlanjeZahtevaZaPregledDTO;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.PacijentService;
import MRSISA.Klinicki.centar.service.PregledService;
import MRSISA.Klinicki.centar.service.SalaService;
import MRSISA.Klinicki.centar.service.TipPregledaService;
import MRSISA.Klinicki.centar.service.ZahtevZPService;

@RestController
@RequestMapping("/pregled")
public class PregledController {

	@Autowired
	private PregledService pregledService;

	@Autowired
	private SalaService salaService;

	@Autowired
	private TipPregledaService tipPregledaService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ZahtevZPService zzpService;

	@Autowired
	private PacijentService pacijentService;

	@GetMapping("/all")
	public ResponseEntity<List<PregledDTO>> getAllPregledi() {
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();

		int klinika = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if (tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			klinika = admink.getKlinikaID();
			for (Pregled p : pregledi) {
				if (p.getSala() != null) {
					if (p.getSala().getKlinika().getId().equals(klinika) && (p.getPacijent() == null)) {
						preglediDTO.add(new PregledDTO(p));
					}
				}

			}
		} else if (tip.equals("lekar")) {
			LekarDTO lekarDTO = (LekarDTO) request.getSession().getAttribute("current");
			Lekar lekar = lekarService.findOne(lekarDTO.getId());
			// klinika = lekar.getKlinika().getId();
			for (Pregled p : lekar.getPregledi()) {
				if (p.getSala() != null && p.getIzvestajiPregleda().size() == 0) {
					preglediDTO.add(new PregledDTO(p));
				}
			}
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping("/page")
	public ResponseEntity<List<PregledDTO>> getPregledPage() {
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Pregled> pregledi = pregledService.findAll(prvihDeset);
		List<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		int klinika = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if (tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			klinika = admink.getKlinikaID();
			for (Pregled p : pregledi) {
				if (p.getSala() != null) {
					if (p.getSala().getKlinika().getId().equals(klinika) && (p.getPacijent() == null)) {
						preglediDTO.add(new PregledDTO(p));
					}
				}
			}
		} else if (tip.equals("lekar")) {
			LekarDTO lekarDTO = (LekarDTO) request.getSession().getAttribute("current");
			Lekar lekar = lekarService.findOne(lekarDTO.getId());
			// klinika = lekar.getKlinika().getId();
			for (Pregled p : lekar.getPregledi()) {
				if (p.getSala() != null && p.getIzvestajiPregleda().size() == 0) {
					preglediDTO.add(new PregledDTO(p));
				}
			}
		}

		/*
		 * for(Pregled p : pregledi) { preglediDTO.add(new PregledDTO(p)); }
		 */
		for (PregledDTO p : preglediDTO) {
			System.out.println(p.getId());
			System.out.println(p.getDatum());
			System.out.println(p.getVreme());
			System.out.println(p.getLekar());
			System.out.println(p.getPacijent());
			System.out.println(p.getSala());
			System.out.println(p.getPopust());
			System.out.println(p.getTipPregleda());
			System.out.println(p.getCena());
			System.out.println(p.getTrajanje());
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
		// return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<PregledDTO> addPregled(@RequestBody PregledDTO pregledDTO) {
		Pregled pregled = new Pregled();
		int salaId = Integer.parseInt(pregledDTO.getSala());
		int tipId = Integer.parseInt(pregledDTO.getTipPregleda());
		int lekarId = Integer.parseInt(pregledDTO.getLekar());
		Sala sala = salaService.findOne(salaId);
		TipPregleda tipPregleda = tipPregledaService.findOne(tipId);
		Lekar lekar = lekarService.findOne(lekarId);

		pregled.setSala(sala);
		pregled.setTipPregleda(tipPregleda);
		pregled.setLekar(lekar);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String datumvreme = pregledDTO.getDatumivreme().replace("T", " ");
		Date date;
		try {
			date = sdf.parse(datumvreme);
			System.out.println(date);
			pregled.setDatum(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		pregled = pregledService.addPregled(pregled);

		System.out.println("ADD PREGLED");
		System.out.println(pregledDTO.getDatumivreme());
		System.out.println(pregledDTO.getSala());
		System.out.println(pregledDTO.getTipPregleda());
		System.out.println(pregledDTO.getLekar());
		System.out.println(pregledDTO.getCena());

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePregled(@PathVariable Integer id){
		Pregled p = pregledService.findOne(id);
		if(p != null) {
			pregledService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	Funkcija vraća listu pregleda za određeni dan i mesec
	@GetMapping("/getDnevniPregled/{dan}/{mesec}")
	public ResponseEntity<Object> getDnevniPregled(@PathVariable String dan, @PathVariable String mesec) {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> retVal = new ArrayList<PregledDTO>();
		for (Pregled p : pregledi) {
			if (((p.getDatum().getMonth() + 1) + "-" + p.getDatum().getDate()).equals(mesec + "-" + dan)) {
				retVal.add(new PregledDTO(p));
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

//	Funkcija vraća listu pregleda za određeni mesec
	@GetMapping("/getDnevniPregled/{mesec}")
	public ResponseEntity<Object> getDnevniPregled(@PathVariable String mesec) {
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> retVal = new ArrayList<PregledDTO>();
		for (Pregled p : pregledi) {
			if ((p.getDatum().getMonth() + 1 + "").equals(mesec + "")) {
				retVal.add(new PregledDTO(p));
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@PostMapping("/brzoZakazivanje/{idPregleda}")
	public ResponseEntity<PregledDTO> brzoZakazivanje(@PathVariable Integer idPregleda) {
		List<Pregled> pregledi = pregledService.findAll();
		for (Pregled p : pregledi) {
			if (p.getId().equals(idPregleda)) {
				if (p.isSlobodan()) {
					p.setSlobodan(false);
					ZahtevZaPregled zzp = new ZahtevZaPregled(1, StanjeZahteva.NA_CEKANJU, p, new Date());
					PacijentDTO pacijentDTO = (PacijentDTO) request.getSession().getAttribute("current");
					Pacijent pacijent = pacijentService.findOne(pacijentDTO.getId());
					if (pacijent == null) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
					p.setPacijent(pacijent);
					Klinika klinika = p.getLekar().getKlinika();
					Set<AdministratorKlinike> admini = klinika.getAdministratori();
					SimpleMailMessage msg = new SimpleMailMessage();
					for (AdministratorKlinike admin : admini) {
						msg.setTo(admin.getEmail());
						msg.setSubject("Novi zahtev za pregled");
						msg.setText(zzp.toString());
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
					zzpService.save(zzp);
					// pregledService.save(p);
					return new ResponseEntity<>(new PregledDTO(), HttpStatus.CREATED);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/definisaniPregledi/{idKlinike}")
	public ResponseEntity<Object> definisaniPregledi(@PathVariable Integer idKlinike) {
		List<Pregled> pregledi = pregledService.findAll();
		Set<PregledDTO> slobodni = new HashSet<PregledDTO>();
		Date danas = new Date();
		for (Pregled p : pregledi) {
			if (p.isSlobodan()) {
				if (p.getLekar().getKlinika().getId().equals(idKlinike)) {
					if (p.getDatum().before(danas))
						continue;
					slobodni.add(new PregledDTO(p));
				}
			}
		}
		return new ResponseEntity<>(slobodni, HttpStatus.OK);

	}

	@PostMapping("/zakaziSvoj")
	public ResponseEntity<Object> zakaziSvoj(@RequestBody SlanjeZahtevaZaPregledDTO zahtev) {
		Lekar lekar = lekarService.findOne(zahtev.getLekarID());
		TipPregleda tipPregleda = tipPregledaService.findOne(zahtev.getTipID());
		PacijentDTO pacijentDTO = (PacijentDTO) request.getSession().getAttribute("current");
		Pacijent pacijent = pacijentService.findOne(pacijentDTO.getId());
		if (pacijent == null || lekar == null || tipPregleda == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String[] tok = zahtev.getVreme().split(":");
		int minuti = Integer.parseInt(tok[1]);
		long sati = Integer.parseInt(tok[0]);
		long time = sati * 60 + minuti;
		time = time * 60000;
		Date datum = new Date(zahtev.getDatum().getTime() + time);
		Pregled pregled = new Pregled(0, datum, null, lekar, tipPregleda, pacijent, 0, false, null, null, null);
		System.out.println("ordinal: " + StanjeZahteva.NA_CEKANJU.ordinal());
		ZahtevZaPregled zzp = new ZahtevZaPregled(0, StanjeZahteva.NA_CEKANJU, pregled, new Date());
		System.out.println(zzp);
		Set<AdministratorKlinike> admini = lekar.getKlinika().getAdministratori();
		SimpleMailMessage msg = new SimpleMailMessage();
		for (AdministratorKlinike admin : admini) {
			msg.setTo(admin.getEmail());
			msg.setSubject("Novi zahtev za pregled");
			msg.setText(zzp.toString());
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
		zzpService.save(zzp);
		return new ResponseEntity<>(new PregledDTO(), HttpStatus.CREATED);
	}

	@PostMapping("/posaljiZahtev/{idPacijenta}/{datum}/{pregledID}")
	public ResponseEntity<PregledDTO> zakazivanjePregleda(@PathVariable Integer idPacijenta, @PathVariable String datum,
			@PathVariable Integer pregledID) {
		ZahtevZaPregled zzp = new ZahtevZaPregled();
		zzp.setStanje(StanjeZahteva.NA_CEKANJU);
		zzp.setDatumSlanja(new Date(System.currentTimeMillis()));
		Pregled pregled = new Pregled();
		String stringdate = datum.replace("T", " ");
		// System.out.println(stringdate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = sdf.parse(stringdate);
			pregled.setDatum(date);
			Pacijent pac = pacijentService.findOne(idPacijenta);
			pregled.setPacijent(pac);
			LekarDTO lekarDTO = (LekarDTO) request.getSession().getAttribute("current");
			Lekar lekar = lekarService.findOne(lekarDTO.getId());
			pregled.setLekar(lekar);
			Pregled trenutni = pregledService.findOne(pregledID);
			TipPregleda tp = trenutni.getTipPregleda();
			pregled.setTipPregleda(tp);
			pregled.setSlobodan(false);
			pregled.setPopust(0);
			pregledService.addPregled(pregled);
			zzp.setPregled(pregled);
			// System.out.println(zzp.getId());
			zzpService.save(zzp);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/slobodniTermini/{idLekara}/{datum}")
	public ResponseEntity<Object> slobodniTermini(@PathVariable Integer idLekara, @PathVariable String datum) {

		Lekar lekar = lekarService.findOne(idLekara);
		Klinika klinika = lekar.getKlinika();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date pocetak = null;
		Date kraj = new Date();
		try {
			pocetak = sdf.parse(datum);
			kraj.setTime(pocetak.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final long ONE_MINUTE_IN_MILLIS = 60000;
		String pocetakString = klinika.getPocetakRadnogVremena();
		String[] pocetakTokens = pocetakString.split(":");
		int pocetakSat = Integer.parseInt(pocetakTokens[0]);
		int pocetakMinut = Integer.parseInt(pocetakTokens[1]);
		pocetakMinut += pocetakSat * 60;
		pocetak.setTime(pocetak.getTime() + pocetakMinut * ONE_MINUTE_IN_MILLIS);

		List<Date> slobodni = new ArrayList<Date>();

		String krajString = klinika.getKrajRadnogVremena();
		String[] krajTokens = krajString.split(":");
		int krajSat = Integer.parseInt(krajTokens[0]);
		int krajMinut = Integer.parseInt(krajTokens[1]);
		krajMinut += krajSat * 60;
		kraj.setTime(kraj.getTime() + krajMinut * ONE_MINUTE_IN_MILLIS);

		Date porednjenje = new Date();
		while (pocetak.before(kraj)) {
			boolean uslov = true;
			for (Pregled pregled : lekar.getPregledi()) {
				porednjenje.setTime(
						pregled.getDatum().getTime() + pregled.getTipPregleda().getTrajanje() * ONE_MINUTE_IN_MILLIS);

				if (pocetak.after(pregled.getDatum()) && pocetak.before(porednjenje)
						|| pocetak.equals(pregled.getDatum()) || pocetak.equals(porednjenje)) {
					// pocetak = new Date(krajPregleda.getTime()+15*ONE_MINUTE_IN_MILLIS);
					// continue;
					uslov = false;
				}

			}

			for (Operacija operacija : lekar.getOperacije()) {
				porednjenje.setTime(operacija.getDatum().getTime() + operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS);

				if (pocetak.after(operacija.getDatum()) && pocetak.before(porednjenje)
						|| pocetak.equals(operacija.getDatum()) || pocetak.equals(porednjenje)) {
					// pocetak = new Date(krajOperacije.getTime()+15*ONE_MINUTE_IN_MILLIS);
					// continue;
					uslov = false;
				}
			}

			for (ZahtevZaGodisnjiOdmor zahtev : lekar.getZahteviZaGodisnji()) {

				if (zahtev.getStanjeZahteva().equals(StanjeZahteva.PRIHVACEN)) {
					if (pocetak.after(zahtev.getPocetniDatum()) && pocetak.before(zahtev.getKrajnjiDatum())
							|| zahtev.getKrajnjiDatum().equals(pocetak) || zahtev.getPocetniDatum().equals(pocetak)) {
						// pocetak = new
						// Date(zahtev.getKrajnjiDatum().getTime()+15*ONE_MINUTE_IN_MILLIS);
						// continue;
						uslov = false;
					}
				}
			}

			if (uslov) {
				Date dodaj = new Date();
				dodaj.setTime(pocetak.getTime());
				slobodni.add(dodaj);
				pocetak.setTime(pocetak.getTime() + 15 * ONE_MINUTE_IN_MILLIS);
			} else {
				pocetak.setTime(pocetak.getTime() + 15 * ONE_MINUTE_IN_MILLIS);
			}

		}
		List<String> retVal = new ArrayList<String>();
		for (Date d : slobodni) {
			System.out.println(d);
			String string = "";
			if (d.getMinutes() == 0) {
				string += d.getHours() + ":" + d.getMinutes();
			} else {
				string += d.getHours() + ":" + d.getMinutes();
			}
			retVal.add(string);
		}
		return new ResponseEntity<Object>(retVal, HttpStatus.OK);
	}

}
