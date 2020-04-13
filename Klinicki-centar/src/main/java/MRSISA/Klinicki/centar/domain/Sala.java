package MRSISA.Klinicki.centar.domain;

public class Sala {
	private Integer id;
	private String naziv;
	private TipSale tip;

	public Sala() {
		super();
	}

	public Sala(Integer id, String naziv, TipSale tip) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipSale getTip() {
		return tip;
	}

	public void setTip(TipSale tip) {
		this.tip = tip;
	}

}
