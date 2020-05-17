package MRSISA.Klinicki.centar.dto;

import java.util.regex.Pattern;

import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.TipKorisnika;

public class MedicinskaSestraDTO extends Osoba{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4050682239958346492L;
	private TipKorisnika tipKorisnika;
	private double prosecnaOcena;
	
	
	public MedicinskaSestraDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime, double prosecnaOcena) {
		super(id, email, lozinka,  ime, prezime,jmbg);
		this.prosecnaOcena = prosecnaOcena;
	}
	
	public MedicinskaSestraDTO(MedicinskaSestra medSestra) {
		super(medSestra.getId(), medSestra.getEmail(), medSestra.getLozinka(),  medSestra.getIme(), medSestra.getPrezime(),medSestra.getJmbg());
		this.tipKorisnika = medSestra.getTipKorisnika();
		this.prosecnaOcena = medSestra.getProsecnaOcena();
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
