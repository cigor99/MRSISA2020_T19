package MRSISA.Klinicki.centar.dto;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder.In;

import MRSISA.Klinicki.centar.domain.ZahtevZaGodisnjiOdmor;

public class ZahtevZaGodisnjiDTO {

	private Integer id;
	private String pocetniDatum;
	private String krajnjiDatum;
	private Integer lekar;
	private Integer medicinskaSestra;

	public ZahtevZaGodisnjiDTO() {
	}

	public ZahtevZaGodisnjiDTO(ZahtevZaGodisnjiOdmor zahtev) {
		super();
		this.id = zahtev.getId();
		this.pocetniDatum = zahtev.getPocetniDatum().toString();
		this.krajnjiDatum = zahtev.getKrajnjiDatum().toString();
		if (zahtev.getLekar() != null) {
			this.lekar = zahtev.getLekar().getId();
		}
		if (zahtev.getMedicinskaSestra() != null) {
			this.medicinskaSestra = zahtev.getMedicinskaSestra().getId();
		}
	}

	public ZahtevZaGodisnjiDTO(Integer id, String pocetniDatum, String krajnjiDatum, Integer lekar,
			Integer medicinskaSestra) {

		super();
		this.id = id;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.lekar = lekar;
		this.medicinskaSestra = medicinskaSestra;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(String pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	public String getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(String krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

	public Integer getLekar() {
		return lekar;
	}

	public void setLekar(Integer lekar) {
		this.lekar = lekar;
	}

	public Integer getMedicinskaSestra() {
		return medicinskaSestra;
	}

	public void setMedicinskaSestra(Integer medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
	}

}
