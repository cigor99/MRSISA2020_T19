package MRSISA.Klinicki.centar.domain;

public class Recept {
	private Integer id;
	private Integer pregled; // Ovo je id izvestaja o pregledu
	private Integer lek;

	public Recept() {
		super();
	}

	public Recept(Integer id, Integer pregled, Integer lek) {
		super();
		this.id = id;
		this.pregled = pregled;
		this.lek = lek;
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

	public Integer getLek() {
		return lek;
	}

	public void setLek(Integer lek) {
		this.lek = lek;
	}

}
