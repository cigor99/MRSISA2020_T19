package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.TipSale;

public class SalaDTO {
	
	private int id;
	private String naziv;
	private TipSale tip;
	
	public SalaDTO() {
		super();
	}

	public SalaDTO(int id, String naziv, TipSale tip) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
	}
	
	public SalaDTO(Sala sala) {
		this.id = sala.getId();
		this.naziv = sala.getNaziv();
		this.tip = sala.getTip();
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

	public TipSale getTip() {
		return tip;
	}

	public void setTip(TipSale tip) {
		this.tip = tip;
	}
	
	

}
