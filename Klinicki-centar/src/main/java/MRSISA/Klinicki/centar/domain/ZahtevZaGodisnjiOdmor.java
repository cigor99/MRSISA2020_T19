package MRSISA.Klinicki.centar.domain;

import java.util.Date;

public class ZahtevZaGodisnjiOdmor {
	
	private Integer id;
	private Date pocetniDatum;
	private Date krajnjiDatum;
	private Integer medicinskoOsoblje;
	
	public ZahtevZaGodisnjiOdmor(Integer id, Date pocetniDatum, Date krajnjiDatum, Integer medicinskoOsoblje) {
		super();
		this.id = id;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.medicinskoOsoblje = medicinskoOsoblje;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(Date pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	public Date getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(Date krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

	public Integer getMedicinskoOsoblje() {
		return medicinskoOsoblje;
	}

	public void setMedicinskoOsoblje(Integer medicinskoOsoblje) {
		this.medicinskoOsoblje = medicinskoOsoblje;
	}

	
	
}
