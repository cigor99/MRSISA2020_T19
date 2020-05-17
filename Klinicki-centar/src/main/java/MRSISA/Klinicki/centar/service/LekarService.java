package MRSISA.Klinicki.centar.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.repository.LekarRepository;

@Service
public class LekarService {
	
	@Autowired
	private LekarRepository lekarRepository;
	
	ArrayList<Lekar> lekari = new ArrayList<Lekar>();
	
	public ArrayList<Lekar> getLekari(){
		return lekari;
	}
	
	public Lekar addLekar(Lekar lekar) {
		return lekarRepository.save(lekar);
	}
	
	public void remove(Integer id) {
		lekarRepository.deleteById(id);
	}
	
	public Lekar save(Lekar lekar) {
		return lekarRepository.save(lekar);
	}
	
	public Page<Lekar> findAll(Pageable page){
		return lekarRepository.findAll(page);
	}
	
	public List<Lekar> findAll(){
		return lekarRepository.findAll();
	}
	
	public Lekar findOne(Integer id) {
		return lekarRepository.findById(id).orElse(null);
	}
	
	public Lekar findByjmbg(String jmbg) {
		return lekarRepository.findByjmbg(jmbg);
	}
	
	
	
	
	
	
	
	
	/*ArrayList<Lekar> lekari = new ArrayList<Lekar>();
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
		
	}*/
    

}
