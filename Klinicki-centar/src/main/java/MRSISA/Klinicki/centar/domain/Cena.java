package MRSISA.Klinicki.centar.domain;

public class Cena {
	private Integer id;
	private Double iznos;

	public Cena() {
		super();
	}

	public Cena(Integer id, Double iznos) {
		super();
		this.id = id;
		this.iznos = iznos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

}
