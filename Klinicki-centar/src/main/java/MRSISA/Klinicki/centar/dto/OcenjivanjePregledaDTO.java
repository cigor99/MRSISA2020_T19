package MRSISA.Klinicki.centar.dto;

public class OcenjivanjePregledaDTO {
	private String ocenaLekar;
	private String ocenaKlinika;
	private String idLekar;
	private String idKlinika;

	public OcenjivanjePregledaDTO(String ocenaLekar, String ocenaKlinika, String idLekar, String idKlinika) {
		super();
		this.ocenaLekar = ocenaLekar;
		this.ocenaKlinika = ocenaKlinika;
		this.idLekar = idLekar;
		this.idKlinika = idKlinika;
	}

	public OcenjivanjePregledaDTO() {
		super();
	}

	public String getOcenaLekar() {
		return ocenaLekar;
	}

	public void setOcenaLekar(String ocenaLekar) {
		this.ocenaLekar = ocenaLekar;
	}

	public String getOcenaKlinika() {
		return ocenaKlinika;
	}

	public void setOcenaKlinika(String ocenaKlinika) {
		this.ocenaKlinika = ocenaKlinika;
	}

	public String getIdLekar() {
		return idLekar;
	}

	public void setIdLekar(String idLekar) {
		this.idLekar = idLekar;
	}

	public String getIdKlinika() {
		return idKlinika;
	}

	public void setIdKlinika(String idKlinika) {
		this.idKlinika = idKlinika;
	}

}
