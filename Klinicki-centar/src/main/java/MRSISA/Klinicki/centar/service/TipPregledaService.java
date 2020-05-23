package MRSISA.Klinicki.centar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.repository.TipPregledaRepository;

@Service
public class TipPregledaService {
	
	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	ArrayList<TipPregleda> tipoviPregleda = new ArrayList<>();
	
	public ArrayList<TipPregleda> getTipoviPregleda(){
		return tipoviPregleda;
	}

	public TipPregleda addTipPregleda(TipPregleda tipPregleda) {
		return tipPregledaRepository.save(tipPregleda);
	}
	
	public void remove(Integer ID) {
		tipPregledaRepository.deleteById(ID);
	}
	
	public TipPregleda save(TipPregleda tipPregleda) {
		return tipPregledaRepository.save(tipPregleda);
	}
	
	public Page<TipPregleda> findAll(Pageable page){
		return tipPregledaRepository.findAll(page);
	}
	
	public List<TipPregleda> findAll(){
		return tipPregledaRepository.findAll();
	}
	
	public TipPregleda findOne(Integer ID) {
		//System.err.println(ID);
		//System.out.println("USAO U FINDONE");
		return tipPregledaRepository.findById(ID).orElse(null);
	}

}
