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
		double a = this.prosecnaOcena * 100;
		double i = (Math.round(a));
		double p = i/100;
		this.prosecnaOcena = p;

	}

	public KlinikaDTO(int id, String naziv, String opis, String adresa, Double prosecnaOcena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.adresa = adresa;
		this.prosecnaOcena = prosecnaOcena;
		double a = this.prosecnaOcena * 100;
		double i = (Math.round(a));
		double p = i/100;
		this.prosecnaOcena = p;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KlinikaDTO other = (KlinikaDTO) obj;
		if (id != other.id)
			return false;
		return true;
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
