package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Lekar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String email;
	@Column
	private String lozinka;
	
	@Column
	private String ime;
	@Column
	private String prezime;
	private Integer klinika;
	private ArrayList<Integer> pregledi;
	private ArrayList<Integer> operacije;

	
	public Lekar(@JsonProperty("email") String email, @JsonProperty("lozinka") String lozinka, @JsonProperty("ime") String ime, @JsonProperty("prezime") String prezime) {
		super();
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.pregledi = new ArrayList<>();
		this.operacije = new ArrayList<>();
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
	public Integer getKlinika() {
		return klinika;
	}
	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}
	public ArrayList<Integer> getPregledi() {
		return pregledi;
	}
	public void setPregledi(ArrayList<Integer> pregledi) {
		this.pregledi = pregledi;
	}
	public ArrayList<Integer> getOperacije() {
		return operacije;
	}
	public void setOperacije(ArrayList<Integer> operacije) {
		this.operacije = operacije;
	}
	

}
