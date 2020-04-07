package MRSISA.Klinicki.centar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
@RequestMapping("/pacijent")
public class PacijentController {
	private final PacijentService pacijentService;

	@Autowired
	public PacijentController(PacijentService pacijentService) {
		this.pacijentService = pacijentService;
	}
	
	@PostMapping
	public void dodajPacijenta(@RequestBody Pacijent pacijent) {
		pacijentService.dodajPacijenta(pacijent);
	}
	
	
}
