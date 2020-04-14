package MRSISA.Klinicki.centar.domain;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Zdravstevni kartoni")
public class ZdravstveniKarton {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Pregleda", unique = true, nullable = false)
	private Integer id;

	private List<Integer> izvestaji;

	@OneToOne(mappedBy = "zdravstveniKarton")
	private Pacijent pacijent;

	public ZdravstveniKarton() {
		super();
	}

	public ZdravstveniKarton(Integer id, List<Integer> izvestaji, Pacijent pacijent) {
		super();
		this.id = id;
		this.izvestaji = izvestaji;
		this.pacijent = pacijent;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getIzvestaji() {
		return izvestaji;
	}

	public void setIzvestaji(List<Integer> izvestaji) {
		this.izvestaji = izvestaji;
	}

}
