package MRSISA.Klinicki.centar.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Zdravstevni kartoni")
public class ZdravstveniKarton {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Pregleda", unique = true, nullable = false)
	private Integer id;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "zdravstveniKarton")
	private Set<IzvestajPregleda> izvestaji;

	@OneToOne(mappedBy = "zdravstveniKarton")
	private Pacijent pacijent;

	public ZdravstveniKarton() {
		super();
	}

	public ZdravstveniKarton(Integer id, Set<IzvestajPregleda> izvestaji, Pacijent pacijent) {
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

	public Set<IzvestajPregleda> getIzvestaji() {
		return izvestaji;
	}

	public void setIzvestaji(Set<IzvestajPregleda> izvestaji) {
		this.izvestaji = izvestaji;
	}

	

}
