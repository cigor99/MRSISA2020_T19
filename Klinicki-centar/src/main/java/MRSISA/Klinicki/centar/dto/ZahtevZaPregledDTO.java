package MRSISA.Klinicki.centar.dto;


import java.text.SimpleDateFormat;
import java.util.Date;

import MRSISA.Klinicki.centar.domain.ZahtevZaPregled;

public class ZahtevZaPregledDTO {
	
	private Integer id;
	private Integer pregled;
	private String stanjeZahteva;
	private String lekar;
	private String pacijent;
	private Integer sala;
	private String datum;
	private String vreme;
	private Date datetime;
	
	public ZahtevZaPregledDTO(Integer id, Integer pregled, String stanjeZahteva, String lekar, String pacijent,
			Integer sala, String datum, String vreme) {
		super();
		this.id = id;
		this.pregled = pregled;
		this.stanjeZahteva = stanjeZahteva;
		this.lekar = lekar;
		this.pacijent = pacijent;
		this.sala = sala;
		this.datum = datum;
		this.vreme = vreme;
	}

	
	
	public ZahtevZaPregledDTO() {
		super();
	}
	
	public ZahtevZaPregledDTO(ZahtevZaPregled zzp) {
		super();
		this.id = zzp.getId();
		this.pregled = zzp.getPregled().getId();
		this.stanjeZahteva = zzp.getStanje().toString();
		Date datum = zzp.getPregled().getDatum();
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String datumVreme = formatter.format(datum);
		this.datum = datumVreme.split(" ")[0];
		this.vreme = datumVreme.split(" ")[1];
		this.lekar = zzp.getPregled().getLekar().getIme() + " " + zzp.getPregled().getLekar().getPrezime();
		this.pacijent = zzp.getPregled().getPacijent().getIme() + " " + zzp.getPregled().getPacijent().getPrezime();
		this.datetime = zzp.getPregled().getDatum();
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPregled() {
		return pregled;
	}

	public void setPregled(Integer pregled) {
		this.pregled = pregled;
	}

	public String getStanjeZahteva() {
		return stanjeZahteva;
	}

	public void setStanjeZahteva(String stanjeZahteva) {
		this.stanjeZahteva = stanjeZahteva;
	}

	public Integer getSala() {
		return sala;
	}

	public void setSala(Integer sala) {
		this.sala = sala;
	}

	

	public String getLekar() {
		return lekar;
	}

	public void setLekar(String lekar) {
		this.lekar = lekar;
	}

	public String getPacijent() {
		return pacijent;
	}

	public void setPacijent(String pacijent) {
		this.pacijent = pacijent;
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



	public Date getDatetime() {
		return datetime;
	}



	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	

}
