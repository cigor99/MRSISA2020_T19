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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Klinicki_Centar")
public class KlinickiCentar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_KC", unique = true, nullable = false)
	private Integer id;

	@JsonIgnoreProperties("klinickiCentar")
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "klinickiCentar")
	private Set<Lek> sifranikLekova = new HashSet<Lek>();

	@JsonIgnoreProperties("klinickiCentar")
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "klinickiCentar")
	private Set<Dijagnoza> sifarnikDijagnoza = new HashSet<Dijagnoza>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "klinickiCentar")
	private Set<ZahtevZaRegistraciju> zahteviZaReg = new HashSet<ZahtevZaRegistraciju>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "klinickiCentar")
	private Set<AdministratorKlinickogCentra> adminiKC = new HashSet<AdministratorKlinickogCentra>();

	public KlinickiCentar() {

	}
	public KlinickiCentar(Integer id) {
		super();
		this.id = id;
	}

	public KlinickiCentar(Integer id, Set<Lek> sifranikLekova, Set<Dijagnoza> sifarnikDijagnoza,
			Set<ZahtevZaRegistraciju> zahteviZaReg, Set<AdministratorKlinickogCentra> adminiKC) {
		super();
		this.id = id;
		this.sifranikLekova = sifranikLekova;
		this.sifarnikDijagnoza = sifarnikDijagnoza;
		this.zahteviZaReg = zahteviZaReg;
		this.adminiKC = adminiKC;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Lek> getSifranikLekova() {
		return sifranikLekova;
	}

	public void setSifranikLekova(Set<Lek> sifranikLekova) {
		this.sifranikLekova = sifranikLekova;
	}

	public Set<Dijagnoza> getSifarnikDijagnoza() {
		return sifarnikDijagnoza;
	}

	public void setSifarnikDijagnoza(Set<Dijagnoza> sifarnikDijagnoza) {
		this.sifarnikDijagnoza = sifarnikDijagnoza;
	}

	public Set<ZahtevZaRegistraciju> getZahteviZaReg() {
		return zahteviZaReg;
	}

	public void setZahteviZaReg(Set<ZahtevZaRegistraciju> zahteviZaReg) {
		this.zahteviZaReg = zahteviZaReg;
	}

	public Set<AdministratorKlinickogCentra> getAdminiKC() {
		return adminiKC;
	}

	public void setAdminiKC(Set<AdministratorKlinickogCentra> adminiKC) {
		this.adminiKC = adminiKC;
	}

}
