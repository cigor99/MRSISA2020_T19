package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;
import java.util.Date;
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

	@Column(name = "stanje_recepta", nullable = false)
	private StanjeRecepta stanjeRecepta;

	@Column(name = "datum_izdavanja_recepta", nullable = false)
	private String datumIzdavanja;

	@ManyToOne
	@JoinColumn(name = "pregled", referencedColumnName = "ID_Pregleda")
	private Pregled pregled;

	@ManyToMany
//	(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "lek_recept", joinColumns = { @JoinColumn(name = "ID_Recepta") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_Leka") })
	private Set<Lek> lekovi = new HashSet<Lek>();

	@ManyToOne
	@JoinColumn(name = "medicinska_sestra", referencedColumnName = "ID_MedSes")
	private MedicinskaSestra medicinskaSestra;

	@ManyToOne
	@JoinColumn(name = "Lekar", referencedColumnName = "ID_lekara")
	private Lekar lekar;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "recept")
	private Set<IzvestajPregleda> izvestajiPregleda = new HashSet<IzvestajPregleda>();

	public Recept() {
		super();
	}
	public Recept(Integer id, String datumIzdavanja ) {
		super();
		this.stanjeRecepta = StanjeRecepta.NIJE_OVEREN;
		this.id = id;
		this.datumIzdavanja = datumIzdavanja;
	}

	public Recept(Integer id, String datumIzdavanja, Pregled pregled, Set<Lek> lekovi,
			MedicinskaSestra medicinskaSestra, Lekar lekar, Set<IzvestajPregleda> izvestajiPregleda) {
		super();
		this.id = id;
		this.stanjeRecepta = StanjeRecepta.NIJE_OVEREN;
		this.datumIzdavanja = datumIzdavanja;
		this.pregled = pregled;
		this.lekovi = lekovi;
		this.medicinskaSestra = medicinskaSestra;
		this.lekar = lekar;
		this.izvestajiPregleda = izvestajiPregleda;
	}

	public StanjeRecepta getStanjeRecepta() {
		return stanjeRecepta;
	}

	public void setStanjeRecepta(StanjeRecepta stanjeRecepta) {
		this.stanjeRecepta = stanjeRecepta;
	}

	public String getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(String datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
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
