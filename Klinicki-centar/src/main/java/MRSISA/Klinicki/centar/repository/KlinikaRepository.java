package MRSISA.Klinicki.centar.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.Klinika;

public interface KlinikaRepository  extends JpaRepository<Klinika, Integer>{
	

	Klinika findOneByNaziv(String naziv);
	
	Page<Klinika> findAll(Pageable pageable);
	
//	Page<Klinika> findall(Pageable pageable);
}
