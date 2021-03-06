package MRSISA.Klinicki.centar.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.TipPregleda;

public class PregledDTO {

	private int id;
	private String datumivreme;
	private String datum;
	private String vreme;
	private String kraj;
	private String sala;
	private String lekar;
	private Integer lekarID;
	private String tipPregleda;
	private String pacijent;
	private int pacijentId;
	private float popust;
	private boolean slobodan;
	private Double cena;
	private int trajanje;
	private Integer pacijentID;

	public PregledDTO() {
		super();
	}

	public PregledDTO(int id, String datumivreme, String sala, String tipPregleda, String lekar, Double cena) {
		this.id = id;
		String[] dt = datumivreme.split("T");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		Date date;
		try {
			date = sdf1.parse(dt[0]);
			String d = sdf2.format(date);
			this.datum = d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.vreme = dt[1];
		this.sala = sala;
		this.lekar = lekar;
		this.tipPregleda = tipPregleda;
		this.slobodan = true;
		this.cena = cena;
	}

	public PregledDTO(int id, String datum, String vreme, int trajanje, String sala, String lekar, String tipPregleda,
			String pacijent, float popust, boolean slobodan, Double cena) {
		super();
		this.id = id;
		this.datum = datum;
		this.sala = sala;
		this.lekar = lekar;
		this.tipPregleda = tipPregleda;
		this.pacijent = pacijent;
		this.popust = popust;
		this.slobodan = slobodan;
		this.cena = cena;
		this.trajanje = trajanje;
	}

	public PregledDTO(int id, Date datum, Sala sala, Lekar lekar, TipPregleda tipPregleda, Pacijent pacijent,
			float popust, boolean slobodan) {
		super();
		this.id = id;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		String d = sdf.format(datum);
		this.datum = d;
		this.sala = sala.getNaziv();
		this.lekar = lekar.getIme() + " " + lekar.getPrezime();
		this.tipPregleda = tipPregleda.getNaziv();
		if (pacijent != null) {
			this.pacijent = pacijent.getIme() + " " + pacijent.getPrezime();
			this.pacijentID = pacijent.getId();
		} else {
			this.pacijent = " ";
		}
		this.popust = popust;
		this.slobodan = slobodan;

	}

	public PregledDTO(Pregled pregled) {
		super();
		this.id = pregled.getId();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		String d = sdf.format(pregled.getDatum());
		this.datum = d.split(" ")[0];
		this.vreme = d.split(" ")[1];
		this.sala = pregled.getSala().getNaziv();
		this.lekar = pregled.getLekar().getIme() + " " + pregled.getLekar().getPrezime();
		this.tipPregleda = pregled.getTipPregleda().getNaziv();

		if (pregled.getPacijent() != null) {
			this.pacijent = pregled.getPacijent().getIme() + " " + pregled.getPacijent().getPrezime();
			this.pacijentId = pregled.getPacijent().getId();
			this.pacijentID = pregled.getPacijent().getId();
		} else {
			this.pacijent = " ";
		}
		this.popust = pregled.getPopust();
		this.slobodan = pregled.isSlobodan();
		this.cena = pregled.getTipPregleda().getCena().getIznos();
		this.trajanje = pregled.getTipPregleda().getTrajanje();
		this.lekarID = pregled.getLekar().getId();
		this.kraj = krajPregleda(pregled.getDatum(), pregled.getTipPregleda().getTrajanje());
		// this.pacijentID = pregled.getPacijent().getId();
	}

	private String krajPregleda(Date pocetak, int trajanje) {
		long k = pocetak.getTime() + trajanje * 60000;
		Date datum = new Date(k);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		String d = sdf.format(datum);
		String kraj = d.split(" ")[1];
		return kraj;
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
		PregledDTO other = (PregledDTO) obj;
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

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getLekar() {
		return lekar;
	}

	public void setLekar(String lekar) {
		this.lekar = lekar;
	}

	public String getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(String tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public String getPacijent() {
		return pacijent;
	}

	public void setPacijent(String pacijent) {
		this.pacijent = pacijent;
	}

	public float getPopust() {
		return popust;
	}

	public void setPopust(float popust) {
		this.popust = popust;
	}

	public boolean isSlobodan() {
		return slobodan;
	}

	public void setSlobodan(boolean slobodan) {
		this.slobodan = slobodan;
	}

	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
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

	public String getDatumivreme() {
		return datumivreme;
	}

	public void setDatumivreme(String datumivreme) {
		this.datumivreme = datumivreme;
	}

	public Integer getLekarID() {
		return lekarID;
	}

	public void setLekarID(Integer lekarID) {
		this.lekarID = lekarID;
	}

	public Integer getPacijentID() {
		return pacijentID;
	}

	public void setPacijentID(Integer pacijentID) {
		this.pacijentID = pacijentID;
	}

	public String getKraj() {
		return kraj;
	}

	public void setKraj(String kraj) {
		this.kraj = kraj;
	}

	public int getPacijentId() {
		return pacijentId;
	}

	public void setPacijentId(int pacijentId) {
		this.pacijentId = pacijentId;
	}

}
