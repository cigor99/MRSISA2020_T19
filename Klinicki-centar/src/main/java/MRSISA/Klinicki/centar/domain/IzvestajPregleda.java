package MRSISA.Klinicki.centar.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Izvestaji Pregleda")
public class IzvestajPregleda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Izvestaja", unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "recepti", referencedColumnName = "ID_Recepta", nullable = false)
	private Set<Integer> recepti = new HashSet<Integer>();

	@ManyToOne
	@JoinColumn(name = "dijagnoza", referencedColumnName = "ID_Dijagnoza", nullable = false)
	private Dijagnoza dijagnoza;

	@ManyToOne
	@JoinColumn(name = "pregled", referencedColumnName = "ID_Pregleda", nullable = false)
	private Pregled pregled;

	@ManyToOne
	@JoinColumn(name = "lekar", referencedColumnName = "ID_lekara", nullable = false)
	private Lekar lekar; 

	public IzvestajPregleda() {
		super();
	}

	public IzvestajPregleda(Integer id, Set<Integer> recepti, Dijagnoza dijagnoza, Pregled pregled, Lekar lekar) {
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

	public Set<Integer> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Integer> recepti) {
		this.recepti = recepti;
	}

	public Dijagnoza getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(Dijagnoza dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

}
