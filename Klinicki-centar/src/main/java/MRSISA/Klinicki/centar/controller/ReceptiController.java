package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeRecepta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.ReceptDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaRegDTO;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.ReceptiService;

@RestController
public class ReceptiController {

	@Autowired
	private ReceptiService receptiService;

	@Autowired
	private MedicinskaSestraSerive medSesService;

	@GetMapping("/recepti/getNeoverene") // VRACA SAMO NEOVERENE
	public ResponseEntity<List<ReceptDTO>> getAll() {
		List<Recept> recepti = receptiService.findAll();
		List<ReceptDTO> retVal = new ArrayList<ReceptDTO>();
		for (Recept recept : recepti) {
			if (recept.getStanjeRecepta().equals(StanjeRecepta.NIJE_OVEREN)) {
				retVal.add(new ReceptDTO(recept));
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@PutMapping("/recepti/overi/{id}") /// {medSesID}"
	public ResponseEntity<ReceptDTO> overi(@PathVariable Integer id) { // @PathVariable Integer medSesID
		Recept recept = receptiService.findOne(id);
//		MedicinskaSestra medSes = medSesService.findOne(medSesID);
		if (recept != null) { // && medSes != null
			recept.setStanjeRecepta(StanjeRecepta.OVEREN);
//			recept.setMedicinskaSestra(medSes);
//			medSes.getRecepti().add(recept);

//			medSes = medSesService.save(medSes);
			recept = receptiService.save(recept);

			return new ResponseEntity<>(new ReceptDTO(recept), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
