package MRSISA.Klinicki.centar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		List<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		for(Pregled p : pregledi) {
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
	
	@GetMapping("/getDnevniPregled/{dan}/{mesec}")
	public ResponseEntity<Object> getDnevniPregled(@PathVariable String dan, @PathVariable String mesec){
//		System.out.println("DAN :" + dan);
//		System.err.println("MESEC: " +mesec);
		
		List<Pregled>pregledi =  pregledService.findAll();
		List<PregledDTO> retVal = new ArrayList<PregledDTO>();
		for(Pregled p: pregledi) {
			if(((p.getDatum().getMonth()+1)+"-"+p.getDatum().getDate()).equals(mesec+"-"+dan)){
				retVal.add(new PregledDTO(p));
			}
		}
		
		return new ResponseEntity<>(retVal, HttpStatus.OK); 
	}
	@GetMapping("/getDnevniPregled/{mesec}")
	public ResponseEntity<Object> getDnevniPregled(@PathVariable String mesec){
		List<Pregled>pregledi =  pregledService.findAll();
		List<PregledDTO> retVal = new ArrayList<PregledDTO>();
		for(Pregled p: pregledi) {
			if((p.getDatum().getMonth()+1+"").equals(mesec+"")){
				retVal.add(new PregledDTO(p));
			}
		}
		
		return new ResponseEntity<>(retVal, HttpStatus.OK); 
	}

}
