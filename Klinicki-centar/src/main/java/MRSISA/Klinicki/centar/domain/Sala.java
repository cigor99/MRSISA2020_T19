package MRSISA.Klinicki.centar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Sale")
public class Sala {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "ID_Sale", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;
	
	@Column(name = "tip sale", unique = false, nullable = false)
	private TipSale tip;

	public Sala() {
		super();
	}

	public Sala(Integer id, String naziv, TipSale tip) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
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

	public TipSale getTip() {
		return tip;
	}

	public void setTip(TipSale tip) {
		this.tip = tip;
	}

}
