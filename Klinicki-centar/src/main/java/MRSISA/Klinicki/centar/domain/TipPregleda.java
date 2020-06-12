package MRSISA.Klinicki.centar.domain;

import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "Tipovi_Pregleda")
public class TipPregleda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TipaPregleda", unique = true, nullable = false)
	private Integer id;

	@Column(name = "trajanje", unique = false, nullable = false)
	private Integer trajanje;

	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "tipPregleda")
	private Set<Pregled> pregledi = new HashSet<Pregled>();

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "cena", referencedColumnName = "ID_Cene")
	private Cena cena; // Id za klasu cena

//	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "tipPregleda")
//	private Set<Operacija> operacije = new HashSet<Operacija>();
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "lekar_tipPregleda", joinColumns = @JoinColumn(name = "ID_lekara"), inverseJoinColumns = @JoinColumn(name = "ID_TipaPregleda"))
	private Set<Lekar> lekari = new HashSet<Lekar>();

	public TipPregleda() {
		super();
	}
	
	

	@Override
	public String toString() {
		return "TipPregleda [id=" + id + ", trajanje=" + trajanje + ", naziv=" + naziv + ", cena=" + cena + "]";
	}



	public TipPregleda(Integer id, Integer trajanje, String naziv, Set<Pregled> pregledi, Cena cena) {
		super();
		this.id = id;
		this.trajanje = trajanje;
		this.naziv = naziv;
		this.pregledi = pregledi;
		this.cena = cena;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(Integer trajanje) {
		this.trajanje = trajanje;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Cena getCena() {
		return cena;
	}

	public void setCena(Cena cena) {
		this.cena = cena;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Set<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(Set<Lekar> lekari) {
		this.lekari = lekari;
	}

}
