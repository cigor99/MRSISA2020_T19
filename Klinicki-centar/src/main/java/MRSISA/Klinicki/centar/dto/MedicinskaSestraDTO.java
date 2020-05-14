package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.MedicinskaSestra;

public class MedicinskaSestraDTO extends Osoba{
	
	public MedicinskaSestraDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime) {
		super(id, email, lozinka, jmbg, ime, prezime);

	}
	
	public MedicinskaSestraDTO(MedicinskaSestra medSestra) {
		super(medSestra.getId(), medSestra.getEmail(), medSestra.getLozinka(), medSestra.getJmbg(), medSestra.getIme(), medSestra.getPrezime());
	}


}
