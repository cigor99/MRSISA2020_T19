package MRSISA.Klinicki.centar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.repository.DijagnozaRepository;

@Service
public class DijagnozaService {

	@Autowired
	private DijagnozaRepository dijagnozaRepo;

	public Dijagnoza addDijagnoza(Dijagnoza dijagnoza) {
		return dijagnozaRepo.save(dijagnoza);
	}

	public Dijagnoza findOne(Integer id) {
		return dijagnozaRepo.findById(id).orElse(null);
	}

	public void remove(Integer id) {
		dijagnozaRepo.deleteById(id);
	}

	public Dijagnoza save(Dijagnoza dijagnoza) {
		return dijagnozaRepo.save(dijagnoza);
	}

	public List<Dijagnoza> findAll() {
		return dijagnozaRepo.findAll();
	}

}
