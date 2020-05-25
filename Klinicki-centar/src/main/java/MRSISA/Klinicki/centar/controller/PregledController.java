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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.PregledDTO;
import MRSISA.Klinicki.centar.dto.SalaDTO;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.PregledService;
import MRSISA.Klinicki.centar.service.SalaService;
import MRSISA.Klinicki.centar.service.TipPregledaService;

@RestController
@RequestMapping("/pregled")
public class PregledController {
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@Autowired
	private LekarService lekarService;
	
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
	
	@PostMapping("/add")
	public ResponseEntity<PregledDTO> addPregled(@RequestBody PregledDTO pregledDTO){
		Pregled pregled = new Pregled();
		int salaId = Integer.parseInt(pregledDTO.getSala());
		int tipId = Integer.parseInt(pregledDTO.getTipPregleda());
		int lekarId = Integer.parseInt(pregledDTO.getLekar());
		Sala sala = salaService.findOne(salaId);	
		TipPregleda tipPregleda = tipPregledaService.findOne(tipId);
		Lekar lekar = lekarService.findOne(lekarId);
		
		pregled.setSala(sala);
		pregled.setTipPregleda(tipPregleda);
		pregled.setLekar(lekar);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String datumvreme = pregledDTO.getDatumivreme().replace("T", " ");
		Date date;
		try {
			date = sdf.parse(datumvreme);
			System.out.println(date);
			pregled.setDatum(date);	
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		
		pregled = pregledService.addPregled(pregled);
		
		System.out.println("ADD PREGLED");
		System.out.println(pregledDTO.getDatumivreme());
		System.out.println(pregledDTO.getSala());
		System.out.println(pregledDTO.getTipPregleda());
		System.out.println(pregledDTO.getLekar());
		System.out.println(pregledDTO.getCena());
		
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.CREATED);
	}
	@GetMapping("/getDnevniPregled/{dan}/{mesec}")
	public ResponseEntity<Object> getDnevniPregled(@PathVariable String dan, @PathVariable String mesec){
		
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
