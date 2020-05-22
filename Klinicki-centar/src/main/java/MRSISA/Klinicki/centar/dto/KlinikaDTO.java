package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Klinika;

public class KlinikaDTO {

	private int id;
	private String naziv;
	private String opis;
	private String adresa;
	private Double prosecnaOcena;

	public KlinikaDTO() {

	}

	public KlinikaDTO(Klinika klinika) {
		this.adresa = klinika.getAdresa();
		this.id = klinika.getId();
		this.naziv = klinika.getNaziv();
		this.opis = klinika.getOpis();
		this.prosecnaOcena = klinika.getProsecnaOcena();
	}

	public KlinikaDTO(int id, String naziv, String opis, String adresa, Double prosecnaOcena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.adresa = adresa;
		this.prosecnaOcena = prosecnaOcena;
	}
	
	

	@Override
	public String toString() {
		return "KlinikaDTO [id=" + id + ", naziv=" + naziv + ", opis=" + opis + ", adresa=" + adresa + "]";
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

	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	
	
}
