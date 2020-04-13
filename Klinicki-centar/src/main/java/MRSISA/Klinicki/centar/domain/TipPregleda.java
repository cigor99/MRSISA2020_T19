package MRSISA.Klinicki.centar.domain;

public class TipPregleda {
	private Integer id;
	private Integer trajanje;
	private String naziv;
	private Integer cena; // Id za klasu cena

	public TipPregleda() {
		super();
	}

	public TipPregleda(Integer id, Integer trajanje, String naziv, Integer cena) {
		super();
		this.id = id;
		this.trajanje = trajanje;
		this.naziv = naziv;
		this.cena = cena;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(Integer trajanje) {
		this.trajanje = trajanje;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Integer getCena() {
		return cena;
	}

	public void setCena(Integer cena) {
		this.cena = cena;
	}

}
