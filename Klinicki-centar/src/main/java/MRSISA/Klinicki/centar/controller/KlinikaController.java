package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.service.KlinikaService;

@RestController
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
//	@GetMapping
//	public ModelAndView getKlinike() {
//		ArrayList<Klinika>klinike = klinikaService.getKlinike();
//		return new ModelAndView("klinika", "klinika", klinike);
//	}

	@PostMapping("/klinika")
	public ResponseEntity    addKlinika(@RequestBody Klinika klinika) {
		System.out.println(klinika.getNaziv());
		klinikaService.addKlinika(klinika);
		return new ResponseEntity(HttpStatus.OK);
	}

}
