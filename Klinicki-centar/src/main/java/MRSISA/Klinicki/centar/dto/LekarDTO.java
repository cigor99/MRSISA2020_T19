package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.TipKorisnika;

public class LekarDTO extends Osoba {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7189354369941803845L;
	private TipKorisnika tipKorisnika;
	private double prosecnaOcena;

	public LekarDTO() {
		super();
	}

	public LekarDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime, double prosecnaOcena) {
		super(id, email, lozinka, ime, prezime, jmbg);
		this.prosecnaOcena = prosecnaOcena;
	}

	public LekarDTO(Lekar lekar) {
		super(lekar.getId(), lekar.getEmail(), lekar.getLozinka(), lekar.getIme(), lekar.getPrezime(), lekar.getJmbg());
		this.tipKorisnika = lekar.getTipKorisnika();
		this.prosecnaOcena = lekar.getProsecnaOcena();
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

}
