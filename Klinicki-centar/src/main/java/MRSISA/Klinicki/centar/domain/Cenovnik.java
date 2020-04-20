package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cenovnik")
public class Cenovnik {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Cenovnika", unique = true, nullable = false)
	private Integer id;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "cenovnik")
	private Set<Cena> cene = new HashSet<Cena>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "cenovnik")
	private Set<Klinika> klinike = new HashSet<Klinika>();

	public Cenovnik() {

	}

	public Cenovnik(Integer id, Set<Cena> cene, Set<Klinika> klinike) {
		super();
		this.id = id;
		this.cene = cene;
		this.klinike = klinike;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Cena> getCene() {
		return cene;
	}

	public void setCene(Set<Cena> cene) {
		this.cene = cene;
	}

	public Set<Klinika> getKlinike() {
		return klinike;
	}

	public void setKlinike(Set<Klinika> klinike) {
		this.klinike = klinike;
	}

}
