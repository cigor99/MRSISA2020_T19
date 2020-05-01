package MRSISA.Klinicki.centar.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Zahtevi_za_registraciju")
public class ZahtevZaRegistraciju {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ZZR", unique = true, nullable = false)
	private Integer id;

	@Column(name = "stanje_zahteva", unique = false, nullable = false)
	private StanjeZahteva stanje;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "pacijent", referencedColumnName = "ID_Pacijenta")
	private Pacijent pacijent;

	@ManyToOne
	@JoinColumn(name = "klinicki_centar", referencedColumnName = "ID_KC", nullable = false)
	private KlinickiCentar klinickiCentar;

	public ZahtevZaRegistraciju() {
		
	}
	
	public ZahtevZaRegistraciju(Integer id, StanjeZahteva stanje, Pacijent pacijent, KlinickiCentar klinickiCentar) {
		super();
		this.id = id;
		this.stanje = stanje;
		this.pacijent = pacijent;
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

	public StanjeZahteva getStanje() {
		return stanje;
	}

	public void setStanje(StanjeZahteva stanje) {
		this.stanje = stanje;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

}
