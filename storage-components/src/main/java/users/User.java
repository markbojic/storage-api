package users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User implements AdminOps {

	private String username;
	private String password;
	private boolean admin = false; // false po defaultu pa za admina setujemo na true
	private boolean[] privileges;
	// niz odredjuje koje privilegije ima korisnik
	// [create, delete, upload, download]
	// svi korisnici mogu da vide sadrzaj i rade listfiles...

	/**
	 * Constructor for the user.
	 * 
	 * @param username Username of the user
	 * @param password Password of the user
	 * @param privileges User's privileges for creating, deleting, uploading and downloading
	 */
	public User(String username, String password, boolean[] privileges) {
		super();
		this.username = username;
		this.password = password;
		this.privileges = privileges;
	}

	/**
	 * Gets username of the user
	 * 
	 * @return Username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets password of the user
	 * 
	 * @return Password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Checks if user is admin
	 * 
	 * @return Returns true if user is admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Used for setting user as admin of the storage
	 * 
	 * @param admin True if user is admin
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * Gets privileges of the user
	 * 
	 * @return List of user's privileges
	 */
	public boolean[] getPrivileges() {
		return privileges;
	}

	/**
	 * Used for setting user's privileges
	 * 
	 * @param privileges List of user's privileges
	 */
	public void setPrivileges(boolean[] privileges) {
		this.privileges = privileges;
	}

	/**
	 * Creates new user with given username, password and privileges for operations.
	 * 
	 * @param username Username of the user you wish to create
	 * @param password Password of the user you wish to create
	 * @param privs User's privileges for creating, deleting, uploading and downloading
	 * @param root Root directory of the storage
	 */
	@Override
	public void createUser(String username, String password, boolean[] privs, String root) {
		if (isAdmin()) {
			if (username != null && password != null && privs.length == 4) {
				File file = new File(root + "\\accounts.log");
				boolean exists = false;
				// Check if user already exists
				try {
					Stream<String> lines = Files.lines(file.toPath());
					if ((lines).anyMatch(line -> line.contains(username))) {
						exists = true;
					}
					lines.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (!exists) {
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
				}
				else {
					System.out.println("User with given username already exists!");
				}
			} else {
				System.out.println("Error! Wrong input!");
			}
		} else {
			System.out.println("Only admin can create new users!");
		}
	}

	/**
	 * Deletes user with given username.
	 * 
	 * @param username Username of the user you wish to delete
	 * @param root Root directory of the storage
	 */
	@Override
	public void deleteUser(String username, String root) {
		if (isAdmin()) {
			File file = new File(root + "\\accounts.log");
		    List<String> newContent;
			try {
				newContent = Files.lines(file.toPath()).filter(line -> !line.contains(username)).collect(Collectors.toList());
				Files.write(file.toPath(), newContent, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
				System.out.println("User " + username + " deleted!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error while deleting user..");
			}
		    
		} 
		else {
			System.out.println("Only admin can delete users!");
		}
	}

	/**
	 * Displays a list of all users.
	 * 
	 * @param root Root directory of the storage
	 */
	@Override
	public void listAllUsers(String root) {
		if (isAdmin()) {
			try (BufferedReader br = new BufferedReader(new FileReader(root + "\\accounts.log"))) {
				String line = null;
				System.out.println("---------");
				System.out.println("Users:");
				while ((line = br.readLine()) != null) {
					System.out.println(line.substring(0, line.indexOf("/")));
				}
				System.out.println("---------");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		else {
			System.out.println("Only admin can do this!");
		}
	}

}
