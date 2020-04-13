package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Klinike")
public class Klinika {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Klinike", unique = true, nullable = false)
	private Integer id;

	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;

	@Column(name = "adresa", unique = false, nullable = false)
	private String adresa;

	@Column(name = "opis", unique = false, nullable = false)
	private String opis;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "klinika")
	private ArrayList<AdministratorKlinike> administratori;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "klinika")
	private ArrayList<Lekar> lekari;

	@Column(name = "cenovnik", unique = false, nullable = false)
	private Cenovnik cenovnik;

	
	private ArrayList<Sala> Sale;

	public String getNaziv() {
		return naziv;
	}

	public Klinika(Integer id, String naziv, String adresa, String opis, ArrayList<AdministratorKlinike> administratori,
			ArrayList<Lekar> lekari, Cenovnik cenovnik, ArrayList<Sala> sale) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.administratori = administratori;
		this.lekari = lekari;
		this.cenovnik = cenovnik;
		Sale = sale;
	}

	public ArrayList<AdministratorKlinike> getAdministratori() {
		return administratori;
	}

	public void setAdministratori(ArrayList<AdministratorKlinike> administratori) {
		this.administratori = administratori;
	}

	public ArrayList<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(ArrayList<Lekar> lekari) {
		this.lekari = lekari;
	}

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}

	public ArrayList<Sala> getSale() {
		return Sale;
	}

	public void setSale(ArrayList<Sala> sale) {
		Sale = sale;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
