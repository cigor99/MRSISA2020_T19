package MRSISA.Klinicki.centar.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.print.attribute.standard.DateTimeAtCompleted;

@Entity
@Table(name = "Sale")
public class Sala {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Sale", unique = true, nullable = false)
	private Integer id;

	@Column(name = "naziv", unique = false, nullable = false)
	private String naziv;

	@Column(name = "tip_sale", unique = false, nullable = false)
	private TipSale tip;
	
	@Column(name = "prvi_slobodan_termin", unique = false, nullable = true)
	private Date prviSlobodanTermin;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "sala")
	private Set<Pregled> pregledi = new HashSet<Pregled>();

	@ManyToOne
	@JoinColumn(name = "klinika", referencedColumnName = "ID_Klinike", nullable = false)
	private Klinika klinika;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "sala", fetch = FetchType.EAGER)// fetch = FetchType.LAZY,
	private Set<Operacija> operacije = new HashSet<Operacija>();

	public Sala() {
		super();
	}

	public Sala(Integer id, String naziv, TipSale tip, Set<Pregled> pregledi, Klinika klinika,
			Set<Operacija> operacije) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
		this.pregledi = pregledi;
		this.klinika = klinika;
		this.operacije = operacije;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipSale getTip() {
		return tip;
	}

	public void setTip(TipSale tip) {
		this.tip = tip;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Set<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		this.operacije = operacije;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	@SuppressWarnings("deprecation")
	public Date getPrviSlobodanTermin() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date(System.currentTimeMillis());
		Date pocetni = date;
		int i = 0;
		while(i == 0) {
			if(this.tip == TipSale.ZA_PREGLED) {
				if(this.pregledi.size() == 0) {
					 i = 1;
					 break;
				}
				for(Pregled p : this.pregledi) {
					int mins = p.getDatum().getMinutes();
					Date krajPregleda = p.getDatum();
					krajPregleda.setMinutes(mins+p.getTipPregleda().getTrajanje());
//					System.out.println("Pocetak: "+p.getDatum()+"; kraj: "+krajPregleda);
					if(pocetni.compareTo(p.getDatum()) > 0) { //stari
//						System.out.println("Prvi");
						continue;
					}
					if(date.compareTo(p.getDatum()) < 0 && (p.getDatum().getTime() - date.getTime() < 900000)) { //uskoro
//						System.out.println(p.getDatum().getTime() - date.getTime());
//						System.out.println("Drugi");
						date = krajPregleda;
					}
					else if(date.compareTo(p.getDatum())>=0 && date.compareTo(krajPregleda) < 0) { //preklapa se
//						System.out.println("Treci");
						date = krajPregleda;
					}
					else {
//						System.out.println("Cetvrti");
						i = 1;
						break;
					}				
				}
				
			}
			else {
				if(this.operacije.size() == 0) {
					 i = 1;
					 break;
				}
				for(Operacija o : this.operacije) {
					int mins = o.getDatum().getMinutes();
					Date krajOperacije = o.getDatum();
					krajOperacije.setMinutes(mins+o.getTrajanje());
//					System.out.println("Pocetak: "+o.getDatum()+"; kraj: "+krajOperacije);
					if(pocetni.compareTo(o.getDatum()) > 0) { //stari
//						System.out.println("Prvi");
						continue;
					}
					if(date.compareTo(o.getDatum()) < 0 && (o.getDatum().getTime() - date.getTime() < 900000)) { //uskoro
//						System.out.println(o.getDatum().getTime() - date.getTime());
//						System.out.println("Drugi");
						date = krajOperacije;
					}
					else if(date.compareTo(o.getDatum())>=0 && date.compareTo(krajOperacije) < 0) { //preklapa se
//						System.out.println("Treci");
						date = krajOperacije;
					}
					else {
//						System.out.println("Cetvrti");
						i = 1;
						break;
					}				
				}
			}
		}
		
		//System.out.println(formatter.format(date));
		int minuti = date.getMinutes();
		int ostatak = minuti%15;
		//System.out.println("OSTATAK: "+ostatak);
		int x = 15-ostatak;
		date.setMinutes(minuti+x);
//		System.out.println("Prvi slobodan termin: "+formatter.format(date));
		/*for(Pregled p : this.pregledi) {
			
		}*/
		prviSlobodanTermin = date;
		return prviSlobodanTermin;
	}

	public void setPrviSlobodanTermin(Date prviSlobodanTermin) {
		this.prviSlobodanTermin = prviSlobodanTermin;
	}

}
