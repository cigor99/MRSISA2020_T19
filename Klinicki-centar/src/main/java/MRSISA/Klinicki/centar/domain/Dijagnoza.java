package MRSISA.Klinicki.centar.domain;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Dijagnoze")
public class Dijagnoza {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Dijagnoza", unique = true, nullable = false)
	private Integer id;

	@Column(name = "sifra", unique = true, nullable = false)
	private String sifra;

	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;

	@Column(name = "opis", unique = false, nullable = false)
	private String opis;

	@JsonIgnoreProperties({"sifarnikDijagnoza", "sifranikLekova", "zahteviZaReg","adminiKC" })
	@ManyToOne
	@JoinColumn(name = "klinicki_centar", referencedColumnName = "ID_KC")
	private KlinickiCentar klinickiCentar;

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "dijagnoza")//cascade = { CascadeType.ALL },
	private Set<IzvestajPregleda> izvestajiPregleda = new HashSet<IzvestajPregleda>();

	public Dijagnoza() {
		super();
	}
	public Dijagnoza(Integer id, String sifra, String naziv, String opis) {
		super();
		this.id = id;
		this.sifra = sifra;
		this.naziv = naziv;
		this.opis = opis;
	}

	public Dijagnoza(Integer id, String sifra, String naziv, String opis, KlinickiCentar klinickiCentar,
			Set<IzvestajPregleda> izvestajiPregleda) {
		super();
		this.id = id;
		this.sifra = sifra;
		this.naziv = naziv;
		this.opis = opis;
		this.klinickiCentar = klinickiCentar;
		this.izvestajiPregleda = izvestajiPregleda;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}

	public Set<IzvestajPregleda> getIzvestajiPregleda() {
		return izvestajiPregleda;
	}

	public void setIzvestajiPregleda(Set<IzvestajPregleda> izvestajiPregleda) {
		this.izvestajiPregleda = izvestajiPregleda;
	}

}
