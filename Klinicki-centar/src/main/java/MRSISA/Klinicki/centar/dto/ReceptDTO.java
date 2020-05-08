package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.domain.StanjeRecepta;

public class ReceptDTO {
	private Integer id;
	private StanjeRecepta stanjeRecepta;
	private String datumIzdavanja;
	private Integer lekarID;
	private Integer pregledID;

	public ReceptDTO() {

	}

	public ReceptDTO(Recept recept) {
		super();
		this.id = recept.getId();
		this.stanjeRecepta = recept.getStanjeRecepta();
		this.datumIzdavanja = recept.getDatumIzdavanja();
		this.lekarID = recept.getLekar().getId();
//		this.pregledID = recept.getPregled().getId();
	}

	
	public ReceptDTO(Integer id, StanjeRecepta stanjeRecepta, String datumIzdavanja, Integer lekarID,
			Integer pregledID) {
		super();
		this.id = id;
		this.stanjeRecepta = stanjeRecepta;
		this.datumIzdavanja = datumIzdavanja;
		this.lekarID = lekarID;
//		this.pregledID = pregledID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StanjeRecepta getStanjeRecepta() {
		return stanjeRecepta;
	}

	public void setStanjeRecepta(StanjeRecepta stanjeRecepta) {
		this.stanjeRecepta = stanjeRecepta;
	}

	public String getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(String datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}

	public Integer getLekarID() {
		return lekarID;
	}

	public void setLekarID(Integer lekarID) {
		this.lekarID = lekarID;
	}

	public Integer getPregledID() {
		return pregledID;
	}

	public void setPregledID(Integer pregledID) {
		this.pregledID = pregledID;
	}

}
