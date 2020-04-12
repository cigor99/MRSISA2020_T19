package MRSISA.Klinicki.centar.domain;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lekar {
	
	@NotEmpty(message = "Email je obavezan.")
	private String email;
	
	@NotEmpty(message = "Lozinka je obavezna.")
	private String lozinka;
	
	private String ime;
	private String prezime;

	
	public Lekar(@JsonProperty("email") String email, @JsonProperty("lozinka") String lozinka, @JsonProperty("ime") String ime, @JsonProperty("prezime") String prezime) {
		super();
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
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
