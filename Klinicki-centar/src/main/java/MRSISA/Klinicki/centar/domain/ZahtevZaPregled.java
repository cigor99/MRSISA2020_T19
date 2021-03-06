package MRSISA.Klinicki.centar.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Zahtevi_za_pregled")
public class ZahtevZaPregled {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ZZP", unique = true, nullable = false)
	private Integer id;

	@Column(name = "stanje_zahteva", unique = false, nullable = false)
	private StanjeZahteva stanjeZahteva;
	
	@Column(name = "datum_slanja", unique = false, nullable = false)
	private Date datumSlanja;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "pregled", referencedColumnName = "ID_Pregleda", nullable = false)
	private Pregled pregled;

	public ZahtevZaPregled() {
		super();
	}

	public ZahtevZaPregled(Integer id, StanjeZahteva stanjeZahteva, Pregled pregled, Date datumSlanja) {
		super();
		this.id = id;
		this.stanjeZahteva = stanjeZahteva;
		this.pregled = pregled;
		this.datumSlanja = datumSlanja;
	}
	
	

	

	@Override
	public String toString() {
		return "ZahtevZaPregled [id=" + id + ", stanjeZahteva=" + stanjeZahteva + ", datumSlanja=" + datumSlanja
				+ ", pregled=" + pregled.getId() + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StanjeZahteva getStanje() {
		return stanjeZahteva;
	}

	public void setStanje(StanjeZahteva stanje) {
		this.stanjeZahteva = stanje;
	}

	public StanjeZahteva getStanjeZahteva() {
		return stanjeZahteva;
	}

	public void setStanjeZahteva(StanjeZahteva stanjeZahteva) {
		this.stanjeZahteva = stanjeZahteva;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public Date getDatumSlanja() {
		return datumSlanja;
	}

	public void setDatumSlanja(Date datumSlanja) {
		this.datumSlanja = datumSlanja;
	}

}
