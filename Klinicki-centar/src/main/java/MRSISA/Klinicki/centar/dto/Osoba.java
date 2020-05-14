package MRSISA.Klinicki.centar.dto;

import java.io.Serializable;

public abstract class Osoba implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2630085096877002277L;
	private int id;
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private String jmbg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Osoba(int id, String ime, String prezime, String email, String lozinka, String jmbg) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
	}
	public Osoba() {
		super();
	}
	@Override
	public String toString() {
		return "Osoba [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", lozinka=" + lozinka
				+ ", jmbg=" + jmbg + "]";
	}
	
	
}
