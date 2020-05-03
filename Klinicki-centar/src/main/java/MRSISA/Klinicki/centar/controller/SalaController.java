
package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.SalaDTO;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.SalaService;

@RestController
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping("/sala/all")
	public ResponseEntity<List<SalaDTO>> getAllSale(){
		
		List<Sala> sale =  salaService.findAll();
		
		List<SalaDTO> saleDTO = new ArrayList<SalaDTO>();
		for(Sala s : sale) {
			saleDTO.add(new SalaDTO(s));
		}
		
		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}
	
	@GetMapping("/sala/page")
	public ResponseEntity<List<SalaDTO>> getSalaPage(){
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Sala> sale =  salaService.findAll(prvihDeset);
		
		List<SalaDTO> saleDTO = new ArrayList<SalaDTO>();
		for(Sala s : sale) {
			saleDTO.add(new SalaDTO(s));
		}
		
		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}
	
	@PostMapping("/sala/add")
	public ResponseEntity<SalaDTO> addSala(@RequestBody SalaDTO salaDTO){
		Sala sala = new Sala();
		Klinika klinika = klinikaService.findOne(1);		
		sala.setNaziv(salaDTO.getNaziv());
		sala.setTip(salaDTO.getTip());
		sala.setId(salaDTO.getId());
		sala.setKlinika(klinika);
		sala = salaService.addSala(sala);
		
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}
	
	@PostMapping("/sala/search")
	public ResponseEntity<List<SalaDTO>> searchSala(@RequestBody String pretraga){
		List<SalaDTO> retVal = new ArrayList<SalaDTO>();
		System.out.println(pretraga);
		for(Sala s : salaService.findAll()) {
			if(s.getNaziv().contains(pretraga)) {
				SalaDTO sala = new SalaDTO(s);
				retVal.add(sala);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	
	}
	
	@PostMapping("/sala/filter")
	public ResponseEntity<List<SalaDTO>> filterSala(@RequestBody String filter){
		List<SalaDTO> retVal = new ArrayList<SalaDTO>();
		for(Sala s : salaService.findAll()) {
			if(s.getTip().toString().equals(filter)) {
				SalaDTO sala = new SalaDTO(s);
				retVal.add(sala);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	
	}
	
	@DeleteMapping("/sala/delete/{id}")
	public ResponseEntity<Void> deleteSala(@PathVariable Integer id){
		Sala sala = salaService.findOne(id);
		if(sala != null) {
			salaService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/sala/getUpdate/{id}")
	public ResponseEntity<SalaDTO> getUpdate(@PathVariable Integer id){
		Sala sala = salaService.findOne(id);
		if(sala != null) {
			return new ResponseEntity<>(new SalaDTO(sala),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@PutMapping("/sala/update")
	public ResponseEntity<SalaDTO> updateSala(@RequestBody SalaDTO salaDTO){
		Sala sala = salaService.findOne(salaDTO.getId());
		if(sala == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		sala.setNaziv(salaDTO.getNaziv());
		sala.setTip(salaDTO.getTip());
		System.out.println(salaDTO.getTip());
		sala = salaService.save(sala);
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.OK);
	}
	
	

}

