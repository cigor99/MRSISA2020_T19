package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;

public class ZahtevZaRegDTO {

	private Integer id;
	private StanjeZahteva stanje;
	private int pacijent;
	private int klinickiCentar;

	public ZahtevZaRegDTO() {

	}
	public ZahtevZaRegDTO(ZahtevZaRegistraciju zahtev) {
		super();
		this.id = zahtev.getId();
		this.stanje = zahtev.getStanje();
		this.pacijent = zahtev.getPacijent().getId();
		this.klinickiCentar = zahtev.getKlinickiCentar().getId();
	}
	

	public ZahtevZaRegDTO(Integer id, StanjeZahteva stanje, int pacijent, int klinickiCentar) {
		super();
		this.id = id;
		this.stanje = stanje;
		this.pacijent = pacijent;
		this.klinickiCentar = klinickiCentar;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StanjeZahteva getStanje() {
		return stanje;
	}

	public void setStanje(StanjeZahteva stanje) {
		this.stanje = stanje;
	}

	public int getPacijent() {
		return pacijent;
	}

	public void setPacijent(int pacijent) {
		this.pacijent = pacijent;
	}

	public int getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(int klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}

}
