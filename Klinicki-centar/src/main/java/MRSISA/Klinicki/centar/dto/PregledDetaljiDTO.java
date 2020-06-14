package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Pregled;

public class PregledDetaljiDTO {
	private String lekarIme;
	private String lekarId;
	private String klinikaNaziv;
	private String klinikaId;
	
	public PregledDetaljiDTO(String lekarIme, String lekarId, String klinikaNaziv, String klinikaId) {
		super();
		this.lekarIme = lekarIme;
		this.lekarId = lekarId;
		this.klinikaNaziv = klinikaNaziv;
		this.klinikaId = klinikaId;
	}
	
	public PregledDetaljiDTO(Pregled p) {
		this.lekarIme = p.getLekar().getIme() + " " + p.getLekar().getPrezime();
		this.lekarId = "" + p.getLekar().getId();
		this.klinikaNaziv = p.getLekar().getKlinika().getNaziv();
		this.klinikaId = "" + p.getLekar().getKlinika().getId();
	}
	public PregledDetaljiDTO() {
		super();
	}
	public String getLekarIme() {
		return lekarIme;
	}
	public void setLekarIme(String lekarIme) {
		this.lekarIme = lekarIme;
	}
	public String getLekarId() {
		return lekarId;
	}
	public void setLekarId(String lekarId) {
		this.lekarId = lekarId;
	}
	public String getKlinikaNaziv() {
		return klinikaNaziv;
	}
	public void setKlinikaNaziv(String klinikaNaziv) {
		this.klinikaNaziv = klinikaNaziv;
	}
	public String getKlinikaId() {
		return klinikaId;
	}
	public void setKlinikaId(String klinikaId) {
		this.klinikaId = klinikaId;
	}
	
	
}
