package MRSISA.Klinicki.centar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.repository.AdminKRepository;

@Service
public class AdminKService {

	@Autowired
	private AdminKRepository adminRepo;

	public AdministratorKlinike findOne(Integer id) {
		return adminRepo.findById(id).orElse(null);
	}

	public AdministratorKlinike addAdmin(AdministratorKlinike admin) {
		return adminRepo.save(admin);
	}

	public void remove(Integer id) {
		adminRepo.deleteById(id);
	}

	public AdministratorKlinike save(AdministratorKlinike admin) {
		return adminRepo.save(admin);
	}

	public List<AdministratorKlinike> findAll() {
		return adminRepo.findAll();
	}

}
