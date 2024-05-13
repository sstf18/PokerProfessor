package service;

import dao.UserDAO;
import model.User;

public class UserService {
	
	private UserDAO userDAO; 
	
	public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean register(String username, String password, String email) {
        if (userDAO.findByUsername(username) == null && userDAO.findByEmail(email) == null) {
            User newUser = new User(0, username, password, email);
            int userId = userDAO.registerUser(newUser);
            if (userId > 0) {
                System.out.println("User registered with ID: " + userId);
                return true;
            }
            return false; // Registration failed

        } else {
        	System.out.println("Username pr email already exists.");
            return false;
        }
    }
    
    public boolean authenticate(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
