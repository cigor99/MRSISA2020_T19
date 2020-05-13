package MRSISA.Klinicki.centar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.dto.LoginDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
@RequestMapping("/loginController")
public class LoginController {

	@Autowired
	private AdminKCSerivce adminKCService;

	@Autowired
	private AdminKService adminService;

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private MedicinskaSestraSerive medSesService;
	
	
	@PostMapping("/prijava")
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO){
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if(p.getStanjePacijenta().equals(StanjePacijenta.AKTIVAN) && p.getEmail().equals(loginDTO.getEmail()) && p.getLozinka().equals(loginDTO.getLozinka())){
				return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
			}
		}

		List<Lekar> lekari = lekarService.findAll();
		for (Lekar l : lekari) {
			if (l.getEmail().equals(loginDTO.getEmail()) && l.getLozinka().equals(loginDTO.getLozinka())) {
				return new ResponseEntity<>(l, HttpStatus.ACCEPTED);
			}
		}

		List<AdministratorKlinike> adminiKlinika = adminService.findAll();
		for (AdministratorKlinike ak : adminiKlinika) {
			if (ak.getEmail().equals(loginDTO.getEmail()) && ak.getLozinka().equals(loginDTO.getLozinka()) ) {
				return new ResponseEntity<>(ak, HttpStatus.ACCEPTED);
			}
		}

		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		for (AdministratorKlinickogCentra adm : admini) {
			if (adm.getEmail().equals(loginDTO.getEmail()) && adm.getLozinka().equals(loginDTO.getLozinka())) {
				return new ResponseEntity<>(adm, HttpStatus.ACCEPTED);
			}
		}
		
		List<MedicinskaSestra> medSestre = medSesService.findAll();
		for(MedicinskaSestra ms : medSestre) {
			if(ms.getEmail().equals(loginDTO.getEmail()) && ms.getLozinka().equals(loginDTO.getLozinka())) {
				return new ResponseEntity<>(ms, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
}
