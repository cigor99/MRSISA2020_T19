package MRSISA.Klinicki.centar.domain;

public class Lek {
	private Integer id;
	private String naziv;
	private String sifra;

	public Lek() {
		super();
	}

	public Lek(Integer id, String naziv, String sifra) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.sifra = sifra;
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

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

}
