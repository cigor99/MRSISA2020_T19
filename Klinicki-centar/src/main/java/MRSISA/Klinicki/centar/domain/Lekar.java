package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Lekar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_lekara", unique = true, nullable = false)
	private Integer id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "lozinka", unique = false, nullable = false)
	private String lozinka;

	@Column(name = "ime", unique = false, nullable = false)
	private String ime;

	@Column(name = "prezime", unique = false, nullable = false)
	private String prezime;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "klinika", referencedColumnName = "ID_Klinike", nullable = false)
	private Klinika klinika;
	//@Column(name = "klinika", unique = false)
	//private Integer klinika;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	private Set<Pregled> pregledi = new HashSet<Pregled>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	private Set<IzvestajPregleda> izvestajiPregleda = new HashSet<IzvestajPregleda>();

	@ManyToMany(mappedBy = "lekari", cascade = CascadeType.ALL)
	private Set<Operacija> operacije = new HashSet<Operacija>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	private Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji = new HashSet<ZahtevZaGodisnjiOdmor>();

	public Lekar() {

	}

	public Lekar(Integer id, String email, String lozinka, String ime, String prezime, Klinika klinika,
			Set<Pregled> pregledi, Set<IzvestajPregleda> izvestajiPregleda, Set<Operacija> operacije,
			Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.klinika = klinika;
		this.pregledi = pregledi;
		this.izvestajiPregleda = izvestajiPregleda;
		this.operacije = operacije;
		this.zahteviZaGodisnji = zahteviZaGodisnji;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
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

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<IzvestajPregleda> getIzvestajiPregleda() {
		return izvestajiPregleda;
	}

	public void setIzvestajiPregleda(Set<IzvestajPregleda> izvestajiPregleda) {
		this.izvestajiPregleda = izvestajiPregleda;
	}

	public Set<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		this.operacije = operacije;
	}

	public Set<ZahtevZaGodisnjiOdmor> getZahteviZaGodisnji() {
		return zahteviZaGodisnji;
	}

	public void setZahteviZaGodisnji(Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji) {
		this.zahteviZaGodisnji = zahteviZaGodisnji;
	}

}
