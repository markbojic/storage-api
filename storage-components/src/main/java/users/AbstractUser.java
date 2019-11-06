package users;

public abstract class AbstractUser {

	private String username;
	private String password;
	private boolean admin = false; // false po defaultu pa za admina setujemo na true
	private boolean[] privileges;
	// niz odredjuje koje privilegije ima korisnik
	// [create, delete, upload, download]
	// svi korisnici mogu da vide sadrzaj i rade listfiles...

	
	public AbstractUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	 * @param privs    User's privileges for creating, deleting, uploading and
	 *                 downloading
	 * @param root     Root directory of the storage
	 */
	public abstract void createUser(String username, String password, boolean[] privs, String root);

	/**
	 * Deletes user with given username.
	 * 
	 * @param username Username of the user you wish to delete
	 * @param root     Root directory of the storage
	 * @param token    access token used to access remote storage in case of remote
	 *                 user
	 */
	public abstract void deleteUser(String username, String root);

	/**
	 * Displays a list of all users.
	 * 
	 * @param root Root directory of the storage
	 */
	public abstract void listAllUsers(String root);

}
