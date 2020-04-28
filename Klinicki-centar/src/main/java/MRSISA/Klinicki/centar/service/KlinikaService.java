package MRSISA.Klinicki.centar.service;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.repository.KlinikaRepository;


@Service
public class KlinikaService {
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
//	ArrayList<Klinika> klinike = new ArrayList<Klinika>();
//	
//	public ArrayList<Klinika> getKlinike(){
//		return klinike;
//	}

	public Klinika addKlinika(Klinika klinika) {
		return klinikaRepository.save(klinika);
	}
	

	public void remove(Integer ID) {
		klinikaRepository.deleteById(ID);
	}
	
	public Klinika save(Klinika klinika) {
		return klinikaRepository.save(klinika);
	}
	
	public Page<Klinika> findAll(Pageable page){
		return klinikaRepository.findAll(page);
	}
	
	public List<Klinika> findAll(){
		return klinikaRepository.findAll();
	}
	
	public Klinika findOne(Integer ID) {
		System.err.println(ID);
		System.out.println("USAO U FINDONE");
		//System.out.println(klinikaRepository.findById(ID).orElseGet(null));
		return klinikaRepository.findById(ID).orElse(null);
	}
}
