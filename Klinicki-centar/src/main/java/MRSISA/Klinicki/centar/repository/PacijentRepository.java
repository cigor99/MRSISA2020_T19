package MRSISA.Klinicki.centar.repository;

import java.util.Collection;

import MRSISA.Klinicki.centar.domain.Pacijent;

public interface PacijentRepository {
	
	public void dodajPacijenta(Pacijent pacijent);
	
	public Collection<Pacijent> vratiPacijente();
	
	public void izmeniPacijenta(String idStarog, Pacijent noviPacijent);
}
