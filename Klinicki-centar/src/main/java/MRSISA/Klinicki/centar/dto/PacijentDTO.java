package MRSISA.Klinicki.centar.dto;

import java.io.Serializable;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pol;

public class PacijentDTO extends Osoba implements Serializable{
	private Pol pol;
	private String grad;
	private String adresa;
	private String drzava;
	private String brojTelefona;
	private String jedinstveniBrOsig;
	public PacijentDTO() {
		super();
	}
	public PacijentDTO(int id, String ime, String prezime, String jmbg, String email, String lozinka, Pol pol,
			String grad, String adresa, String drzava, String brojTelefona, String jedinstveniBrOsig) {
		super(id, ime, prezime, email, lozinka, jmbg);
		this.pol = pol;
		this.grad = grad;
		this.adresa = adresa;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		this.jedinstveniBrOsig = jedinstveniBrOsig;
	}
	public PacijentDTO(Pacijent p) {
		super(p.getId(), p.getIme(), p.getPrezime(), p.getEmail(), p.getLozinka(), p.getJmbg());
		this.pol = p.getPol();
		this.grad = p.getGrad();
		this.adresa = p.getAdresa();
		this.drzava = p.getDrzava();
		this.brojTelefona = p.getBrojTelefona();
		this.jedinstveniBrOsig = p.getJedinstveniBrOsig();
	}
	
	public boolean proveraPolja() {
		return (this.getIme() == null || this.getPrezime() == null || this.getJmbg() == null || this.getEmail() == null || this.getLozinka() == null || this.pol == null || this.grad == null || this.adresa == null || this.drzava == null || this.brojTelefona == null || this.jedinstveniBrOsig == null);
	}
	
	public Pol getPol() {
		return pol;
	}
	public void setPol(Pol pol) {
		this.pol = pol;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getDrzava() {
		return drzava;
	}
	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	public String getBrojTelefona() {
		return brojTelefona;
	}
	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
	public String getJedinstveniBrOsig() {
		return jedinstveniBrOsig;
	}
	public void setJedinstveniBrOsig(String jedinstveniBrOsig) {
		this.jedinstveniBrOsig = jedinstveniBrOsig;
	}
	
	
	
	
	

}
