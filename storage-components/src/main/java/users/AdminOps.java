package users;

public interface AdminOps {
	
	/**
	 * Creates new user with given username, password and privileges for operations.
	 * 
	 * @param username Username of the user you wish to create
	 * @param password Password of the user you wish to create
	 * @param privs User's privileges for creating, deleting, uploading and downloading
	 * @param root Root directory of the storage
	 */
	public void createUser(String username, String password, boolean[] privs, String root);

	/**
	 * Deletes user with given username.
	 * 
	 * @param username Username of the user you wish to delete
	 * @param root Root directory of the storage
	 */
	public void deleteUser(String username, String root);
	
	/**
	 * Displays a list of all users.
	 * 
	 * @param root Root directory of the storage
	 */
	public void listAllUsers(String root);

}
