package entity;

/**
 * @author User
 *
 */
public class ForumUser {
    private String email;
    private String username;
    private String password;
    

	public ForumUser(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public ForumUser(String username, String password) {
		this.username = username;
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}



}
