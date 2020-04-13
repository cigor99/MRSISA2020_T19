package MRSISA.Klinicki.centar.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Zahtevi za godisnji")
public class ZahtevZaGodisnjiOdmor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "ID_ZZG", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "pocetni datum", unique = false, nullable = false)
	private Date pocetniDatum;
	
	@Column(name = "krajnji datum", unique = false, nullable = false)
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
