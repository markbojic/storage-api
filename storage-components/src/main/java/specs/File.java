package specs;

public interface File {
	
	public void createFile(String name, String path, String fileExtension);

	public void deleteFile(String path);
	
	public void uploadFile(String path, String FileExtension);
	
	public void downloadFile(String path);

}
