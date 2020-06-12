package MRSISA.Klinicki.centar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaOpDTO;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.OperacijaService;
import MRSISA.Klinicki.centar.service.SalaService;
import MRSISA.Klinicki.centar.service.ZZOService;

@RestController
public class ZZOController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ZZOService zzoService;

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	private SalaService SalaService;

	@Autowired
	private LekarService LekarService;

	@GetMapping("/ZZO/getAll")
	public ResponseEntity<Object> getall() {
		List<ZahtevZaOperaciju> zahtevi = zzoService.findAll();
		List<ZahtevZaOpDTO> retVal = new ArrayList<ZahtevZaOpDTO>();
		for (ZahtevZaOperaciju zahtev : zahtevi) {
			if (zahtev.getStanjeZahteva().equals(StanjeZahteva.NA_CEKANJU)) {
				retVal.add(new ZahtevZaOpDTO(zahtev));
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@PostMapping("/ZZO/rezervisi")
	public ResponseEntity<Object> add(@RequestBody ZahtevZaOpDTO zahtevDTO) {
		System.out.println(zahtevDTO.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ZahtevZaOperaciju zahtev = zzoService.findById(zahtevDTO.getId());

		if (zahtev == null) {
			return new ResponseEntity<>("Zahtev nije pronadjen", HttpStatus.BAD_REQUEST);
		}

		Operacija operacija = operacijaService.findById(zahtevDTO.getOperacija());
		if (operacija == null) {
			return new ResponseEntity<>("Operacija nije pronadjena", HttpStatus.BAD_REQUEST);
		}

		Sala sala = SalaService.findOne(zahtevDTO.getSala());
		if (sala == null) {
			return new ResponseEntity<>("Sala nije pronadjena", HttpStatus.BAD_REQUEST);
		}

		String datumVreme = zahtevDTO.getDatumVreme();

		operacija.getZahteviZaOperaciju().add(zahtev);
		operacija.setSala(sala);
		try {
			operacija.setDatum(sdf.parse(datumVreme));
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Datum i vreme nisu ispravni", HttpStatus.BAD_REQUEST);
		}

		zahtev.setStanjeZahteva(StanjeZahteva.PRIHVACEN);
		zahtev.setOperacija(operacija);

		zahtev = zzoService.save(zahtev);
		operacija = operacijaService.save(operacija);
		sala.getOperacije().add(operacija);
		sala = SalaService.addSala(sala);

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(operacija.getPacijent().getEmail());
		msg.setSubject("Operacija je uspešno zakazana");
		msg.setText(String.format("Poštovani operacija je zakazana, dana: %s u %S, u sali: %s, na klinici: %s.",
				sdf.format(operacija.getDatum()).split(" ")[0], sdf.format(operacija.getDatum()).split(" ")[1],
				operacija.getSala().getNaziv(), operacija.getSala().getKlinika().getNaziv()));

		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ZahtevZaOpDTO(zahtev), HttpStatus.OK);
	}

	@PostMapping("/ZZO/AddLekar")
	public ResponseEntity<Object> addLekar(@RequestBody ZahtevZaOpDTO zahtevDTO) {
		Lekar lekar = LekarService.findOne(zahtevDTO.getId());
		if (lekar == null) {
			return new ResponseEntity<>("Lekar nije pronadjen", HttpStatus.BAD_REQUEST);
		}
		boolean uslov = false;
		System.out.println(zahtevDTO.toString());
		for (Sala sala : lekar.getKlinika().getSale()) {
			if (sala.getId() == zahtevDTO.getSala()) {
				uslov = true;
			}
		}
		if (!uslov) {
			return new ResponseEntity<>("Lekar i sala nisu sa iste klinike", HttpStatus.BAD_REQUEST);
		}
		Operacija operacija = operacijaService.findById(zahtevDTO.getOperacija());
		if (operacija == null) {
			return new ResponseEntity<>("Operacaija nije pronadjena", HttpStatus.BAD_REQUEST);
		}
		lekar.getOperacije().add(operacija);
		operacija.getLekari().add(lekar);

		lekar = LekarService.save(lekar);
		operacija = operacijaService.save(operacija);

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(lekar.getEmail());
		msg.setSubject("Nova operacija");
		msg.setText(String.format("Poštovani obavezni ste da prisustvujete operaciji, dana: %s, u sali: %s",
				operacija.getDatum().toString(), operacija.getSala().getNaziv()));

		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	@Scheduled(cron = "20 44 2 * * *") // 0 59 23
	public void autoDodelaTermina() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<ZahtevZaOperaciju> zahtevi = zzoService.findAll();
//		System.out.println(zahtevi);
		Klinika k = null;
		Sala s = null;
		Lekar l = null;
		Date datum = new Date();
		int counter = 0;
		for (ZahtevZaOperaciju zahtev : zahtevi) {

			counter = 0;
			if (zahtev.getStanjeZahteva().equals(StanjeZahteva.NA_CEKANJU)) {
				k = zahtev.getOperacija().getSala().getKlinika();
//				System.out.println(k.toString());
				for (Sala sala : k.getSale()) {
					if (counter == 0) {
						s = sala;
						datum = sala.getPrviSlobodanTermin();
					}
					if (sala.getPrviSlobodanTermin().before(datum)) {
						s = sala;
						datum = sala.getPrviSlobodanTermin();
					}
					counter++;
				}
				Operacija op = operacijaService.findById(zahtev.getOperacija().getId());
//				zahtev.getOperacija().setSala(s);
				op.setSala(s);
				Sala ss = SalaService.findOne(s.getId());
				ss.getOperacije().add(zahtev.getOperacija());
				Date date = new Date(datum.getTime());
			
//				zahtev.getOperacija().setDatum(date);
				op.setDatum(date);
				zahtev.setStanjeZahteva(StanjeZahteva.PRIHVACEN);
				
				Set<Lekar> lekari = ss.getKlinika().getLekari();

				int size = lekari.size();
				int i = 0;
				int broj = new Random().nextInt(size);
				for (Lekar lekar : lekari) {
					if (i == broj) {
						l = lekar;
					}
					i++;
				}
				Lekar lek = LekarService.findOne(l.getId());
//				zahtev.getOperacija().getLekari().add(lek);
				
				op.getLekari().add(lek);
				
//				System.out.println(lek.getOperacije().size());
				lek.getOperacije().add(zahtev.getOperacija());
//				System.out.println(lek.getOperacije().size());

				zahtev.setOperacija(op);
				zahtev = zzoService.save(zahtev);
				ss = SalaService.addSala(ss);
				lek = LekarService.save(lek);
				op = operacijaService.save(op);
				
				
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(zahtev.getOperacija().getPacijent().getEmail());
				msg.setSubject("Operacija je uspešno zakazana");
				msg.setText(String.format("Poštovani operacija je zakazana, dana: %s u %S, u sali: %s, na klinici: %s.",
						sdf.format(zahtev.getOperacija().getDatum()).split(" ")[0],
						sdf.format(zahtev.getOperacija().getDatum()).split(" ")[1],
						zahtev.getOperacija().getSala().getNaziv(),
						zahtev.getOperacija().getSala().getKlinika().getNaziv()));

				try {
					javaMailSender.send(msg);
				} catch (MailException e) {
					e.printStackTrace();
					System.out.println("Automatsko dodeljivanje termina operacije pri slanju email-a pacijentu");
				}
				
				SimpleMailMessage msg1 = new SimpleMailMessage();
				msg1.setTo(lek.getEmail());
				msg1.setSubject("Nova operacija");
				msg1.setText(String.format("Poštovani obavezni ste da prisustvujete operaciji, dana: %s, u sali: %s",
						zahtev.getOperacija().getDatum().toString(), zahtev.getOperacija().getSala().getNaziv()));

				try {
					javaMailSender.send(msg1);
				} catch (MailException e) {
					 e.printStackTrace();
					System.out.println("Automatsko dodeljivanje termina operacije pri slanju email-a lekaru");
				}
				
				System.out.println(l.getEmail());

				System.out.println(s.getNaziv());
				System.out.println(datum);
			}

		}

	}
}
