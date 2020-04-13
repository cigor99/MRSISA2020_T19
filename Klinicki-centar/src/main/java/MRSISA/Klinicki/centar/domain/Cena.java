package MRSISA.Klinicki.centar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cene")
public class Cena {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Cene", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "iznos", unique = false, nullable = false)
	private Double iznos;

	public Cena() {
		super();
	}

	public Cena(Integer id, Double iznos) {
		super();
		this.id = id;
		this.iznos = iznos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

}
