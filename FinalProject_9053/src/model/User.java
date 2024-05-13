package model;

public class User {
	
	private final int userId; 
	private String userName; 
	private String password; 
	private String email; 
	
	// Constructor
    public User(int userId, String userName, String password, String email) {
    	this.userId = userId; 
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
    
 // Getters and setters    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public int getUserID() {
		// TODO Auto-generated method stub
		return userId;
	}
}
