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
import javax.persistence.Version;

@Entity
@Table(name = "Operacije")
public class Operacija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Operacije", unique = true, nullable = false)
	private Integer id;

	@Column(name = "datum", unique = false, nullable = false)
	private Date datum;
	
	@Column(name = "trajanje", unique = false, nullable = false)
	private int trajanje;

	@ManyToOne
	@JoinColumn(name = "sala", referencedColumnName = "ID_Sale", nullable = true)
	private Sala sala;

	// MOZDA STAVITI EAGER
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "lekar_operacija", joinColumns = @JoinColumn(name = "ID_Operacije"), inverseJoinColumns = @JoinColumn(name = "ID_Lekara"))
	private Set<Lekar> lekari = new HashSet<Lekar>();

//	@ManyToOne
//	@JoinColumn(name = "tip_pregleda", referencedColumnName = "ID_TipaPregleda", nullable = false)
//	private TipPregleda tipPregleda;

	@ManyToOne
	@JoinColumn(name = "pacijent", referencedColumnName = "ID_Pacijenta", nullable = false)
	private Pacijent pacijent;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "operacija")
	private Set<ZahtevZaOperaciju> zahteviZaOperaciju = new HashSet<ZahtevZaOperaciju>();
	
	@Version
	@Column(name = "verzija", nullable = false, unique = false)
	private int version;

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
//		this.tipPregleda = tipPregleda;
		this.pacijent = pacijent;
		this.zahteviZaOperaciju = zahteviZaOperaciju;
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
		Operacija other = (Operacija) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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


	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

}