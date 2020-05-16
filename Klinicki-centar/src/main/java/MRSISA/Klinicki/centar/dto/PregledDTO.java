package MRSISA.Klinicki.centar.dto;

import java.util.Date;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.TipPregleda;

public class PregledDTO {
	
	private int id;
	private Date datum;
	private Sala sala;
	private Lekar lekar;
	private TipPregleda tipPregleda;
	private Pacijent pacijent;
	private float popust;
	private boolean slobodan;

	public PregledDTO() {
		super();
	}

	public PregledDTO(int id, Date datum, Sala sala, Lekar lekar, TipPregleda tipPregleda, Pacijent pacijent,
			float popust, boolean slobodan) {
		super();
		this.id = id;
		this.datum = datum;
		this.sala = sala;
		this.lekar = lekar;
		this.tipPregleda = tipPregleda;
		this.pacijent = pacijent;
		this.popust = popust;
		this.slobodan = slobodan;
	}
	
	public PregledDTO(Pregled pregled) {
		super();
		this.id = pregled.getId();
		this.datum = pregled.getDatum();
		this.sala = pregled.getSala();
		this.lekar = pregled.getLekar();
		this.tipPregleda = pregled.getTipPregleda();
		this.pacijent = pregled.getPacijent();
		this.popust = pregled.getPopust();
		this.slobodan = pregled.isSlobodan();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
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
