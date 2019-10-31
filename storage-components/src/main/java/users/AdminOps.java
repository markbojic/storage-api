package users;

public interface AdminOps {
	
	public void createUser(String username, String password, boolean[] privs);
		// proveri da li admin ovo radi posto samo on moze
		// doda u fajl sa userima

	public void deleteUser(String username);
		// proveri da li je admin ulogovan
		// nadje u fajlu pa obrise
	
	public void listAllUsers();
		// proveri da li je adm ulogovan
	
	// ovo radi samo pri prvom setovanju storage-a i stavi u neki fajl koji sacuva u storage-u
	public void forbiddenExtensions(String[] extensions);

}
