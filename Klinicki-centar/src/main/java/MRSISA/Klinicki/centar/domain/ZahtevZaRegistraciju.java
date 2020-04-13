package MRSISA.Klinicki.centar.domain;

public class ZahtevZaRegistraciju {
	private Integer id;
	private StanjeZahteva stanje;
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
