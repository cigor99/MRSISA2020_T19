package MRSISA.Klinicki.centar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.LekDTO;
import MRSISA.Klinicki.centar.service.KCService;

@RestController
public class KCController {

	@Autowired
	private KCService kcService;

	@GetMapping("/KC/sifarnikLekova/getAll")
	public ResponseEntity<List<Lek>> getAllLekovi() {
		KlinickiCentar kc = kcService.findOne(1);
		List<Lek> lekovi = kcService.getLekovi(kc);

		return new ResponseEntity<>(lekovi, HttpStatus.OK);
	}

	@GetMapping("/KC/sifarnikDijagnoza/getAll")
	public ResponseEntity<List<Dijagnoza>> getAllDijagnoze() {
		KlinickiCentar kc = kcService.findOne(1);
		List<Dijagnoza> dijagnoze = kcService.getDijagnoze(kc);

		return new ResponseEntity<>(dijagnoze, HttpStatus.OK);
	}

	@GetMapping("/KC/ZZR/getAll")
	public ResponseEntity<List<ZahtevZaRegistraciju>> getAllZahteviZaReg() {
		KlinickiCentar kc = kcService.findOne(1);
		List<ZahtevZaRegistraciju> zahtevi = kcService.getZahteviZaReg(kc);

		return new ResponseEntity<>(zahtevi, HttpStatus.OK);
	}

	@GetMapping("/KC/ZZR/getOne/{id}")
	public ResponseEntity<ZahtevZaRegistraciju> getOne(@PathVariable Integer id) {
		KlinickiCentar kc = kcService.findOne(1);
		ZahtevZaRegistraciju zahtev = kcService.findZahtevZaReg(kc, id);

		if (zahtev != null) {
			return new ResponseEntity<>(zahtev, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	@PostMapping("/KC/sifarnikLekova/addLek")
//	public ResponseEntity<KlinickiCentarDTO> addLek(@RequestBody LekDTO lekDTO){
//		KlinickiCentar kc = kcService.findOne(1);
//		Lek lek = new Lek();
//		lek.setNaziv(lekDTO.getNaziv());
//		lek.setSifra(lekDTO.getSifra());
//		//KAKO DODATI KLINICKI CENTAR????
//		
//		lek = kcService.addLek(lek, kc);
//	
//		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.OK);
//	}	

}