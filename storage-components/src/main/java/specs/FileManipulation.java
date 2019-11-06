package specs;

import users.AbstractUser;

public interface FileManipulation {
	
	/**
	 * Creates new file on given path.
	 * 
	 * @param name File's name
	 * @param path Path of the directory where file will be stored
	 * @param user Current user
	 */
	public void createFile(String name, String path, AbstractUser user);

	/**
	 * Deletes file on given path.
	 * 
	 * @param path
	 * @param user Current user
	 */
	public void deleteFile(String path, AbstractUser user);
	
	/**
	 * Uploads file from selected path to desired path on storage.
	 * 
	 * @param selectedPath Path of chosen file
	 * @param destinationPath Path where file will be stored
	 * @param user Current user
	 */
	public void uploadFile(String selectedPath, String destinationPath, AbstractUser user);
	
	/**
	 * Downloads file to given path.
	 * 
	 * @param selectedPath File's path on storage
	 * @param destinationPath Download file on this path
	 * @param user Current user
	 */
	public void downloadFile(String selectedPath, String destinationPath, AbstractUser user);
	
	/**
	 * Zips given files and uploads them.
	 * 
	 * @param filePaths Array of paths to files waiting to be zipped
	 * @param destinationPath Path to where the files will be stored once zipped
	 * @param zipName Name of the zip archive
	 * @param user Current user
	 */
	public void uploadMultipleFilesZip(String[] filePaths, String destinationPath, String zipName, AbstractUser user);

}
