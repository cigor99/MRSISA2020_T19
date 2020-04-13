package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Cenovnik")
public class Cenovnik {

	@Column(name = "cene", unique = false, nullable = false)
	private ArrayList<Cena> cene;

	public Cenovnik() {

	}

	public Cenovnik(ArrayList<Cena> cenovnik) {
		super();
		this.cene = cenovnik;
	}

	public ArrayList<Cena> getCenovnik() {
		return cene;
	}

	public void setCenovnik(ArrayList<Cena> cenovnik) {
		this.cene = cenovnik;
	}

}
