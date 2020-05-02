package MRSISA.Klinicki.centar.domain;

import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Operacije")
public class Operacija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Operacije", unique = true, nullable = false)
	private Integer id;

	@Column(name = "datum", unique = false, nullable = false)
	private Date datum;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sala", referencedColumnName = "ID_Sale", nullable = false)
	private Sala sala;

	// MOZDA STAVITI EAGER
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "lekari", joinColumns = @JoinColumn(name = "ID_Leka"), inverseJoinColumns = @JoinColumn(name = "ID_Operacije"))
	private Set<Lekar> lekari = new HashSet<Lekar>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tip_pregleda", referencedColumnName = "ID_TipaPregleda", nullable = false)
	private TipPregleda tipPregleda;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pacijent", referencedColumnName = "ID_Pacijenta", nullable = false)
	private Pacijent pacijent;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "operacija")
	private Set<ZahtevZaOperaciju> zahteviZaOperaciju = new HashSet<ZahtevZaOperaciju>();

	public Operacija() {
		super();
	}

	public Operacija(Integer id, Date datum, Sala sala, Set<Lekar> lekari, TipPregleda tipPregleda, Pacijent pacijent,
			Set<ZahtevZaOperaciju> zahteviZaOperaciju) {
		super();
		this.id = id;
		this.datum = datum;
		this.sala = sala;
		this.lekari = lekari;
		this.tipPregleda = tipPregleda;
		this.pacijent = pacijent;
		this.zahteviZaOperaciju = zahteviZaOperaciju;
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

	public Set<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(Set<Lekar> lekari) {
		this.lekari = lekari;
	}

	public Set<ZahtevZaOperaciju> getZahteviZaOperaciju() {
		return zahteviZaOperaciju;
	}

	public void setZahteviZaOperaciju(Set<ZahtevZaOperaciju> zahteviZaOperaciju) {
		this.zahteviZaOperaciju = zahteviZaOperaciju;
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

}
