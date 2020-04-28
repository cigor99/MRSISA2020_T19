package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;

public class AdminKCDTO {

	private Integer id;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String jmbg;

	public AdminKCDTO() {

	}

	public AdminKCDTO(AdministratorKlinickogCentra admin) {
		super();
		this.id = admin.getId();
		this.email = admin.getEmail();
		this.ime = admin.getIme();
		this.prezime = admin.getPrezime();
		this.lozinka = admin.getLozinka();
		this.jmbg = admin.getJmbg();

	}

	public AdminKCDTO(Integer id, String email, String lozinka, String ime, String prezime, String jmbg) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

}
