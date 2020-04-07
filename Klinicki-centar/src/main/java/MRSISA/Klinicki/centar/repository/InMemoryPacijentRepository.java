package MRSISA.Klinicki.centar.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import MRSISA.Klinicki.centar.domain.Pacijent;

@Repository("PacijentRepo")
public class InMemoryPacijentRepository implements PacijentRepository{

	private final ConcurrentMap<String, Pacijent> pacijenti = new ConcurrentHashMap<String, Pacijent>() {
	};
	
	@Override
	public void dodajPacijenta(Pacijent pacijent) {
		pacijenti.put(pacijent.getEmail(), pacijent);
		
	}

}
