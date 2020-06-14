package MRSISA.Klinicki.centar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.OperacijaDTO;
import MRSISA.Klinicki.centar.dto.PregledDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaOpDTO;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.OperacijaService;

@RestController
public class OperacijaController {

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	HttpServletRequest request;

	@GetMapping("/Operacija/getOne/{id}")
	public ResponseEntity<Object> getOne(@PathVariable Integer id) {
		Operacija op = operacijaService.findById(id);
		if (op == null) {
			return new ResponseEntity<>("Operacija nije pronadjena", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new OperacijaDTO(op), HttpStatus.OK);
	}
	
	@GetMapping("/Operacija/all")
	public ResponseEntity<List<OperacijaDTO>> getAllOperacije() {
		List<Operacija> operacije = operacijaService.findAll();
		List<OperacijaDTO> operacijeDTO = new ArrayList<OperacijaDTO>();

		int klinika = -1;
		String tip = (String) request.getSession().getAttribute("tip");
		if (tip.equals("lekar")) {
			LekarDTO lekarDTO = (LekarDTO) request.getSession().getAttribute("current");
			Lekar lekar = lekarService.findOne(lekarDTO.getId());
			// klinika = lekar.getKlinika().getId();
			for (Operacija o : lekar.getOperacije()) {
				if (o.getSala() != null) {
					operacijeDTO.add(new OperacijaDTO(o));
				}
			}
		}
		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	//Funkcija koja vraca sve operacije lekara
	@GetMapping("/Operacija/getAllLekar/{id}")
	public ResponseEntity<Object> getAllLekar(@PathVariable Integer id) {
		List<Operacija> operacije = operacijaService.findAll();
		List<OperacijaDTO> retVal = new ArrayList<OperacijaDTO>();
		Lekar l = lekarService.findOne(id);
		if (l == null) {
			return new ResponseEntity<>("Lekar nije pronadjen", HttpStatus.BAD_REQUEST);
		}

		for (Operacija op : operacije) {
			for (Lekar lekar : op.getLekari()) {
				if (lekar.getId() == l.getId()) {
					retVal.add(new OperacijaDTO(op));
				}
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
//	Funkcija vraća listu operacija za određeni dan i mesec
	@GetMapping("/getDnevneOperacije/{dan}/{mesec}")
	public ResponseEntity<Object> getDnevneOperacije(@PathVariable String dan, @PathVariable String mesec){
		Object obj = request.getSession().getAttribute("current");
		Object tip = request.getSession().getAttribute("tip");
		System.err.println(tip);
		LekarDTO l = (LekarDTO) obj;
		System.out.println(l.getIme());
		
		List<Operacija>operacije =  operacijaService.findAll();
		List<OperacijaDTO> retVal = new ArrayList<OperacijaDTO>();
		for(Operacija op: operacije) {
			if(((op.getDatum().getMonth()+1)+"-"+op.getDatum().getDate()).equals(mesec+"-"+dan)){
				for (Lekar lekar : op.getLekari()) {
					if(lekar.getId() == l.getId()) {
						retVal.add(new OperacijaDTO(op));
					}
				}
				
			}
		}
		
		return new ResponseEntity<>(retVal, HttpStatus.OK); 
	}

}
