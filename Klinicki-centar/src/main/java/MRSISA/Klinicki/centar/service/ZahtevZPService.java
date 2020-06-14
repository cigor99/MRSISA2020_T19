package MRSISA.Klinicki.centar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;
import MRSISA.Klinicki.centar.domain.ZahtevZaPregled;
import MRSISA.Klinicki.centar.repository.ZahtevZPRepository;

@Service
public class ZahtevZPService {

	@Autowired
	private ZahtevZPRepository zzpRep;
	
	public ZahtevZaPregled findOne(Integer id) {
		return zzpRep.findById(id).orElse(null);
	}
	
	public ZahtevZaPregled save(ZahtevZaPregled zahtev) {
		return zzpRep.save(zahtev);
	}
	
	public List<ZahtevZaPregled> findAll(){
		return zzpRep.findAll();
	}
}
