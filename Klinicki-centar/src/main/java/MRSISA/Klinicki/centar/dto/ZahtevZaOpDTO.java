package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.ZahtevZaOperaciju;

public class ZahtevZaOpDTO {

	private Integer id;
	private Integer operacija;
	private String stanjeZahteva;
	private Integer sala;
	private String datumVreme;

	public ZahtevZaOpDTO() {

	}

	public ZahtevZaOpDTO(Integer id, Integer operacija, String stanjeZahteva, Integer sala, String datumVreme) {
		super();
		this.id = id;
		this.operacija = operacija;
		this.stanjeZahteva = stanjeZahteva;
		this.sala = sala;
		this.datumVreme = datumVreme;

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

	public Integer getSala() {
		return sala;
	}

	public void setSala(Integer sala) {
		this.sala = sala;
	}

	public String getDatumVreme() {
		return datumVreme;
	}

	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
	}

	@Override
	public String toString() {
		return "ZahtevZaOpDTO [id=" + id + ", operacija=" + operacija + ", stanjeZahteva=" + stanjeZahteva + ", sala="
				+ sala + ", datumVreme=" + datumVreme + "]";
	}

}
