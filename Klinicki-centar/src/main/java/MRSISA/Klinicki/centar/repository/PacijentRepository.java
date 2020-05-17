package MRSISA.Klinicki.centar.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import MRSISA.Klinicki.centar.domain.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer>{
	
	//public void dodajPacijenta(Pacijent pacijent);
	
	//public Collection<Pacijent> vratiPacijente();
	
	//public void izmeniPacijenta(String idStarog, Pacijent noviPacijent);

	Pacijent findOneByEmail(String email);
	
	Page<Pacijent> findAll(Pageable pageable);
	
	Pacijent findByjmbg(String jmbg);
}
