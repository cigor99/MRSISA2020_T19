package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Lekar;

public class LekarDTO {
	
	private int id;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	
	public LekarDTO() {
		super();
	}

	public LekarDTO(int id, String email, String lozinka, String ime, String prezime) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
	}
	
	public LekarDTO(Lekar lekar) {
		this.id = lekar.getId();
		this.email = lekar.getEmail();
		this.lozinka = lekar.getLozinka();
		this.ime = lekar.getIme();
		this.prezime = lekar.getPrezime();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	
}
