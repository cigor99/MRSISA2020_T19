package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pol;

public class PacijentDTO {
	private int id;
	private String ime;
	private String prezime;
	private String jmbg;
	private String email;
	private String lozinka;
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
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.email = email;
		this.lozinka = lozinka;
		this.pol = pol;
		this.grad = grad;
		this.adresa = adresa;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		this.jedinstveniBrOsig = jedinstveniBrOsig;
	}
	public PacijentDTO(Pacijent p) {
		super();
		this.id = p.getId();
		this.ime = p.getIme();
		this.prezime = p.getPrezime();
		this.jmbg = p.getJmbg();
		this.email = p.getEmail();
		this.lozinka = p.getLozinka();
		this.pol = p.getPol();
		this.grad = p.getGrad();
		this.adresa = p.getAdresa();
		this.drzava = p.getDrzava();
		this.brojTelefona = p.getBrojTelefona();
		this.jedinstveniBrOsig = p.getJedinstveniBrOsig();
	}
	
	public boolean proveraPolja() {
		return (this.ime == null || this.prezime == null || this.jmbg == null || this.email == null || this.lozinka == null || this.pol == null || this.grad  == null || this.adresa == null || this.drzava == null || this.brojTelefona == null || this.jedinstveniBrOsig == null);
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
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
	@Override
	public String toString() {
		return "PacijentDTO [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", jmbg=" + jmbg + ", email=" + email
				+ ", lozinka=" + lozinka + ", pol=" + pol + ", grad=" + grad + ", adresa=" + adresa + ", drzava="
				+ drzava + ", brojTelefona=" + brojTelefona + ", jedinstveniBrOsig=" + jedinstveniBrOsig + "]]";
	}
	
	
	
	

}
