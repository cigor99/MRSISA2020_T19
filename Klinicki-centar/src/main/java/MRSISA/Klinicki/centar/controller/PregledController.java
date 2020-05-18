package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.dto.PregledDTO;
import MRSISA.Klinicki.centar.service.PregledService;

@RestController
@RequestMapping("/pregled")
public class PregledController {
	
	@Autowired
	private PregledService pregledService;
	
	@GetMapping("/all")
	public ResponseEntity<List<PregledDTO>> getAllPregledi(){
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		for(Pregled p : pregledi) {
			preglediDTO.add(new PregledDTO(p));
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<List<PregledDTO>> getPregledPage(){
		Pageable prvihDeset = PageRequest.of(0,10);
		Page<Pregled> pregledi = pregledService.findAll(prvihDeset);
		System.out.println("PREGLEDI");
		List<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		for(Pregled p : pregledi) {
			System.out.println(p);
			preglediDTO.add(new PregledDTO(p));
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

}
