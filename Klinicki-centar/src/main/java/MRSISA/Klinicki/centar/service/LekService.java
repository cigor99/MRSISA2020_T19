package MRSISA.Klinicki.centar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.dto.LekDTO;
import MRSISA.Klinicki.centar.repository.LekRepository;

@Service
public class LekService {

	@Autowired
	private LekRepository lekRep;

	public Lek addLek(Lek lek) {
		return lekRep.save(lek);
	}

	public Lek findOne(Integer id) {
		return lekRep.findById(id).orElse(null);
	}

	public void remove(Integer id) {
		lekRep.deleteById(id);
	}

	public Lek save(Lek lek) {
		return lekRep.save(lek);
	}

	public List<Lek> findAll() {	
		return lekRep.findAll();
	}
}
