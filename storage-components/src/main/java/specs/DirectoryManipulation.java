package specs;

import users.AbstractUser;

public interface DirectoryManipulation {

	/**
	 * Creates new directory on given path.
	 * 
	 * @param name         Directory name
	 * @param path         Dir's path on the storage
	 * @param AbstractUser Current AbstractUser
	 */
	public void createDirectory(String name, String path, AbstractUser user);

	/**
	 * Deletes directory on given path.
	 * 
	 * @param path                 Dir's path on the storage
	 * @param AbstractAbstractUser Current AbstractUser
	 */
	public void deleteDirectory(String path, AbstractUser user);

	/**
	 * Uploads directory from chosen path to given path on storage.
	 * 
	 * @param selectedPath    Path of the chosen directory
	 * @param destinationPath Path on the storage where directory will be uploaded
	 *                        to
	 * @param AbstractUser    Current AbstractUser
	 */
	public void uploadDirectory(String selectedPath, String destinationPath, AbstractUser user);

	/**
	 * Downloads directory to given path.
	 * 
	 * @param selectedPath    Path of the directory on storage
	 * @param destinationPath Path where directory will be downloaded to
	 * @param AbstractUser    Current AbstractUser
	 */
	public void downloadDirectory(String selectedPath, String destinationPath, AbstractUser user);

	/**
	 * Prints names of all files from given directory and it's sub directories.
	 * 
	 * @param path Path of the chosen directory
	 */
	public void listAllFiles(String path);

	/**
	 * Prints names of files with given extension from given directory. If @param
	 * extension equals "all" then function should print all files from given
	 * directory.
	 * 
	 * @param path      Path of the chosen directory
	 * @param extension Chosen extension for display
	 */
	public void listFiles(String path, String extension);

	/**
	 * Prints names of all directories from chosen path.
	 * 
	 * @param path Path of the chosen directory
	 */
	public void listDirectories(String path);

	/**
	 * This method zips and uploads directory.
	 * 
	 * @param selectedPath    Path of the directory on disk
	 * @param destinationPath Path where zipped directory will be uploaded
	 * @param AbstractUser    Current AbstractUser
	 */
	public void uploadZipDirectory(String selectedPath, String destinationPath, AbstractUser user);

	/**
	 * Initializes storage.
	 * 
	 * @param storagePath Path of the storage
	 * @param user        User who initiates the storage
	 */
	public void initStorage(String storagePath, AbstractUser user);

	/**
	 * Used for setting root's path.
	 * 
	 * @param Root's path
	 */
	public void setRoot(String root);

	/**
	 * Gets path of the root directory.
	 * 
	 * @return Path of the root directory
	 */
	public String getRoot();

	/**
	 * Used for setting forbidden extensions.
	 * 
	 * @param forbidden Forbidden extensions
	 */
	public void setForbiddenExtensions(String[] forbiddenExtensions);

	/**
	 * Gets array of forbidden extensions.
	 * 
	 * @return Array of forbidden extensions
	 */
	public String[] getForbiddenExtensions();

}
