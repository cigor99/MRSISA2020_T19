package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.TipPregleda;

public class TipPregledaDTO {
	
	private int id;
	private String naziv;
	private int trajanje;
	private double cena;
	
	public TipPregledaDTO() {
		super();
	}

	public TipPregledaDTO(int id, String naziv, int trajanje, double cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trajanje = trajanje;
		this.cena = cena;
	}
	
	public TipPregledaDTO(TipPregleda tip) {
		this.id = tip.getId();
		this.naziv = tip.getNaziv();
		this.trajanje = tip.getTrajanje();
		this.cena = tip.getCena().getIznos();
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

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	

}
