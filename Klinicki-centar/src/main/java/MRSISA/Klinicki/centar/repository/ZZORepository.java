package MRSISA.Klinicki.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;

public interface ZZORepository extends JpaRepository<ZahtevZaOperaciju, Integer> {

}
