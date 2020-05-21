package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.RequestMapping;


import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.dto.PregledDTO;
import MRSISA.Klinicki.centar.service.PregledService;

@RestController
@RequestMapping("/pregled")
public class PregledController {
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	HttpServletRequest request;
	
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
			System.out.println(p.getDatum());
			preglediDTO.add(new PregledDTO(p));
		}
		for(PregledDTO p : preglediDTO) {
			System.out.println(p.getId());
			System.out.println(p.getDatum());
			System.out.println(p.getVreme());
			System.out.println(p.getLekar());
			System.out.println(p.getPacijent());
			System.out.println(p.getSala());
			System.out.println(p.getPopust());
			System.out.println(p.getTipPregleda());
			System.out.println(p.getCena());
			System.out.println(p.getTrajanje());
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
		//return new ResponseEntity<>(HttpStatus.OK);
	}

}
