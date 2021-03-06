package MRSISA.Klinicki.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.Dijagnoza;

public interface DijagnozaRepository extends JpaRepository<Dijagnoza, Integer> {

	Dijagnoza findBySifra(String sifra);

}
