package MRSISA.Klinicki.centar.domain;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class MedicinskaSestra {
	private Integer id;
	private String ime;
	private String prezime;
	private String jmbg;
	private String email;
	private String lozinka;
	private List<Integer> recepti;

	public MedicinskaSestra() {
		super();
	}

	public MedicinskaSestra(Integer id, String ime, String prezime, String jmbg, String email, String lozinka,
			List<Integer> recepti) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.email = email;
		this.lozinka = lozinka;
		this.recepti = recepti;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public List<Integer> getRecepti() {
		return recepti;
	}

	public void setRecepti(List<Integer> recepti) {
		this.recepti = recepti;
	}

}
