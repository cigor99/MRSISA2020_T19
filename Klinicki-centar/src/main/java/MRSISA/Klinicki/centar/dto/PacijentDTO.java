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

	public PacijentDTO(int id, String ime, String prezime, String jmbg, String email, String lozinka, Pol pol) {

		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.email = email;
		this.lozinka = lozinka;
		this.pol = pol;
	}

	public PacijentDTO() {
	}

	public PacijentDTO(Pacijent pacijent) {
		this.id = pacijent.getId();
		this.ime = pacijent.getIme();
		this.prezime = pacijent.getPrezime();
		this.email = pacijent.getEmail();
		this.lozinka = pacijent.getLozinka();
		this.jmbg = pacijent.getJmbg();
		this.pol = pacijent.getPol();
	}
	
	


	@Override
	public String toString() {
		return "PacijentDTO [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", jmbg=" + jmbg + ", email=" + email
				+ ", lozinka=" + lozinka + ", pol=" + pol + "]";
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
	
	

}
