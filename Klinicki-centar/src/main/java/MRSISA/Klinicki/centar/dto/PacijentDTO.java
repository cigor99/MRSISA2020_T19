package MRSISA.Klinicki.centar.dto;

import java.util.regex.Pattern;

import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pol;
import MRSISA.Klinicki.centar.domain.TipKorisnika;

public class PacijentDTO extends Osoba {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8611189422858887935L;
	private Pol pol;
	private String grad;
	private String adresa;
	private String drzava;
	private String brojTelefona;
	private String jedinstveniBrOsig;
	private TipKorisnika tipKorisnika = TipKorisnika.PACIJENT;

	public PacijentDTO() {
		super();
	}

	public PacijentDTO(int id, String ime, String prezime, String jmbg, String email, String lozinka, Pol pol,
			String grad, String adresa, String drzava, String brojTelefona, String jedinstveniBrOsig) {
		super(id, email, lozinka, ime, prezime, jmbg);
		this.pol = pol;
		this.grad = grad;
		this.adresa = adresa;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		this.jedinstveniBrOsig = jedinstveniBrOsig;
	}

	public PacijentDTO(Pacijent p) {
		super(p.getId(), p.getEmail(), p.getLozinka(), p.getIme(), p.getPrezime(), p.getJmbg());
		this.pol = p.getPol();
		this.grad = p.getGrad();
		this.adresa = p.getAdresa();
		this.drzava = p.getDrzava();
		this.brojTelefona = p.getBrojTelefona();
		this.jedinstveniBrOsig = p.getJedinstveniBrOsig();
		this.tipKorisnika = p.getTipKorisnika();
	}

	@Override
	public boolean proveraPolja() {
		if (this.getIme() == null || this.getPrezime() == null || this.getJmbg() == null || this.getEmail() == null
				|| this.getLozinka() == null || this.pol == null || this.grad == null || this.adresa == null
				|| this.drzava == null || this.brojTelefona == null || this.jedinstveniBrOsig == null) {
			return false;
		}

		if (this.getIme().equals("") || this.getPrezime().equals("") || this.getJmbg().equals("")
				|| this.getEmail().equals("") || this.getLozinka().equals("") || this.grad.equals("")
				|| this.adresa.equals("") || this.drzava.equals("") || this.brojTelefona.equals("")
				|| this.jedinstveniBrOsig.equals("")) {
			return false;
		}

		if (this.getEmail().length() > 128) {
			return false;
		}

		if (this.getLozinka().length() > 256) {
			return false;
		}

		if (this.getIme().length() > 40) {
			return false;
		}
		if (this.getPrezime().length() > 40) {
			return false;
		}
		if (this.grad.length() > 256) {
			return false;
		}

		if (this.drzava.length() > 256) {
			return false;
		}

		if (this.adresa.length() > 256) {
			return false;
		}

		if (this.jedinstveniBrOsig.length() > 20) {
			return false;
		}

		if (this.getJmbg().length() != 13) {
			return false;
		}
		if (this.brojTelefona.length() > 20) {
			return false;
		}

		Pattern regPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
		Pattern regEmail = Pattern.compile(
				"^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
		Pattern regName = Pattern.compile("^[a-zA-Z]{1,40}$");
		Pattern regPhone = Pattern.compile("^[+]{1}[0-9]{1,12}$");
		Pattern regGrad = Pattern.compile("^([a-zA-Z]+[ ]*)+{1,40}$");
//		Pattern regAdresa = Pattern.compile("^([A-Z]{1}[a-z]+[ ]*)+[0-9]+$");
		Pattern regAdresa = Pattern.compile("^([a-zA-Z0-9 .,'-]*)+{3,30}$");
		Pattern regJmbg = Pattern.compile("^[0-9]{13}$");
		Pattern regOsig = Pattern.compile("^[0-9]{1,13}$");

		if (!regPass.matcher(this.getLozinka()).matches()) {
			System.out.println("LOZNIKA");
			return false;
		}

		if (!regEmail.matcher(this.getEmail()).matches()) {
			System.out.println("EMAIL");
			return false;
		}

		if (!regName.matcher(this.getIme()).matches()) {
			System.out.println("ime");
			return false;
		}

		if (!regName.matcher(this.getPrezime()).matches()) {
			System.out.println("prezime");
			return false;
		}

		if (!regPhone.matcher(this.brojTelefona).matches()) {
			System.out.println("telefon");
			return false;
		}

		if (!regGrad.matcher(this.grad).matches()) {
			System.out.println("grad");
			return false;
		}

		if (!regGrad.matcher(this.drzava).matches()) {
			System.out.println("drzava");
			return false;
		}

		if (!regAdresa.matcher(this.adresa).matches()) {
			System.out.println("adresa");
			return false;
		}

		if (!regJmbg.matcher(this.getJmbg()).matches()) {
			System.out.println("JMBG");
			return false;
		}

		if (!regOsig.matcher(this.jedinstveniBrOsig).matches()) {
			System.out.println("OSIGURANJE");
			return false;
		}

		return true;
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
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
		return super.toString() + " PacijentDTO [pol=" + pol + ", grad=" + grad + ", adresa=" + adresa + ", drzava=" + drzava
				+ ", brojTelefona=" + brojTelefona + ", jedinstveniBrOsig=" + jedinstveniBrOsig + ", tipKorisnika="
				+ tipKorisnika + "]";
	}
	
	

}
