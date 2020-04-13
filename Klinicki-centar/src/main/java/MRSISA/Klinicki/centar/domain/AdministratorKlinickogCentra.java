package MRSISA.Klinicki.centar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdministratorKlinickogCentra {
	
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
	@Column
	private String jmbg;
	private Integer klinickiCentar;
	
	public AdministratorKlinickogCentra(Integer id, String email, String lozinka, String ime, String prezime,
			String jmbg, Integer klinickiCentar) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.klinickiCentar = klinickiCentar;
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

	public Integer getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(Integer klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
	
	

}
