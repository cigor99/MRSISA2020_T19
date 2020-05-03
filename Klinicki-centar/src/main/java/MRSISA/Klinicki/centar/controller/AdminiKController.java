package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
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
}
