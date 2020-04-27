package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.ZdravstveniKarton;
import MRSISA.Klinicki.centar.repository.ZdravstveniKartonRepository;

@Service
public class ZdravstveniKartonService {

	@Autowired
	private ZdravstveniKartonRepository zkr;

	public ZdravstveniKarton findOne(Integer ID) {
		return zkr.findById(ID).orElse(null);
	}

	public List<ZdravstveniKarton> findAll() {
		return zkr.findAll();
	}

	public List<ZdravstveniKarton> findAll(Pageable page) {
		return zkr.findAll();
	}

	public ZdravstveniKarton addKarton(ZdravstveniKarton zdravstveniKarton) {
		return zkr.save(zdravstveniKarton);
	}

	public void remove(Integer ID) {
		zkr.deleteById(ID);
	}

	public ZdravstveniKarton save(ZdravstveniKarton zdravstveniKarton) {
		return zkr.save(zdravstveniKarton);
	}

}
