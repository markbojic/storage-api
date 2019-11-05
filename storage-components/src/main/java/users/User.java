package users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;

public class User implements AdminOps {

	private String username;
	private String password;
	private boolean admin = false; // false po defaultu pa za admina setujemo na true
	private boolean[] privileges;
	// niz odredjuje koje privilegije ima korisnik
	// [create, delete, upload, download]
	// svi korisnici mogu da vide sadrzaj i rade listfiles...

	/**
	 * Constructor for the user.
	 * 
	 * @param username   Username of the user
	 * @param password   Password of the user
	 * @param privileges User's privileges for creating, deleting, uploading and
	 *                   downloading
	 */
	public User(String username, String password, boolean[] privileges) {
		super();
		this.username = username;
		this.password = password;
		this.privileges = privileges;
	}

	/**
	 * Gets username of the user
	 * 
	 * @return Username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets password of the user
	 * 
	 * @return Password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Checks if user is admin
	 * 
	 * @return Returns true if user is admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Used for setting user as admin of the storage
	 * 
	 * @param admin True if user is admin
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * Gets privileges of the user
	 * 
	 * @return List of user's privileges
	 */
	public boolean[] getPrivileges() {
		return privileges;
	}

	/**
	 * Used for setting user's privileges
	 * 
	 * @param privileges List of user's privileges
	 */
	public void setPrivileges(boolean[] privileges) {
		this.privileges = privileges;
	}

