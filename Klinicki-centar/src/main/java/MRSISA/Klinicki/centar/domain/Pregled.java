package MRSISA.Klinicki.centar.domain;

import java.util.Date;
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

@Entity
@Table(name = "Pregledi")
public class Pregled {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Pregleda", unique = true, nullable = false)
	private Integer id;

	@Column(name = "date", unique = false, nullable = false)
	private Date datum;

	@ManyToOne
	@JoinColumn(name = "sala", referencedColumnName = "ID_Sale", nullable = false)
	private Sala sala;

	@ManyToOne
	@JoinColumn(name = "lekar", referencedColumnName = "ID_lekara", nullable = false)
	private Lekar lekar;

	@ManyToOne
	@JoinColumn(name = "tip_pregleda", referencedColumnName = "ID_TipaPregleda", nullable = false)
	private TipPregleda tipPregleda;

	@ManyToOne
	@JoinColumn(name = "pacijent", referencedColumnName = "ID_Pacijenta", nullable = true)
	private Pacijent pacijent;

	@Column(name = "popust", unique = false, nullable = false)
	private float popust;

	@Column(name = "slobodan", unique = false, nullable = false)
	private boolean slobodan;

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "pregled")
	private Set<IzvestajPregleda> izvestajiPregleda = new HashSet<IzvestajPregleda>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "pregled")
	private Set<Recept> recepti = new HashSet<Recept>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "pregled")
	private Set<ZahtevZaPregled> zahteviZaPregled = new HashSet<ZahtevZaPregled>();

	public Pregled() {
		super();
	}

	public Pregled(Integer id, Date datum, Sala sala, Lekar lekar, TipPregleda tipPregleda, Pacijent pacijent,
			float popust, boolean slobodan, Set<IzvestajPregleda> izvestajiPregleda, Set<Recept> recepti,
			Set<ZahtevZaPregled> zahteviZaPregled) {
		super();
		this.id = id;
		this.datum = datum;
		this.sala = sala;
		this.lekar = lekar;
		this.tipPregleda = tipPregleda;
		this.pacijent = pacijent;
		this.popust = popust;
		this.slobodan = slobodan;
		this.izvestajiPregleda = izvestajiPregleda;
		this.recepti = recepti;
		this.zahteviZaPregled = zahteviZaPregled;
	}
	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pregled other = (Pregled) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pregled [id=" + id + ", datum=" + datum + ", lekar=" + lekar.getId() + ", tipPregleda=" + tipPregleda.getNaziv()
				+ ", pacijent=" + pacijent.getId() + ", slobodan=" + slobodan + "]";
	}

	public Set<ZahtevZaPregled> getZahteviZaPregled() {
		return zahteviZaPregled;
	}

	public void setZahteviZaPregled(Set<ZahtevZaPregled> zahteviZaPregled) {
		this.zahteviZaPregled = zahteviZaPregled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public float getPopust() {
		return popust;
	}

	public void setPopust(float popust) {
		this.popust = popust;
	}

	public boolean isSlobodan() {
		return slobodan;
	}

	public void setSlobodan(boolean slobodan) {
		this.slobodan = slobodan;
	}

	public Set<IzvestajPregleda> getIzvestajiPregleda() {
		return izvestajiPregleda;
	}

	public void setIzvestajiPregleda(Set<IzvestajPregleda> izvestajiPregleda) {
		this.izvestajiPregleda = izvestajiPregleda;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}

}
