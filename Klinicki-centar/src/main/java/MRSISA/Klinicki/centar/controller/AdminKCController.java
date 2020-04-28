package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

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

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import antlr.ASdebug.ASDebugStream;

@RestController
public class AdminKCController {

	@Autowired
	private AdminKCSerivce adminSerivce;
	
	@GetMapping("/adminKC/getAll")
	public ResponseEntity<List<AdminKCDTO>> getAllAdmini(){
		List<AdministratorKlinickogCentra> admini =  adminSerivce.findAll();
		List<AdminKCDTO> ret = new ArrayList<AdminKCDTO>();
		for (AdministratorKlinickogCentra ad : admini) {
			ret.add(new AdminKCDTO(ad));
		}
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	
	@PostMapping("/adminKC/add")
	public ResponseEntity<AdminKCDTO> addAdmin(@RequestBody AdminKCDTO adminKCDTO){
		AdministratorKlinickogCentra admin = new AdministratorKlinickogCentra();
		admin.setEmail(adminKCDTO.getEmail());
		admin.setIme(adminKCDTO.getIme());
		admin.setPrezime(adminKCDTO.getPrezime());
		admin.setJmbg(adminKCDTO.getJmbg());
		admin.setLozinka(adminKCDTO.getLozinka());
		
		admin = adminSerivce.addAdmin(admin);
		
		return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/adminKC/delete/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id){
		AdministratorKlinickogCentra admin = adminSerivce.findOne(id);
		if(admin != null) {
			adminSerivce.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/adminKC/getupdate/{id}")
	public ResponseEntity<AdminKCDTO> getUpdate(@PathVariable Integer id){
		AdministratorKlinickogCentra admin = adminSerivce.findOne(id);
		if(admin != null) {
			return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/adminKC/update")
	public ResponseEntity<AdminKCDTO> updateAdmina(@RequestBody AdminKCDTO adminKCDTO){
		AdministratorKlinickogCentra admin = adminSerivce.findOne(adminKCDTO.getId());
		if(admin == null) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		admin.setEmail(adminKCDTO.getEmail());
		admin.setIme(adminKCDTO.getIme());
		admin.setPrezime(adminKCDTO.getPrezime());
		admin.setLozinka(adminKCDTO.getLozinka());
		admin.setJmbg(adminKCDTO.getJmbg());
		
		admin = adminSerivce.save(admin);
		
		return new ResponseEntity<>(new AdminKCDTO(admin), HttpStatus.OK);
				
	}
}
