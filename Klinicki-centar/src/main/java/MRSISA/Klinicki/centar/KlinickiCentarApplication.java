package MRSISA.Klinicki.centar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.AdministratorKlinike;
import MRSISA.Klinicki.centar.domain.Cena;
import MRSISA.Klinicki.centar.domain.Cenovnik;
import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.KrvnaGrupa;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.domain.MedicinskaSestra;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pol;
import MRSISA.Klinicki.centar.domain.Recept;
import MRSISA.Klinicki.centar.domain.Sala;
import MRSISA.Klinicki.centar.domain.StanjePacijenta;
import MRSISA.Klinicki.centar.domain.StanjeRecepta;
import MRSISA.Klinicki.centar.domain.StanjeZahteva;
import MRSISA.Klinicki.centar.domain.TipPregleda;
import MRSISA.Klinicki.centar.domain.TipSale;
import MRSISA.Klinicki.centar.domain.ZahtevZaRegistraciju;
import MRSISA.Klinicki.centar.domain.ZdravstveniKarton;

@SpringBootApplication
@RestController
public class KlinickiCentarApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		SpringApplication.run(KlinickiCentarApplication.class, args);

		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "sa");

		KlinickiCentar KC = new KlinickiCentar(1);

		AdministratorKlinickogCentra akc1 = new AdministratorKlinickogCentra(1, "losakaunt1234@gmail.com", "Lozinka1", "Neko",
				"Prezimenko", "123");
		AdministratorKlinickogCentra akc2 = new AdministratorKlinickogCentra(2, "apprentice.magic99@gmail.com", "Password1", "Ime",
				"Prezimenko", "213");
		AdministratorKlinickogCentra akc3 = new AdministratorKlinickogCentra(3, "admin3@nesto.com", "Sifra1", "Imenko",
				"Neko", "321");
		
		AdministratorKlinike a1 = new AdministratorKlinike(1, "staznam@nesto.com", "nemam", "Marko", "Markovic", "1234567891011", null);
		AdministratorKlinike a2 = new AdministratorKlinike(2, "stam@nesto.com", "nema", "Mirko", "Mirkovic", "1234567891012", null);
	

		akc1.setKlinickiCentar(KC);
		akc2.setKlinickiCentar(KC);
		akc3.setKlinickiCentar(KC);

		KC.getAdminiKC().add(akc1);
		KC.getAdminiKC().add(akc2);
		KC.getAdminiKC().add(akc3);

		HashSet<Cena> cene = new HashSet<>();
		HashSet<Klinika> klinike = new HashSet<>();
		Cenovnik cenovnik = new Cenovnik(1, cene, klinike);

		Dijagnoza d1 = new Dijagnoza(1, "1", "prva", "prva dijagnoza");
		Dijagnoza d2 = new Dijagnoza(2, "2", "druga", "druga dijagnoza");
		Dijagnoza d3 = new Dijagnoza(3, "3", "treca", "treca dijagnoza");

		d1.setKlinickiCentar(KC);
		d2.setKlinickiCentar(KC);
		d3.setKlinickiCentar(KC);

		KC.getSifarnikDijagnoza().add(d1);
		KC.getSifarnikDijagnoza().add(d2);
		KC.getSifarnikDijagnoza().add(d3);

		Lek l1 = new Lek(1, "lek1", "1");
		l1.setKlinickiCentar(KC);
		Lek l2 = new Lek(2, "lek2", "2");
		l2.setKlinickiCentar(KC);
		Lek l3 = new Lek(3, "lek3", "3");
		l3.setKlinickiCentar(KC);
		Lek l4 = new Lek(4, "lek4", "4");

		l4.setKlinickiCentar(KC);
		KC.getSifranikLekova().add(l1);
		KC.getSifranikLekova().add(l2);
		KC.getSifranikLekova().add(l3);
		KC.getSifranikLekova().add(l4);

		Klinika k1 = new Klinika(1, "klinika 1", "Adresa Klinike 1", "opis", null);
		Klinika k2 = new Klinika(2, "klinika 2", "Adresa Klinike 2", "opis", null);
		a1.setKlinika(k1);
		k1.getAdministratori().add(a1);
		
		Lekar lekar1 = new Lekar(1, "lekar1@gmail.com", "123", "1", "ImeLekara", "Prezime", k1);
		Lekar lekar2 = new Lekar(2, "lekar2@gmail.com", "123", "2", "ImeLekaraa", "Prezimee", k2);
		Lekar lekar3 = new Lekar(3, "lekar3@gmail.com", "123", "3", "ImeLekaraaa", "Prezimeee", k1);
		
		MedicinskaSestra sestra1 = new MedicinskaSestra(1, "sestra1@gmail.com", "111", "4", "Sestra1", "Preyime", k1);
		MedicinskaSestra sestra2 = new MedicinskaSestra(2, "sestra2@gmail.com", "111", "5", "Sestra2", "Preyime2", k1);
		MedicinskaSestra sestra3 = new MedicinskaSestra(3, "sestra3@gmail.com", "111", "6", "Sestra3", "Preyime", k2);

		Sala s1 = new Sala(1, "sala1", TipSale.ZA_PREGLED, null, k1, null);
		Sala s2 = new Sala(2, "sala2", TipSale.OPERACIONA, null, k1, null);
		Sala s3 = new Sala(3, "sala3", TipSale.OPERACIONA, null, k2, null);
		
		Cena c1 = new Cena();
		c1.setId(1);
		c1.setIznos((double) 1000);
		TipPregleda tp1 = new TipPregleda(1, 30, "tip1", null, c1, null);
		c1.setTipPregleda(tp1);
		
		Cena c2 = new Cena();
		c2.setId(2);
		c2.setIznos((double) 800);
		TipPregleda tp2 = new TipPregleda(2, 15, "tip2", null, c2, null);
		c2.setTipPregleda(tp2);

		Pacijent p1 = new Pacijent(1, "Marko", "Markovic", "1", "neko@gmail.com", "Password1", null, Pol.MUSKI, "Novi Sad",
				"Srbija", "Bul Oslobodjenja 12", "+381622222", "1");
		Pacijent p2 = new Pacijent(2, "Marko", "Maric", "2", "neko2@gmail.com", "Password2", null, Pol.ZENSKI,
				"Novi Sad", "Srbija", "Bul Oslobodjenja 15", "+3816233333", "2");
		Pacijent p3 = new Pacijent(3, "Ana", "Anic", "3", "ilijag@hotmail.com", "Sifraaa1", null, Pol.ZENSKI, "Beograd",
				"Srbija", "Nemanjina 15", "+378623332533", "3");
		Pacijent p4 = new Pacijent(4, "Marko", "Nemanjic", "4", "neko4@hotmail.com", "Stolica12", null, Pol.MUSKI,
				"Nis", "Srbija", "Nemanjina 20", "+345623653333", "4");
		Pacijent p5 = new Pacijent(5, "Marina", "Maric", "5", "marinaMaric@hotmail.com", "Frizider1", null, Pol.ZENSKI,
				"Kragujevac", "Srbija", "Narodnog fronta 76", "+352533653333", "5");
		Pacijent p6 = new Pacijent(6, "Dusan", "Kostic", "6", "dusan6@hotmail.com", "Password2", null, Pol.MUSKI,
				"Smederevo", "Srbija", "Jovana Ducica 12", "+352623696333", "6");
		Pacijent p7 = new Pacijent(7, "Jovan", "Jovanovic", "7", "jovan7@hotmail.com", "Sifra1", null, Pol.MUSKI,
				"Subotica", "Srbija", "Patrijarha Pavla 20", "+352623653333", "7");
		Pacijent p8 = new Pacijent(8, "Jovana", "Jovic", "8", "jovanaj@hotmail.com", "Laptop43", null, Pol.ZENSKI,
				"Novi Sad", "Srbija", "Kosovska 12", "+352623656633", "8");

		ZahtevZaRegistraciju z1 = new ZahtevZaRegistraciju(1, StanjeZahteva.NA_CEKANJU, null, KC);
		ZahtevZaRegistraciju z2 = new ZahtevZaRegistraciju(2, StanjeZahteva.NA_CEKANJU, null, KC);

		z1.setPacijent(p3);
		z2.setPacijent(p4);
		p3.setZahtevZaRegistraciju(z1);
		p4.setZahtevZaRegistraciju(z2);
		p1.setStanjePacijenta(StanjePacijenta.AKTIVAN);
		p2.setStanjePacijenta(StanjePacijenta.AKTIVAN);
		p3.setStanjePacijenta(StanjePacijenta.NA_CEKANJU);
		p4.setStanjePacijenta(StanjePacijenta.AKTIVAN);
		p5.setStanjePacijenta(StanjePacijenta.AKTIVAN);
		p6.setStanjePacijenta(StanjePacijenta.AKTIVAN);
		p7.setStanjePacijenta(StanjePacijenta.AKTIVAN);
		p8.setStanjePacijenta(StanjePacijenta.AKTIVAN);

		ZdravstveniKarton zk1 = new ZdravstveniKarton(1, 180.0, 80.0, KrvnaGrupa.ABNEGATIVNA, 0, null, p1);
		ZdravstveniKarton zk2 = new ZdravstveniKarton(2, 160.0, 50.0, KrvnaGrupa.NULTANEGATIVNA, 0.75, null, p2);
		ZdravstveniKarton zk3 = new ZdravstveniKarton(3, 180.0, 80.0, KrvnaGrupa.ABNEGATIVNA, 0, null, p3);
		ZdravstveniKarton zk4 = new ZdravstveniKarton(4, 150.0, 60.0, KrvnaGrupa.BNEGATIVNA, 0.55, null, p4);
		ZdravstveniKarton zk5 = new ZdravstveniKarton(5, 200.0, 110.0, KrvnaGrupa.ABNEGATIVNA, 0, null, p5);
		ZdravstveniKarton zk6 = new ZdravstveniKarton(6, 140.0, 50.0, KrvnaGrupa.NULTAPOZITIVNA, 1.0, null, p6);
		ZdravstveniKarton zk7 = new ZdravstveniKarton(7, 167.0, 80.0, KrvnaGrupa.ABNEGATIVNA, 0.6, null, p7);
		ZdravstveniKarton zk8 = new ZdravstveniKarton(8, 158.0, 60.0, KrvnaGrupa.BPOZITIVNA, 0.55, null, p8);
		
