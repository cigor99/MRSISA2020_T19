package MRSISA.Klinicki.centar.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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
	private Set<AdministratorKlinike> administratori = new HashSet<AdministratorKlinike>();

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "klinika", fetch = FetchType.EAGER)//fetch = FetchType.LAZY, 
	private Set<Lekar> lekari = new HashSet<Lekar>();

	@ManyToOne
	@JoinColumn(name = "cenovnik", referencedColumnName = "ID_Cenovnika")
	private Cenovnik cenovnik;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "klinika")
	private Set<Sala> sale = new HashSet<Sala>();
	
	@ElementCollection
	private List<Ocena> ocene = new ArrayList<Ocena>();
	
	@Column(name = "ocena", unique = false)
	private Double prosecnaOcena = 3.0;
	
	@Column(name="pocetak_RV", unique=false)
	private String pocetakRadnogVremena;
	
	@Column(name="kraj_RV", unique=false)
	private String krajRadnogVremena;

	public String getNaziv() {
		return naziv;
	}
	public Klinika() {
		
	}
	
	
	@Override
	public String toString() {
		String l = "";
		for(Lekar lekar: lekari) {
			l+= lekar.getId()+"\n";
		}
		return "Klinika [id=" + id + ", naziv=" + naziv + ", lekari=\n" + l + "]";
	}
	public Klinika(Integer id, String naziv, String adresa, String opis, Cenovnik cenovnik) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.cenovnik = cenovnik;
	}

	
	public Klinika(Integer id, String naziv, String adresa, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	
	public Klinika(Integer id, String naziv, String adresa, String opis, String pocetakRV, String krajRV) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.pocetakRadnogVremena = pocetakRV;
		this.krajRadnogVremena = krajRV;
	}

	public Klinika(Integer id, String naziv, String adresa, String opis, Set<AdministratorKlinike> administratori,
			Set<Lekar> lekari, Cenovnik cenovnik, Set<Sala> sale, List<Ocena> ocene, String pocetakRV, String krajRV) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.administratori = administratori;
		this.lekari = lekari;
		this.cenovnik = cenovnik;
		this.sale = sale;
		this.ocene = ocene;
		this.prosecnaOcena = izracunajProsecnuOcenu();
		this.pocetakRadnogVremena = pocetakRV;
		this.krajRadnogVremena = krajRV;
	}
	
	public double izracunajProsecnuOcenu() {
		double suma = 0;
		int i = this.ocene.size();
		if(i == 0) {
			return 3.0;
		}
		for(Ocena o : this.ocene) {
			int ocena = o.ordinal() + 1;
			suma += ocena;
		}
		double prosek = suma/i;
		this.prosecnaOcena = prosek;
		return prosek;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Set<AdministratorKlinike> getAdministratori() {
		return administratori;
	}

	public void setAdministratori(Set<AdministratorKlinike> administratori) {
		this.administratori = administratori;
	}

	public Set<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(Set<Lekar> lekari) {
		this.lekari = lekari;
	}

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}

	public Set<Sala> getSale() {
		return sale;
	}

	public void setSale(Set<Sala> sale) {
		this.sale = sale;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public List<Ocena> getOcene() {
		return ocene;
	}
	public void setOcene(List<Ocena> ocene) {
		this.ocene = ocene;
	}
	public Double getProsecnaOcena() {
		return izracunajProsecnuOcenu();
	}
	
	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}
	public String getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}
	public void setPocetakRadnogVremena(String pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}
	public String getKrajRadnogVremena() {
		return krajRadnogVremena;
	}
	public void setKrajRadnogVremena(String krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}
	
	
	
	
}
