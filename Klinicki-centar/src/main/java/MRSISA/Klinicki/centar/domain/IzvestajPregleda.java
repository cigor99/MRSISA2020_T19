package MRSISA.Klinicki.centar.domain;

import java.util.List;

public class IzvestajPregleda {
	private Integer id;
	private List<Integer> recepti;
	private Integer dijagnoza;
	private Integer pregled; // Ovo je id pregleda
	private Integer lekar; // Ovo je id lekara

	public IzvestajPregleda() {
		super();
	}

	public IzvestajPregleda(Integer id, List<Integer> recepti, Integer dijagnoza, Integer pregled, Integer lekar) {
		super();
		this.id = id;
		this.recepti = recepti;
		this.dijagnoza = dijagnoza;
		this.pregled = pregled;
		this.lekar = lekar;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getRecepti() {
		return recepti;
	}

	public void setRecepti(List<Integer> recepti) {
		this.recepti = recepti;
	}

	public Integer getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(Integer dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Integer getPregled() {
		return pregled;
	}

	public void setPregled(Integer pregled) {
		this.pregled = pregled;
	}

	public Integer getLekar() {
		return lekar;
	}

	public void setLekar(Integer lekar) {
		this.lekar = lekar;
	}

}
