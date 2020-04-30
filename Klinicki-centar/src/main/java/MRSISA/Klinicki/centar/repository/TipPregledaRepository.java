package MRSISA.Klinicki.centar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Integer> {

	TipPregleda findOneByNaziv(String naziv);
	
	Page<TipPregleda> findAll(Pageable pageable);

}
