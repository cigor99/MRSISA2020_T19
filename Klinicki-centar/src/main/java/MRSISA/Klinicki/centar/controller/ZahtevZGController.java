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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pregled;
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
			zahtev.setLekar(lekar);
			lekar.getZahteviZaGodisnji().add(zahtev);
			lekar = lekarService.save(lekar);
			Set<Pregled> pregledi = lekar.getPregledi();
			Iterator<Pregled> value = pregledi.iterator();

//			Set<ZahtevZaGodisnjiOdmor> godisnji = lekar.getZahteviZaGodisnji();
//			Iterator<ZahtevZaGodisnjiOdmor> it1 = godisnji.iterator();
//			Date datumPoc = null;
//			Date datumKraj = null;
//			ZahtevZaGodisnjiOdmor zzg = null;
//			while (it1.hasNext()) {
//				zzg = it1.next();
//				System.out.println(zzg.getId());
//				if (zzg != null) {
//					
//					datumKraj = zzg.getKrajnjiDatum();
//					datumPoc = zzg.getPocetniDatum();
//					System.out.println(datumPoc + "       " + datumKraj);
//					if (datumPoc.after(zahtev.getPocetniDatum()) && datumKraj.before(zahtev.getKrajnjiDatum())) {
//						return new ResponseEntity<>("U izabranom terminu ste već na godišnjem odmoru",
//								HttpStatus.BAD_REQUEST);
//					}
//				}
//			}

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
		} else {

			zahtev.setMedicinskaSestra(medSes);
			medSes.getZahteviZaGodisnji().add(zahtev);
			medSes = medicinskaSestraSerive.save(medSes);

		}

		zahtev = zahtevService.save(zahtev);

		return new ResponseEntity<>(new ZahtevZaGodisnjiDTO(zahtev), HttpStatus.OK);
	}

	@GetMapping("/ZZG/getZahtev")
	public ResponseEntity<Object> getAll() {
		Object logged = request.getSession().getAttribute("current");
		Object tipKorisnika = request.getSession().getAttribute("tip");
		System.out.println(request.getSession().getId());
		System.out.println(logged);
		System.err.println(tipKorisnika);
		List<ZahtevZaGodisnjiOdmor> godisnji = zahtevService.findAll();
		List<ZahtevZaGodisnjiDTO> retVal = new ArrayList<ZahtevZaGodisnjiDTO>();
		if (tipKorisnika.equals("lekar")) {

			for (ZahtevZaGodisnjiOdmor zahtevZaGodisnjiOdmor : godisnji) {
				if (zahtevZaGodisnjiOdmor.getLekar() != null) {
					if (zahtevZaGodisnjiOdmor.getLekar().getId() == ((LekarDTO) logged).getId()) {
						retVal.add(new ZahtevZaGodisnjiDTO(zahtevZaGodisnjiOdmor));
					}
				}
			}
		} else if (tipKorisnika.equals("sestra")) {
			for (ZahtevZaGodisnjiOdmor zahtevZaGodisnjiOdmor : godisnji) {
				if (zahtevZaGodisnjiOdmor.getMedicinskaSestra() != null) {
					if (zahtevZaGodisnjiOdmor.getMedicinskaSestra().getId() == ((MedicinskaSestraDTO) logged).getId()) {
						retVal.add(new ZahtevZaGodisnjiDTO(zahtevZaGodisnjiOdmor));
					}
				}
			}
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

}
