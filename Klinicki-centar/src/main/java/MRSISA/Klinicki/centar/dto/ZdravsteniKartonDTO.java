package MRSISA.Klinicki.centar.dto;

import MRSISA.Klinicki.centar.domain.KrvnaGrupa;
import MRSISA.Klinicki.centar.domain.ZdravstveniKarton;

public class ZdravsteniKartonDTO {

	private Integer id;
	private double visina;
	private double tezina;
	private KrvnaGrupa krvnaGrupa;
	private double dioptrija;

	public ZdravsteniKartonDTO() {

	}

	public ZdravsteniKartonDTO(ZdravstveniKarton zdravstveniKarton) {
		super();
		this.id = zdravstveniKarton.getId();
		this.visina = zdravstveniKarton.getVisina();
		this.tezina = zdravstveniKarton.getTezina();
		this.krvnaGrupa = zdravstveniKarton.getKrvnaGrupa();
		this.dioptrija = zdravstveniKarton.getDioptrija();
	}

	public ZdravsteniKartonDTO(Integer id, double visina, double tezina, KrvnaGrupa krvnaGrupa, double dioptrija) {
		super();
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
		this.dioptrija = dioptrija;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public KrvnaGrupa getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(KrvnaGrupa krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(double dioptrija) {
		this.dioptrija = dioptrija;
	}
	
	

}
