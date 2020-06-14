package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//import java.lang.module.FindException;

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

import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.IzvestajPregleda;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.dto.DijagnozaDTO;
import MRSISA.Klinicki.centar.service.DijagnozaService;

@RestController
public class DijagnozaController {

	@Autowired
	private DijagnozaService dijaService;

	/*
	 * Funkcija za dodavanje dijagnoze u sifrarnik
	 */
	@PostMapping("/sifarnikDijagnoza/addDijagnoza")
	public ResponseEntity<DijagnozaDTO> addDijagnoza(@RequestBody DijagnozaDTO dijagnozaDTO) {
		Dijagnoza dijagnoza = new Dijagnoza();
		dijagnoza.setNaziv(dijagnozaDTO.getNaziv());
		dijagnoza.setOpis(dijagnozaDTO.getOpis());
		dijagnoza.setSifra(dijagnozaDTO.getSifra());
		dijagnoza.setKlinickiCentar(new KlinickiCentar(1));
		
		dijagnoza = dijaService.addDijagnoza(dijagnoza);

		return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.OK);
	}

	/*
	 * Funkcija za brisanje dijagnoze iz sifrarnika
	 */
	@DeleteMapping("/sifarnikDijagnoza/delete/{id}")
	public ResponseEntity<Void> deleteDijagnoza(@PathVariable Integer id){
		Dijagnoza dijagnoza = dijaService.findOne(id);
		Set<IzvestajPregleda> izvestaji = dijagnoza.getIzvestajiPregleda();
		
		if(dijagnoza.getIzvestajiPregleda().size() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(dijagnoza != null) {
			dijaService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Funkcija za dobavljanje podataka dijagnoze koju hocemo da izmenimo
	 */
	@GetMapping("/sifarnikDijagnoza/getUpdate/{id}")
	public ResponseEntity<DijagnozaDTO> getUpdate(@PathVariable Integer id){
		Dijagnoza dijagnoza = dijaService.findOne(id);
		if(dijagnoza != null) {
			return new ResponseEntity<>(new DijagnozaDTO(dijagnoza),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Funkcija za izmenu dijagnoze iz sifrarnika
	 */
	@PutMapping("/sifarnikDijagnoza/Update")
	public ResponseEntity<DijagnozaDTO> updateDijagnoze(@RequestBody DijagnozaDTO dijagnozaDTO){
		Dijagnoza dijagnoza = dijaService.findOne(dijagnozaDTO.getId());
		if(dijagnoza == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		dijagnoza.setNaziv(dijagnozaDTO.getNaziv());
		dijagnoza.setOpis(dijagnozaDTO.getOpis());
		dijagnoza.setSifra(dijagnozaDTO.getSifra());
		
		dijagnoza = dijaService.save(dijagnoza);
		
		return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.OK);
	}
	
	
	/*
	 * Funkcija koja vraca sve dijagnoze iz sifrarnika
	 */
	@GetMapping("/sifarnikDijagnoza/getAll")
	public ResponseEntity<List<DijagnozaDTO>> getAll(){
		ArrayList<DijagnozaDTO> retVal = new ArrayList<DijagnozaDTO>();
		for (Dijagnoza dijagnoza : dijaService.findAll()) {
			retVal.add(new DijagnozaDTO(dijagnoza));
		}
		
		return  new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	
}