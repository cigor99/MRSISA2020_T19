package MRSISA.Klinicki.centar;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.AdministratorKlinickogCentra;
import MRSISA.Klinicki.centar.domain.Cena;
import MRSISA.Klinicki.centar.domain.Cenovnik;
import MRSISA.Klinicki.centar.domain.Dijagnoza;
import MRSISA.Klinicki.centar.domain.KlinickiCentar;
import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.KrvnaGrupa;
import MRSISA.Klinicki.centar.domain.Lek;
import MRSISA.Klinicki.centar.domain.Pacijent;
import MRSISA.Klinicki.centar.domain.Pol;
import MRSISA.Klinicki.centar.domain.ZdravstveniKarton;

@SpringBootApplication
@RestController
public class KlinickiCentarApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		SpringApplication.run(KlinickiCentarApplication.class, args);

		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
		
		KlinickiCentar KC = new KlinickiCentar(1);
		
		AdministratorKlinickogCentra akc1 = new AdministratorKlinickogCentra(1, "admin@nesto.com", "lozinka", "neko", "prezimenko", "123");
		AdministratorKlinickogCentra akc2 = new AdministratorKlinickogCentra(2, "admin2@nesto.com", "password", "ime", "prezimenko", "213");
		AdministratorKlinickogCentra akc3 = new AdministratorKlinickogCentra(3, "admin3@nesto.com", "sifra", "imenko", "neko", "321");
		
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

		Klinika k1 = new Klinika(1, "klinika 1", "adresa klinike 1", "opis", null);
		Klinika k2 = new Klinika(2, "klinika 2", "adresa klinike 2", "opis", null);

		Pacijent p1 = new Pacijent(1, "neko", "neko", "1", "neko@gmail.com", "password", null, Pol.MUSKI);
		Pacijent p2 = new Pacijent(2, "neko2", "neko2", "2", "neko2@gmail.com", "password2", null, Pol.ZENSKI);

		ZdravstveniKarton zk1 = new ZdravstveniKarton(1, 180.0, 80.0, KrvnaGrupa.ABNEGATIVNA, 0, null, p1);
		ZdravstveniKarton zk2 = new ZdravstveniKarton(2, 160.0, 50.0, KrvnaGrupa.NULTANEGATIVNA, 0.75, null, p2);

		zk1.setPacijent(p1);
		zk1.setPacijent(p2);

		p1.setZdravstveniKarton(zk1);
		p2.setZdravstveniKarton(zk2);

		PreparedStatement ps1 = conn.prepareStatement(
				"INSERT INTO ZDRAVSTEVNI_KARTONI (ID_ZDRAVSTVENOG_KARTONA, DIOPTRIJA, KRVNA_GRUPA, TEZINA, VISINA) VALUES (?, ?, ?, ?, ?)");
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

		ps1.close();

		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Pacijenti (ID_PACIJENTA, EMAIL, IME, JMBG, LOZINKA, PREZIME ,ZDRAVSTENI_KARTON, POL ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

		ps.setInt(1, p1.getId());
		ps.setString(2, p1.getEmail());
		ps.setString(3, p1.getIme());
		ps.setString(4, p1.getJmbg());
		ps.setString(5, p1.getLozinka());
		ps.setString(6, p1.getPrezime());
		ps.setInt(7, p1.getZdravstveniKarton().getId());
		ps.setInt(8, p1.getPol().ordinal());
		ps.executeUpdate();
		System.out.println("inserted: " + p1);

		ps.setInt(1, p2.getId());
		ps.setString(2, p2.getEmail());
		ps.setString(3, p2.getIme());
		ps.setString(4, p2.getJmbg());
		ps.setString(5, p2.getLozinka());
		ps.setString(6, p2.getPrezime());
		ps.setInt(7, p2.getZdravstveniKarton().getId());
		ps.setObject(8, p2.getPol().ordinal());
		ps.executeUpdate();
		System.out.println("inserted: " + p2);
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
		
		PreparedStatement ps6 = conn.prepareStatement(
				"INSERT INTO CENOVNIK (ID_CENOVNIKA) VALUES (?)");
		ps6.setInt(1, cenovnik.getId());
		ps6.executeUpdate();
		
		conn.close();

	}

	@GetMapping("/index")
	public String index(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}