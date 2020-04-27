package MRSISA.Klinicki.centar.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Zdravstevni_kartoni")
public class ZdravstveniKarton {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Zdravstvenog_kartona", unique = true, nullable = false)
	private Integer id;

	@Column(name = "visina", unique = false)
	private double visina;

	@Column(name = "tezina", unique = false)
	private double tezina;

	@Column(name = "krvna_grupa", unique = false)
	private KrvnaGrupa krvnaGrupa;

	@Column(name = "dioptrija", unique = false)
	private double dioptrija;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "zdravstveniKarton")
	private Set<IzvestajPregleda> izvestaji;

	@OneToOne(mappedBy = "zdravstveniKarton")
	private Pacijent pacijent;

	public ZdravstveniKarton() {
		super();
	}

	public ZdravstveniKarton(Integer id, double visina, double tezina, KrvnaGrupa krvnaGrupa, double dioptrija,
			Set<IzvestajPregleda> izvestaji, Pacijent pacijent) {
		super();
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
		this.dioptrija = dioptrija;
		this.izvestaji = izvestaji;
		this.pacijent = pacijent;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public KrvnaGrupa getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(KrvnaGrupa krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(double dioptrija) {
		this.dioptrija = dioptrija;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<IzvestajPregleda> getIzvestaji() {
		return izvestaji;
	}

	public void setIzvestaji(Set<IzvestajPregleda> izvestaji) {
		this.izvestaji = izvestaji;
	}

}
