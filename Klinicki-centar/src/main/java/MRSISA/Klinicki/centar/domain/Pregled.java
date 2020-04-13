package MRSISA.Klinicki.centar.domain;

import java.util.Date;

public class Pregled {
	private Integer id;
	private Date datum;
	private Integer sala;
	private Integer lekar;
	private TipPregleda tip;
	private Integer pacijent;
	private float popust; // U modelu je int, zar nije popust decimala?
	private boolean slobodan;

	public Pregled() {
		super();
	}

	public Pregled(Integer id, Date datum, Integer sala, Integer lekar, TipPregleda tip, Integer pacijent, float popust,
			boolean slobodan) {
		super();
		this.id = id;
		this.datum = datum;
		this.sala = sala;
		this.lekar = lekar;
		this.tip = tip;
		this.pacijent = pacijent;
		this.popust = popust;
		this.slobodan = slobodan;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Integer getSala() {
		return sala;
	}

	public void setSala(Integer sala) {
		this.sala = sala;
	}

	public Integer getLekar() {
		return lekar;
	}

	public void setLekar(Integer lekar) {
		this.lekar = lekar;
	}

	public TipPregleda getTip() {
		return tip;
	}

	public void setTip(TipPregleda tip) {
		this.tip = tip;
	}

	public Integer getPacijent() {
		return pacijent;
	}

	public void setPacijent(Integer pacijent) {
		this.pacijent = pacijent;
	}

	public float getPopust() {
		return popust;
	}

	public void setPopust(float popust) {
		this.popust = popust;
	}

	public boolean isSlobodan() {
		return slobodan;
	}

	public void setSlobodan(boolean slobodan) {
		this.slobodan = slobodan;
	}

}
