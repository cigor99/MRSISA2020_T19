package MRSISA.Klinicki.centar.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Embeddable
@Entity
@Table(name = "Lekovi")
public class Lek {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Leka", unique = true, nullable = false)
	private Integer id;

	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;

	@Column(name = "sifra", unique = true, nullable = false)
	private String sifra;

	@ManyToMany(mappedBy = "lekovi")
	private Set<Recept> recepti = new HashSet<Recept>();

	@JsonIgnoreProperties({"sifarnikDijagnoza", "sifranikLekova", "zahteviZaReg","adminiKC" })
	@ManyToOne
	@JoinColumn(name = "klinicki_centar", referencedColumnName = "ID_KC")
	private KlinickiCentar klinickiCentar;

	public Lek() {
		super();
	}

	public Lek(Integer id, String naziv, String sifra) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.sifra = sifra;
	}

	public Lek(Integer id, String naziv, String sifra, Set<Recept> recepti, KlinickiCentar klinickiCentar) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.sifra = sifra;
		this.recepti = recepti;
		this.klinickiCentar = klinickiCentar;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}

}
