package MRSISA.Klinicki.centar.domain;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "Tipovi Pregleda")
public class TipPregleda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "ID_TipaPregleda", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "trajanje", unique = false, nullable = false)
	private Integer trajanje;
	
	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;
	
	@Column(name = "cena", unique = false, nullable = false)
	private Integer cena; // Id za klasu cena

	public TipPregleda() {
		super();
	}

	public TipPregleda(Integer id, Integer trajanje, String naziv, Integer cena) {
		super();
		this.id = id;
		this.trajanje = trajanje;
		this.naziv = naziv;
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

	public Integer getCena() {
		return cena;
	}

	public void setCena(Integer cena) {
		this.cena = cena;
	}

}
