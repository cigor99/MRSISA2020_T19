package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
@RequestMapping("/pacijent")
public class PacijentController {
	
	@Autowired
	private PacijentService pacijentService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<PacijentDTO>> getAllPacijenti(){
		System.out.println("PacijentController-getAllPacijetni");
		List<Pacijent> pacijenti = pacijentService.findAll();
		List<PacijentDTO> pacijentiDTO = new ArrayList<PacijentDTO>();
		for(Pacijent p : pacijenti) 
			pacijentiDTO.add(new PacijentDTO(p));
		
		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<List<PacijentDTO>> getPacijentPage(){
		System.out.println("PacijentController-getPacijentPage");
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Pacijent> pacijenti = null;
		try {
			pacijenti = pacijentService.findAll(prvihDeset);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		List<PacijentDTO> pacijentiDTO = new ArrayList<PacijentDTO>();
		
		for(Pacijent p : pacijenti)
			pacijentiDTO.add(new PacijentDTO(p));
		
		
		return new ResponseEntity<List<PacijentDTO>>(pacijentiDTO, HttpStatus.OK);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<PacijentDTO> addPacijent(@RequestBody PacijentDTO pacijentDTO){
		System.out.println("PacijentController-addPacijent");
		System.out.println(pacijentDTO);
		Pacijent pacijent = new Pacijent();
		pacijent.setId(pacijentDTO.getId());
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setJmbg(pacijentDTO.getJmbg());
		
		pacijent = pacijentService.addPacijent(pacijent);
		
		return new ResponseEntity<PacijentDTO>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePacijent(@PathVariable Integer id){
		System.out.println("PacijentController-deletePacijent");
		Pacijent pacijent = pacijentService.findOne(id);
		
		if(pacijent != null) {
			pacijentService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getUpdate/{id}")
	public ResponseEntity<PacijentDTO> getUpdate(@PathVariable Integer id){
		System.out.println("PacijentController-getUpdate");
		Pacijent pacijent = pacijentService.findOne(id);
		if(pacijent != null) 
			return new ResponseEntity<PacijentDTO>(new PacijentDTO(pacijent), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/update")
	public ResponseEntity<PacijentDTO> updatePacijent(@RequestBody PacijentDTO pacijentDTO){
		System.out.println("PacijentController-updatePacijent");
		Pacijent pacijent = pacijentService.findOne(pacijentDTO.getId());
		if(pacijent == null) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(!pacijent.getEmail().equals(pacijentDTO.getEmail())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		
		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<PacijentDTO>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	
	/*
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
	*/
	
	
	
	
}
