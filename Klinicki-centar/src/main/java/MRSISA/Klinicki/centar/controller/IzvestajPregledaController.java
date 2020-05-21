package MRSISA.Klinicki.centar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.IzvestajPregleda;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.dto.IzvestajPregledaDTO;
import MRSISA.Klinicki.centar.service.DijagnozaService;
import MRSISA.Klinicki.centar.service.IzvestajPregledaService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.ReceptiService;

@RestController
public class IzvestajPregledaController {

	@Autowired
	private IzvestajPregledaService izvestajService;

	@Autowired
	private DijagnozaService dijagnozaService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private ReceptiService receptService;

	@PostMapping("/izvestajPregleda/add")
	public ResponseEntity<Object> addIzvestaj(@RequestBody IzvestajPregledaDTO izvestajDTO) {
		IzvestajPregleda izvestaj = new IzvestajPregleda();
		Dijagnoza dijagnoza = dijagnozaService.findBySifra(izvestajDTO.getDijagnoza());
//		System.out.println(dijagnoza.getNaziv());
		if (dijagnoza == null) {
			return new ResponseEntity<>("Dijagnoza nije pronadjena", HttpStatus.NOT_FOUND);
		}
		izvestaj.setDijagnoza(dijagnoza);

		Lekar lekar = lekarService.findOne(izvestajDTO.getLekar());
		if (lekar == null) {
			return new ResponseEntity<>("Lekar nije pronadjena", HttpStatus.NOT_FOUND);
		}
		izvestaj.setLekar(lekar);
		lekar.getIzvestajiPregleda().add(izvestaj);
		lekar = lekarService.save(lekar);
//		
		if (izvestajDTO.getRecept() != null) {
			Recept recept = receptService.findOne(izvestajDTO.getRecept());
			if (recept == null) {
				return new ResponseEntity<>("Recept nije pronadjena", HttpStatus.NOT_FOUND);
			}
			izvestaj.setRecept(recept);
			recept.setIzvestajiPregleda(izvestaj);
//			recept = receptService.save(recept);
		}

		izvestaj.setOpis(izvestajDTO.getOpis());

		dijagnoza.getIzvestajiPregleda().add(izvestaj);
		dijagnoza = dijagnozaService.save(dijagnoza);
		izvestaj = izvestajService.save(izvestaj);
		
		return new ResponseEntity<>(new IzvestajPregledaDTO(izvestaj), HttpStatus.OK);
	}

}
