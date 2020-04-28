package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Dijagnoza;

public class DijagnozaDTO {
	private Integer id;
	private String sifra;
	private String naziv;
	private String opis;

	public DijagnozaDTO() {

	}

	public DijagnozaDTO(Integer id, String sifra, String naziv, String opis) {
		super();
		this.id = id;
		this.sifra = sifra;
		this.naziv = naziv;
		this.opis = opis;
	}

	public DijagnozaDTO(Dijagnoza dijagnoza) {
		super();
		this.id = dijagnoza.getId();
		this.sifra = dijagnoza.getSifra();
		this.naziv = dijagnoza.getNaziv();
		this.opis = dijagnoza.getOpis();
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

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

}
