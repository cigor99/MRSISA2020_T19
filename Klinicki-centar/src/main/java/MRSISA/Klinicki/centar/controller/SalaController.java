
package MRSISA.Klinicki.centar.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.domain.TipSale;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.SalaDTO;
import MRSISA.Klinicki.centar.service.KlinikaService;
import MRSISA.Klinicki.centar.service.SalaService;
import MRSISA.Klinicki.centar.service.TipPregledaService;

@RestController
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	
	@Autowired
	HttpServletRequest request;

	
	@GetMapping("/sala/all")
	public ResponseEntity<List<SalaDTO>> getAllSale(){		
		List<Sala> sale =  salaService.findAll();
		List<SalaDTO> saleDTO = new ArrayList<SalaDTO>();
		int klinika = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if(tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			klinika = admink.getKlinikaID();
		}
		else if(tip.equals("lekar")) {
			LekarDTO lekar = (LekarDTO) request.getSession().getAttribute("current");
			klinika = lekar.getKlinikaID();
		}
		if(klinika != -1) {			
			for(Sala s : sale) {
				if(s.getKlinika().getId().equals(klinika)) {
					saleDTO.add(new SalaDTO(s));
				}
			}
		}
		
		
		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}
	
	@GetMapping("/sala/page")
	public ResponseEntity<List<SalaDTO>> getSalaPage(){
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Sala> sale =  salaService.findAll(prvihDeset);
		List<SalaDTO> saleDTO = new ArrayList<SalaDTO>();
		int klinika = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if(tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			klinika = admink.getKlinikaID();
		}
		else if(tip.equals("lekar")) {
			LekarDTO lekar = (LekarDTO) request.getSession().getAttribute("current");
			klinika = lekar.getKlinikaID();
		}
		if(klinika != -1) {			
			for(Sala s : sale) {
				if(s.getKlinika().getId().equals(klinika)) {
					saleDTO.add(new SalaDTO(s));
					System.out.println(s.getNaziv());
				}
			}
		}
		
		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}
	
	@PostMapping("/sala/add")
	public ResponseEntity<SalaDTO> addSala(@RequestBody SalaDTO salaDTO){
		Sala sala = new Sala();
		int id = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if(tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			id = admink.getKlinikaID();
		}
		Klinika klinika = klinikaService.findOne(id);		
		sala.setNaziv(salaDTO.getNaziv());
		sala.setTip(salaDTO.getTip());
		sala.setId(salaDTO.getId());
		sala.setKlinika(klinika);
		sala = salaService.addSala(sala);
		
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}
	
	@PostMapping("/sala/search/{w}")
	public ResponseEntity<List<SalaDTO>> searchSala(@RequestBody String pretraga, @PathVariable String w){
		List<SalaDTO> retVal = new ArrayList<SalaDTO>();
		System.out.println(pretraga);
		int klinika = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if(tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			klinika = admink.getKlinikaID();
		}
		else if(tip.equals("lekar")) {
			LekarDTO lekar = (LekarDTO) request.getSession().getAttribute("current");
			klinika = lekar.getKlinikaID();
		}
		System.out.println(w);
		if(klinika != -1) {
			for(Sala s : salaService.findAll()) {
				if(s.getNaziv().contains(pretraga)  && s.getKlinika().getId().equals(klinika)) {
					if(w.equals("ZA_PREGLED")) {
						if(s.getTip().equals(TipSale.ZA_PREGLED)) {
							SalaDTO sala = new SalaDTO(s);
							retVal.add(sala);
						}
					}
					else {
						SalaDTO sala = new SalaDTO(s);
						retVal.add(sala);
					}
				}
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	
	}
	
	@PostMapping("/sala/filter")
	public ResponseEntity<List<SalaDTO>> filterSala(@RequestBody String filter){
		List<SalaDTO> retVal = new ArrayList<SalaDTO>();
		int klinika = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if(tip.equals("adminKlinike")) {
			AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
			klinika = admink.getKlinikaID();
		}
		else if(tip.equals("lekar")) {
			LekarDTO lekar = (LekarDTO) request.getSession().getAttribute("current");
			klinika = lekar.getKlinikaID();
		}
		if(klinika != -1) {
			for(Sala s : salaService.findAll()) {
				if(s.getTip().toString().equals(filter) && s.getKlinika().getId().equals(klinika)) {
					SalaDTO sala = new SalaDTO(s);
					retVal.add(sala);
				}
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	
	}
	
	@PostMapping("/sala/filterTime")
	public ResponseEntity<List<SalaDTO>> filterSalaTime(@RequestBody String filter){
		List<SalaDTO> retVal = new ArrayList<SalaDTO>();
		System.out.println(filter);
		String[] f = filter.split(";");
		String stringdate = f[0];
		stringdate = stringdate.replace("T", " ");
		System.out.println(stringdate);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = sdf.parse(stringdate);
			System.out.println(date);
			Date date2 = new Date();
			long tim = Long.parseLong(f[1])*60000;
			date2.setTime(date.getTime()+tim);
			System.out.println(date2);
			int klinika = -1;
			String tip = (String) request.getSession().getAttribute("tip");
			if(tip.equals("adminKlinike")) {
				AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
				klinika = admink.getKlinikaID();
			}
			else if(tip.equals("lekar")) {
				LekarDTO lekar = (LekarDTO) request.getSession().getAttribute("current");
				klinika = lekar.getKlinikaID();
			}				
			for(Sala s : salaService.findAll()) {
				if(klinika != -1 && s.getKlinika().getId().equals(klinika) && s.getTip().equals(TipSale.ZA_PREGLED)) {
					boolean moze = true;
					for(Pregled p : s.getPregledi()) {
						Date datum1 = p.getDatum();
						Date datum2 = new Date();
						datum2.setTime(datum1.getTime()+p.getTipPregleda().getTrajanje());
						if(date2.compareTo(datum1)<=0 || date.compareTo(datum2)>=0) {
							moze = true;
						}
						else {
							moze = false;
						}					
					}
					if(moze) {
						SalaDTO sala = new SalaDTO(s);
						retVal.add(sala);
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	@GetMapping("/sala/proveriDatum/{id}/{datum}/{tipPregledaId}")
	public ResponseEntity<String> proveriDatum(@PathVariable int id, @PathVariable String datum, @PathVariable int tipPregledaId) {
		Sala sala = salaService.findOne(id);
		TipPregleda tipPregleda = tipPregledaService.findOne(tipPregledaId);
		int trajanje = tipPregleda.getTrajanje();
		if(sala != null) {
			String stringdate = datum.replace("T", " ");
			//System.out.println(stringdate);		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				Date date = sdf.parse(stringdate);
				System.out.println(date);
				Date date2 = new Date();
				long tim = trajanje*60000;
				date2.setTime(date.getTime()+tim);
				//System.out.println(date2);
				boolean moze = true;
				for(Pregled p : sala.getPregledi()) {
					Date datum1 = p.getDatum();
					Date datum2 = new Date();
					datum2.setTime(datum1.getTime()+p.getTipPregleda().getTrajanje());
					if(date2.compareTo(datum1)<=0 || date.compareTo(datum2)>=0) {
						moze = true;
					}
					else {
						moze = false;
					}					
				}
				if(moze) {
					System.out.println("SLOBODNA");
					return new ResponseEntity<>("slobodna", HttpStatus.OK);
				}
				else {
					System.out.println("ZAUZETA");
					return new ResponseEntity<>("zauzeta", HttpStatus.OK);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	

}

