package MRSISA.Klinicki.centar.dto;

public class PrvoLogovanjeDTO {
	private Integer id;
	private String sifra;

	public PrvoLogovanjeDTO() {

	}

	public PrvoLogovanjeDTO(Integer id, String sifra) {
		super();
		this.id = id;
		this.sifra = sifra;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

}
