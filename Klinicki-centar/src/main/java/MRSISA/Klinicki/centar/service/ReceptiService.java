package MRSISA.Klinicki.centar.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.repository.ReceptiRepository;

@Service
public class ReceptiService {
	
	@Autowired
	private ReceptiRepository receptiRepo;
	
	public Recept addRecept(Recept recept) {
		return receptiRepo.save(recept);
	}
	
	public Recept save(Recept recept) {
		return receptiRepo.save(recept);
	}
	
	public void remove(Integer id) {
		receptiRepo.deleteById(id);
	}
	
	public List<Recept> findAll(){
		return receptiRepo.findAll();
	}
	
	public Page<Recept> findAll(Pageable page){
		return receptiRepo.findAll(page);
	}
	
	public Recept findOne(Integer id) {
		return receptiRepo.findById(id).orElse(null);
	}
	
}
