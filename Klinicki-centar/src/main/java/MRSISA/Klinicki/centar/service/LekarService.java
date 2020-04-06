package MRSISA.Klinicki.centar.service;

import java.util.Collection;

import MRSISA.Klinicki.centar.domain.Lekar;

public interface LekarService {

	Collection<Lekar> findAll();

	Lekar findOne(String email);

	Lekar create(Lekar lekar) throws Exception;
	
}