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
	
	
	public MedicinskaSestraDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime) {
		super(id, email, lozinka,  ime, prezime,jmbg);
		
	}
	
	public MedicinskaSestraDTO(MedicinskaSestra medSestra) {
		super(medSestra.getId(), medSestra.getEmail(), medSestra.getLozinka(),  medSestra.getIme(), medSestra.getPrezime(),medSestra.getJmbg());
		this.tipKorisnika = medSestra.getTipKorisnika();
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}
}
