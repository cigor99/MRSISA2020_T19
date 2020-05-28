package MRSISA.Klinicki.centar.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import MRSISA.Klinicki.centar.domain.ZahtevZaGodisnjiOdmor;
import MRSISA.Klinicki.centar.repository.ZahtevZGRepository;

@Service
public class ZahtevZGService {

	@Autowired
	private ZahtevZGRepository zahtevRepo;

	public ZahtevZaGodisnjiOdmor save(ZahtevZaGodisnjiOdmor zahtev) {
		return zahtevRepo.save(zahtev);
	}

	public ZahtevZaGodisnjiOdmor addZahtev(ZahtevZaGodisnjiOdmor zahtev) {
		return zahtevRepo.save(zahtev);
	}

	public void remove(Integer id) {
		zahtevRepo.deleteById(id);
	}

	public List<ZahtevZaGodisnjiOdmor> findAll() {
		return zahtevRepo.findAll();
	}

	public ZahtevZaGodisnjiOdmor findById(Integer id) {
		return zahtevRepo.findById(id).orElse(null);
	}

	public Page<ZahtevZaGodisnjiOdmor> findAll(Pageable page) {
		return zahtevRepo.findAll(page);
	}

}
