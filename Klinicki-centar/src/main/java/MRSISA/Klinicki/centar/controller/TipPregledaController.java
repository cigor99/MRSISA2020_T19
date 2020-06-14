package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Cena;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.SalaDTO;
import MRSISA.Klinicki.centar.dto.TipPregledaDTO;
import MRSISA.Klinicki.centar.service.CenaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.TipPregledaService;

@RestController
public class TipPregledaController {
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@Autowired
	private CenaService cenaService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	HttpServletRequest request;
	
	@GetMapping("/tipPregleda/all")
	public ResponseEntity<List<TipPregledaDTO>> getAllTipoviPregleda(){
		
		List<TipPregleda> tipoviPregleda =  tipPregledaService.findAll();
		
		List<TipPregledaDTO> tipoviPregledaDTO = new ArrayList<TipPregledaDTO>();
		for(TipPregleda t : tipoviPregleda) {
			tipoviPregledaDTO.add(new TipPregledaDTO(t));
		}
		
		return new ResponseEntity<>(tipoviPregledaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/tipPregleda/page")
	public ResponseEntity<List<TipPregledaDTO>> getTipPregledaPage(){
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<TipPregleda> tipoviPregleda =  tipPregledaService.findAll(prvihDeset);
		
		List<TipPregledaDTO> tipoviPregledaDTO = new ArrayList<TipPregledaDTO>();
		for(TipPregleda t : tipoviPregleda) {
			tipoviPregledaDTO.add(new TipPregledaDTO(t));
		}
		
		return new ResponseEntity<>(tipoviPregledaDTO, HttpStatus.OK);
	}
	
	@PostMapping("/tipPregleda/add")
	public ResponseEntity<TipPregledaDTO> addTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO){
		TipPregleda tipPregleda = new TipPregleda();
		tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
		tipPregleda.setTrajanje(tipPregledaDTO.getTrajanje());
		//List<Cena> cene = cenaService.findAll();
		Cena cena = new Cena();
		cena.setIznos(tipPregledaDTO.getCena());
		cena.setTipPregleda(tipPregleda);
		tipPregleda.setCena(cena);
		tipPregleda = tipPregledaService.addTipPregleda(tipPregleda);
		cena = cenaService.addCena(cena);
		
		return new ResponseEntity<>(new TipPregledaDTO(tipPregleda), HttpStatus.CREATED);
	}
	
	@PostMapping("/tipPregleda/search")
	public ResponseEntity<List<TipPregledaDTO>> searchLekar(@RequestBody String pretraga){
		List<TipPregledaDTO> retVal = new ArrayList<TipPregledaDTO>();
		System.out.println(pretraga);
		for(TipPregleda t : tipPregledaService.findAll()) {
			if(t.getNaziv().contains(pretraga)) {
				TipPregledaDTO tip = new TipPregledaDTO(t);
				retVal.add(tip);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	
	}
	
	
	@DeleteMapping("/tipPregleda/delete/{id}")
	public ResponseEntity<Void> deleteTipPregleda(@PathVariable Integer id){
		TipPregleda tipPregleda = tipPregledaService.findOne(id);
		if(tipPregleda != null) {
			tipPregledaService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/tipPregleda/getUpdate/{id}")
	public ResponseEntity<TipPregledaDTO> getUpdate(@PathVariable Integer id){
		TipPregleda tipPregleda = tipPregledaService.findOne(id);
		if(tipPregleda != null) {
			return new ResponseEntity<>(new TipPregledaDTO(tipPregleda), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	/*
	 * Funkcija koja vraca sve tipove pregleda koje izabrani lekar moze da vrsi
	 */
	@GetMapping("/tipPregleda/getLekari/{id}")
	public ResponseEntity<List<LekarDTO>> getLekari(@PathVariable Integer id){
		TipPregleda tipPregleda = tipPregledaService.findOne(id);
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
		int klinika = admink.getKlinikaID();
		if(tipPregleda != null) {
			for(Lekar l : lekarService.findAll()) {
				if(l.getTipoviPregleda().contains(tipPregleda) && l.getKlinika().getId().equals(klinika)) {
					lekariDTO.add(new LekarDTO(l));
				}
			}
			return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@PutMapping("/tipPregleda/update")
	public ResponseEntity<TipPregledaDTO> updateSala(@RequestBody TipPregledaDTO tipPregledaDTO){
		TipPregleda tipPregleda = tipPregledaService.findOne(tipPregledaDTO.getId());
		if(tipPregleda == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
		Cena cena = cenaService.findOneByTipPregleda(tipPregleda);
		//Cena cena = new Cena();		
		cena.setIznos(tipPregledaDTO.getCena());
		tipPregleda.setCena(cena);
		tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
		tipPregleda.setTrajanje(tipPregledaDTO.getTrajanje());
		tipPregleda = tipPregledaService.save(tipPregleda);
		return new ResponseEntity<>(new TipPregledaDTO(tipPregleda), HttpStatus.OK);
	}

}
