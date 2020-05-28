package MRSISA.Klinicki.centar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaGodisnjiOdmor;
import MRSISA.Klinicki.centar.dto.LekarDTO;
import MRSISA.Klinicki.centar.dto.MedicinskaSestraDTO;
import MRSISA.Klinicki.centar.dto.ZahtevZaGodisnjiDTO;
import MRSISA.Klinicki.centar.service.LekarService;
import MRSISA.Klinicki.centar.service.MedicinskaSestraSerive;
import MRSISA.Klinicki.centar.service.ZahtevZGService;

@RestController
public class ZahtevZGController {

	@Autowired
	private ZahtevZGService zahtevService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private MedicinskaSestraSerive medicinskaSestraSerive;

	@Autowired
	HttpServletRequest request;

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Dodavanje novog zahteva za godišnji odomor, moguće samo za medicinsko
	 * osoblje, provera da li za izabrani termin god. odmora postoje neki zakazani
	 * pregled
	 */
	@PostMapping("/ZZG/add")
	public ResponseEntity<Object> addZahtev(@RequestBody ZahtevZaGodisnjiDTO zahtevDTO) {
		ZahtevZaGodisnjiOdmor zahtev = new ZahtevZaGodisnjiOdmor();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Lekar lekar = null;
		MedicinskaSestra medSes = null;

		try {
			zahtev.setKrajnjiDatum(sdf.parse(zahtevDTO.getKrajnjiDatum()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			zahtev.setPocetniDatum(sdf.parse(zahtevDTO.getPocetniDatum()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (zahtev.getPocetniDatum().after(zahtev.getKrajnjiDatum())
				|| zahtev.getKrajnjiDatum().before(zahtev.getPocetniDatum())) {
			return new ResponseEntity<>("Neispravan redosled datuma", HttpStatus.BAD_REQUEST);
		}
		Date danas = new Date();
		if(zahtev.getPocetniDatum().before(danas)){
			return new ResponseEntity<>("Neispravan datum", HttpStatus.BAD_REQUEST);
		}
		List<ZahtevZaGodisnjiOdmor> zahtevi = zahtevService.findAll();

		if (zahtevDTO.getLekar() != null) {
			lekar = lekarService.findOne(zahtevDTO.getLekar());
		}

		if (zahtevDTO.getMedicinskaSestra() != null) {
			medSes = medicinskaSestraSerive.findOne(zahtevDTO.getMedicinskaSestra());
		}

		if (lekar == null && medSes == null) {
			return new ResponseEntity<>("Medicinsko osoblje nije pronađeno", HttpStatus.NOT_FOUND);
		}

		if (lekar != null && medSes != null) {
			return new ResponseEntity<>("Neispravni podaci!", HttpStatus.NOT_FOUND);
		}

		if (medSes == null) {

			Set<Pregled> pregledi = lekar.getPregledi();
			Iterator<Pregled> value = pregledi.iterator();

			Set<ZahtevZaGodisnjiOdmor> godisnji = lekar.getZahteviZaGodisnji();
			Iterator<ZahtevZaGodisnjiOdmor> it1 = godisnji.iterator();
			Date datumPoc = null;
			Date datumKraj = null;
			ZahtevZaGodisnjiOdmor zzg = null;

			// PROVERAVA DA LI VEC POSTOJI ZAHTEV ZA GODISNJI U IZABRANOM TERMINU
			while (it1.hasNext()) {
				zzg = it1.next();
				if (zzg != null) {
					datumKraj = zzg.getKrajnjiDatum();
					datumPoc = zzg.getPocetniDatum();
					if (zahtev.getPocetniDatum().after(datumPoc) && zahtev.getKrajnjiDatum().before(datumKraj)
							&& zahtevDTO.getLekar() == zzg.getLekar().getId()) {
						return new ResponseEntity<>("U izabranom terminu ste već na godišnjem odmoru",
								HttpStatus.BAD_REQUEST);
					}
				}
			}

			Date datum = null;
			while (value.hasNext()) {
				datum = value.next().getDatum();
				if ((datum.after(zahtev.getPocetniDatum()) && datum.before(zahtev.getKrajnjiDatum()))) {
					return new ResponseEntity<>("Godišnji odmor za izabrani datum nije moguć", HttpStatus.BAD_REQUEST);
				}
				if (sdf.format(datum).equals(sdf.format(zahtev.getPocetniDatum()))
						|| sdf.format(datum).equals(sdf.format(zahtev.getKrajnjiDatum()))) {
					return new ResponseEntity<>("Godišnji odmor za izabrani datum nije moguć", HttpStatus.BAD_REQUEST);
				}
			}
			zahtev.setLekar(lekar);
			lekar.getZahteviZaGodisnji().add(zahtev);
			lekar = lekarService.save(lekar);
		} else {

			zahtev.setMedicinskaSestra(medSes);
			medSes.getZahteviZaGodisnji().add(zahtev);
			medSes = medicinskaSestraSerive.save(medSes);

		}
		zahtev.setStanjeZahteva(StanjeZahteva.NA_CEKANJU);
		zahtev = zahtevService.save(zahtev);

		if (medSes == null) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
			Object logged = request.getSession().getAttribute("current");
			SimpleMailMessage msg = new SimpleMailMessage();
			for (AdministratorKlinike admin : lekar.getKlinika().getAdministratori()) {
				msg.setTo(admin.getEmail());
				msg.setSubject("Zahtev za godišnji odmor");
				msg.setText(((LekarDTO) logged).getIme() + " " + ((LekarDTO) logged).getPrezime()
						+ " je zatražio godišnji odmor u periodu od " + sdf1.format(zahtev.getPocetniDatum()) + " do "
						+ sdf1.format(zahtev.getKrajnjiDatum()));

				try {
					javaMailSender.send(msg);
				} catch (MailException e) {
//					 e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}

		}else if (lekar == null) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
			Object logged = request.getSession().getAttribute("current");
			SimpleMailMessage msg = new SimpleMailMessage();
			for (AdministratorKlinike admin : medSes.getKlinika().getAdministratori()) {
				msg.setTo(admin.getEmail());
				msg.setSubject("Zahtev za godišnji odmor");
				msg.setText(((MedicinskaSestraDTO) logged).getIme() + " " + ((MedicinskaSestraDTO) logged).getPrezime()
						+ " je zatražio godišnji odmor u periodu od " + sdf1.format(zahtev.getPocetniDatum()) + " do "
						+ sdf1.format(zahtev.getKrajnjiDatum()));

				try {
					javaMailSender.send(msg);
				} catch (MailException e) {
//					 e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		}

		return new ResponseEntity<>(new ZahtevZaGodisnjiDTO(zahtev), HttpStatus.OK);
	}

	/*
	 * Dobavljanje svih zahteva za godišnji odmor u zavisnosti od tipa korisnika
	 * lekar/medicinska sestra
	 * 
	 */
	@GetMapping("/ZZG/getZahtev")
	public ResponseEntity<Object> getAll() {
		Object logged = request.getSession().getAttribute("current");
		Object tipKorisnika = request.getSession().getAttribute("tip");
		List<ZahtevZaGodisnjiOdmor> godisnji = zahtevService.findAll();
		List<ZahtevZaGodisnjiDTO> retVal = new ArrayList<ZahtevZaGodisnjiDTO>();
		if (tipKorisnika.equals("lekar")) {

			for (ZahtevZaGodisnjiOdmor zahtevZaGodisnjiOdmor : godisnji) {
				if (zahtevZaGodisnjiOdmor.getLekar() != null) {
					if (zahtevZaGodisnjiOdmor.getLekar().getId() == ((LekarDTO) logged).getId()
							&& zahtevZaGodisnjiOdmor.getStanjeZahteva() == StanjeZahteva.PRIHVACEN) {
						retVal.add(new ZahtevZaGodisnjiDTO(zahtevZaGodisnjiOdmor));
					}
				}
			}
		} else if (tipKorisnika.equals("sestra")) {
			for (ZahtevZaGodisnjiOdmor zahtevZaGodisnjiOdmor : godisnji) {
				if (zahtevZaGodisnjiOdmor.getMedicinskaSestra() != null) {
					if (zahtevZaGodisnjiOdmor.getMedicinskaSestra().getId() == ((MedicinskaSestraDTO) logged).getId()
							&& zahtevZaGodisnjiOdmor.getStanjeZahteva() == StanjeZahteva.PRIHVACEN) {
						retVal.add(new ZahtevZaGodisnjiDTO(zahtevZaGodisnjiOdmor));
					}
				}
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

}
