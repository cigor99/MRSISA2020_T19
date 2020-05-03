package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.AdministratorKlinike;

public class AdminKDTO {
	private String ime;
	private String prezime;
	private String JMBG;
	private String email;
	private String lozinka;
	private Integer id;
	private Integer klinikaID;

	public AdminKDTO() {

	}

	public AdminKDTO(AdministratorKlinike adminK) {
		super();
		this.ime = adminK.getIme();
		this.prezime = adminK.getPrezime();
		this.JMBG = adminK.getJmbg();
		this.email = adminK.getEmail();
		this.lozinka = adminK.getLozinka();
		this.id = adminK.getId();
		if (adminK.getKlinika() == null) {
			System.out.println("USAO U IF");
			this.klinikaID = null;
		} else {
			this.klinikaID = adminK.getKlinika().getId();
		}
	}

	public AdminKDTO(String ime, String prezime, String jMBG, String email, String lozinka, Integer id,
			Integer klinikaID) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		JMBG = jMBG;
		this.email = email;
		this.lozinka = lozinka;
		this.id = id;
		this.klinikaID = klinikaID;
	}

	public Integer getKlinikaID() {
		return klinikaID;
	}

	public void setKlinikaID(Integer klinikaID) {
		this.klinikaID = klinikaID;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
