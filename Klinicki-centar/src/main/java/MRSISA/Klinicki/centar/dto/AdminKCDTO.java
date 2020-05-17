package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.TipKorisnika;

public class AdminKCDTO extends Osoba{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4109828345782217976L;
	
	private TipKorisnika tipKorisnika;

	public AdminKCDTO() {
		super();
	}

	public AdminKCDTO(AdministratorKlinickogCentra admin) {
		super(admin.getId(), admin.getEmail(), admin.getLozinka(), admin.getIme(), admin.getPrezime(), admin.getJmbg());
		this.tipKorisnika = admin.getTipKorisnika();
		System.out.println(this.tipKorisnika);

	}

	public AdminKCDTO(Integer id, String email, String lozinka, String ime, String prezime, String jmbg) {
		super(id, email, lozinka, ime, prezime, jmbg);
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

}
