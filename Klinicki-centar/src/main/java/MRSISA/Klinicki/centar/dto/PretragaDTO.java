package MRSISA.Klinicki.centar.dto;

import java.util.Date;


public class PretragaDTO {
	public Date datum;
	public Double ocena;
	public Integer tip;
	public PretragaDTO(Date datum, Double ocena, Integer tip) {
		super();
		this.datum = datum;
		this.ocena = ocena;
		this.tip = tip;
	}
	public PretragaDTO() {
		super();
	}
	@Override
	public String toString() {
		return "PretragaDTO [datum=" + datum + ", ocena=" + ocena + ", tip=" + tip + "]";
	}
	
	

}
