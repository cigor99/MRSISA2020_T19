package MRSISA.Klinicki.centar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Integer> {
	
	Page<Pregled> findAll(Pageable pageable);

}
