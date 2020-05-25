package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.repository.PacijentRepository;

@Service
public class PacijentService {
	@Autowired
	private PacijentRepository pacijentRepo;
	
	ArrayList<Pacijent> pacijenti = new ArrayList<Pacijent>();
	
	
	public ArrayList<Pacijent> getPacijenti(){
		return pacijenti;
	}
	
	public Pacijent addPacijent(Pacijent pacijent) {
		//System.out.println("PacijentService-addPacijent");
		//System.out.println(pacijent);
		return pacijentRepo.save(pacijent);
	}
	
	public void remove(Integer ID) {
		pacijentRepo.deleteById(ID);
	}
	
	public Pacijent save(Pacijent pacijent) {
		return pacijentRepo.save(pacijent);
	}
	
	public Page<Pacijent> findAll(Pageable page){
		return pacijentRepo.findAll(page);
	}
	
	public List<Pacijent> findAll(){
		return pacijentRepo.findAll();
	}
	
	public Pacijent findOne(Integer ID) {
		return pacijentRepo.findById(ID).orElse(null);
	}
	
	public Pacijent findByjmbg(String jmbg) {
		return pacijentRepo.findByjmbg(jmbg);
	}

	
	
	
	
	
}
