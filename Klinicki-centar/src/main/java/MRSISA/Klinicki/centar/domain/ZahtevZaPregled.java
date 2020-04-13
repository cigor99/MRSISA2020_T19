package MRSISA.Klinicki.centar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Zahtevi za pregled")
public class ZahtevZaPregled {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "ID_ZZP", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "stanje zahteva", unique = false, nullable = false)
	private StanjeZahteva stanje;
	
	private Integer pregled;

	public ZahtevZaPregled() {
		super();
	}

	public ZahtevZaPregled(Integer id, StanjeZahteva stanje, Integer pregled) {
		super();
		this.id = id;
		this.stanje = stanje;
		this.pregled = pregled;
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

	public Integer getPregled() {
		return pregled;
	}

	public void setPregled(Integer pregled) {
		this.pregled = pregled;
	}

}
