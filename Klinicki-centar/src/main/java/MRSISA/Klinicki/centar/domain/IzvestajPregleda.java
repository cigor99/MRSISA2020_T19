package MRSISA.Klinicki.centar.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Izvestaji Pregleda")
public class IzvestajPregleda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Izvestaja", unique = true, nullable = false)
	private Integer id;
	
//	@Column(name = "recepti", unique = false)
	private List<Integer> recepti;
	
//	@Column(name = "dijgnoza")
	private Integer dijagnoza;
	
//	@Column(name = "pregled")
	private Integer pregled; // Ovo je id pregleda
	
//	@Column(name = "lekar")
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
