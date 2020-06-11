package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;

public class ZahtevZaOpDTO {

	private Integer id;
	private Integer operacija;
	private String stanjeZahteva;

	public ZahtevZaOpDTO() {

	}

	public ZahtevZaOpDTO(Integer id, Integer operacija, String stanjeZahteva) {
		super();
		this.id = id;
		this.operacija = operacija;
		this.stanjeZahteva = stanjeZahteva;
	}

	public ZahtevZaOpDTO(ZahtevZaOperaciju zahtev) {
		this.id = zahtev.getId();
		this.operacija = zahtev.getOperacija().getId();
		this.stanjeZahteva = zahtev.getStanjeZahteva().name();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperacija() {
		return operacija;
	}

	public void setOperacija(Integer operacija) {
		this.operacija = operacija;
	}

	public String getStanjeZahteva() {
		return stanjeZahteva;
	}

	public void setStanjeZahteva(String stanjeZahteva) {
		this.stanjeZahteva = stanjeZahteva;
	}

}
