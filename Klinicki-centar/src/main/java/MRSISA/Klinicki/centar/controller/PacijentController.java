package MRSISA.Klinicki.centar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping("/dodaj")
	public Pacijent dodajPacijenta(@RequestBody Pacijent pacijent) {
		pacijentService.dodajPacijenta(pacijent);
		return pacijent;
	}
	
	@GetMapping("/preuzmiSve")
	public List<Pacijent> preuzmiPacijente(){
		return pacijentService.vratiPacijente();
	}
	
	@PostMapping(path = "/pronadjiPacijenta", consumes = "text/plain", produces = "application/json")
	public Pacijent pronadjiPacijenta(@RequestBody String email) {
		return pacijentService.pronadjiPacijenta(email);
	}
	
	@PostMapping(path="/izmeniPacijenta", consumes="application/json", produces="application/json")
	public Pacijent izmeniPacijenta(@RequestBody Pacijent pacijent) {
		pacijentService.izmeniPacijenta(pacijent);
		return pacijent;
	}
	
	
	
	
	
}
