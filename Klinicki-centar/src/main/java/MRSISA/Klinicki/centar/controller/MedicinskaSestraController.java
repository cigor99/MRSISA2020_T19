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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.dto.MedicinskaSestraDTO;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;

@RestController
public class MedicinskaSestraController {

	@Autowired
	private MedicinskaSestraSerive medSesService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping("/medicinskaSestra/all")
	public ResponseEntity<List<MedicinskaSestraDTO>> getAllMS(){
		List<MedicinskaSestra> sestre = medSesService.findAll();
		List<MedicinskaSestraDTO> sestreDTO = new ArrayList<MedicinskaSestraDTO>();
		for(MedicinskaSestra ms : sestre) {
			sestreDTO.add(new MedicinskaSestraDTO(ms));
		}
		return new ResponseEntity<>(sestreDTO, HttpStatus.OK);
	}
	
	@GetMapping("/medicinskaSestra/page")
	public ResponseEntity<List<MedicinskaSestraDTO>> getMSPage(){
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<MedicinskaSestra> sestre = medSesService.findAll(prvihDeset);
		List<MedicinskaSestraDTO> sestreDTO = new ArrayList<MedicinskaSestraDTO>();
		for(MedicinskaSestra ms : sestre) {
			sestreDTO.add(new MedicinskaSestraDTO(ms));
		}
		return new ResponseEntity<>(sestreDTO, HttpStatus.OK);
	}
	
	@PostMapping("/medicinskaSestra/add")
	public ResponseEntity<MedicinskaSestraDTO> addLekar(@RequestBody MedicinskaSestraDTO sestraDTO){
		MedicinskaSestra sestra = new MedicinskaSestra();
		String email = sestraDTO.getEmail();
		for(MedicinskaSestra ms : medSesService.getSestre()) {
			if(email.equals(ms.getEmail())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}					
		sestra.setEmail(email);
		sestra.setLozinka(sestraDTO.getLozinka());
		sestra.setIme(sestraDTO.getIme());
		sestra.setPrezime(sestraDTO.getPrezime());
		Klinika klinika = klinikaService.findOne(1);
		sestra.setKlinika(klinika);
		sestra = medSesService.addSestra(sestra);
		return new ResponseEntity<>(new MedicinskaSestraDTO(sestra), HttpStatus.CREATED);		
	}
	
	
	@DeleteMapping("/medicinskaSestra/delete/{id}")
	public ResponseEntity<Void> deleteSala(@PathVariable Integer id){
		MedicinskaSestra sestra = medSesService.findOne(id);
		if(sestra != null) {
			medSesService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
		

}