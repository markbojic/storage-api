package specs;

public interface File {
	
	/**
	 * Creates new file with given extension on given path.
	 * 
	 * @param name File's name
	 * @param path File's path
	 * @param fileExtension File's extension
	 */
	public void createFile(String name, String path, String fileExtension);

	/**
	 * Deletes file on given path.
	 * 
	 * @param path
	 */
	public void deleteFile(String path);
	
	/**
	 * Uploads file form selected path to desired path on storage.
	 * 
	 * @param selectedPath Path of chosen file
	 * @param destinationPath Path where file will be stored
	 * @param fileExtension Chosen file's extension
	 */
	public void uploadFile(String selectedPath, String destinationPath, String fileExtension);
	
	/**
	 * Downloads file to given path.
	 * 
	 * @param selectedPath File's path on storage
	 * @param destinationPath Download file on this path
	 */
	public void downloadFile(String selectedPath, String destinationPath);

}
