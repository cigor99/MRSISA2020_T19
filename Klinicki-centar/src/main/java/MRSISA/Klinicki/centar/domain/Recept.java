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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "Recepti")
public class Recept {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Recepta", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pregled", referencedColumnName = "ID_Pregleda", nullable = false)
	private Pregled pregled;

	@ManyToMany(cascade = CascadeType.ALL)
//	(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "lek_recept", 
	joinColumns = {@JoinColumn(name = "ID_Recepta")}, 
	inverseJoinColumns = {@JoinColumn(name = "ID_Leka")})
	private Set<Lek> lekovi = new HashSet<Lek>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "medicinska_sestra", referencedColumnName = "ID_MedSes", nullable = false)
	private MedicinskaSestra medicinskaSestra;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "recept")
	private Set<IzvestajPregleda> izvestajiPregleda = new HashSet<IzvestajPregleda>();

	public Recept() {
		super();
	}

	public Recept(Integer id, Pregled pregled, Set<Lek> lekovi, MedicinskaSestra medicinskaSestra,
			Set<IzvestajPregleda> izvestajiPregleda) {
		super();
		this.id = id;
		this.pregled = pregled;
		this.lekovi = lekovi;
		this.medicinskaSestra = medicinskaSestra;
		this.izvestajiPregleda = izvestajiPregleda;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public Set<IzvestajPregleda> getIzvestajiPregleda() {
		return izvestajiPregleda;
	}

	public void setIzvestajiPregleda(Set<IzvestajPregleda> izvestajiPregleda) {
		this.izvestajiPregleda = izvestajiPregleda;
	}

	public Set<Lek> getLekovi() {
		return lekovi;
	}

	public void setLekovi(Set<Lek> lekovi) {
		this.lekovi = lekovi;
	}

	public MedicinskaSestra getMedicinskaSestra() {
		return medicinskaSestra;
	}

	public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
	}

}
