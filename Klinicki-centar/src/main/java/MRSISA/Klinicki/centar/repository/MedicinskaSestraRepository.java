package MRSISA.Klinicki.centar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.MedicinskaSestra;

public interface MedicinskaSestraRepository extends JpaRepository<MedicinskaSestra, Integer> {
	MedicinskaSestra findByEmail(String email);
	
	Page<MedicinskaSestra> findAll(Pageable pageable);

	MedicinskaSestra findByjmbg(String jmbg);
}
