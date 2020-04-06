package MRSISA.Klinicki.centar.repository;

import java.util.Collection;

import MRSISA.Klinicki.centar.domain.Lekar;


public interface LekarRepository {

	Collection<Lekar> findAll();

	Lekar create(Lekar lekar);

	Lekar findOne(String email);


}
