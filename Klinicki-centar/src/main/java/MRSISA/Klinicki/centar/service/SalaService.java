package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.repository.SalaRepository;

@Service
public class SalaService {
	
	@Autowired
	private SalaRepository salaRepository;
	
	ArrayList<Sala> sale = new ArrayList<>();
	
	public ArrayList<Sala> getSale(){
		return sale;
	}

	public Sala addSala(Sala sala) {
		return salaRepository.save(sala);
	}
	
	public void remove(Integer ID) {
		salaRepository.deleteById(ID);
	}
	
	public Sala save(Sala sala) {
		return salaRepository.save(sala);
	}
	
	public Page<Sala> findAll(Pageable page){
		return salaRepository.findAll(page);
	}
	
	public List<Sala> findAll(){
		return salaRepository.findAll();
	}
	
	public Sala findOne(Integer ID) {
		System.err.println(ID);
		System.out.println("USAO U FINDONE");
		return salaRepository.findById(ID).orElse(null);
	}

}
