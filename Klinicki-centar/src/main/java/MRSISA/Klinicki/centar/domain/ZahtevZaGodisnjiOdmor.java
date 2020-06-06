package MRSISA.Klinicki.centar.domain;

import java.util.Date;

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
@Table(name = "Zahtevi_za_godisnji")
public class ZahtevZaGodisnjiOdmor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ZZG", unique = true, nullable = false)
	private Integer id;

	@Column(name = "pocetni_datum", unique = false, nullable = false)
	private Date pocetniDatum;

	@Column(name = "krajnji_datum", unique = false, nullable = false)
	private Date krajnjiDatum;
	
	@Column(name = "razlog", unique = false, nullable = true)
	private String razlogOdbijanja;

	@ManyToOne
	@JoinColumn(name = "lekar", referencedColumnName = "ID_lekara")
	private Lekar lekar;

	@Column(name = "stanje")
	private StanjeZahteva stanjeZahteva;

	@ManyToOne
	@JoinColumn(name = "medicinska_sestra", referencedColumnName = "ID_MedSes")
	private MedicinskaSestra medicinskaSestra;

	public ZahtevZaGodisnjiOdmor() {
	}

	public ZahtevZaGodisnjiOdmor(Integer id, Date pocetniDatum, Date krajnjiDatum, Lekar lekar,
			MedicinskaSestra medicinskaSestra, StanjeZahteva stanje, String razlog) {
		super();
		this.id = id;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.lekar = lekar;
		this.medicinskaSestra = medicinskaSestra;
		this.stanjeZahteva = stanje;
		this.razlogOdbijanja = razlog;
	}

	public StanjeZahteva getStanjeZahteva() {
		return stanjeZahteva;
	}

	public void setStanjeZahteva(StanjeZahteva stanjeZahteva) {
		this.stanjeZahteva = stanjeZahteva;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public MedicinskaSestra getMedicinskaSestra() {
		return medicinskaSestra;
	}

	public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(Date pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	public Date getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(Date krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

	public String getRazlogOdbijanja() {
		return razlogOdbijanja;
	}

	public void setRazlogOdbijanja(String razlogOdbijanja) {
		this.razlogOdbijanja = razlogOdbijanja;
	}

}
