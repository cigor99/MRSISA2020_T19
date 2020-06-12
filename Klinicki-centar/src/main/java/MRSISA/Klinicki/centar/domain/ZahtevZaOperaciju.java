package MRSISA.Klinicki.centar.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Zahtevi_za_operaciju")
public class ZahtevZaOperaciju {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ZZO", unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "operacija", referencedColumnName = "ID_Operacije", nullable = false)
	private Operacija operacija;

	@Column(name = "stanje_zahteva", unique = false, nullable = false)
	private StanjeZahteva stanjeZahteva;

	public ZahtevZaOperaciju() {

	}

	public ZahtevZaOperaciju(Integer id, Operacija operacija, StanjeZahteva stanjeZahteva) {
		super();
		this.id = id;
		this.operacija = operacija;
		this.stanjeZahteva = stanjeZahteva;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Operacija getOperacija() {
		return operacija;
	}

	public void setOperacija(Operacija operacija) {
		this.operacija = operacija;
	}

	public StanjeZahteva getStanjeZahteva() {
		return stanjeZahteva;
	}

	public void setStanjeZahteva(StanjeZahteva stanjeZahteva) {
		this.stanjeZahteva = stanjeZahteva;
	}
	
	

}
