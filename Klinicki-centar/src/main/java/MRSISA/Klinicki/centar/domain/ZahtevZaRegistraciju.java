package MRSISA.Klinicki.centar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Zahtevi za registraciju")
public class ZahtevZaRegistraciju {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "ID_ZZR", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "stanje zahteva", unique = false, nullable = false)
	private StanjeZahteva stanje;
	
	@Column(name = "pacijent", unique = false, nullable = false)
	private Pacijent pacijent;
	
	public ZahtevZaRegistraciju(Integer id, StanjeZahteva stanje, Pacijent pacijent) {
		super();
		this.id = id;
		this.stanje = stanje;
		this.pacijent = pacijent;
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
