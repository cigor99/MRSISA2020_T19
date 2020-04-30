package MRSISA.Klinicki.centar.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.Lekar;


public interface LekarRepository extends JpaRepository<Lekar, Integer> {
	
	Lekar findByEmail(String email);
	
	Page<Lekar> findAll(Pageable pageable);


}
