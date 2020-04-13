package MRSISA.Klinicki.centar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Lekovi")
public class Lek {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Leka", unique = true, nullable = false)
	private Integer id;

	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;

	@Column(name = "sifra", unique = true, nullable = false)
	private String sifra;

	public Lek() {
		super();
	}

	public Lek(Integer id, String naziv, String sifra) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.sifra = sifra;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

}