//		zk1.setPacijent(p1);
//		zk2.setPacijent(p2);
//		zk3.setPacijent(p3);
//		zk4.setPacijent(p4);
//		zk5.setPacijent(p5);
//	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  

		
		Recept r1 = new Recept(1, "07/05/2020");
		Recept r2 = new Recept(2, "06/05/2020");
		Recept r3 = new Recept(3, "03/05/2020");
		Recept r4 = new Recept(4, "04/05/2020");
		Recept r5 = new Recept(5, "08/05/2020");
		
		lekar1.getRecepti().add(r1);
		lekar2.getRecepti().add(r2);
		lekar3.getRecepti().add(r3);
		lekar3.getRecepti().add(r4);
		lekar1.getRecepti().add(r5);
		
		r1.setStanjeRecepta(StanjeRecepta.OVEREN);
		
		r1.setLekar(lekar1);
		r2.setLekar(lekar2);
		r3.setLekar(lekar3);
		r4.setLekar(lekar3);
		r5.setLekar(lekar1);
		
		r1.getLekovi().add(l1);
		r1.getLekovi().add(l2);
		r2.getLekovi().add(l2);
		r3.getLekovi().add(l3);
		r4.getLekovi().add(l4);
		r4.getLekovi().add(l3);
		r4.getLekovi().add(l2);
		
		
		l1.getRecepti().add(r1);
		l2.getRecepti().add(r1);
		l2.getRecepti().add(r2);
		l3.getRecepti().add(r3);
		l3.getRecepti().add(r4);
		l4.getRecepti().add(r4);
		l2.getRecepti().add(r4);
		
		
		p1.setZdravstveniKarton(zk1);
		p2.setZdravstveniKarton(zk2);
		p3.setZdravstveniKarton(zk3);
		p4.setZdravstveniKarton(zk4);
		p5.setZdravstveniKarton(zk5);
		p6.setZdravstveniKarton(zk6);
		p7.setZdravstveniKarton(zk7);
		p8.setZdravstveniKarton(zk8);
		PreparedStatement ps1 = conn.prepareStatement(
		
				"INSERT INTO ZDRAVSTEVNI_KARTONI (ID_ZDRAVSTVENOG_KARTONA, DIOPTRIJA, KRVNA_GRUPA, TEZINA, VISINA) VALUES (?, ?, ?, ?, ?)");
		try {
			
		ps1.setInt(1, zk1.getId());
		ps1.setDouble(2, zk1.getDioptrija());
		ps1.setInt(3, zk1.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk1.getTezina());
		ps1.setDouble(5, zk1.getVisina());
		ps1.executeUpdate();

		ps1.setInt(1, zk2.getId());
		ps1.setDouble(2, zk2.getDioptrija());
		ps1.setInt(3, zk2.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk2.getTezina());
		ps1.setDouble(5, zk2.getVisina());
		ps1.executeUpdate();

		ps1.setInt(1, zk3.getId());
		ps1.setDouble(2, zk3.getDioptrija());
		ps1.setInt(3, zk3.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk3.getTezina());
		ps1.setDouble(5, zk3.getVisina());
		ps1.executeUpdate();

		ps1.setInt(1, zk4.getId());
		ps1.setDouble(2, zk4.getDioptrija());
		ps1.setInt(3, zk4.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk4.getTezina());
		ps1.setDouble(5, zk4.getVisina());
		ps1.executeUpdate();
		
		ps1.setInt(1, zk5.getId());
		ps1.setDouble(2, zk5.getDioptrija());
		ps1.setInt(3, zk5.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk5.getTezina());
		ps1.setDouble(5, zk5.getVisina());
		ps1.executeUpdate();
		
		ps1.setInt(1, zk6.getId());
		ps1.setDouble(2, zk6.getDioptrija());
		ps1.setInt(3, zk6.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk6.getTezina());
		ps1.setDouble(5, zk6.getVisina());
		ps1.executeUpdate();
		
		ps1.setInt(1, zk7.getId());
		ps1.setDouble(2, zk7.getDioptrija());
		ps1.setInt(3, zk7.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk7.getTezina());
		ps1.setDouble(5, zk7.getVisina());
		ps1.executeUpdate();
		
		ps1.setInt(1, zk8.getId());
		ps1.setDouble(2, zk8.getDioptrija());
		ps1.setInt(3, zk8.getKrvnaGrupa().ordinal());
		ps1.setDouble(4, zk8.getTezina());
		ps1.setDouble(5, zk8.getVisina());
		ps1.executeUpdate();
		}catch(SQLException e) {
			//e.printStackTrace();
			try{ps1.close();
			
			}
			catch (NullPointerException npe) {
				//npe.printStackTrace();
			}
		}finally {
			ps1.close();
		}
		

		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Pacijenti (ID_PACIJENTA, ADRESA, BROJ_TELEFONA, DRZAVA, EMAIL, GRAD, IME, JEDINSTVENI_BROJ_OSIGURANIKA, JMBG, LOZINKA, POL, PREZIME, ZDRAVSTENI_KARTON, STANJE_PACIJENTA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		ps.setInt(1, p1.getId());
		ps.setString(2, p1.getAdresa());
		ps.setString(3, p1.getBrojTelefona());
		ps.setString(4, p1.getDrzava());
		ps.setString(5, p1.getEmail());
		ps.setString(6, p1.getGrad());
		ps.setString(7, p1.getIme());
		ps.setString(8, p1.getJedinstveniBrOsig());
		ps.setString(9, p1.getJmbg());
		ps.setString(10, p1.getLozinka());
		ps.setInt(11, p1.getPol().ordinal());
		ps.setString(12, p1.getPrezime());
		ps.setInt(13, p1.getZdravstveniKarton().getId());
		ps.setInt(14, p1.getStanjePacijenta().ordinal());
		ps.executeUpdate();
		System.out.println("inserted: " + p1);

		ps.setInt(1, p2.getId());
		ps.setString(2, p2.getAdresa());
		ps.setString(3, p2.getBrojTelefona());
		ps.setString(4, p2.getDrzava());
		ps.setString(5, p2.getEmail());
		ps.setString(6, p2.getGrad());
		ps.setString(7, p2.getIme());
		ps.setString(8, p2.getJedinstveniBrOsig());
		ps.setString(9, p2.getJmbg());
		ps.setString(10, p2.getLozinka());
		ps.setInt(11, p2.getPol().ordinal());
		ps.setString(12, p2.getPrezime());
		ps.setInt(13, p2.getZdravstveniKarton().getId());
		ps.setInt(14, p2.getStanjePacijenta().ordinal());
		ps.executeUpdate();
		System.out.println("inserted: " + p2);

		ps.setInt(1, p3.getId());
		ps.setString(2, p3.getAdresa());
		ps.setString(3, p3.getBrojTelefona());
		ps.setString(4, p3.getDrzava());
		ps.setString(5, p3.getEmail());
		ps.setString(6, p3.getGrad());
		ps.setString(7, p3.getIme());
		ps.setString(8, p3.getJedinstveniBrOsig());
		ps.setString(9, p3.getJmbg());
		ps.setString(10, p3.getLozinka());
		ps.setInt(11, p3.getPol().ordinal());
		ps.setString(12, p3.getPrezime());
		ps.setInt(13, p3.getZdravstveniKarton().getId());
		ps.setInt(14, p3.getStanjePacijenta().ordinal());
		ps.executeUpdate();
//		System.out.println("inserted: " + p3);

		ps.setInt(1, p5.getId());
		ps.setString(2, p5.getAdresa());
		ps.setString(3, p5.getBrojTelefona());
		ps.setString(4, p5.getDrzava());
		ps.setString(5, p5.getEmail());
		ps.setString(6, p5.getGrad());
		ps.setString(7, p5.getIme());
		ps.setString(8, p5.getJedinstveniBrOsig());
		ps.setString(9, p5.getJmbg());
		ps.setString(10, p5.getLozinka());
		ps.setInt(11, p5.getPol().ordinal());
		ps.setString(12, p5.getPrezime());
		ps.setInt(13, p5.getZdravstveniKarton().getId());
		ps.setInt(14, p5.getStanjePacijenta().ordinal());
		ps.executeUpdate();
//		System.out.println("inserted: " + p4);
		
		ps.setInt(1, p6.getId());
		ps.setString(2, p6.getAdresa());
		ps.setString(3, p6.getBrojTelefona());
		ps.setString(4, p6.getDrzava());
		ps.setString(5, p6.getEmail());
		ps.setString(6, p6.getGrad());
		ps.setString(7, p6.getIme());
		ps.setString(8, p6.getJedinstveniBrOsig());
		ps.setString(9, p6.getJmbg());
		ps.setString(10, p6.getLozinka());
		ps.setInt(11, p6.getPol().ordinal());
		ps.setString(12, p6.getPrezime());
		ps.setInt(13, p6.getZdravstveniKarton().getId());
		ps.setInt(14, p6.getStanjePacijenta().ordinal());
		ps.executeUpdate();
		
		ps.setInt(1, p7.getId());
		ps.setString(2, p7.getAdresa());
		ps.setString(3, p7.getBrojTelefona());
		ps.setString(4, p7.getDrzava());
		ps.setString(5, p7.getEmail());
		ps.setString(6, p7.getGrad());
		ps.setString(7, p7.getIme());
		ps.setString(8, p7.getJedinstveniBrOsig());
		ps.setString(9, p7.getJmbg());
		ps.setString(10, p7.getLozinka());
		ps.setInt(11, p7.getPol().ordinal());
		ps.setString(12, p7.getPrezime());
		ps.setInt(13, p7.getZdravstveniKarton().getId());
		ps.setInt(14, p7.getStanjePacijenta().ordinal());
		ps.executeUpdate();
		
		ps.setInt(1, p8.getId());
		ps.setString(2, p8.getAdresa());
		ps.setString(3, p8.getBrojTelefona());
		ps.setString(4, p8.getDrzava());
		ps.setString(5, p8.getEmail());
		ps.setString(6, p8.getGrad());
		ps.setString(7, p8.getIme());
		ps.setString(8, p8.getJedinstveniBrOsig());
		ps.setString(9, p8.getJmbg());
		ps.setString(10, p8.getLozinka());
		ps.setInt(11, p8.getPol().ordinal());
		ps.setString(12, p8.getPrezime());
		ps.setInt(13, p8.getZdravstveniKarton().getId());
		ps.setInt(14, p8.getStanjePacijenta().ordinal());
		ps.executeUpdate();
		
		
		ps.setInt(1, p4.getId());
		ps.setString(2, p4.getAdresa());
		ps.setString(3, p4.getBrojTelefona());
		ps.setString(4, p4.getDrzava());
		ps.setString(5, p4.getEmail());
		ps.setString(6, p4.getGrad());
		ps.setString(7, p4.getIme());
		ps.setString(8, p4.getJedinstveniBrOsig());
		ps.setString(9, p4.getJmbg());
		ps.setString(10, p4.getLozinka());
		ps.setInt(11, p4.getPol().ordinal());
		ps.setString(12, p4.getPrezime());
		ps.setInt(13, p4.getZdravstveniKarton().getId());
		ps.setInt(14, p4.getStanjePacijenta().ordinal());
		ps.executeUpdate();

		ps.close();

		PreparedStatement p = conn
				.prepareStatement("INSERT INTO Klinike (ID_Klinike, naziv, adresa, opis) VALUES (?, ?, ?, ?)");
		p.setInt(1, k1.getId());
		p.setString(2, k1.getNaziv());
		p.setString(3, k1.getAdresa());
		p.setString(4, k1.getOpis());
		p.executeUpdate();
		System.out.println("inserted: " + k1);

		p.setInt(1, k2.getId());
		p.setString(2, k2.getNaziv());
		p.setString(3, k2.getAdresa());
		p.setString(4, k2.getOpis());
		p.executeUpdate();
		System.out.println("inserted: " + k2);

		p.close();

		PreparedStatement ps2 = conn.prepareStatement("INSERT INTO KLINICKI_CENTAR (ID_KC) VALUES (?)");
		ps2.setInt(1, KC.getId());
		ps2.executeUpdate();
		ps2.close();

		PreparedStatement ps3 = conn
				.prepareStatement("INSERT INTO LEKOVI (ID_LEKA, NAZIV, SIFRA, klinicki_centar) VALUES (?, ?, ?, ?)");
		ps3.setInt(1, l1.getId());
		ps3.setString(2, l1.getNaziv());
		ps3.setString(3, l1.getSifra());
		ps3.setInt(4, l1.getKlinickiCentar().getId());
		ps3.executeUpdate();

		ps3.setInt(1, l2.getId());
		ps3.setString(2, l2.getNaziv());
		ps3.setString(3, l2.getSifra());
		ps3.setInt(4, l2.getKlinickiCentar().getId());
		ps3.executeUpdate();

		ps3.setInt(1, l3.getId());
		ps3.setString(2, l3.getNaziv());
		ps3.setString(3, l3.getSifra());
		ps3.setInt(4, l3.getKlinickiCentar().getId());
		ps3.executeUpdate();

		ps3.setInt(1, l4.getId());
		ps3.setString(2, l4.getNaziv());
		ps3.setString(3, l4.getSifra());
		ps3.setInt(4, l4.getKlinickiCentar().getId());
		ps3.executeUpdate();
		ps3.close();

		PreparedStatement ps4 = conn.prepareStatement(
				"INSERT INTO DIJAGNOZE (ID_DIJAGNOZA, NAZIV, OPIS, SIFRA, KLINICKI_CENTAR) VALUES (?, ?, ?, ?, ?)");
		ps4.setInt(1, d1.getId());
		ps4.setString(2, d1.getNaziv());
		ps4.setString(3, d1.getOpis());
		ps4.setString(4, d1.getSifra());
		ps4.setInt(5, d1.getKlinickiCentar().getId());
		ps4.executeUpdate();

		ps4.setInt(1, d2.getId());
		ps4.setString(2, d2.getNaziv());
		ps4.setString(3, d2.getOpis());
		ps4.setString(4, d2.getSifra());
		ps4.setInt(5, d2.getKlinickiCentar().getId());
		ps4.executeUpdate();

		ps4.setInt(1, d3.getId());
		ps4.setString(2, d3.getNaziv());
		ps4.setString(3, d3.getOpis());
		ps4.setString(4, d3.getSifra());
		ps4.setInt(5, d3.getKlinickiCentar().getId());
		ps4.executeUpdate();

		ps4.close();

		PreparedStatement ps5 = conn.prepareStatement(
				"INSERT INTO ADMINISTRATORIKC (ID_ADMINKC, EMAIL, IME, JMBG, LOZINKA, PREZIME, KLINICKI_CENTAR) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps5.setInt(1, akc1.getId());
		ps5.setString(2, akc1.getEmail());
		ps5.setString(3, akc1.getIme());
		ps5.setString(4, akc1.getJmbg());
		ps5.setString(5, akc1.getLozinka());
		ps5.setString(6, akc1.getPrezime());
		ps5.setInt(7, akc1.getKlinickiCentar().getId());
		ps5.executeUpdate();

		ps5.setInt(1, akc2.getId());
		ps5.setString(2, akc2.getEmail());
		ps5.setString(3, akc2.getIme());
		ps5.setString(4, akc2.getJmbg());
		ps5.setString(5, akc2.getLozinka());
		ps5.setString(6, akc2.getPrezime());
		ps5.setInt(7, akc2.getKlinickiCentar().getId());
		ps5.executeUpdate();

		ps5.setInt(1, akc3.getId());
		ps5.setString(2, akc3.getEmail());
		ps5.setString(3, akc3.getIme());
		ps5.setString(4, akc3.getJmbg());
		ps5.setString(5, akc3.getLozinka());
		ps5.setString(6, akc3.getPrezime());
		ps5.setInt(7, akc3.getKlinickiCentar().getId());
		ps5.executeUpdate();

		PreparedStatement ps6 = conn.prepareStatement("INSERT INTO CENOVNIK (ID_CENOVNIKA) VALUES (?)");
		ps6.setInt(1, cenovnik.getId());
		ps6.executeUpdate();

		PreparedStatement ps7 = conn.prepareStatement(
				"INSERT INTO ZAHTEVI_ZA_REGISTRACIJU (ID_ZZR, STANJE_ZAHTEVA, KLINICKI_CENTAR, PACIJENT) VALUES (?, ?, ?, ?)");
		ps7.setInt(1, z1.getId());
		ps7.setInt(2, z1.getStanje().ordinal());
		ps7.setInt(3, z1.getKlinickiCentar().getId());
		ps7.setInt(4, z1.getPacijent().getId());

		ps7.executeUpdate();

		ps7.setInt(1, z2.getId());
		ps7.setInt(2, z2.getStanje().ordinal());
		ps7.setInt(3, z2.getKlinickiCentar().getId());
		ps7.setInt(4, z2.getPacijent().getId());

		ps7.executeUpdate();
		ps7.close();
		
		PreparedStatement ps8 = conn.prepareStatement(
				"INSERT INTO  ADMINI_KLINIKE  (ID_ADMINK, EMAIL, IME, JMBG, LOZINKA, PREZIME, KLINIKA) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps8.setInt(1, a1.getId());
		ps8.setString(2, a1.getEmail());
		ps8.setString(3, a1.getIme());
		ps8.setString(4, a1.getJmbg());
		ps8.setString(5, a1.getLozinka());
		ps8.setString(6, a1.getPrezime());
		ps8.setInt(7, a1.getKlinika().getId());
		ps8.executeUpdate();
		
		ps8.setInt(1, a2.getId());
		ps8.setString(2, a2.getEmail());
		ps8.setString(3, a2.getIme());
		ps8.setString(4, a2.getJmbg());
		ps8.setString(5, a2.getLozinka());
		ps8.setString(6, a2.getPrezime());
		ps8.setNull(7, java.sql.Types.INTEGER);
		ps8.executeUpdate();
		
		ps8.close();
		
		PreparedStatement ps9 = conn.prepareStatement(
				"INSERT INTO  LEKAR  (ID_LEKARA, EMAIL, LOZINKA, IME, PREZIME, JMBG, KLINIKA) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps9.setInt(1, lekar1.getId());
		ps9.setString(2, lekar1.getEmail());
		ps9.setString(3, lekar1.getLozinka());
		ps9.setString(4, lekar1.getIme());		
		ps9.setString(5, lekar1.getPrezime());
		ps9.setString(6, lekar1.getJmbg());
		ps9.setInt(7, lekar1.getKlinika().getId());
		ps9.executeUpdate();
		
		ps9.setInt(1, lekar2.getId());
		ps9.setString(2, lekar2.getEmail());
		ps9.setString(3, lekar2.getLozinka());
		ps9.setString(4, lekar2.getIme());		
		ps9.setString(5, lekar2.getPrezime());
		ps9.setString(6, lekar2.getJmbg());
		ps9.setInt(7, lekar2.getKlinika().getId());
		ps9.executeUpdate();
		
		ps9.setInt(1, lekar3.getId());
		ps9.setString(2, lekar3.getEmail());
		ps9.setString(3, lekar3.getLozinka());
		ps9.setString(4, lekar3.getIme());		
		ps9.setString(5, lekar3.getPrezime());
		ps9.setString(6, lekar3.getJmbg());
		ps9.setInt(7, lekar3.getKlinika().getId());
		ps9.executeUpdate();
		
		PreparedStatement ps15 = conn.prepareStatement(
				"INSERT INTO  MEDICINSKE_SESTRE  (ID_MED_SES, EMAIL, LOZINKA, IME, PREZIME, JMBG, KLINIKA) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps15.setInt(1, sestra1.getId());
		ps15.setString(2, sestra1.getEmail());
		ps15.setString(3, sestra1.getLozinka());
		ps15.setString(4, sestra1.getIme());		
		ps15.setString(5, sestra1.getPrezime());
		ps15.setString(6, sestra1.getJmbg());
		ps15.setInt(7, sestra1.getKlinika().getId());
		ps15.executeUpdate();
		
		ps15.setInt(1, sestra2.getId());
		ps15.setString(2, sestra2.getEmail());
		ps15.setString(3, sestra2.getLozinka());
		ps15.setString(4, sestra2.getIme());		
		ps15.setString(5, sestra2.getPrezime());
		ps15.setString(6, sestra2.getJmbg());
		ps15.setInt(7, sestra2.getKlinika().getId());
		ps15.executeUpdate();
		
		ps15.setInt(1, sestra3.getId());
		ps15.setString(2, sestra3.getEmail());
		ps15.setString(3, sestra3.getLozinka());
		ps15.setString(4, sestra3.getIme());		
		ps15.setString(5, sestra3.getPrezime());
		ps15.setString(6, sestra3.getJmbg());
		ps15.setInt(7, sestra3.getKlinika().getId());
		ps15.executeUpdate();
		
		PreparedStatement ps10 = conn.prepareStatement(
				"INSERT INTO  SALE  (ID_SALE, NAZIV, TIP_SALE, KLINIKA) VALUES (?, ?, ?, ?)");
		ps10.setInt(1, s1.getId());
		ps10.setString(2, s1.getNaziv());
		ps10.setInt(3, s1.getTip().ordinal());
		ps10.setInt(4, s1.getKlinika().getId());
		ps10.executeUpdate();
		
		ps10.setInt(1, s2.getId());
		ps10.setString(2, s2.getNaziv());
		ps10.setInt(3, s2.getTip().ordinal());
		ps10.setInt(4, s2.getKlinika().getId());
		ps10.executeUpdate();
		
		ps10.setInt(1, s3.getId());
		ps10.setString(2, s3.getNaziv());
		ps10.setInt(3, s3.getTip().ordinal());
		ps10.setInt(4, s3.getKlinika().getId());
		ps10.executeUpdate();
		
		PreparedStatement ps12 = conn.prepareStatement(
				"INSERT INTO  CENE  (ID_CENE, IZNOS) VALUES (?, ?)");
		ps12.setInt(1, c1.getId());
		ps12.setDouble(2, c1.getIznos());
		ps12.executeUpdate();
		
		ps12.setInt(1, c2.getId());
		ps12.setDouble(2, c2.getIznos());
		ps12.executeUpdate();
		
		PreparedStatement ps11 = conn.prepareStatement(
				"INSERT INTO  Tipovi_Pregleda  (ID_Tipa_Pregleda, TRAJANJE, NAZIV, CENA) VALUES (?, ?, ?, ?)");
		ps11.setInt(1, tp1.getId());
		ps11.setInt(2, tp1.getTrajanje());
		ps11.setString(3, tp1.getNaziv());
		ps11.setInt(4, tp1.getCena().getId());
		ps11.executeUpdate();
		
		ps11.setInt(1, tp2.getId());
		ps11.setInt(2, tp2.getTrajanje());
		ps11.setString(3, tp2.getNaziv());
		ps11.setInt(4, tp2.getCena().getId());
		ps11.executeUpdate();
		
		
		PreparedStatement ps13 = conn.prepareStatement(
				"INSERT INTO  RECEPTI  (ID_RECEPTA, DATUM_IZDAVANJA_RECEPTA, STANJE_RECEPTA, LEKAR, MEDICINSKA_SESTRA, PREGLED) VALUES (?, ?, ?, ?, ?, ?)");
		ps13.setInt(1, r1.getId());
		ps13.setString(2, r1.getDatumIzdavanja());
		ps13.setInt(3, r1.getStanjeRecepta().ordinal());
		ps13.setInt(4, r1.getLekar().getId());
		ps13.setNull(5, java.sql.Types.INTEGER);
		ps13.setNull(6, java.sql.Types.INTEGER);
		ps13.executeUpdate();
		
		
		ps13.setInt(1, r2.getId());
		ps13.setString(2, r2.getDatumIzdavanja());
		ps13.setInt(3, r2.getStanjeRecepta().ordinal());
		ps13.setInt(4, r2.getLekar().getId());
		ps13.setNull(5, java.sql.Types.INTEGER);
		ps13.setNull(6, java.sql.Types.INTEGER);
		ps13.executeUpdate();
		
		ps13.setInt(1, r3.getId());
		ps13.setString(2, r3.getDatumIzdavanja());
		ps13.setInt(3, r3.getStanjeRecepta().ordinal());
		ps13.setInt(4, r3.getLekar().getId());
		ps13.setNull(5, java.sql.Types.INTEGER);
		ps13.setNull(6, java.sql.Types.INTEGER);
		ps13.executeUpdate();
		
		
		ps13.setInt(1, r4.getId());
		ps13.setString(2, r4.getDatumIzdavanja());
		ps13.setInt(3, r4.getStanjeRecepta().ordinal());
		ps13.setInt(4, r4.getLekar().getId());
		ps13.setNull(5, java.sql.Types.INTEGER);
		ps13.setNull(6, java.sql.Types.INTEGER);
		ps13.executeUpdate();
		
		ps13.setInt(1, r5.getId());
		ps13.setString(2, r5.getDatumIzdavanja());
		ps13.setInt(3, r5.getStanjeRecepta().ordinal());
		ps13.setInt(4, r5.getLekar().getId());
		ps13.setNull(5, java.sql.Types.INTEGER);
		ps13.setNull(6, java.sql.Types.INTEGER);
		ps13.executeUpdate();
		
		ps13.close();
		
		PreparedStatement ps14 = conn.prepareStatement(
				"INSERT INTO  LEK_RECEPT  (ID_RECEPTA, ID_LEKA) VALUES (?, ?)");
		
		for (Lek lek : r1.getLekovi()) {
			ps14.setInt(1, r1.getId());
			ps14.setInt(2, lek.getId());
			ps14.executeUpdate();
		}
		
		for (Lek lek : r2.getLekovi()) {
			ps14.setInt(1, r2.getId());
			ps14.setInt(2, lek.getId());
			ps14.executeUpdate();
		}
		
		for (Lek lek : r3.getLekovi()) {
			ps14.setInt(1, r3.getId());
			ps14.setInt(2, lek.getId());
			ps14.executeUpdate();
		}
		
		for (Lek lek : r4.getLekovi()) {
			ps14.setInt(1, r4.getId());
			ps14.setInt(2, lek.getId());
			ps14.executeUpdate();
		}
		
		for (Lek lek : r5.getLekovi()) {
			ps14.setInt(1, r5.getId());
			ps14.setInt(2, lek.getId());
			ps14.executeUpdate();
		}
		

		ps14.close();

		conn.close();

	}

	@GetMapping("/index")
	public String index(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}