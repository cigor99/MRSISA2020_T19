package MRSISA.Klinicki.centar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.repository.ZahtevZRRepository;

@Service
public class ZahtevZRService {

	@Autowired
	private ZahtevZRRepository zzrRep;

	public ZahtevZaRegistraciju findOne(Integer id) {
		return zzrRep.findById(id).orElse(null);
	}
	
	public ZahtevZaRegistraciju save(ZahtevZaRegistraciju zahtev) {
		return zzrRep.save(zahtev);
	}
	
}
