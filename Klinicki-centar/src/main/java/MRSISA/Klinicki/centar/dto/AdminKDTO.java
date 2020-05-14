package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;

public class AdminKDTO extends Osoba{
	private Integer klinikaID;

	public AdminKDTO() {

	}//super(id, email, lozinka, jmbg, ime, prezime);

	public AdminKDTO(AdministratorKlinike adminK) {
		super(adminK.getId(), adminK.getEmail(), adminK.getLozinka(), adminK.getJmbg(), adminK.getIme(), adminK.getPrezime());
		if (adminK.getKlinika() == null) {
			System.out.println("USAO U IF");
			this.klinikaID = null;
		} else {
			this.klinikaID = adminK.getKlinika().getId();
		}
	}

	public AdminKDTO(String ime, String prezime, String jMBG, String email, String lozinka, Integer id,
			Integer klinikaID) {
		super(id, email, lozinka, jMBG, ime, prezime);
		this.klinikaID = klinikaID;
	}

	public Integer getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Integer klinikaID) {
		this.klinikaID = klinikaID;
	}


}
