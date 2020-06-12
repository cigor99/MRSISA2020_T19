package MRSISA.Klinicki.centar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.dto.OperacijaDTO;
import MRSISA.Klinicki.centar.service.OperacijaService;

@RestController
public class OperacijaController {
	
	@Autowired
	private OperacijaService operacijaService;
	
	@GetMapping("/Operacija/getOne/{id}")
	public ResponseEntity<Object> getOne(@PathVariable Integer id){
		Operacija op = operacijaService.findById(id);
		if(op == null) {
			return new ResponseEntity<>("Operacija nije pronadjena", HttpStatus.BAD_REQUEST);
		}
		
		
		return new ResponseEntity<>(new OperacijaDTO(op), HttpStatus.OK);
	}
	
	

}
