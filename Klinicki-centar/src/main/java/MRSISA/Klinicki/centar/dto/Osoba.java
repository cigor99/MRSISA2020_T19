package MRSISA.Klinicki.centar.dto;

import java.io.Serializable;
import java.util.regex.Pattern;

public abstract class Osoba implements Serializable{
	
	private static final long serialVersionUID = 2630085096877002277L;
	private int id;
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private String jmbg;
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
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Osoba(int id,String email, String lozinka, String ime, String prezime,  String jmbg) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
	}
	public Osoba() {
		super();
	}
	@Override
	public String toString() {
		return "Osoba [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", lozinka=" + lozinka
				+ ", jmbg=" + jmbg + "]";
	}
	
	public boolean proveraPolja() {
		if (this.getIme() == null || this.getPrezime() == null || this.getJmbg() == null || this.getEmail() == null
				|| this.getLozinka() == null) {
			return false;
		}

		if (this.getIme().equals("") || this.getPrezime().equals("") || this.getJmbg().equals("")
				|| this.getEmail().equals("") || this.getLozinka().equals("") ) {
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

		if (this.getJmbg().length() != 13) {
			return false;
		}
		

		Pattern regPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
		Pattern regEmail = Pattern.compile(
				"^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
		Pattern regName = Pattern.compile("^[A-Z]{1}[a-z]{1,20}$");
		Pattern regJmbg = Pattern.compile("^[0-9]{13}$");

		if (!regPass.matcher(this.getLozinka()).matches()) {
			return false;
		}

		if (!regEmail.matcher(this.getEmail()).matches()) {
			return false;
		}

		if (!regName.matcher(this.getIme()).matches()) {
			return false;
		}

		if (!regName.matcher(this.getPrezime()).matches()) {
			return false;
		}

		if (!regJmbg.matcher(this.getJmbg()).matches()) {
			return false;
		}

		return true;
	}
	
	
	
	
}
