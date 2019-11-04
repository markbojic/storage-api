package users;

public interface AdminOps {
	
	/**
	 * Creates new user with given username, password and privileges for operations.
	 * 
	 * @param username Username of the user you wish to create
	 * @param password Password of the user you wish to create
	 * @param privs User's privileges for creating, deleting, uploading and downloading
	 * @param root Root directory of the storage
	 * @param token if remote storage is used the token will not be empty or null
	 */
	public void createUser(String username, String password, boolean[] privs, String root, String token);

	/**
	 * Deletes user with given username.
	 * 
	 * @param username Username of the user you wish to delete
	 * @param root Root directory of the storage
	 * @param token access token used to access remote storage in case of remote user
	 */
	public void deleteUser(String username, String root,String token);
	
	/**
	 * Displays a list of all users.
	 * 
	 * @param root Root directory of the storage
	 * @param token access token to access remote storage
	 */
	public void listAllUsers(String root,String token);

}
