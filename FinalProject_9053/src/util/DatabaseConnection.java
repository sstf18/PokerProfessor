package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the JDBC driver
                Class.forName("org.sqlite.JDBC");
                // Create a connection to the database
                connection = DriverManager.getConnection("jdbc:sqlite:javabook.db");
                System.out.println("database connected");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return connection;
    }
}