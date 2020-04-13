package MRSISA.Klinicki.centar.domain;

import java.util.Date;
import java.util.List;

public class Operacija {
	private Integer id;
	private Date datum;
	private Integer sala;
	private List<Integer> lekari;
	private TipPregleda tip;
	private Integer pacijent;

	public Operacija() {
		super();
	}

	public Operacija(Integer id, Date datum, Integer sala, List<Integer> lekari, TipPregleda tip, Integer pacijent) {
		super();
		this.id = id;
		this.datum = datum;
		this.sala = sala;
		this.lekari = lekari;
		this.tip = tip;
		this.pacijent = pacijent;
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

	public List<Integer> getLekari() {
		return lekari;
	}

	public void setLekari(List<Integer> lekari) {
		this.lekari = lekari;
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

}
