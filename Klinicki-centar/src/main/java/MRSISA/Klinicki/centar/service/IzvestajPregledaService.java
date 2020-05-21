package MRSISA.Klinicki.centar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.IzvestajPregleda;
import MRSISA.Klinicki.centar.repository.IzvestajPregledaRepository;

@Service
public class IzvestajPregledaService {

	@Autowired
	private IzvestajPregledaRepository izvestajRepo;
	
	public IzvestajPregleda save(IzvestajPregleda izvestaj) {
		return izvestajRepo.save(izvestaj);
	}
	
	public IzvestajPregleda add(IzvestajPregleda izvestaj) {
		return izvestajRepo.save(izvestaj);
	}
	
}
