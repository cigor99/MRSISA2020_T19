package MRSISA.Klinicki.centar.service;

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
	
	
	
	
}
