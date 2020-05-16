package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.repository.PregledRepository;

@Service
public class PregledService {
	
	@Autowired
	private PregledRepository pregledRepository;
	
	ArrayList<Pregled> pregledi = new ArrayList<Pregled>();
	
	public ArrayList<Pregled> getPregledi(){
		return pregledi;
	}
	
	public Pregled addPregled(Pregled pregled) {
		return pregledRepository.save(pregled);
	}
	
	public void remove(Integer id) {
		pregledRepository.deleteById(id);
	}
	
	public Pregled save(Pregled pregled) {
		return pregledRepository.save(pregled);
	}
	
	public Page<Pregled> findAll(Pageable page){
		return pregledRepository.findAll(page);
	}
	
	public List<Pregled> findAll(){
		return pregledRepository.findAll();
	}
	
	public Pregled findOne(Integer id) {
		return pregledRepository.findById(id).orElse(null);
	}

}
