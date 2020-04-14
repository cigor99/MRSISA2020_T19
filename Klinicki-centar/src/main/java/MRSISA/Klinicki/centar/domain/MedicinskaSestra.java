package MRSISA.Klinicki.centar.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Medicinske Sestre")

public class MedicinskaSestra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MedSes", unique = true, nullable = false)
	private Integer id;

	@Column(name = "ime", unique = false, nullable = false)
	private String ime;

	@Column(name = "prezime", unique = false, nullable = false)
	private String prezime;

	@Column(name = "JMBG", unique = true, nullable = false)
	private String jmbg;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "lozinka", unique = false, nullable = false)
	private String lozinka;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "medicinskaSestra")
	private Set<Integer> recepti = new HashSet<Integer>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "medicinskaSestra")
	private Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji = new HashSet<ZahtevZaGodisnjiOdmor>();

	public MedicinskaSestra() {
		super();
	}

	public MedicinskaSestra(Integer id, String ime, String prezime, String jmbg, String email, String lozinka,
			Set<Integer> recepti, Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.email = email;
		this.lozinka = lozinka;
		this.recepti = recepti;
		this.zahteviZaGodisnji = zahteviZaGodisnji;
	}

	public Set<ZahtevZaGodisnjiOdmor> getZahteviZaGodisnji() {
		return zahteviZaGodisnji;
	}

	public void setZahteviZaGodisnji(Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji) {
		this.zahteviZaGodisnji = zahteviZaGodisnji;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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

	public Set<Integer> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Integer> recepti) {
		this.recepti = recepti;
	}

}
