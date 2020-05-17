package MRSISA.Klinicki.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;

public interface AdminKRepository extends JpaRepository<AdministratorKlinike, Integer> {

	AdministratorKlinike findByjmbg(String jmbg);

}
