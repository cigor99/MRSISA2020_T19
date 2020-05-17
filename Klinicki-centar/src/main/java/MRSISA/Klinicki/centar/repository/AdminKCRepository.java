package MRSISA.Klinicki.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;

public interface AdminKCRepository  extends JpaRepository<AdministratorKlinickogCentra, Integer>{
	AdministratorKlinickogCentra findByjmbg(String jmbg);
}
