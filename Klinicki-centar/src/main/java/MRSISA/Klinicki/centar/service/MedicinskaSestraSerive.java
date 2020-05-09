package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.repository.MedicinskaSestraRepository;

@Service
public class MedicinskaSestraSerive {

	@Autowired
	private MedicinskaSestraRepository medSesRepo;
	
	ArrayList<MedicinskaSestra> sestre = new ArrayList<MedicinskaSestra>();
	
	public ArrayList<MedicinskaSestra> getSestre(){
		return sestre;
	}
	
	public MedicinskaSestra addSestra(MedicinskaSestra sestra) {
		return medSesRepo.save(sestra);
	}
	
	public MedicinskaSestra findOne(Integer id) {
		return medSesRepo.findById(id).orElse(null);
	}
	
	public MedicinskaSestra save(MedicinskaSestra medSes) {
		return medSesRepo.save(medSes);		
	}
	
	public List<MedicinskaSestra> findAll(){
		return medSesRepo.findAll();
	}
	
	public void remove(Integer id) {
		medSesRepo.deleteById(id);
	}
	
	public Page<MedicinskaSestra> findAll(Pageable page){
		return medSesRepo.findAll(page);
	}
}
