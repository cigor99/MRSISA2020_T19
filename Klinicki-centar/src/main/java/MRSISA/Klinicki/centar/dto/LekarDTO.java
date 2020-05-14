package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Lekar;

public class LekarDTO extends Osoba{
		
	public LekarDTO() {
		super();
	}

	public LekarDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime) {
		super(id, email, lozinka, jmbg, ime, prezime);
	}
	
	public LekarDTO(Lekar lekar) {
		super(lekar.getId(), lekar.getEmail(), lekar.getLozinka(), lekar.getJmbg(), lekar.getIme(), lekar.getPrezime());
	}

	
}
