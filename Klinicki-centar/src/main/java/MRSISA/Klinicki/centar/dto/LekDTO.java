package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Lek;

public class LekDTO {

	private Integer id;
	private String naziv;
	private String sifra;

	public LekDTO() {

	}
	public LekDTO(Lek lek) {
		this.id = lek.getId();
		this.naziv = lek.getNaziv();
		this.sifra = lek.getSifra();
	}

	public LekDTO(Integer id, String naziv, String sifra) {
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
