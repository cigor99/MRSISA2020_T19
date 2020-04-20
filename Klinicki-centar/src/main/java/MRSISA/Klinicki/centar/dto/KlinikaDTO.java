package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Klinika;

public class KlinikaDTO {

	private int id;
	private String naziv;
	private String opis;
	private String adresa;

	public KlinikaDTO() {

	}

	public KlinikaDTO(Klinika klinika) {
		this.adresa = klinika.getAdresa();
		this.id = klinika.getId();
		this.naziv = klinika.getNaziv();
		this.opis = klinika.getOpis();
	}

	public KlinikaDTO(int id, String naziv, String opis, String adresa) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.adresa = adresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

}
