package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.service.AdminKService;

@RestController
public class AdminiKController {

	@Autowired
	private AdminKService adminService;
	
	@GetMapping("/adminK/getAll")
	public ResponseEntity<List<AdminKDTO>> getAll(){
		List<AdminKDTO> admini = new ArrayList<AdminKDTO>();
		List<AdministratorKlinike> adminiKlinike = adminService.findAll();
		for (AdministratorKlinike administratorKlinike : adminiKlinike) {
			admini.add(new AdminKDTO(administratorKlinike));
		}
		
		return new ResponseEntity<>(admini, HttpStatus.OK);
	}
	
	
	@PutMapping("/adminK/update")
	public ResponseEntity<AdminKDTO> updateAdmina(@RequestBody AdminKDTO adminKDTO){
		AdministratorKlinike admin = adminService.findOne(adminKDTO.getId());
		if(admin == null) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		admin.setEmail(adminKDTO.getEmail());
		admin.setIme(adminKDTO.getIme());
		admin.setPrezime(adminKDTO.getPrezime());
		admin.setLozinka(adminKDTO.getLozinka());
		admin.setJmbg(adminKDTO.getJmbg());
		
		admin = adminService.save(admin);
		
		return new ResponseEntity<>(new AdminKDTO(admin), HttpStatus.OK);
				
	}
}
