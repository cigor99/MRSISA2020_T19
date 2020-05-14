package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.MedicinskaSestra;

public class MedicinskaSestraDTO extends Osoba{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4050682239958346492L;

	public MedicinskaSestraDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime) {
		super(id, email, lozinka,  ime, prezime,jmbg);

	}
	
	public MedicinskaSestraDTO(MedicinskaSestra medSestra) {
		super(medSestra.getId(), medSestra.getEmail(), medSestra.getLozinka(),  medSestra.getIme(), medSestra.getPrezime(),medSestra.getJmbg());
	}


}
