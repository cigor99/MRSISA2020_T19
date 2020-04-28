package MRSISA.Klinicki.centar.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Klinika;
import MRSISA.Klinicki.centar.domain.Lekar;

@Service
public class LekarServiceImpl implements LekarService {
	ArrayList<Lekar> lekari = new ArrayList<Lekar>();
	ArrayList<Integer> idLekara = new ArrayList<Integer>();
	
	@Override
	public ArrayList<Lekar> getLekari(){
		return lekari;
	}

	@Override
	public void dodajLekara(Lekar lekar) throws ClassNotFoundException, SQLException {
		System.out.println(lekar.getEmail());
		int id = idLekara.size()+1;
		if(!idLekara.contains(id)){
			lekar.setId(id);
			System.out.println(lekar.getId());
			lekari.add(lekar);
			idLekara.add(id);
			
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
			
			PreparedStatement p = conn.prepareStatement(
					"INSERT INTO Lekar (ID_lekara, email, lozinka, ime, prezime) VALUES (?, ?, ?, ?, ?)");
			p.setInt(1, lekar.getId());
			p.setString(2, lekar.getEmail());
			p.setString(3, lekar.getLozinka());
			p.setString(4, lekar.getIme());
			p.setString(5, lekar.getPrezime());
			//p.setInt(6, lekar.getKlinika());
			p.executeUpdate();
			System.out.println("inserted: "+lekar);
			p.close();
			
			conn.close();
		}
		
	}
	
	
    

}
