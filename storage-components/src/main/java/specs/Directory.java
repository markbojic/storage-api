package specs;

public interface Directory {
	
	public void createDirectory(String name, String path);
	
	public void deleteDirectory(String path);
	
	public void uploadDirectory(String path);
	
	public void downloadDirectory(String path);
	
	public void listAllFiles();
	//Za listanje celog starage-a, mozda ne treba name
	
	public void listFiles(String name, String extension);
	//Za listanje fajlova sa specificiranom ext ili ukoliko je prazno listaj sve fajlove sa zadatog direktorijuma.
	
	public void listDirectories(String name);

}
