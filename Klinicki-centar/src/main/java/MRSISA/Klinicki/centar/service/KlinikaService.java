package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Klinika;


@Service
public class KlinikaService {
	ArrayList<Klinika> klinike = new ArrayList<Klinika>();
	
	public ArrayList<Klinika> getKlinike(){
		return klinike;
	}

	public void addKlinika(Klinika klinika) {
		klinike.add(klinika);
	}
}
