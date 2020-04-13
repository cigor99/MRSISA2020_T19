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

@Entity
@Table(name = "Klinicki_Centar")
public class KlinickiCentar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_KC", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "Sifarnik_lekova")
	private ArrayList<Lek> sifranikLekova;

	@Column(name = "Sifarnik_dijagnoza")
	private ArrayList<Dijagnoza> sifarnikDijagnoza;

	@Column(name = "Zahtevi_za_registraciju")
	private ArrayList<ZahtevZaRegistraciju> zahteviZaReg;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "klinickiCentar")
	private ArrayList<AdministratorKlinickogCentra> adminiKC;

	public KlinickiCentar() {

	}

	public KlinickiCentar(Integer id, ArrayList<Lek> sifranikLekova, ArrayList<Dijagnoza> sifarnikDijagnoza,
			ArrayList<ZahtevZaRegistraciju> zahteviZaReg, ArrayList<AdministratorKlinickogCentra> adminiKC) {
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

	public ArrayList<Lek> getSifranikLekova() {
		return sifranikLekova;
	}

	public void setSifranikLekova(ArrayList<Lek> sifranikLekova) {
		this.sifranikLekova = sifranikLekova;
	}

	public ArrayList<Dijagnoza> getSifarnikDijagnoza() {
		return sifarnikDijagnoza;
	}

	public void setSifarnikDijagnoza(ArrayList<Dijagnoza> sifarnikDijagnoza) {
		this.sifarnikDijagnoza = sifarnikDijagnoza;
	}

	public ArrayList<ZahtevZaRegistraciju> getZahteviZaReg() {
		return zahteviZaReg;
	}

	public void setZahteviZaReg(ArrayList<ZahtevZaRegistraciju> zahteviZaReg) {
		this.zahteviZaReg = zahteviZaReg;
	}

	public ArrayList<AdministratorKlinickogCentra> getAdminiKC() {
		return adminiKC;
	}

	public void setAdminiKC(ArrayList<AdministratorKlinickogCentra> adminiKC) {
		this.adminiKC = adminiKC;
	}

}
