package MRSISA.Klinicki.centar.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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

import MRSISA.Klinicki.centar.domain.IzvestajPregleda;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeRecepta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.dto.ReceptDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaRegDTO;
import MRSISA.Klinicki.centar.service.LekService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PregledService;
import MRSISA.Klinicki.centar.service.ReceptiService;

@RestController
public class ReceptiController {

	@Autowired
	private ReceptiService receptiService;

	@Autowired
	private MedicinskaSestraSerive medSesService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private LekService lekService;

	//Funkcija vraća sve neoverene recepte
	//Prolazi kroj tabelu recepti iz BP i proverava stanje recepta
	//Ukoliko je stanje NIJE_OVEREN dodaje ga u listu koju vraća kao povratnu vrednost
	@GetMapping("/recepti/getNeoverene") 
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

	//Funkcija overava recept
	//Prima, id recepta koji treba da se overi i id medicinske sestra koja overeca recept, kao parametarz
	@PutMapping("/recepti/overi/{id}/{medSesID}") /// {medSesID}"
	public ResponseEntity<ReceptDTO> overi(@PathVariable Integer id, @PathVariable Integer medSesID) { // @PathVariable Integer medSesID
		Recept recept = receptiService.findOne(id);
		System.out.println(medSesID);
		MedicinskaSestra medSes = medSesService.findOne(medSesID);
		if (recept != null && medSes != null) { // && medSes != null
			recept.setStanjeRecepta(StanjeRecepta.OVEREN);
			recept.setMedicinskaSestra(medSes);
			medSes.getRecepti().add(recept);

			medSes = medSesService.save(medSes);
			recept = receptiService.save(recept);

			return new ResponseEntity<>(new ReceptDTO(recept), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//Funkcija dodaje recept u bazu podataka
	//Ukoliko lekar nije pronađen vraća NOT_FOUND
	//Ukoliko recept ne sadrži nijedan lek vraća BAD_REQUEST
	//Ukoliko lek koji želimo da dodamo u recept ne postoji u bazi prijavljuje se greška NOT_FOUND
	@PostMapping("/recepti/add")
	public ResponseEntity<Object> addRecept(@RequestBody ReceptDTO receptDTO) {
		System.out.println("DODAVANJE RECEPTA");
		Recept recept = new Recept();
		recept.setStanjeRecepta(StanjeRecepta.NIJE_OVEREN);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		recept.setDatumIzdavanja(sdf.format(new Date()).toString());
		Lekar lekar = lekarService.findOne(receptDTO.getLekarID());
		if (lekar == null) {
			return new ResponseEntity<>("Lekar nije pronađen", HttpStatus.NOT_FOUND);
		}
		if(receptDTO.getLekoviID().size() == 0) {
			return new ResponseEntity<>("Niste dodali nijedan lek u recept", HttpStatus.BAD_REQUEST);
		}
		for (Integer id: receptDTO.getLekoviID()) {
			Lek lek = lekService.findOne(id);
			if(lek == null) {
				return new ResponseEntity<>("Lek koji ste dodali u recept ne postoji", HttpStatus.NOT_FOUND);
			}
			recept.getLekovi().add(lek);
			lek.getRecepti().add(recept);
			lek = lekService.save(lek);
		}
		recept.setLekar(lekar);
		lekar.getRecepti().add(recept);
		lekar = lekarService.save(lekar);
		// D O D A T I N A K N A D N O ! ! ! ! !
//		Pregled pregled = pregledService.findOne(receptDTO.getPregledID());
//		if (pregled == null) {
//			return new ResponseEntity<>("Pregled nije pronađen", HttpStatus.NOT_FOUND);
//		}
//		recept.setPregled(pregled);
//		recept.setIzvestajiPregleda();
		// D O D A T I N A K N A D N O ! ! ! ! !
		
		recept = receptiService.save(recept);

		
		return new ResponseEntity<>(new ReceptDTO(recept), HttpStatus.OK);
	}

}
