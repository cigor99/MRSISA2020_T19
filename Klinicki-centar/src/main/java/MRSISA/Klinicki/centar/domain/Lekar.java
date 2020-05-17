package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
	
	@Column(name = "JMBG", unique = true, nullable = false)
	private String jmbg;

	@Column(name = "ime", unique = false, nullable = false)
	private String ime;

	@Column(name = "prezime", unique = false, nullable = false)
	private String prezime;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	private Set<Recept> recepti = new HashSet<Recept>();
	
	//@OneToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY, mappedBy = "lekar")
	@ElementCollection
	private Set<Ocena> ocene = new HashSet<Ocena>();
	
	@Column(name = "ocena", unique = false)
	private Double prosecnaOcena;

	@ManyToOne
	@JoinColumn(name = "klinika", referencedColumnName = "ID_Klinike", nullable = false)
	private Klinika klinika;
	// @Column(name = "klinika", unique = false)
	// private Integer klinika;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	private Set<Pregled> pregledi = new HashSet<Pregled>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	private Set<IzvestajPregleda> izvestajiPregleda = new HashSet<IzvestajPregleda>();

	@ManyToMany(mappedBy = "lekari")
	private Set<Operacija> operacije = new HashSet<Operacija>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	private Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji = new HashSet<ZahtevZaGodisnjiOdmor>();

	private TipKorisnika tipKorisnika = TipKorisnika.LEKAR;
	
	public Lekar() {

	}
	
	public Lekar(Integer id, String email, String lozinka, String jmbg, String ime, String prezime, Klinika klinika) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.klinika = klinika;
	}

	public Lekar(Integer id, String email, String lozinka, String jmbg, String ime, String prezime, Set<Recept> recepti,
			Klinika klinika, Set<Pregled> pregledi, Set<IzvestajPregleda> izvestajiPregleda, Set<Operacija> operacije,
			Set<ZahtevZaGodisnjiOdmor> zahteviZaGodisnji, Set<Ocena> ocene) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.recepti = recepti;
		this.klinika = klinika;
		this.pregledi = pregledi;
		this.izvestajiPregleda = izvestajiPregleda;
		this.operacije = operacije;
		this.zahteviZaGodisnji = zahteviZaGodisnji;
		this.ocene = ocene;
	}
	
	public double izracunajProsecnuOcenu() {
		int suma = 0;
		int i = this.ocene.size();
		if(i == 0) {
			return 3.0;
		}
		for(Ocena o : this.ocene) {
			int ocena = o.ordinal()+1;
			suma += ocena;
		}
		double prosek = suma/i;
		this.prosecnaOcena = prosek;
		return prosek;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public Set<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(Set<Ocena> ocene) {
		this.ocene = ocene;
	}

	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

}
