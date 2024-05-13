package util;

public class SessionManager {
	private static int userId = -1;
	private static String userName = "";
	private static String userEmail = "";
	
	public static void setUserDetails(int id, String name, String email) {
        userId = id;
        userName = name;
        userEmail = email;
    }

    public static int getUserId() {
        return userId;
    }
    
    public static String getUserName() {
        return userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static boolean isUserLoggedIn() {
        return userId != -1;
    }

    public static void logout() {
        userId = -1;
        userName = "";
        userEmail = "";
    }
}
