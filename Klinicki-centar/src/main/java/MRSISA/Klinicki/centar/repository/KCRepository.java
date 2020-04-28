package MRSISA.Klinicki.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.KlinickiCentar;

public interface KCRepository extends JpaRepository<KlinickiCentar, Integer> {

}
