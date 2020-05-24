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
	private String sala;
	private String lekar;
	private String tipPregleda;
	private String pacijent;
	private float popust;
	private boolean slobodan;
	private Double cena;
	private int trajanje;

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
	

	public PregledDTO(int id, String datum, String vreme, int trajanje, String sala, String lekar, String tipPregleda, String pacijent, float popust,
			boolean slobodan, Double cena) {
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
		this.lekar = lekar.getIme() + " " +lekar.getPrezime();
		this.tipPregleda = tipPregleda.getNaziv();
		if(pacijent != null) {
			this.pacijent = pacijent.getIme() + " " + pacijent.getPrezime();
		}
		else {
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
		
		if(pacijent != null) {
			this.pacijent = pregled.getPacijent().getIme() + " " + pregled.getPacijent().getPrezime();
		}
		else {
			this.pacijent = " ";
		}
		this.popust = pregled.getPopust();
		this.slobodan = pregled.isSlobodan();
		this.cena = pregled.getTipPregleda().getCena().getIznos();
		this.trajanje = pregled.getTipPregleda().getTrajanje();
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
	

}
