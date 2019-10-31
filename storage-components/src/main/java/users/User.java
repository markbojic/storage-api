package users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class User implements AdminOps {

	private String username;
	private String password;
	private boolean admin = false; // false po defaultu pa za admina setujemo na true
	private boolean[] privileges;
	// niz odredjuje koje privilegije ima korisnik
	// [create, delete, upload, download]
	// svi korisnici mogu da vide sadrzaj i rade listfiles...

	public User(String username, String password, boolean[] privileges) {
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean[] getPrivileges() {
		return privileges;
	}

	public void setPrivileges(boolean[] privileges) {
		this.privileges = privileges;
	}

	@Override
	public void createUser(String username, String password, boolean[] privs) {
		if (getUsername().equals("admin")) {
			// TODO proveri da li user postoji u if-u
			if (username != null && password != null && privs.length == 4) {
				// TODO zameni path za path podesenog storage-a
				File file = new File("C:\\Users\\R930\\Desktop\\Test Folder\\accounts.txt");
				
				try {
					FileOutputStream fos = new FileOutputStream(file, true); //Set to true for append mode
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					try {
						bw.write(username + "/" + password + "/" + privs[0] + "/" + privs[1] + "/" + privs[2] + "/" + privs[3]);
						bw.newLine();
						bw.close();
						System.out.println("User " + username + " created!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Error! Wrong input!");
			}
		} else {
			System.out.println("Only admin can create new users!");
		}
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listAllUsers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void forbiddenExtensions(String[] extensions) {
		// TODO Auto-generated method stub
		// niz zabranjenih napravim u fileimplementation i preko setera ovde promenim samo
	}

}
