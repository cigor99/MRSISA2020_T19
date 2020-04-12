package MRSISA.Klinicki.centar.domain;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pacijent {

	@NotEmpty(message = "Ime je obavezno")
	private String ime;
	
	@NotEmpty(message = "Prezime je obavezno")
	private String prezime;
	
	@NotEmpty(message = "Email je obavezan")
	private String email;
	
	@NotEmpty(message = "Lozinka je obavezna")
	private String lozinka;

	public Pacijent() {
		super();
	}

	public Pacijent(@NotEmpty(message = "Ime je obavezno") @JsonProperty("ime")String ime,
			@NotEmpty(message = "Prezime je obavezno") @JsonProperty("prezime") String prezime,
			@NotEmpty(message = "Email je obavezan")  @JsonProperty("email") String email,
			@NotEmpty(message = "Lozinka je obavezna")  @JsonProperty("lozinka") String lozinka) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
	}
	
	

	@Override
	public String toString() {
		return "Pacijent [ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", lozinka=" + lozinka + "]";
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
	
	
}
