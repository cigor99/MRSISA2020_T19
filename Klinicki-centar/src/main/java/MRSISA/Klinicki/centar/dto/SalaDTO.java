package MRSISA.Klinicki.centar.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.TipSale;

public class SalaDTO {
	
	private int id;
	private String naziv;
	private TipSale tip;
	private String prviSlobodanTermin;
	
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
		Date d = sala.getPrviSlobodanTermin();
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.prviSlobodanTermin = formatter.format(d);
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

	public String getPrviSlobodanTermin() {
		return prviSlobodanTermin;
	}

	public void setPrviSlobodanTermin(String prviSlobodanTermin) {
		this.prviSlobodanTermin = prviSlobodanTermin;
	}
	
	

}
