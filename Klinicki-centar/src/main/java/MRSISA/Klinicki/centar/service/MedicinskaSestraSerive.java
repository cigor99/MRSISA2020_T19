package MRSISA.Klinicki.centar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.repository.MedicinskaSestraRepository;

@Service
public class MedicinskaSestraSerive {

	@Autowired
	private MedicinskaSestraRepository medSesRepo;
	
	public MedicinskaSestra findOne(Integer id) {
		return medSesRepo.findById(id).orElse(null);
	}
	
	public MedicinskaSestra save(MedicinskaSestra medSes) {
		return medSesRepo.save(medSes);		
	}
}
