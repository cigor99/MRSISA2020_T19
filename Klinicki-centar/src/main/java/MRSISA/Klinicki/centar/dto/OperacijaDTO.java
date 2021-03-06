package MRSISA.Klinicki.centar.dto;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Operacija;
public class OperacijaDTO {
	private int id;
	private String datumivreme;
	private String datum;
	private String vreme;
	private String sala;
	private Integer salaID;
	private String tipPregleda;
	private Double cena;
	private int trajanje;
	private Integer pacijent;
	// private Date datum;
	// private Sala sala;
	private Set<String> lekari;

	public OperacijaDTO() {
		super();
	}
	
	

	public OperacijaDTO(int id, String datumivreme, String datum, String vreme, String sala, String tipPregleda,
			Double cena, int trajanje, Integer pacijent, Set<String> lekari) {
		super();
		this.id = id;
		this.datumivreme = datumivreme;
		this.datum = datum;
		this.vreme = vreme;
		this.sala = sala;
		this.tipPregleda = tipPregleda;
		this.cena = cena;
		this.trajanje = trajanje;
		this.pacijent = pacijent;
		this.lekari = lekari;
	}



	public OperacijaDTO(Operacija o) {
		this.id = o.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String d = sdf.format(o.getDatum());
		this.datum = d.split(" ")[0];
		this.vreme = d.split(" ")[1];
		System.err.println(d.split(" ")[1]);
		if(o.getSala() != null) {
			this.sala = o.getSala().getNaziv();	
			this.salaID = o.getSala().getId();
		}
		else {
			this.sala = "";
		}
		
		Set<String> imena = new HashSet<>();
		for (Lekar l : o.getLekari()) {
			imena.add("" + l.getIme() + " " + l.getPrezime());
		}
		this.trajanje = o.getTrajanje();
		this.lekari = imena;
//		this.tipPregleda = o.getTipPregleda().getNaziv();
		this.pacijent = o.getPacijent().getId();
//		this.cena = o.getTipPregleda().getCena().getIznos();
//		this.trajanje  = o.getTipPregleda().getTrajanje();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperacijaDTO other = (OperacijaDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatumivreme() {
		return datumivreme;
	}

	public void setDatumivreme(String datumivreme) {
		this.datumivreme = datumivreme;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(String tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public Integer getPacijent() {
		return pacijent;
	}

	public void setPacijent(Integer pacijent) {
		this.pacijent = pacijent;
	}

	public Set<String> getLekari() {
		return lekari;
	}

	public void setLekari(Set<String> lekari) {
		this.lekari = lekari;
	}

	public Integer getSalaID() {
		return salaID;
	}

	public void setSalaID(Integer salaID) {
		this.salaID = salaID;
	}

	@Override
	public String toString() {
		return "OperacijaDTO [id=" + id + ", datumivreme=" + datumivreme + ", datum=" + datum + ", vreme=" + vreme
				+ ", sala=" + sala + ", tipPregleda=" + tipPregleda + ", cena=" + cena + ", trajanje=" + trajanje
				+ ", pacijent=" + pacijent + ", lekari=" + lekari + "]";
	}

}
