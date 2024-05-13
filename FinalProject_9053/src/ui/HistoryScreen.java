package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.util.List;

import dao.SessionDAO;
import util.SessionManager;

public class HistoryScreen extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
    JMenu menu;
    JMenuItem homeItem, logoutItem; 
	private JLabel userNameLabel, userEmailLabel;
    private JTable historyTable;
    private JLabel totalScoreLabel;
    private String userName; 
    private String userEmail; 
    private static final int TRIES_PER_SESSION = 4; 
    
    public HistoryScreen(int userId, String userName, String userEmail) {
    	
    	this.userName = userName; 
    	this.userEmail = userEmail; 
    	
    	setTitle("User History");
        setSize(700, 800);
        setLayout(new BorderLayout());
        
        SessionDAO sessionDAO = new SessionDAO();
        
        List<Object[]> sessionDataList = sessionDAO.getUserSessions(userId);
        Object[][] data = new Object[sessionDataList.size()][]; 
        for (int i = 0; i < sessionDataList.size(); i++) {
            data[i] = sessionDataList.get(i);
        }
        
        initializeComponents();
        initializeHistoryTable(data);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);	
    }
    
    private void initializeComponents() {
    	// Menu bar setup
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        homeItem = new JMenuItem("Home");
        logoutItem = new JMenuItem("Logout");
        
        menu.add(homeItem);
        menu.add(logoutItem);
        menuBar.add(menu);       
        setJMenuBar(menuBar);
        
    	// Top Panel: User Information
        JPanel topPanel = new JPanel(new FlowLayout());
        userNameLabel = new JLabel("User: " + userName);
        userEmailLabel = new JLabel("Email: " + userEmail);
        topPanel.add(userNameLabel);
        topPanel.add(userEmailLabel);
        
        // Bottom Panel: Statistics
        JPanel bottomPanel = new JPanel(new FlowLayout());
        totalScoreLabel = new JLabel("Total Score: ......");
        bottomPanel.add(totalScoreLabel);
        
        // Adding panels to the frame
        add(topPanel, BorderLayout.NORTH);        
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Adding action listeners to menu items
        homeItem.addActionListener(this);
        logoutItem.addActionListener(this);
    	
    }

    private void initializeHistoryTable(Object[][] sessionData) {
        String[] columnNames = {"Session ID", "Score", "Date"};
        Object[][] data = new Object[sessionData.length][3];
        
        for (int i = 0; i < sessionData.length; i++) {
            data[i][0] = i + 1;  // Session ID
            data[i][1] = sessionData[i][1];  // Score
            data[i][2] = sessionData[i][2];	 // Date
        }
        
        historyTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);
        
        displayScore(data);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle actions based on the source of the event
        if (e.getSource() == logoutItem) {
        	SessionManager.logout();
        	this.dispose();
			new LoginScreen();
			//System.out.println("open learning mode");
        	
        } else if (e.getSource() == homeItem) {
        	this.dispose();
        	new HomeScreen();
        } 
    }
    
    private void displayScore(Object[][] data) {
    	int totalScore = 0; 
    	for (Object[] session : data) {
    		totalScore += Integer.parseInt(session[1].toString());
    	}
    	int totalSessions = data.length; 
    	int totalTries = totalSessions * TRIES_PER_SESSION; 
    	totalScoreLabel.setText("Total Score/Total Tries ( " + totalScore + "/" + totalTries + " )");
    }
    
//    public static void main(String[] args) {
//        new HistoryScreen(1, "John Doe", "john.doe@example.com");
//    }
}
