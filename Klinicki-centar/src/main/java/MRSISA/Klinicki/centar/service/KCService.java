package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.repository.KCRepository;

@Service
public class KCService {
	
	@Autowired
	private KCRepository kcRepository;
	
	
	public List<Lek> getLekovi(KlinickiCentar kc){
		Set<Lek> set = kc.getSifranikLekova();
		List<Lek> lekovi = new ArrayList<Lek>();
		Iterator<Lek> iter = set.iterator();
		while(iter.hasNext()) {
			lekovi.add(iter.next());
		}
		return lekovi;
	}
	
	public List<Dijagnoza> getDijagnoze(KlinickiCentar kc){
		Set<Dijagnoza> set = kc.getSifarnikDijagnoza();
		List<Dijagnoza> dijagnoze = new ArrayList<Dijagnoza>();
		Iterator<Dijagnoza> iter = set.iterator();
		while(iter.hasNext()) {
			dijagnoze.add(iter.next());
		}
		return dijagnoze;
	}
	

	
	
	public KlinickiCentar findOne(Integer ID) {
		return kcRepository.findById(ID).orElse(null);
	}

	public List<ZahtevZaRegistraciju> getZahteviZaReg(KlinickiCentar kc) {
		List<ZahtevZaRegistraciju> zahtevi = new ArrayList<ZahtevZaRegistraciju>();
		Set<ZahtevZaRegistraciju> set = kc.getZahteviZaReg();
		Iterator<ZahtevZaRegistraciju> iter = set.iterator();
		while(iter.hasNext()) {
			zahtevi.add(iter.next());
		}
		return zahtevi;
	}

	public ZahtevZaRegistraciju findZahtevZaReg(KlinickiCentar kc, Integer id) {
		Set<ZahtevZaRegistraciju> set = kc.getZahteviZaReg();
		Iterator<ZahtevZaRegistraciju> iter = set.iterator();

		while(iter.hasNext()) {
			if(iter.next().getId().equals(id)) {
				return iter.next();
			}
		}
		return null;	
	}

	



//	public KlinickiCentar addLek(Lek lek, KlinickiCentar kc) {
//		
//		return null;
//	}
	
}
