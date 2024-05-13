package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseConnection;

public class SessionDAO {
	
	private Connection connection;

    public SessionDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
	public boolean saveSession(int userId, int score) {
	    String sql = "INSERT INTO sessions (user_id, score) VALUES (?, ?)";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, userId);
	        pstmt.setInt(2, score);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public List<Object[]> getUserSessions(int userId) {
	    List<Object[]> sessions = new ArrayList<>();
	    String sql = "SELECT session_id, score, session_date FROM sessions WHERE user_id = ? ORDER BY session_id";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, userId);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            sessions.add(new Object[]{rs.getInt("session_id"), rs.getInt("score"), rs.getString("session_date")});
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return sessions;
	}

	
}
