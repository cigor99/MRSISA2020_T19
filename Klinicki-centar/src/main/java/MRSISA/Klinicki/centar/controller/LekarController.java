package MRSISA.Klinicki.centar.controller;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.service.LekarService;


@RestController
public class LekarController {
	
	@Autowired
	private LekarService lekarService;

	
	/*@GetMapping("/dobaviKlinike")
	public ResponseEntity<List<Klinika>> dobaviKlinike() {
		ArrayList<Klinika> klinike = new ArrayList<Klinika>();
		Klinika k1 = new Klinika(1, "klinika 1", "adresa klinike 1", "opis", null, null, null, null);
		Klinika k2 = new Klinika(2, "klinika 2", "adresa klinike 2", "opis", null, null, null, null);
		klinike.add(k1);
		klinike.add(k2);
		
		return new ResponseEntity<List<Klinika>>(klinike, HttpStatus.OK);
	}*/
	

	
	@PostMapping("/dodajNovogLekara")
	public ResponseEntity dodajLekara(@RequestBody Lekar lekar) throws ClassNotFoundException, SQLException {
		System.out.println(lekar);
		lekarService.dodajLekara(lekar);
		//System.out.println(lekar.getEmail());
		return new ResponseEntity(HttpStatus.OK);
	}

}