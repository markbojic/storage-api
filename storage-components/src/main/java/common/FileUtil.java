package common;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

/**
 * This Java class is used to compress a selected file into a .zip archive.
 *
 */

public class FileUtil {

	/**
	 * 
	 * @param filePath    - Name of the file to zip
	 * @param destination - Destination of the zip archive
	 * @param zipName     - Name of the archive
	 */
	public static void zipFile(String filePath, String destination, String zipName) {
		try {
			File file = new File(filePath);
			File zipFile = new File(destination + File.separator + zipName + ".zip");
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			zos.putNextEntry(new ZipEntry(file.getName()));

			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			zos.write(bytes, 0, bytes.length);
			zos.closeEntry();
			zos.close();

		} catch (FileNotFoundException ex) {
			System.err.format("The file %s does not exist", filePath);
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
		}
	}

	/**
	 * 
	 * @param filePaths   - Array of files to be archived
	 * @param destination - Destination path of the zip archive
	 * @param zipName     - Name of the archive
	 */
	public static void zipFiles(String[] filePaths, String destination, String zipName) {
		try {
			String zipFileName = destination + File.separator + zipName + ".zip";
			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String aFile : filePaths) {
				zos.putNextEntry(new ZipEntry(new File(aFile).getName()));

				byte[] bytes = Files.readAllBytes(Paths.get(aFile));
				zos.write(bytes, 0, bytes.length);
				zos.closeEntry();
			}

			zos.close();

		} catch (FileNotFoundException ex) {
			System.err.println("A file does not exist: " + ex);
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
		}
	}

	/**
	 * This method zips the directory
	 * 
	 * @param dir
	 * @param zipDirName
	 */

	private static List<String> filesListInDir = new ArrayList<String>();

	public static void zipDirectory(File dir, String destination, String zipDirName) {
		try {

			populateFilesList(dir);
			FileOutputStream fos = new FileOutputStream(destination + File.separator + zipDirName + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String filePath : filesListInDir) {
				System.out.println("Zipping " + filePath);
				ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		filesListInDir.clear();
	}

	/**
	 * This method populates all the files in a directory to a List
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private static void populateFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile())
				filesListInDir.add(file.getAbsolutePath());
			else
				populateFilesList(file);
		}
	}

}