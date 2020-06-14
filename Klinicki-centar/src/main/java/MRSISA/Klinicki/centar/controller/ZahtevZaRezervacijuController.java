package MRSISA.Klinicki.centar.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;
import MRSISA.Klinicki.centar.domain.ZahtevZaPregled;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaOpDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaPregledDTO;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.PregledService;
import MRSISA.Klinicki.centar.service.SalaService;
import MRSISA.Klinicki.centar.service.ZahtevZPService;

@RestController
public class ZahtevZaRezervacijuController {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ZahtevZPService zzpService;
	
	@Autowired
	private SalaService SalaService;

	@Autowired
	private LekarService LekarService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	HttpServletRequest request;
	
	@GetMapping("/ZZP/getAll")
	public ResponseEntity<Object> getall() {
		List<ZahtevZaPregled> zahtevi = zzpService.findAll();
		List<ZahtevZaPregledDTO> retVal = new ArrayList<ZahtevZaPregledDTO>();
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
		int klinika = admink.getKlinikaID();
		System.out.println("Usao");
		for (ZahtevZaPregled zahtev : zahtevi) {
			System.out.println(zahtev);
			int idk = zahtev.getPregled().getLekar().getKlinika().getId();
			if (zahtev.getStanjeZahteva().equals(StanjeZahteva.NA_CEKANJU) && idk == klinika) {
				System.out.println(zahtev.getStanje());
				retVal.add(new ZahtevZaPregledDTO(zahtev));
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@Scheduled(cron = "0 59 23 * * *") // 0 59 23
	public void autoDodelaTermina() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<ZahtevZaPregled> zahtevi = zzpService.findAll();
		Klinika k = null;
		Sala s = null;
		Lekar l = null;
		Date datum = new Date();
		int counter = 0;
		for (ZahtevZaPregled zahtev : zahtevi) {
			counter = 0;
			if (zahtev.getStanjeZahteva().equals(StanjeZahteva.NA_CEKANJU)) {
				k = zahtev.getPregled().getLekar().getKlinika();
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
				Pregled p = pregledService.findOne(zahtev.getPregled().getId());
				p.setSala(s);
				Sala ss = SalaService.findOne(s.getId());
				ss.getPregledi().add(zahtev.getPregled());
				Date date = new Date(datum.getTime());			
				p.setDatum(date);
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
				p.setLekar(lek);
				lek.getPregledi().add(zahtev.getPregled());
				zahtev.setPregled(p);
				
				ss = SalaService.save(ss);
				lek = LekarService.save(lek);
				p = pregledService.save(p);
				zahtev = zzpService.save(zahtev);
				
				
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(zahtev.getPregled().getPacijent().getEmail());
				msg.setSubject("Pregled je uspešno zakazan");
				msg.setText(String.format("Poštovani, pregled je zakazan, dana: %s u %S, u sali: %s, na klinici: %s.",
						sdf.format(zahtev.getPregled().getDatum()).split(" ")[0],
						sdf.format(zahtev.getPregled().getDatum()).split(" ")[1],
						s.getNaziv(),
						k.getNaziv()));

				try {
					javaMailSender.send(msg);
				} catch (MailException e) {
					e.printStackTrace();
					System.out.println("Automatsko dodeljivanje termina pregleda pri slanju email-a pacijentu");
				}
				
				SimpleMailMessage msg1 = new SimpleMailMessage();
				msg1.setTo(lek.getEmail());
				msg1.setSubject("Novi pregled");
				msg1.setText(String.format("Poštovani obavezni ste da prisustvujete pregledu, dana: %s, u sali: %s",
						zahtev.getPregled().getDatum().toString(), zahtev.getPregled().getSala().getNaziv()));

				try {
					javaMailSender.send(msg1);
				} catch (MailException e) {
					 e.printStackTrace();
					System.out.println("Automatsko dodeljivanje termina pregleda pri slanju email-a lekaru");
				}
				
				System.out.println(l.getEmail());

				System.out.println(s.getNaziv());
				System.out.println(datum);
			}

		}

	}


}