	/**
	 * Creates new user with given username, password and privileges for operations.
	 * 
	 * @param username Username of the user you wish to create
	 * @param password Password of the user you wish to create
	 * @param privs    User's privileges for creating, deleting, uploading and
	 *                 downloading
	 * @param root     Root directory of the storage
	 */
	@Override
	public void createUser(String username, String password, boolean[] privs, String root, String token) {
		if (isAdmin()) {
			if (token.equalsIgnoreCase((""))) {
				if (username != null && password != null && privs.length == 4) {
					File file = new File(root + "\\accounts.log");
					boolean exists = false;
					// Check if user already exists
					try {
						Stream<String> lines = Files.lines(file.toPath());
						if ((lines).anyMatch(line -> line.contains(username))) {
							exists = true;
						}
						lines.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (!exists) {
						try {
							FileOutputStream fos = new FileOutputStream(file, true); // Set to true for append mode
							BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
							try {
								bw.write(username + "/" + password + "/" + privs[0] + "/" + privs[1] + "/" + privs[2]
										+ "/" + privs[3]);
								bw.newLine();
								bw.close();
								System.out.println("User " + username + " created!");
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("User with given username already exists!");
					}
				} else {
					System.out.println("Error! Wrong input!");
				}
			} else {
				if (username != null && password != null && privs.length == 4) {

					DbxRequestConfig config = DbxRequestConfig.newBuilder(this.getUsername()).build();
					DbxClientV2 client = new DbxClientV2(config, token);
					ListFolderResult result;
					File c = new File("src" + "/" + "accounts.log");
					try {
						c.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					OutputStream outputStream;
					//boolean exists = false;
					try {
						result = client.files().listFolder(root);
						while (true) {
							for (Metadata metadata : result.getEntries()) {
								if (metadata instanceof FileMetadata && metadata.getName().equals("accounts.log")) {
									outputStream = new FileOutputStream(c);
									metadata = client.files().downloadBuilder(root + "/accounts.log").download(outputStream);// Kopija
																											// accounts.log
																											// je u src
									outputStream.close();
									BufferedReader reader;
									try {
										reader = new BufferedReader(new FileReader(c.getAbsoluteFile()));
										String line = reader.readLine();
										while (line != null) {
											System.out.println(line);
											String splitter[] = line.split("/");
											if(splitter[0].equalsIgnoreCase(username)) {
												System.out.println("Username taken!");
												Files.deleteIfExists(Paths.get(c.getAbsolutePath()));
												return;
											}
											line = reader.readLine();
										}
										reader.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							if (!result.getHasMore()) {
								break;
							}
							result = client.files().listFolderContinue(result.getCursor());
						}
					} catch (ListFolderErrorException e) {
						e.printStackTrace();
					} catch (DbxException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					try {
						FileOutputStream fos = new FileOutputStream(c, true); // Set to true for append mode
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
						try {
							bw.write(username + "/" + password + "/" + privs[0] + "/" + privs[1] + "/" + privs[2] + "/"
									+ privs[3]);
							bw.newLine();
							bw.close();
							fos.close();
							rewriteAccountLog(c, root, token);
							System.out.println("User " + username + " created!");
							PrintWriter writer;
							try {
								writer = new PrintWriter(c);
								writer.print("");
								writer.close();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

				} else {
					System.out.println("Worng user input!");

				}

			}
		} else {
			System.out.println("Only admin can create new users!");
		}
	}

	/**
	 * Deletes user with given username.
	 * 
	 * @param username Username of the user you wish to delete
	 * @param root     Root directory of the storage
	 */
	@Override
	public void deleteUser(String username, String root,String token) {
		if (isAdmin()) {
			if(token.equalsIgnoreCase("")) {
			File file = new File(root + "\\accounts.log");
			List<String> newContent;
			try {
				newContent = Files.lines(file.toPath()).filter(line -> !line.contains(username))
						.collect(Collectors.toList());
				Files.write(file.toPath(), newContent, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
				System.out.println("User " + username + " deleted!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error while deleting user..");
			}

		}
			else {
				if (username != null && password != null) {

					DbxRequestConfig config = DbxRequestConfig.newBuilder(this.getUsername()).build();
					DbxClientV2 client = new DbxClientV2(config, token);
					ListFolderResult result;
					File c = new File("src" + "/" + "accounts.log");
					try {
						c.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					OutputStream outputStream;
					boolean exists = false;
					try {
						result = client.files().listFolder(root);
						while (true) {
							for (Metadata metadata : result.getEntries()) {
								if (metadata instanceof FileMetadata && metadata.getName().equals("accounts.log")) {
									outputStream = new FileOutputStream(c);
									metadata = client.files().downloadBuilder(root + "/accounts.log").download(outputStream);// Kopija
																											// accounts.log
																											// je u src
									outputStream.close();
									BufferedReader reader;
									try {
										reader = new BufferedReader(new FileReader(c.getAbsoluteFile()));
										String line = reader.readLine();
										while (line != null) {
											String splitter[] = line.split("/");
											if(splitter[0].equalsIgnoreCase(username)) {
												exists = true;
											}
											line = reader.readLine();
										}
										reader.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
									
								}

							}
							if (!result.getHasMore()) {
								break;
							}
							result = client.files().listFolderContinue(result.getCursor());
						}
					} catch (ListFolderErrorException e) {
						e.printStackTrace();
					} catch (DbxException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					if(exists == false)
					{
						System.out.println("No user with that name.");
						return;
					}
						List<String> newContent;
						try {
							newContent = Files.lines(c.toPath()).filter(line -> !line.contains(username))
									.collect(Collectors.toList());
							Files.write(c.toPath(), newContent, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
							rewriteAccountLog(c, root, token);
							PrintWriter writer;
							try {
								writer = new PrintWriter(c);
								writer.print("");
								writer.close();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							System.out.println("User " + username + " deleted!");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					System.out.println("Worng user input!");

				}

			}
			} else {
			System.out.println("Only admin can delete users!");
		}
	}

	/**
	 * Displays a list of all users.
	 * 
	 * @param root Root directory of the storage
	 */
	@Override
	public void listAllUsers(String root, String token) {
		if (isAdmin()) {
			if(token.equalsIgnoreCase("")) {
			try (BufferedReader br = new BufferedReader(new FileReader(root + "\\accounts.log"))) {
				String line = null;
				System.out.println("---------");
				System.out.println("Users:");
				while ((line = br.readLine()) != null) {
					System.out.println(line.substring(0, line.indexOf("/")));
				}
				System.out.println("---------");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			else {
				DbxRequestConfig config = DbxRequestConfig.newBuilder(this.getUsername()).build();
				DbxClientV2 client = new DbxClientV2(config, token);
				ListFolderResult result;
				File c = new File("src" + "/" + "accounts.log");
				try {
					c.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				OutputStream outputStream;
				try {
					result = client.files().listFolder(root);
					while (true) {
						for (Metadata metadata : result.getEntries()) {
							if (metadata instanceof FileMetadata && metadata.getName().equals("accounts.log")) {
								outputStream = new FileOutputStream(c);
								metadata = client.files().downloadBuilder(root + "/accounts.log").download(outputStream);// Kopija
																										// accounts.log
																										// je u src
								outputStream.close();
								BufferedReader reader;
								try {
									reader = new BufferedReader(new FileReader(c.getAbsoluteFile()));
									String line = reader.readLine();
									while (line != null) {
										System.out.println(line);
										line = reader.readLine();
									}
									reader.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}
						if (!result.getHasMore()) {
							break;
						}
						result = client.files().listFolderContinue(result.getCursor());
					}
				} catch (ListFolderErrorException e) {
					e.printStackTrace();
				} catch (DbxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				PrintWriter writer;
				try {
					writer = new PrintWriter(c);
					writer.print("");
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				}
		} else {
			System.out.println("Only admin can do this!");
		}
	}

	/**
	 * 
	 * @param newFile new file to replace the old account.log
	 * @param dest    root destination where the new file will be stored
	 * @param token   access token required to get to the storage
	 */
	public void rewriteAccountLog(File newFile, String dest, String token) {
		DbxRequestConfig config = DbxRequestConfig.newBuilder(this.getUsername()).build();
		DbxClientV2 client = new DbxClientV2(config, token);
		try {

			try (InputStream in = new FileInputStream(newFile)) {
				@SuppressWarnings("unused")
				FileMetadata metadata = client.files().uploadBuilder(dest + "/" + "accounts.log")
						.withMode(WriteMode.OVERWRITE).uploadAndFinish(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
}
