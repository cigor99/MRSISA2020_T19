package MRSISA.Klinicki.centar.dto;

public class Admin_klinikaDTO {
	private Integer adminID;
	private Integer klinikaID;

	public Admin_klinikaDTO() {

	}

	public Admin_klinikaDTO(Integer adminID, Integer klinikaID) {
		super();
		this.adminID = adminID;
		this.klinikaID = klinikaID;
	}

	public Integer getAdminID() {
		return adminID;
	}

	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}

	public Integer getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Integer klinikaID) {
		this.klinikaID = klinikaID;
	}

}
