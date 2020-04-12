package MRSISA.Klinicki.centar.repository;

import java.util.Collection;
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
	
	public Collection<Pacijent> vratiPacijente(){
		return pacijenti.values();
	}

	@Override
	public void izmeniPacijenta(String idStarog, Pacijent noviPacijent) {
		obrisiPacijenta(idStarog);
		dodajPacijenta(noviPacijent);
	}
	
	public void obrisiPacijenta(String id) {
		pacijenti.remove(id);
	}

}
