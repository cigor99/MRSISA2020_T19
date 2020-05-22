package MRSISA.Klinicki.centar.dto;

import java.util.Date;


public class PretragaKlinikaDTO {
	public Date datum;
	public Double ocena;
	public Integer tip;
	public PretragaKlinikaDTO(Date datum, Double ocena, Integer tip) {
		super();
		this.datum = datum;
		this.ocena = ocena;
		this.tip = tip;
	}
	public PretragaKlinikaDTO() {
		super();
	}
	@Override
	public String toString() {
		return "PretragaDTO [datum=" + datum + ", ocena=" + ocena + ", tip=" + tip + "]";
	}
	
	

}
