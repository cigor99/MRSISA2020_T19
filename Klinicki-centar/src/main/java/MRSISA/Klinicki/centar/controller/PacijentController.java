package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pol;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.domain.ZdravstveniKarton;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
@RequestMapping("/pacijent")
public class PacijentController {
	
	@Autowired
	private PacijentService pacijentService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<PacijentDTO>> getAllPacijenti(){
		System.out.println("PacijentController-getAllPacijetni");
		List<Pacijent> pacijenti = pacijentService.findAll();
		List<PacijentDTO> pacijentiDTO = new ArrayList<PacijentDTO>();
		for(Pacijent p : pacijenti) 
			pacijentiDTO.add(new PacijentDTO(p));
		
		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<List<PacijentDTO>> getPacijentPage(){
		System.out.println("PacijentController-getPacijentPage");
		Pageable prvihDeset = PageRequest.of(0, 10);
		Page<Pacijent> pacijenti = null;
		try {
			pacijenti = pacijentService.findAll(prvihDeset);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		List<PacijentDTO> pacijentiDTO = new ArrayList<PacijentDTO>();
		
		for(Pacijent p : pacijenti) {
			System.out.println("---------");
			System.out.println(p);
			pacijentiDTO.add(new PacijentDTO(p));
		}
		
		return new ResponseEntity<List<PacijentDTO>>(pacijentiDTO, HttpStatus.OK);
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<PacijentDTO> register(@RequestBody PacijentDTO pacijentDTO){
		System.out.println("PacijentController-register");
		if(!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println(pacijentDTO);
		Pacijent novi = new Pacijent(0, pacijentDTO.getIme(), pacijentDTO.getPrezime(), pacijentDTO.getJmbg(), pacijentDTO.getEmail(), pacijentDTO.getLozinka(), null, pacijentDTO.getPol(), pacijentDTO.getGrad(), pacijentDTO.getDrzava(), pacijentDTO.getAdresa(),pacijentDTO.getBrojTelefona(), pacijentDTO.getJedinstveniBrOsig() );

		ZahtevZaRegistraciju zzr = new ZahtevZaRegistraciju(0, StanjeZahteva.NA_CEKANJU, novi, new KlinickiCentar());
		return new ResponseEntity<PacijentDTO>(new PacijentDTO(), HttpStatus.CREATED);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<PacijentDTO> addPacijent(@RequestBody PacijentDTO pacijentDTO){
		System.out.println("PacijentController-addPacijent");
		if(!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println(pacijentDTO);
		Pacijent pacijent = new Pacijent();
		pacijent.setId(pacijentDTO.getId());
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setJmbg(pacijentDTO.getJmbg());
		pacijent.setPol(pacijentDTO.getPol());
		pacijent.setStanjePacijenta(StanjePacijenta.NA_CEKANJU);
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		pacijent.setJedinstveniBrOsig(pacijentDTO.getJedinstveniBrOsig());
		
		pacijent = pacijentService.addPacijent(pacijent);
		
		return new ResponseEntity<PacijentDTO>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePacijent(@PathVariable Integer id){
		System.out.println("PacijentController-deletePacijent");
		Pacijent pacijent = pacijentService.findOne(id);
		
		if(pacijent != null) {
			pacijentService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getUpdate/{id}")
	public ResponseEntity<PacijentDTO> getUpdate(@PathVariable Integer id){
		System.out.println("PacijentController-getUpdate");
		Pacijent pacijent = pacijentService.findOne(id);
		if(pacijent != null) 
			return new ResponseEntity<PacijentDTO>(new PacijentDTO(pacijent), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getOnePacijent/{id}")
	public ResponseEntity<PacijentDTO> getPacijent(@PathVariable Integer id){
		Pacijent pacijent = pacijentService.findOne(id);
		System.out.println("USAO U GET");
		if(pacijent == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<PacijentDTO> updatePacijent(@RequestBody PacijentDTO pacijentDTO){
		System.out.println("PacijentController-updatePacijent");
		if(!pacijentDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println(pacijentDTO);
		Pacijent pacijent = pacijentService.findOne(pacijentDTO.getId());
		System.out.println(pacijent);
		if(pacijent == null) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(!pacijent.getEmail().equals(pacijentDTO.getEmail())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setPol(pacijentDTO.getPol());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		pacijent.setJedinstveniBrOsig(pacijentDTO.getJedinstveniBrOsig());
		
		System.out.println(pacijent);
		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<PacijentDTO>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	
	
	
	
	
}
