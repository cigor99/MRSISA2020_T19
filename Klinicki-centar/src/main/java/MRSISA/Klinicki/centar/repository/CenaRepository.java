package MRSISA.Klinicki.centar.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.Cena;
import MRSISA.Klinicki.centar.domain.TipPregleda;

public interface CenaRepository extends JpaRepository<Cena, Integer> {
	
	Cena findOneByTipPregleda(TipPregleda tipPregleda);
	
	Page<Cena> findAll(Pageable page);

}
