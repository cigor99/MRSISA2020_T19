package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.dto.KlinikaDTO;
import MRSISA.Klinicki.centar.service.KlinikaService;

@RestController
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping("/klinika/all")
	public ResponseEntity<List<KlinikaDTO>> getAllKlinike(){
		
		List<Klinika> klinike =  klinikaService.findAll();
		
		List<KlinikaDTO> klinikeDTO = new ArrayList<KlinikaDTO>();
		for(Klinika k : klinike) {
			klinikeDTO.add(new KlinikaDTO(k));
		}
		
		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}

	
	@GetMapping("/klinika/page")
	public ResponseEntity<List<KlinikaDTO>> getKlinikaPage(){
		//AKO TREBA DA SE MENJA MOZE SE PROSLEDITI Pageable pageable
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Klinika> klinike =  klinikaService.findAll(prvihDeset);
		
		List<KlinikaDTO> klinikeDTO = new ArrayList<KlinikaDTO>();
		for(Klinika k : klinike) {
			klinikeDTO.add(new KlinikaDTO(k));
		}
		
		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}
	@PostMapping("/klinika/add")
	public ResponseEntity<KlinikaDTO> addKlinika(@RequestBody KlinikaDTO klinikaDTO) {
		System.out.println("USAO");
		Klinika klinika = new Klinika();
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setId(klinikaDTO.getId());
		System.out.println(klinika.getNaziv());
		klinika = klinikaService.addKlinika(klinika);
		
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/klinika/delete/{id}")
	public ResponseEntity<Void> deleteKlinika(@PathVariable Integer id){
		System.out.println("CONTROLER");
		System.out.println(id);
		Klinika klinika = klinikaService.findOne(id);
		System.out.println("VRATIO se");
		System.out.println(klinika != null);
		
		if (klinika != null) {
			klinikaService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/klinika/getUpdate/{id}")
	public ResponseEntity<KlinikaDTO> getUpdate(@PathVariable Integer id){
		Klinika klinika = klinikaService.findOne(id);
		if (klinika != null) {
			
			return new ResponseEntity<>(new KlinikaDTO(klinika),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/klinika/update")
	public ResponseEntity<KlinikaDTO> updateklinika(@RequestBody KlinikaDTO klinikaDTO){
		Klinika klinika = klinikaService.findOne(klinikaDTO.getId());
		
		if(klinika == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setNaziv(klinikaDTO.getNaziv());
		
		klinika = klinikaService.save(klinika);
		
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.OK);
	}

}
