package MRSISA.Klinicki.centar.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.dto.AdminKCDTO;
import MRSISA.Klinicki.centar.dto.AdminKDTO;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.LoginDTO;
import MRSISA.Klinicki.centar.dto.MedicinskaSestraDTO;
import MRSISA.Klinicki.centar.dto.Osoba;
import MRSISA.Klinicki.centar.dto.PacijentDTO;
import MRSISA.Klinicki.centar.service.AdminKCSerivce;
import MRSISA.Klinicki.centar.service.AdminKService;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.PacijentService;
import MRSISA.Klinicki.centar.service.TipPregledaService;

/*Kontroler u kom se nalaze metode za prijavu korisnika*/

@RestController
@RequestMapping("/login")
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
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@Autowired
	HttpServletRequest request;
	
	
	/*Object obj = request.getSession().getAttribute("current");
		Lekar l2 = (Lekar) obj;
		System.out.println(l2.getIme());
	 */
	
	

	
	/*Post zahtev
	 * Funkcija za prijavu na sistem
	 *Proverava se da li su svi podaci ispravno uneseni
	 *Ako jesu trazi se da li postoji korisnik sa tim login podacima
	 *Ako da zakaci se za sesiju
	 *Ako ne onda se vraca error 400 */
	@PostMapping("/prijava")
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
		if(loginDTO.getLozinka().equals("XAEA12")) {
			return new ResponseEntity<>("Niste aktivirali nalog! Poslat Vam je email sa linkom za aktivaciju naloga.",HttpStatus.CONFLICT);
		}
		if(!loginDTO.proveraPolja()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<TipPregleda> tipovi = tipPregledaService.findAll();
		
		for(TipPregleda tip : tipovi) {
			for(Lekar l : tip.getLekari()) {
				System.out.println(l);
			}
		}
		
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent p : pacijenti) {
			if (p.getStanjePacijenta().equals(StanjePacijenta.AKTIVAN) && p.getEmail().equals(loginDTO.getEmail())
					&& p.getLozinka().equals(loginDTO.getLozinka())) {
				PacijentDTO pacijentDTO = new PacijentDTO(p);
				request.getSession().setAttribute("current", pacijentDTO);
				request.getSession().setAttribute("tip", "pacijent");

				return new ResponseEntity<>(pacijentDTO, HttpStatus.ACCEPTED);
			}
		}

		List<Lekar> lekari = lekarService.findAll();
		for (Lekar l : lekari) {
			if (l.getEmail().equals(loginDTO.getEmail()) && l.getLozinka().equals(loginDTO.getLozinka())) {
				LekarDTO lekarDTO = new LekarDTO(l);
				request.getSession().setAttribute("current", lekarDTO);
				request.getSession().setAttribute("tip", "lekar");

				return new ResponseEntity<>(lekarDTO, HttpStatus.ACCEPTED);
			}
		}

		List<AdministratorKlinike> adminiKlinika = adminService.findAll();
		for (AdministratorKlinike ak : adminiKlinika) {
			if (ak.getEmail().equals(loginDTO.getEmail()) && ak.getLozinka().equals(loginDTO.getLozinka())) {
				AdminKDTO adminKDTO = new AdminKDTO(ak);
				request.getSession().setAttribute("current", adminKDTO);
				request.getSession().setAttribute("tip", "adminKlinike");

				return new ResponseEntity<>(adminKDTO, HttpStatus.ACCEPTED);
			}
		}

		List<AdministratorKlinickogCentra> admini = adminKCService.findAll();
		for (AdministratorKlinickogCentra adm : admini) {
			if (adm.getEmail().equals(loginDTO.getEmail()) && adm.getLozinka().equals(loginDTO.getLozinka())) {
				AdminKCDTO adminKCDTO = new AdminKCDTO(adm);
				request.getSession().setAttribute("current", adminKCDTO);
				if(adminKCDTO.getEmail().equals("super")) {
					request.getSession().setAttribute("tip", "superAdmin");
					System.out.println("SUPERADMIN");
					return new ResponseEntity<>(adminKCDTO, HttpStatus.ACCEPTED);
				}
				request.getSession().setAttribute("tip", "adminKC");

				return new ResponseEntity<>(adminKCDTO, HttpStatus.ACCEPTED);
			}
		}

		List<MedicinskaSestra> medSestre = medSesService.findAll();
		for (MedicinskaSestra ms : medSestre) {
			if (ms.getEmail().equals(loginDTO.getEmail()) && ms.getLozinka().equals(loginDTO.getLozinka())) {
				MedicinskaSestraDTO medSestraDTO = new MedicinskaSestraDTO(ms);
				request.getSession().setAttribute("current", medSestraDTO);
				request.getSession().setAttribute("tip", "sestra");

				return new ResponseEntity<>(medSestraDTO, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("/tipKorisnika")
	public ResponseEntity<String> getTipKorisnika(){
		String tip = (String) request.getSession().getAttribute("tip");
		return new ResponseEntity<>(tip, HttpStatus.OK);
	}
	
	@GetMapping("/getLoggedUser")
	public ResponseEntity<Osoba> getLoggedUser(){
		Osoba osoba = (Osoba) request.getSession().getAttribute("current");
		return new ResponseEntity<>(osoba, HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logOut(){
		request.getSession().setAttribute("tip", "");
		String tip = (String) request.getSession().getAttribute("tip");
		return new ResponseEntity<>(tip, HttpStatus.OK);
	}
	
	@PostMapping("/proveriStaruLozinku")
	public ResponseEntity<Boolean> proveriStaruLozinku(String lozinka){
		Osoba korisnik = (Osoba) request.getSession().getAttribute("current");
		if(korisnik.getLozinka().equals(lozinka)) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
	}
	
}
