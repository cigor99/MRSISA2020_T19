package MRSISA.Klinicki.centar.dto;

import java.util.Date;

public class SlanjeZahtevaZaPregledDTO {
	private Date datum;
	private String vreme;
	private Integer tipID;
	private Integer lekarID;
	
	public SlanjeZahtevaZaPregledDTO() {
		
	}

	@Override
	public String toString() {
		return "SlanjeZahtevaZaPregledDTO [datum=" + datum + ", vreme=" + vreme + ", tipID=" + tipID + ", lekarID="
				+ lekarID + "]";
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
	}

	public Integer getTipID() {
		return tipID;
	}

	public void setTipID(Integer tipID) {
		this.tipID = tipID;
	}

	public Integer getLekarID() {
		return lekarID;
	}

	public void setLekarID(Integer lekarID) {
		this.lekarID = lekarID;
	}
	
	
}
