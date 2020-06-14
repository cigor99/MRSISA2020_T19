package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.ConfirmationToken;
import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.MedicinskaSestraDTO;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaRegDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.ConfirmationTokenService;
import MRSISA.Klinicki.centar.service.KCService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;

@RestController
public class KCController {
	
	@Autowired
	private ConfirmationTokenService tokenService;

	@Autowired
	private KCService kcService;
	
	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private AdminKCSerivce adminKCService;

	@Autowired
	private AdminKService adminService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private MedicinskaSestraSerive medSesService;

	
	/*
	 * Funkcija za dobavljanje svih lekova sifrarnika
	 */
	@GetMapping("/KC/sifarnikLekova/getAll")
	public ResponseEntity<List<Lek>> getAllLekovi() {
		KlinickiCentar kc = kcService.findOne(1);
		List<Lek> lekovi = kcService.getLekovi(kc);

		return new ResponseEntity<>(lekovi, HttpStatus.OK);
	}

	/*
	 * Funkcija za dobavljanje svih dijagnoza sifrarnika
	 */
	@GetMapping("/KC/sifarnikDijagnoza/getAll")
	public ResponseEntity<List<Dijagnoza>> getAllDijagnoze() {
		KlinickiCentar kc = kcService.findOne(1);
		List<Dijagnoza> dijagnoze = kcService.getDijagnoze(kc);

		return new ResponseEntity<>(dijagnoze, HttpStatus.OK);
	}

	
	/*
	 * Funkcija za dobavljanje svih zahteva za registraciju
	 */
	@GetMapping("/KC/ZZR/getAll")
	public ResponseEntity<List<ZahtevZaRegDTO>> getAllZahteviZaReg() {
		KlinickiCentar kc = kcService.findOne(1);
		List<ZahtevZaRegistraciju> zahtevi = kcService.getZahteviZaReg(kc);
		List<ZahtevZaRegDTO> ret = new ArrayList<ZahtevZaRegDTO>();
		for (ZahtevZaRegistraciju zahtevZaReg : zahtevi) {
			ret.add(new ZahtevZaRegDTO(zahtevZaReg));
		}

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	/*
	 * Funkcija za aktivaciju profila admina KC
	 */
	@GetMapping("/KC/aktivacija/{token}")
	public ResponseEntity<Object> aktivacijaNaloga(@PathVariable String token){
		ConfirmationToken confToken = tokenService.finByToken(token);

		if (confToken != null) {
			Pacijent pacijent = pacijentService.findByjmbg(confToken.getJMBG());
			AdministratorKlinickogCentra adminKC = adminKCService.findByjmbg(confToken.getJMBG());
			AdministratorKlinike adminK = adminService.findByjmbg(confToken.getJMBG());
			Lekar lekar = lekarService.findByjmbg(confToken.getJMBG());
			MedicinskaSestra medSes = medSesService.findByjmbg(confToken.getJMBG());
			
			if(pacijent != null ) {
				return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
			}
			else if(adminKC != null) {
				return new ResponseEntity<>(new AdminKCDTO(adminKC), HttpStatus.OK);
			}
			else if(adminK != null) {
				return new ResponseEntity<>(new AdminKDTO(adminK), HttpStatus.OK);
			}
			else if(lekar != null) {
				return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
			}
			else if(medSes != null) {
				return new ResponseEntity<>(new MedicinskaSestraDTO(medSes), HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Link nije važeći", HttpStatus.NOT_FOUND);
			}
			
		} else {
			return new ResponseEntity<>("Link nije važeći", HttpStatus.NOT_FOUND);
		}
		
	}	

}
