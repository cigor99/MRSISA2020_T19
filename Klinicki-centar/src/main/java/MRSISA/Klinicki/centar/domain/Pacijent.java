package MRSISA.Klinicki.centar.domain;

import java.util.HashMap;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Pacijenti")
public class Pacijent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Pacijenta", unique = true, nullable = false)
	private Integer id;

	@NotEmpty(message = "Ime je obavezno")
	@Column(name = "ime", unique = false, nullable = false)
	private String ime;

	@Column(name = "Pol", unique = false, nullable = false)
	private Pol pol;

	@NotEmpty(message = "Prezime je obavezno")
	@Column(name = "prezime", unique = false, nullable = false)
	private String prezime;

	@Column(name = "JMBG", unique = true, nullable = false)
	private String jmbg;

	@NotEmpty(message = "Email je obavezan")
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@NotEmpty(message = "Lozinka je obavezna")
	@Column(name = "lozinka", unique = false, nullable = false)
	private String lozinka;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "zdravsteni_karton", referencedColumnName = "ID_Zdravstvenog_kartona", unique = true)
	private ZdravstveniKarton zdravstveniKarton;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "pacijent")
	private List<Pregled> istorijaPregleda;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "pacijent")
	private List<Operacija> istorijaOperacija;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "pacijent")
	private Set<Operacija> Operacije = new HashSet<Operacija>();

	@OneToOne(mappedBy = "pacijent")
	private ZahtevZaRegistraciju zahtevZaRegistraciju;

	public Pacijent() {
		super();
	}

//	public Pacijent(@NotEmpty(message = "Ime je obavezno") @JsonProperty("ime") String ime,
//			@NotEmpty(message = "Prezime je obavezno") @JsonProperty("prezime") String prezime,
//			@NotEmpty(message = "Email je obavezan") @JsonProperty("email") String email,
//			@NotEmpty(message = "Lozinka je obavezna") @JsonProperty("lozinka") String lozinka) {
//		this.ime = ime;
//		this.prezime = prezime;
//		this.email = email;
//		this.lozinka = lozinka;
//	}
//
//	public Pacijent(Integer id, @NotEmpty(message = "Ime je obavezno") String ime,
//			@NotEmpty(message = "Prezime je obavezno") String prezime, String jmbg,
//			@NotEmpty(message = "Email je obavezan") String email,
//			@NotEmpty(message = "Lozinka je obavezna") String lozinka, Integer karton, List<Integer> istorijaPregleda,
//			List<Integer> istorijaOperacija) {
//		super();
//		this.id = id;
//		this.ime = ime;
//		this.prezime = prezime;
//		this.jmbg = jmbg;
//		this.email = email;
//		this.lozinka = lozinka;
//		this.karton = karton;
//		this.istorijaPregleda = istorijaPregleda;
//		this.istorijaOperacija = istorijaOperacija;
//	}

	public Integer getId() {
		return id;
	}

	public Pacijent(Integer id, @NotEmpty(message = "Ime je obavezno") String ime,
			@NotEmpty(message = "Prezime je obavezno") String prezime, String jmbg,
			@NotEmpty(message = "Email je obavezan") String email,
			@NotEmpty(message = "Lozinka je obavezna") String lozinka, ZdravstveniKarton zdravstveniKarton,
			List<Pregled> istorijaPregleda, List<Operacija> istorijaOperacija, Set<Operacija> operacije,
			ZahtevZaRegistraciju zahtevZaRegistraciju, Pol pol) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.email = email;
		this.lozinka = lozinka;
		this.zdravstveniKarton = zdravstveniKarton;
		this.istorijaPregleda = istorijaPregleda;
		this.istorijaOperacija = istorijaOperacija;
		Operacije = operacije;
		this.zahtevZaRegistraciju = zahtevZaRegistraciju;
		this.pol = pol;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}

	public ZahtevZaRegistraciju getZahtevZaRegistraciju() {
		return zahtevZaRegistraciju;
	}

	public void setZahtevZaRegistraciju(ZahtevZaRegistraciju zahtevZaRegistraciju) {
		this.zahtevZaRegistraciju = zahtevZaRegistraciju;
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}

	public Set<Operacija> getOperacije() {
		return Operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		Operacije = operacije;
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

	public List<Pregled> getIstorijaPregleda() {
		return istorijaPregleda;
	}

	public void setIstorijaPregleda(List<Pregled> istorijaPregleda) {
		this.istorijaPregleda = istorijaPregleda;
	}

	public List<Operacija> getIstorijaOperacija() {
		return istorijaOperacija;
	}

	public void setIstorijaOperacija(List<Operacija> istorijaOperacija) {
		this.istorijaOperacija = istorijaOperacija;
	}

}
