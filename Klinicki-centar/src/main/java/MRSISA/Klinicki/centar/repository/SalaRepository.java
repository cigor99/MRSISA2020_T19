package MRSISA.Klinicki.centar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.Sala;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

	Sala findOneByNaziv(String naziv);
	
	Page<Sala> findAll(Pageable pageable);

}
