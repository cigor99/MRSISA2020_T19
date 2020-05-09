package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.MedicinskaSestra;

public class MedicinskaSestraDTO {
	
	private int id;
	private String email;
	private String lozinka;
	private String jmbg;
	private String ime;
	private String prezime;
	
	
	public MedicinskaSestraDTO(int id, String email, String lozinka, String jmbg, String ime, String prezime) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
	}
	
	public MedicinskaSestraDTO(MedicinskaSestra medSestra) {
		this.id = medSestra.getId();
		this.email = medSestra.getEmail();
		this.lozinka = medSestra.getLozinka();
		this.jmbg = medSestra.getJmbg();
		this.ime = medSestra.getIme();
		this.prezime = medSestra.getPrezime();
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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
