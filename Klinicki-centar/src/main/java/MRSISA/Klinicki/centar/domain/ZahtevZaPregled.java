package MRSISA.Klinicki.centar.domain;

public class ZahtevZaPregled {
	private Integer id;
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
