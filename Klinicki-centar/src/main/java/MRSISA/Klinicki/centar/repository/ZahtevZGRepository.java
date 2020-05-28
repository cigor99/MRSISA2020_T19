package MRSISA.Klinicki.centar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import MRSISA.Klinicki.centar.domain.ZahtevZaGodisnjiOdmor;

public interface ZahtevZGRepository extends JpaRepository<ZahtevZaGodisnjiOdmor, Integer> {

	Page<ZahtevZaGodisnjiOdmor> findAll(Pageable pageable);
}
