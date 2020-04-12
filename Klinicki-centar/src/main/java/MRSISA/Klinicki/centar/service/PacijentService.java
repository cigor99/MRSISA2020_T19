package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.repository.PacijentRepository;

@Service
public class PacijentService {
	@Autowired
	private final PacijentRepository pacijentRepo;
	
	@Autowired
	public PacijentService(@Qualifier("PacijentRepo") PacijentRepository pacijentRepo) {
		this.pacijentRepo = pacijentRepo;
	}
	
	public void dodajPacijenta(Pacijent pacijent) {
		pacijentRepo.dodajPacijenta(pacijent);
	}
	
	public List<Pacijent> vratiPacijente(){
		ArrayList<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentRepo.vratiPacijente()) {
			pacijenti.add(p);
		}
		return pacijenti;
	}
	
	public Pacijent pronadjiPacijenta(String email) {
		for(Pacijent p : pacijentRepo.vratiPacijente()) {
			if(p.getEmail().equalsIgnoreCase(email))
				return p;
		}
		return null;
	}
	
	public Pacijent izmeniPacijenta(Pacijent pacijent) {
		Pacijent p = pronadjiPacijenta(pacijent.getEmail());
		if(p != null) {
			pacijentRepo.izmeniPacijenta(p.getEmail(), pacijent);
			return pacijent;
		}else {
			return null;
		}
	}
	
	
	
}
