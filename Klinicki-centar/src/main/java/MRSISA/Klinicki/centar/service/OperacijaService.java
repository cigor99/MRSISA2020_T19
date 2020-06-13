package MRSISA.Klinicki.centar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.repository.OperacijaRepository;

@Service
public class OperacijaService {

	@Autowired
	private OperacijaRepository operacijaRepo;
	
	public Operacija save (Operacija operacija) {
		return operacijaRepo.save(operacija);
	}
	
	public Operacija addOperacije (Operacija operacija) {
		return operacijaRepo.save(operacija);
	}
	
	public void remove(Integer id) {
		operacijaRepo.deleteById(id);
	}
	
	public Operacija findById(Integer id) {
		return operacijaRepo.findById(id).orElse(null);
	}
	
	public List<Operacija> findAll(){
		return operacijaRepo.findAll();
	}
	
}
