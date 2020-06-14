package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.dto.LekDTO;
import MRSISA.Klinicki.centar.service.LekService;

@RestController
public class LekController {

	@Autowired
	private LekService lekService;

	/*
	 * Funkcija za dodavanje leka
	 */
	@PostMapping("/sifarnikLekova/addLek")
	public ResponseEntity<LekDTO> addLek(@RequestBody LekDTO lekDTO) {

		Lek lek = new Lek();
		lek.setNaziv(lekDTO.getNaziv());
		lek.setSifra(lekDTO.getSifra());
		lek.setKlinickiCentar(new KlinickiCentar(1));

		lek = lekService.addLek(lek);

		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.OK);
	}

	/*
	 * Funkcija za brisanje leka iz sifrarnika
	 */
	@DeleteMapping("/sifarnikLekova/deleteLek/{id}")
	public ResponseEntity<Void> deleteLek(@PathVariable Integer id) {
		Lek lek = lekService.findOne(id);

		if (lek != null) {
			if(lek.getRecepti().size() != 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			lekService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Funkcija za dobavljanje podatke lekova za lek koji zelimo da izmenimo
	 */
	@GetMapping("/sifarnikLekova/getUpdate/{ID}")
	public ResponseEntity<LekDTO> getUpdate(@PathVariable Integer ID){
		Lek lek = lekService.findOne(ID);
		if(lek != null) {
			return new ResponseEntity<>(new LekDTO(lek), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Funkcija za izmenu leka
	 */
	@PutMapping("/sifarnikLekova/Update")
	public ResponseEntity<LekDTO> updateLek(@RequestBody LekDTO lekDTO){
		Lek lek = lekService.findOne(lekDTO.getId());
		if(lek == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		lek.setNaziv(lekDTO.getNaziv());
		lek.setSifra(lekDTO.getSifra());
		
		lek = lekService.save(lek);

		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.OK);
		
	}
	
	/*
	 * Funkcija za dobavljanje svih lekova
	 */
	@GetMapping("/sifarnikLekova/getall")
	public ResponseEntity<List<LekDTO>> getAll(){
		ArrayList<LekDTO> retVal = new ArrayList<LekDTO>();
		for (Lek lek : lekService.findAll()) {
			retVal.add(new LekDTO(lek));
		}
		
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	/*
	 * Funkcija za dobavljanje leka
	 */
	@GetMapping("/sifarnikLekova/getOne/{id}")
	public ResponseEntity<Object> getOne(@PathVariable Integer id){
		List<Lek> lekovi = lekService.findAll();
		for (Lek lek : lekovi) {
			if(lek.getId() == id) {
				return new ResponseEntity<>(new LekDTO(lek), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>( "Lek nije pronađen u bazi podataka.", HttpStatus.NOT_FOUND);
		
		
	}
}
