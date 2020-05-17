package MRSISA.Klinicki.centar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.repository.AdminKCRepository;

@Service
public class AdminKCSerivce {
	@Autowired
	private AdminKCRepository adminRep;

	public AdministratorKlinickogCentra addAdmin(AdministratorKlinickogCentra admin) {
		return  adminRep.save(admin);
	}
	
	public AdministratorKlinickogCentra findOne(Integer id) {
		return adminRep.findById(id).orElse(null);
	}
	
	public AdministratorKlinickogCentra save (AdministratorKlinickogCentra admin) {
		return adminRep.save(admin);
	}
	
	public void remove(Integer id) {
		adminRep.deleteById(id);
	}

	public List<AdministratorKlinickogCentra> findAll() {
		return adminRep.findAll();
	}
	
	public AdministratorKlinickogCentra findByjmbg(String jmbg) {
		return adminRep.findByjmbg(jmbg);
	}
}
