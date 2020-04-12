package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Klinika {
	private String naziv;
	private String adresa;
	private String opis;
//	private ArrayList<Lekar> lekari;
//	private ArrayList<Cena>Cenovik;
//	private ArrayList<Sala>Sale;

	public String getNaziv() {
		return naziv;
	}

	public Klinika(@JsonProperty("naziv") String naziv, @JsonProperty("adresa") String adresa,
			@JsonProperty("opis") String opis) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
//		this.lekari = lekari;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

//	public ArrayList<Lekar> getLekari() {
//		return lekari;
//	}
//
//	public void setLekari(ArrayList<Lekar> lekari) {
//		this.lekari = lekari;
//	}

}
