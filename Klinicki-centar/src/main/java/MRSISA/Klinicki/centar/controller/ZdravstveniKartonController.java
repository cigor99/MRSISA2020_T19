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
import org.springframework.web.servlet.resource.HttpResource;

import MRSISA.Klinicki.centar.domain.ZdravstveniKarton;
import MRSISA.Klinicki.centar.dto.ZdravsteniKartonDTO;
import MRSISA.Klinicki.centar.service.ZdravstveniKartonService;

@RestController
public class ZdravstveniKartonController {

	@Autowired
	private ZdravstveniKartonService zdService;

	@GetMapping("/karton/get/{JMBG}")
	public ResponseEntity<ZdravsteniKartonDTO> getKarton(@PathVariable String JMBG) {
		List<ZdravstveniKarton> kartoni = zdService.findAll();
		ZdravstveniKarton zk = null;
		for (ZdravstveniKarton zdravstveniKarton : kartoni) {
			System.out.println(zdravstveniKarton.getPacijent().getJmbg());
			if (zdravstveniKarton.getPacijent().getJmbg().equals(JMBG)) {
				zk = zdravstveniKarton;
			}
		}
		if (zk != null) {
			return new ResponseEntity<>(new ZdravsteniKartonDTO(zk), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/karton/update")
	public ResponseEntity<ZdravsteniKartonDTO> updateKartona(@RequestBody ZdravsteniKartonDTO zdDto) {
		ZdravstveniKarton zk = zdService.findOne(zdDto.getId());
		if (zk == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		zk.setDioptrija(zdDto.getDioptrija());
		zk.setVisina(zdDto.getVisina());
		zk.setTezina(zdDto.getTezina());
		zk.setKrvnaGrupa(zdDto.getKrvnaGrupa());

		zk = zdService.save(zk);
		return new ResponseEntity<>(new ZdravsteniKartonDTO(zk),HttpStatus.OK);
		//ZASTO MORA DA VRATI 

	}

	@PostMapping("/karton/add")
	public ResponseEntity<Void> addKarton(@RequestBody ZdravsteniKartonDTO zddDto) {
		ZdravstveniKarton zk = new ZdravstveniKarton();
		zk.setDioptrija(zddDto.getDioptrija());
		zk.setKrvnaGrupa(zddDto.getKrvnaGrupa());
		zk.setTezina(zddDto.getTezina());
		zk.setVisina(zddDto.getVisina());

		zk = zdService.save(zk);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
