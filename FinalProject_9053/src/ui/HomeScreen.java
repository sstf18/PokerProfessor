package ui;

import javax.swing.*;

import util.SessionManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * This Screen will be a navigator 
 * it will lead the user either go to Learning mode or Test Mode
 * also, go to Game Intro, History page, Logout page...
 * **/
public class HomeScreen extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JButton learningButton, testingButton;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem introItem,historyItem,logoutItem;
    
    public HomeScreen() {
    	setTitle("Poker Professor - Home");
    	setSize(500, 500);
    	setResizable(false);
    	setLayout(null);
    	
    	// Buttons for Learning Mode and Testing Mode
        learningButton = new JButton("Learning Mode");
        testingButton = new JButton("Testing Mode");
        learningButton.setBounds(100, 100, 300, 50);
        testingButton.setBounds(100, 200, 300, 50);
        
        // Adding action listeners to buttons
        learningButton.addActionListener(this);
        testingButton.addActionListener(this);

        add(learningButton);
        add(testingButton);
        
        // Menu bar setup
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        introItem = new JMenuItem("Game Introduction");
        historyItem = new JMenuItem("History");
        logoutItem = new JMenuItem("Logout");
        
        
        // Adding menu items to menu
        menu.add(introItem);
        menu.add(historyItem);
        menu.add(logoutItem);
        
        
        // Adding menu to menu bar
        menuBar.add(menu);

        // Set the menu bar to the frame
        setJMenuBar(menuBar);
        
        // Adding action listeners to menu items
        historyItem.addActionListener(this);
        logoutItem.addActionListener(this);
        introItem.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    	
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle actions based on the source of the event
        if (e.getSource() == learningButton) {
        	
        	int userId = SessionManager.getUserId(); 
        	
        	System.out.println("current userID: " + userId);
        	this.dispose();
			new LearningModeScreen();
			//System.out.println("open learning mode");
        	
        } else if (e.getSource() == testingButton) {
        	this.dispose();
        	new TestModeScreen();
            // Switch to Testing Mode screen
        } else if (e.getSource() == logoutItem) {
            // Perform logout operation
        	SessionManager.logout();
            this.dispose(); // Closes the current window
            new LoginScreen(); // Opens Login Screen
        } else if (e.getSource() == introItem) {
        	this.dispose();
        	new GameIntroScreen();
            // Navigate to game introduction Page
        } else if (e.getSource() == historyItem) {
        	// Navigate to the history page
        	if (SessionManager.isUserLoggedIn()) {
        		int userId = SessionManager.getUserId();
        		String userName = SessionManager.getUserName();
        		String userEmail = SessionManager.getUserEmail(); 
        		System.out.println("Opening HistoryScreen for user: " + userName + " with email: " + userEmail);
        		this.dispose();
            	new HistoryScreen(userId, userName, userEmail);
        	}
        	
        }
    }
    
//    public static void main(String[] args) {
//    	new HomeScreen();
//    }

}
