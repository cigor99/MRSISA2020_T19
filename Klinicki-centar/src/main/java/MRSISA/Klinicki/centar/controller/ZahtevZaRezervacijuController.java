package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaPregled;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaPregledDTO;
import MRSISA.Klinicki.centar.service.ZahtevZPService;

@RestController
public class ZahtevZaRezervacijuController {

	@Autowired
	private ZahtevZPService zzpService;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/ZZP/getAll")
	public ResponseEntity<Object> getall() {
		List<ZahtevZaPregled> zahtevi = zzpService.findAll();
		List<ZahtevZaPregledDTO> retVal = new ArrayList<ZahtevZaPregledDTO>();
		AdminKDTO admink = (AdminKDTO) request.getSession().getAttribute("current");
		int klinika = admink.getKlinikaID();
		System.out.println("Usao");
		for (ZahtevZaPregled zahtev : zahtevi) {
			System.out.println(zahtev);
			int idk = zahtev.getPregled().getLekar().getKlinika().getId();
			if (zahtev.getStanjeZahteva().equals(StanjeZahteva.NA_CEKANJU) && idk == klinika) {
				System.out.println(zahtev.getStanje());
				retVal.add(new ZahtevZaPregledDTO(zahtev));
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

}
