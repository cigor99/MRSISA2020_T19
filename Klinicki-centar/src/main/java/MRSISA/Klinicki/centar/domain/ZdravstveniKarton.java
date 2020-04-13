package MRSISA.Klinicki.centar.domain;

import java.util.HashMap;
import java.util.List;

public class ZdravstveniKarton {
	private Integer id;
	private List<Integer> izvestaji;
	private Integer pacijent; // Id pacijenta

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getIzvestaji() {
		return izvestaji;
	}

	public void setIzvestaji(List<Integer> izvestaji) {
		this.izvestaji = izvestaji;
	}

	public Integer getPacijent() {
		return pacijent;
	}

	public void setPacijent(Integer pacijent) {
		this.pacijent = pacijent;
	}

	public ZdravstveniKarton(Integer id, List<Integer> izvestaji, Integer pacijent) {
		super();
		this.id = id;
		this.izvestaji = izvestaji;
		this.pacijent = pacijent;
	}

	public ZdravstveniKarton() {
		super();
	}

}
