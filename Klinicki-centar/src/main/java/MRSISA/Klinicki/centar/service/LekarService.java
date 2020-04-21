package MRSISA.Klinicki.centar.service;

import java.sql.SQLException;
import java.util.ArrayList;

import MRSISA.Klinicki.centar.domain.Lekar;

public interface LekarService {

	void dodajLekara(Lekar lekar) throws ClassNotFoundException, SQLException;

	ArrayList<Lekar> getLekari();
	
}