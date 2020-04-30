package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Cena;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.repository.CenaRepository;

@Service
public class CenaService {
	
	@Autowired
	private CenaRepository cenaRepository;
	
	ArrayList<Cena> cene = new ArrayList<>();
	
	public ArrayList<Cena> getCene(){
		return cene;
	}
	
	public Cena addCena(Cena cena) {
		return cenaRepository.save(cena);
	}
	
	public void remove(Integer ID) {
		cenaRepository.deleteById(ID);;		
	}
	
	public Cena save(Cena cena) {
		return cenaRepository.save(cena);
	}

	
	public Page<Cena> finAll(Pageable page){
		return cenaRepository.findAll(page);
	}
	
	public List<Cena> findAll(){
		return cenaRepository.findAll();
	}
	
	public Cena findOne(Integer id) {
		return cenaRepository.findById(id).orElse(null);
	}
	
	public Cena findOneByTipPregleda(TipPregleda tipPregleda) {
		return cenaRepository.findOneByTipPregleda(tipPregleda);
	}

}
