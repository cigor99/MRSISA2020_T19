package MRSISA.Klinicki.centar.dto;

import java.util.regex.Pattern;

public class LoginDTO {
	private String email;
	private String lozinka;
	
	public LoginDTO(String email, String lozinka) {
		super();
		this.email = email;
		this.lozinka = lozinka;
	}
	
	
	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", lozinka=" + lozinka + "]";
	}
	
	public boolean proveraPolja() {
		if(this.email == null || this.lozinka == null) {
			return false;
		}
		
		if(this.email.equals("") || this.lozinka.equals("")) {
			return false;
		}
		
		if(this.email.length() > 128) {
			return false;
		}
		
		if(this.lozinka.length()>256) {
			return false;
		}
		
		//Pattern regPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
		Pattern regEmail = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
		
		/*if(!regPass.matcher(this.lozinka).matches()) {
			return false;
		}*/
		
		if(!regEmail.matcher(this.email).matches() && !this.email.equals("super")) {
			return false;
		}
		
		return true;
	}


	public LoginDTO() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	
}
