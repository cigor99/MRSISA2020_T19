package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;

public class AdminKCDTO extends Osoba{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4109828345782217976L;

	public AdminKCDTO() {
		super();
	}

	public AdminKCDTO(AdministratorKlinickogCentra admin) {
		super(admin.getId(), admin.getEmail(), admin.getLozinka(), admin.getIme(), admin.getPrezime(), admin.getJmbg());

	}

	public AdminKCDTO(Integer id, String email, String lozinka, String ime, String prezime, String jmbg) {
		super(id, email, lozinka, ime, prezime, jmbg);
	}


}
