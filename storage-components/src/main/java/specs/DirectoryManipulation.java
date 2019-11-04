package specs;

import users.User;

public interface DirectoryManipulation {
	
	/**
	 * Creates new directory on given path.
	 * 
	 * @param name Directory name
	 * @param path Dir's path on the storage
	 * @param user Current user
	 */
	public void createDirectory(String name, String path, User user);
	
	/**
	 * Deletes directory on given path.
	 * 
	 * @param path Dir's path on the storage
	 * @param user Current user
	 */
	public void deleteDirectory(String path, User user);
	
	/**
	 * Uploads directory from chosen path to given path on storage.
	 * 
	 * @param selectedPath Path of the chosen directory
	 * @param destinationPath Path on the storage where directory will be uploaded to
	 * @param user Current user
	 */
	public void uploadDirectory(String selectedPath, String destinationPath, User user);
	
	/**
	 * Downloads directory to given path.
	 * 
	 * @param selectedPath Path of the directory on storage
	 * @param destinationPath Path where directory will be downloaded to
	 * @param user Current user
	 */
	public void downloadDirectory(String selectedPath, String destinationPath, User user);
	
	/**
	 * Prints names of all files from given directory and it's sub directories
	 * 
	 * @param path Path of the chosen directory
	 */
	public void listAllFiles(String path);
	
	/**
	 * Prints names of files with given extension from given directory
	 * If @param extension equals "all" then function should print all files from given directory
	 * 
	 * @param path Path of the chosen directory
	 * @param extension Chosen extension for display
	 */
	public void listFiles(String path, String extension);
	
	/**
	 * Prints names of all directories from chosen path
	 * 
	 * @param path Path of the chosen directory
	 */
	public void listDirectories(String path);
	
	/**
	 * 
	 * @param path local directory path
	 * @param destination destination path
	 * @param user Userwho uploads directory.zip
	 */
	public void uploadZipDirectory(String path, String destination, User user);

}
