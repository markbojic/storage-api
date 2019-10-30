package specs;

public interface FileManipulation {
	
	/**
	 * Creates new file on given path.
	 * 
	 * @param name File's name
	 * @param path Path of the directory where file will be stored
	 */
	public void createFile(String name, String path);

	/**
	 * Deletes file on given path.
	 * 
	 * @param path
	 */
	public void deleteFile(String path);
	
	/**
	 * Uploads file from selected path to desired path on storage.
	 * 
	 * @param selectedPath Path of chosen file
	 * @param destinationPath Path where file will be stored
	 */
	public void uploadFile(String selectedPath, String destinationPath);
	
	/**
	 * Downloads file to given path.
	 * 
	 * @param selectedPath File's path on storage
	 * @param destinationPath Download file on this path
	 */
	public void downloadFile(String selectedPath, String destinationPath);

}
