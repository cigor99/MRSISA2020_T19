package MRSISA.Klinicki.centar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "AdminiKlinike")
public class AdministratorKlinike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_AdminK", unique = true, nullable = false)
	private Integer id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "lozinka", unique = false, nullable = false)
	private String lozinka;

	@Column(name = "ime", unique = false, nullable = false)
	private String ime;

	@Column(name = "prezime", unique = false, nullable = false)
	private String prezime;

	@Column(name = "JMBG", unique = false, nullable = false)
	private String jmbg;

	@ManyToOne
	@JoinColumn(name = "klinika", referencedColumnName = "ID_Klinike", nullable = false)
	private Klinika klinika;

	public AdministratorKlinike(Integer id, String email, String lozinka, String ime, String prezime, String jmbg,
			Klinika klinika) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.klinika = klinika;
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

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

}
