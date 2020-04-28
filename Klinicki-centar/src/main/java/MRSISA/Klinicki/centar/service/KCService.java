package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Lek;
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

	
	
	public KlinickiCentar findOne(Integer ID) {
		return kcRepository.findById(ID).orElse(null);
	}



//	public KlinickiCentar addLek(Lek lek, KlinickiCentar kc) {
//		
//		return null;
//	}
	
}
