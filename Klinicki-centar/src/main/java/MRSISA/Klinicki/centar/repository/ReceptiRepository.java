package MRSISA.Klinicki.centar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Recept;

public interface ReceptiRepository extends JpaRepository<Recept, Integer> {

	Page<Recept> findAll(Pageable pageable);
}
