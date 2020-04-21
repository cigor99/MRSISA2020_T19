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

@SpringBootApplication
@RestController
public class KlinickiCentarApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		SpringApplication.run(KlinickiCentarApplication.class, args);
		
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
		
		Klinika k1 = new Klinika(1, "klinika 1", "adresa klinike 1", "opis", null, null, null, null);
		Klinika k2 = new Klinika(2, "klinika 2", "adresa klinike 2", "opis", null, null, null, null);
		
		PreparedStatement p = conn.prepareStatement(
				"INSERT INTO Klinike (ID_Klinike, naziv, adresa, opis) VALUES (?, ?, ?, ?)");
		p.setInt(1, k1.getId());
		p.setString(2, k1.getNaziv());
		p.setString(3, k1.getAdresa());
		p.setString(4, k1.getOpis());
		p.executeUpdate();
		System.out.println("inserted: "+k1);
		
		p.setInt(1, k2.getId());
		p.setString(2, k2.getNaziv());
		p.setString(3, k2.getAdresa());
		p.setString(4, k2.getOpis());
		p.executeUpdate();
		System.out.println("inserted: "+k2);
		
		p.close();
		
		conn.close();
		
		
	}   
	 
	@GetMapping("/index")
	public String index(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	


}