package MRSISA.Klinicki.centar.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.repository.InMemoryLekarRepository;

@Service
public class LekarServiceImpl implements LekarService {
    
    @Autowired
    private InMemoryLekarRepository lekarRepository;

    @Override
    public Collection<Lekar> findAll() {
        Collection<Lekar> greetings = lekarRepository.findAll();
        return greetings;
    }

	@Override
	public Lekar findOne(String email) {
		Lekar lekar = lekarRepository.findOne(email);
        return lekar;
	}

	@Override
	public Lekar create(Lekar lekar) throws Exception {
        Lekar saved = lekarRepository.create(lekar);
        return saved;
	}

}
