package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.TipKorisnika;

public class LekarDTO extends Osoba {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7189354369941803845L;
	private TipKorisnika tipKorisnika;
	private Integer klinikaID;
	private double prosecnaOcena;

	public LekarDTO() {
		super();
	}

	public LekarDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime, double prosecnaOcena, Integer klinikaID) {
		super(id, email, lozinka, ime, prezime, jmbg);
		this.prosecnaOcena = prosecnaOcena;
		double a = this.prosecnaOcena * 100;
		double i = (Math.round(a));
		double p = i/100;
		this.prosecnaOcena = p;
		this.klinikaID = klinikaID;
	}

	public LekarDTO(Lekar lekar) {
		super(lekar.getId(), lekar.getEmail(), lekar.getLozinka(), lekar.getIme(), lekar.getPrezime(), lekar.getJmbg());
		this.tipKorisnika = lekar.getTipKorisnika();
		this.prosecnaOcena = lekar.getProsecnaOcena();
		double a = this.prosecnaOcena * 100;
		double i = (Math.round(a));
		double p = i/100;
		this.prosecnaOcena = p;
		this.klinikaID = lekar.getKlinika().getId();
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

	public Integer getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Integer klinikaID) {
		this.klinikaID = klinikaID;
	}

}
