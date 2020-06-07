package MRSISA.Klinicki.centar.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Operacija;
import MRSISA.Klinicki.centar.domain.Sala;

public class OperacijaDTO {
	private int id;
	private String datumivreme;
	private String datum;
	private String vreme;
	private String sala;
	private String tipPregleda;
	private Double cena;
	private int trajanje;
	private Integer pacijent;
	//private Date datum;
	//private Sala sala;
	private Set<String> lekari;

	public OperacijaDTO() {
		super();
	}

	public OperacijaDTO(Operacija o) {
		this.id = o.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		String d = sdf.format(o.getDatum());
		this.datum = d.split(" ")[0];
		this.vreme = d.split(" ")[1];
		this.sala = o.getSala().getNaziv();
		Set<String> imena = new HashSet<>();
		for(Lekar l : o.getLekari()) {
			imena.add(""+l.getIme()+ " " + l.getPrezime());
		}
		this.lekari = imena;
		this.tipPregleda = o.getTipPregleda().getNaziv();
		this.pacijent = o.getPacijent().getId();
		this.cena = o.getTipPregleda().getCena().getIznos();
		this.trajanje  = o.getTipPregleda().getTrajanje();
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

}
