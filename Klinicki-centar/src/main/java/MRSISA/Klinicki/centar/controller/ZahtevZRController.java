package MRSISA.Klinicki.centar.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.ConfirmationToken;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.EmailDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaRegDTO;
import MRSISA.Klinicki.centar.service.ConfirmationTokenService;
import MRSISA.Klinicki.centar.service.PacijentService;
import MRSISA.Klinicki.centar.service.ZahtevZRService;

@RestController
public class ZahtevZRController {

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private ConfirmationTokenService tokenService;

	@Autowired
	private ZahtevZRService zzrService;

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	/*
	 * Funkcija za dobavljanje zahteva za registraciju 
	 */
	@GetMapping("/KC/ZZR/getOne/{id}")
	public ResponseEntity<ZahtevZaRegDTO> getOne(@PathVariable Integer id) {
		ZahtevZaRegistraciju zahtev = zzrService.findOne(id);

		if (zahtev != null) {
			return new ResponseEntity<>(new ZahtevZaRegDTO(zahtev), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	/*
	 * Funkcija koja prihvata zahtev za registraciju
	 */
	@PutMapping("/KC/ZZR/Prihvati/{id}")
	public ResponseEntity<Object> prihvatiZZR(@PathVariable Integer id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("prihvatiZZR");
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			ZahtevZaRegistraciju zahtev = zzrService.findOne(id);
			
			if(zahtev == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else {
				System.out.println(zahtev.getStanje());
				if(zahtev.getStanje().equals(StanjeZahteva.ODBIJEN)) {
					return new ResponseEntity<>("Zahtev je već odbijen",HttpStatus.BAD_REQUEST);
				}
				zahtev.setStanje(StanjeZahteva.PRIHVACEN);
				zahtev.getPacijent().setStanjePacijenta(StanjePacijenta.PRIHVACEN);
				zahtev = zzrService.save(zahtev);

				ConfirmationToken confirmationToken = new ConfirmationToken(zahtev.getPacijent().getJmbg());
				confirmationToken = tokenService.save(confirmationToken);
				SimpleMailMessage msg = new SimpleMailMessage();

				msg.setTo(zahtev.getPacijent().getEmail());

				msg.setSubject("Uspešna registracija na klinički centar!");
				msg.setText("Da bi ste potvrdili nalog, kliknite na link: "
						+ "http://localhost:8080/klinicki-centar/confirmation.html?token="
						+ confirmationToken.getConfirmationToken());
				// https://mrsisa2020-t19.herokuapp.com
				// http://localhost:8080
				try {
					javaMailSender.send(msg);
				} catch (MailException e) {
					// e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				em.flush();
				em.getTransaction().commit();
				em.close();
				return new ResponseEntity<>(new ZahtevZaRegDTO(zahtev), HttpStatus.OK);
			} 

		} catch (OptimisticLockException e) {
			System.out.println("Greška pri prihvatanju registracije!");
			return new ResponseEntity<>("Zahtev je vec obrađen, od strane nekog drugog", HttpStatus.BAD_REQUEST);
		}

	}
	
	
	/*
	 * Funkcija koja proverava token pri prvoj registraciji 
	 */
	@GetMapping("/ZZR/potvrda-registracije/{token}")
	public ResponseEntity<String> potvrdaRegistracije(@PathVariable String token) {
		ConfirmationToken confToken = tokenService.finByToken(token);

		if (confToken != null) {
			Pacijent pacijent = pacijentService.findByjmbg(confToken.getJMBG());
			System.out.println(pacijent);
			pacijent.setStanjePacijenta(StanjePacijenta.AKTIVAN);
			pacijent = pacijentService.save(pacijent);
			return new ResponseEntity<String>("Uspešna aktivacija naloga!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Link nije važeći", HttpStatus.NOT_FOUND);
		}
	}

	
	
	/* 
	 * Funkcija za odbijanje zahteva za registraciju
	 */
	@PutMapping("/KC/ZZR/Odbij/{id}")
	public ResponseEntity<Object> odbijZZR(@PathVariable Integer id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("odbijZZR");

		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			ZahtevZaRegistraciju zahtev = zzrService.findOne(id);
			if(zahtev == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else {
				System.out.println(zahtev.getStanje());
				if(zahtev.getStanje().equals(StanjeZahteva.PRIHVACEN)) {
					return new ResponseEntity<>("Zahtev je već prihvaćen",HttpStatus.BAD_REQUEST);
				}
				zahtev.setStanje(StanjeZahteva.ODBIJEN);
				zahtev.getPacijent().setStanjePacijenta(StanjePacijenta.ODBIJEN);
				zahtev = zzrService.save(zahtev);

				em.flush();
				em.getTransaction().commit();
				em.close();

				return new ResponseEntity<>(new ZahtevZaRegDTO(zahtev), HttpStatus.OK);
			} 

		} catch (OptimisticLockException e) {
			System.out.println("Greška pri odbijanju registracije!");
			return new ResponseEntity<>("Zahtev je vec obrađen, od strane nekog drugog", HttpStatus.BAD_REQUEST);
		}

	}

	
	/*
	 * Funkcija koja salje email 
	 */
	@PostMapping("/ZZR/sendEmail")
	public ResponseEntity<EmailDTO> sendEmail(@RequestBody EmailDTO emailDTO) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(emailDTO.getEmail());

		msg.setSubject(emailDTO.getSubject());
		msg.setText(emailDTO.getMessage());

		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new EmailDTO(), HttpStatus.OK);

	}
}
