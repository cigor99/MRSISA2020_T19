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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.SalaDTO;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.LekarService;


@RestController
public class LekarController {
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping("/lekar/all")
	public ResponseEntity<List<LekarDTO>> getAllLekari(){
		List<Lekar> lekari = lekarService.findAll();
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		for(Lekar l : lekari) {
			lekariDTO.add(new LekarDTO(l));
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}
	
	@GetMapping("/lekar/page")
	public ResponseEntity<List<LekarDTO>> getLekarPage(){
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Lekar> lekari = lekarService.findAll(prvihDeset);
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		for(Lekar l : lekari) {
			lekariDTO.add(new LekarDTO(l));
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}
	
	@PostMapping("/lekar/add")
	public ResponseEntity<LekarDTO> addLekar(@RequestBody LekarDTO lekarDTO){
		Lekar lekar = new Lekar();
		String email = lekarDTO.getEmail();
		for(Lekar l : lekarService.getLekari()) {
			if(email.equals(l.getEmail())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}					
		lekar.setEmail(email);
		lekar.setLozinka(lekarDTO.getLozinka());
		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		Klinika klinika = klinikaService.findOne(1);
		lekar.setKlinika(klinika);
		lekar = lekarService.addLekar(lekar);
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);		
	}
	
	@PostMapping("/lekar/search")
	public ResponseEntity<List<LekarDTO>> searchLekar(@RequestBody String pretraga){
		List<LekarDTO> retVal = new ArrayList<LekarDTO>();
		System.out.println(pretraga);
		
		for(Lekar l : lekarService.findAll()) {
			System.out.println(l.getIme());
			if(l.getIme().contains(pretraga) || l.getPrezime().contains(pretraga) || l.getEmail().contains(pretraga)) {
				System.out.println(l.getPrezime());
				LekarDTO lekar = new LekarDTO(l);
				retVal.add(lekar);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	
	}
	
	@DeleteMapping("/lekar/delete/{id}")
	public ResponseEntity<Void> deleteSala(@PathVariable Integer id){
		Lekar lekar = lekarService.findOne(id);
		if(lekar != null) {
			lekarService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*@GetMapping("/lekar/getUpdate/{id}")
	public ResponseEntity<LekarDTO> getUpdate(@PathVariable Integer id){
		Lekar lekar = lekarService.findOne(id);
		if(lekar != null) {
			return new ResponseEntity<>(new LekarDTO(lekar),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@PutMapping("/lekar/update")
	public ResponseEntity<LekarDTO> updateSala(@RequestBody LekarDTO lekarDTO){
		Lekar lekar = lekarService.findOne(lekarDTO.getId());
		if(lekar == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		lekar.setEmail(lekarDTO.getEmail());
		lekar.setLozinka(lekarDTO.getLozinka());
		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		;
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}*/
	

	
	/*@GetMapping("/dobaviKlinike")
	public ResponseEntity<List<Klinika>> dobaviKlinike() {
		ArrayList<Klinika> klinike = new ArrayList<Klinika>();
		Klinika k1 = new Klinika(1, "klinika 1", "adresa klinike 1", "opis", null, null, null, null);
		Klinika k2 = new Klinika(2, "klinika 2", "adresa klinike 2", "opis", null, null, null, null);
		klinike.add(k1);
		klinike.add(k2);
		
		return new ResponseEntity<List<Klinika>>(klinike, HttpStatus.OK);
	}*/
	

	
	/*@PostMapping("/dodajNovogLekara")
	public ResponseEntity dodajLekara(@RequestBody Lekar lekar) throws ClassNotFoundException, SQLException {
		System.out.println(lekar);
		lekarService.dodajLekara(lekar);
		//System.out.println(lekar.getEmail());
		return new ResponseEntity(HttpStatus.OK);
	}*/

}