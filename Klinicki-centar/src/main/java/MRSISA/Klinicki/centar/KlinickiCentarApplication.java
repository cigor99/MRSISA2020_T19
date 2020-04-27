package MRSISA.Klinicki.centar;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.KrvnaGrupa;
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

		Klinika k1 = new Klinika(1, "klinika 1", "adresa klinike 1", "opis", null, null, null, null);
		Klinika k2 = new Klinika(2, "klinika 2", "adresa klinike 2", "opis", null, null, null, null);

		Pacijent p1 = new Pacijent(1, "neko", "neko", "1", "neko@gmail.com", "password", null, null, null, null,
				null, Pol.MUSKI);
		Pacijent p2 = new Pacijent(2, "neko2", "neko2", "2", "neko2@gmail.com", "password2", null, null, null,
				null, null, Pol.ZENSKI);
		ZdravstveniKarton zk1 = new ZdravstveniKarton(1, 180.0, 80.0, KrvnaGrupa.ABNEGATIVNA, 0, null, p1);
		ZdravstveniKarton zk2 = new ZdravstveniKarton(2, 160.0, 50.0, KrvnaGrupa.NULTANEGATIVNA, 0.75, null, p2);
		zk1.setPacijent(p1);
		zk1.setPacijent(p2);
		p1.setZdravstveniKarton(zk1);
		p2.setZdravstveniKarton(zk2);

		PreparedStatement ps1 = conn
				.prepareStatement("INSERT INTO ZDRAVSTEVNI_KARTONI (ID_ZDRAVSTVENOG_KARTONA, DIOPTRIJA, KRVNA_GRUPA, TEZINA, VISINA) VALUES (?, ?, ?, ?, ?)");
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

		conn.close();

	}

	@GetMapping("/index")
	public String index(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}