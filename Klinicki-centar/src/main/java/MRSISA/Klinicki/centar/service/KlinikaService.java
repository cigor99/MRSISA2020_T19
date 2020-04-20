package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.repository.KlinikaRepository;


@Service
public class KlinikaService {
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	ArrayList<Klinika> klinike = new ArrayList<Klinika>();
	
	public ArrayList<Klinika> getKlinike(){
		return klinike;
	}

	public Klinika addKlinika(Klinika klinika) {
		return klinikaRepository.save(klinika);
	}
	
	public void remove(Integer ID) {
		klinikaRepository.deleteById(ID);
	}
	
	public Klinika findOne(Integer ID) {
		System.out.println(klinikaRepository.findById(ID).orElseGet(null));
		return klinikaRepository.findById(ID).orElseGet(null);
	}
}
