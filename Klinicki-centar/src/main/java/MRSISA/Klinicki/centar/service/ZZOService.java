package MRSISA.Klinicki.centar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;
import MRSISA.Klinicki.centar.repository.ZZORepository;

@Service
public class ZZOService {
	
	@Autowired
	private ZZORepository ZZORepo;
	
	public ZahtevZaOperaciju save(ZahtevZaOperaciju zahtev) {
		return ZZORepo.save(zahtev);
	}
	
	public List<ZahtevZaOperaciju> findAll(){
		return ZZORepo.findAll();
	}
	
	public void remove(Integer id) {
		ZZORepo.deleteById(id);
	}

	public ZahtevZaOperaciju findById(Integer id) {
		return ZZORepo.findById(id).orElse(null);
	}
	
	public ZahtevZaOperaciju addZahtev(ZahtevZaOperaciju zahtev) {
		return ZZORepo.save(zahtev);
	}
	
	
}
