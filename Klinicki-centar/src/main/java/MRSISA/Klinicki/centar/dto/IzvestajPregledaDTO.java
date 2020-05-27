package MRSISA.Klinicki.centar.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.IzvestajPregleda;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.Pregled;
import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.domain.ZdravstveniKarton;

public class IzvestajPregledaDTO {
	private Integer id;
	private String opis;
	private String dijagnoza;// SIFRA DIJAGNOZE(Ne ID)
	private Integer pregled;
	private Integer lekar;
	private Integer recept;

	public IzvestajPregledaDTO() {

	}

	public IzvestajPregledaDTO(Integer id, String opis, String dijagnoza, Integer lekar) {
		System.out.println("Konstruktor 2");
		this.id = id;
		this.opis = opis;
		this.dijagnoza = dijagnoza;
		this.lekar = lekar;
	}

	public IzvestajPregledaDTO(IzvestajPregleda izvestajPregleda) {
		super();
		this.dijagnoza = izvestajPregleda.getDijagnoza().getSifra();
		this.id = izvestajPregleda.getId();
		this.opis = izvestajPregleda.getOpis();
		this.lekar = izvestajPregleda.getLekar().getId();
		if (izvestajPregleda.getRecept() != null) {
			System.out.println(izvestajPregleda.getRecept().getId());
			this.recept = izvestajPregleda.getRecept().getId();
		}
		if (izvestajPregleda.getPregled() != null) {
			this.pregled = izvestajPregleda.getPregled().getId();
		}

	}

	public IzvestajPregledaDTO(Integer id, String opis, String dijagnoza, Integer lekar, Integer recept,
			Integer pregled) {
		super();
		System.out.println("Konstruktor 1");
		this.id = id;
		this.opis = opis;
		this.dijagnoza = dijagnoza;
		this.lekar = lekar;
		this.recept = recept;
		this.pregled = pregled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(String dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Integer getLekar() {
		return lekar;
	}

	public void setLekar(Integer lekar) {
		this.lekar = lekar;
	}

	public Integer getRecept() {
		return recept;
	}

	public void setRecept(Integer recept) {
		this.recept = recept;
	}

	public Integer getPregled() {
		return pregled;
	}

	public void setPregled(Integer pregled) {
		this.pregled = pregled;
	}
	
	

}
