package MRSISA.Klinicki.centar.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TokeniPregled")
public class ConfirmationTokenPregled {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Tokena", unique = true, nullable = false)
	private Integer id;

	@Column(name = "confirmation_token")
	private String confirmationToken;

	@Column(name = "datum_kreiranja")
	private Date datumKreiranja;

//	@OneToOne(targetEntity = Pacijent.class, fetch = FetchType.EAGER)
//	@JoinColumn(name = "ID_Pacijenta") // nullable = false,
//	private Pacijent pacijent;
	
	@Column(name= "ID_Zahteva")
	private Integer idZahteva;

	public ConfirmationTokenPregled() {

	}

	public ConfirmationTokenPregled(Integer idPregleda) {
		this.confirmationToken = UUID.randomUUID().toString();
		this.datumKreiranja = new Date();
		this.idZahteva = idPregleda;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Date getDatumKreiranja() {
		return datumKreiranja;
	}

	public void setDatumKreiranja(Date datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}


	public Integer getIdZahteva() {
		return idZahteva;
	}

	public void setIdZahteva(Integer idZahteva) {
		this.idZahteva = idZahteva;
	}



//	public Pacijent getPacijent() {
//		return pacijent;
//	}
//
//	public void setPacijent(Pacijent pacijent) {
//		this.pacijent = pacijent;
//	}
	

}
