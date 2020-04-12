package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;

import MRSISA.Klinicki.centar.domain.Lekar;

public interface LekarService {

	void dodajLekara(Lekar lekar);

	ArrayList<Lekar> getLekari();
	
}