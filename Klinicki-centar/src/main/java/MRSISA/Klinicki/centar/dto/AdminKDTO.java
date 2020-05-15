package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;

public class AdminKDTO extends Osoba{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8176448401842720033L;
	private Integer klinikaID;

	public AdminKDTO() {

	}//super(id, email, lozinka, jmbg, ime, prezime);
	
	public AdminKDTO(AdministratorKlinike adminK) {
		super(adminK.getId(), adminK.getEmail(), adminK.getLozinka(), adminK.getIme(), adminK.getPrezime(), adminK.getJmbg());
		if (adminK.getKlinika() == null) {
			System.out.println("USAO U IF");
			this.klinikaID = null;
		} else {
			this.klinikaID = adminK.getKlinika().getId();
		}
	}

	public AdminKDTO(String ime, String prezime, String jMBG, String email, String lozinka, Integer id,
			Integer klinikaID) {
		super(id, email, lozinka, ime, prezime, jMBG);
		this.klinikaID = klinikaID;
	}

	public Integer getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Integer klinikaID) {
		this.klinikaID = klinikaID;
	}


}
