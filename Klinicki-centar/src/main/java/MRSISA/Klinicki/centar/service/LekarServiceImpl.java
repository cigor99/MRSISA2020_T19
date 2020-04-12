package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Lekar;

@Service
public class LekarServiceImpl implements LekarService {
	ArrayList<Lekar> lekari = new ArrayList<Lekar>();
	
	@Override
	public ArrayList<Lekar> getLekari(){
		return lekari;
	}

	@Override
	public void dodajLekara(Lekar lekar) {
		lekari.add(lekar);
	}
    

}
