package MRSISA.Klinicki.centar.controller;

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

import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.EmailDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaRegDTO;
import MRSISA.Klinicki.centar.service.ZahtevZRService;

@RestController
public class ZahtevZRController {
	
	@Autowired
	private ZahtevZRService zzrService;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@GetMapping("/KC/ZZR/getOne/{id}")
	public ResponseEntity<ZahtevZaRegDTO> getOne(@PathVariable Integer id) {
		ZahtevZaRegistraciju zahtev = zzrService.findOne(id);

		if (zahtev != null) {
			return new ResponseEntity<>(new ZahtevZaRegDTO(zahtev), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/KC/ZZR/Prihvati/{id}")
	public ResponseEntity<ZahtevZaRegDTO> prihvatiZZR(@PathVariable Integer id){
		ZahtevZaRegistraciju zahtev = zzrService.findOne(id);
		
		if (zahtev != null) {
			zahtev.setStanje(StanjeZahteva.PRIHVACEN);
			zahtev.getPacijent().setStanjePacijenta(StanjePacijenta.PRIHVACEN);
			zahtev = zzrService.save(zahtev);
			return new ResponseEntity<>(new ZahtevZaRegDTO(zahtev), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/KC/ZZR/Odbij/{id}")
	public ResponseEntity<ZahtevZaRegDTO> odbijZZR(@PathVariable Integer id){
		ZahtevZaRegistraciju zahtev = zzrService.findOne(id);
		
		if (zahtev != null) {
			zahtev.setStanje(StanjeZahteva.ODBIJEN);
//			zahtev.getPacijent().setStanjePacijenta(StanjePacijenta.);
			zahtev = zzrService.save(zahtev);
			return new ResponseEntity<>(new ZahtevZaRegDTO(zahtev), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/ZZR/sendEmail")
	public ResponseEntity<EmailDTO> sendEmail(@RequestBody EmailDTO emailDTO){
		 SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(emailDTO.getEmail());

	        msg.setSubject(emailDTO.getSubject());
	        msg.setText(emailDTO.getMessage());

	        try {
	        	 javaMailSender.send(msg);
			} catch (MailException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	        
	        return new ResponseEntity<>(new EmailDTO(), HttpStatus.OK);
	       
	}
}
