package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;

public class Recept {
	private Integer id;
	private Integer pregled; // Ovo je id izvestaja o pregledu
	private ArrayList<Integer> lekovi;

	public Recept() {
		super();
	}

	public Recept(Integer id, Integer pregled, ArrayList<Integer> lekovi) {
		super();
		this.id = id;
		this.pregled = pregled;
		this.lekovi = lekovi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPregled() {
		return pregled;
	}

	public void setPregled(Integer pregled) {
		this.pregled = pregled;
	}

	public ArrayList<Integer> getLekovi() {
		return lekovi;
	}

	public void setLekovi(ArrayList<Integer> lekovi) {
		this.lekovi = lekovi;
	}

	

}
