package users;

import java.util.ArrayList;

public class User {
	
	private String username;
	private String password;
	private ArrayList<Boolean> privileges =  new ArrayList<Boolean>();
	// lista odredjuje koje privilegije ima korisnik
	// [create, delete, upload, download]
	// svi korisnici mogu da vide sadrzaj i rade listfiles...

	public User(String username, String password, ArrayList<Boolean> privileges) {
		super();
		this.username = username;
		this.password = password;
		this.privileges = privileges;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Boolean> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(ArrayList<Boolean> privileges) {
		this.privileges = privileges;
	}

}
