package ui;

import util.ImageUtils;
import service.CardButtonTestHandler;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import dao.SessionDAO;
import util.SessionManager;
import java.awt.*;
import java.awt.event.*;
import java.util.List; 
import java.util.ArrayList; 


public class TestModeScreen extends JFrame {
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar; 
	JMenu menu; 
	JMenuItem homeItem, historyItem, logoutItem; 

	//private JTextField preFlopEquity, postFlopEquity, turnEquity, riverEquity;
	private JTextArea preFlopEquityArea, flopEquityArea, turnEquityArea, riverEquityArea;
	private JButton[] yourCards, flopCards, turnCard, riverCard;
	private CardButtonTestHandler testHandler;
	private SessionDAO sessionDAO; 
	private int numSession = 0; 
	private int score = 0; 
	
	
	public TestModeScreen() {
        setTitle("Test Mode");
        setSize(700, 800);
        setLayout(new BorderLayout());
        setResizable(false);
        
        this.sessionDAO = new SessionDAO();
        
        initializeMenu();
        initializeTopPanel();
        initializeCardPanel();
        initializeEquityPanel();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        testHandler = new CardButtonTestHandler(preFlopEquityArea, flopEquityArea, turnEquityArea, riverEquityArea);
        testHandler.setupCardListeners(yourCards, flopCards, turnCard, riverCard);
    }
	
		// Add menu items (logout, introduction, etc.) here
		private void initializeMenu() {
			JMenuBar menuBar = new JMenuBar();
	        JMenu menu = new JMenu("Menu");
	        homeItem = new JMenuItem("Home");
	        historyItem = new JMenuItem("History");
	        logoutItem = new JMenuItem("Logout");
	        
	        
	        // Adding menu items to menu
	        menu.add(homeItem);
	        menu.add(historyItem);
	        menu.add(logoutItem);
	        
	        // Adding menu to menu bar
	        menuBar.add(menu);
	        
	        // Set the menu bar to the frame
	        setJMenuBar(menuBar);
	        
	        // Adding action listeners to menu items
	        homeItem.addActionListener(new ActionListener() {
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	                new HomeScreen();
	            }
	        });
	        logoutItem.addActionListener(new ActionListener() {
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	        		SessionManager.logout();
	                dispose();
	                new LoginScreen();
	            }
	        });
	        historyItem.addActionListener(new ActionListener() {
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	        		if (SessionManager.isUserLoggedIn()) {
	            		int userId = SessionManager.getUserId();
	            		String userName = SessionManager.getUserName();
	            		String userEmail = SessionManager.getUserEmail(); 
	            		
	            		dispose();
	                	new HistoryScreen(userId, userName, userEmail);
	            	}
	            }
	        });
		}
		
		// Add Top panel
		private void initializeTopPanel() {
			// Create a top panel for the start button 
	        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        JButton startResetButton = new JButton("Start/Reset");
	        startResetButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Handle the button click event here
	                // For flap these two cards in "Your cards"
	            	//preFlopEquityArea.setText("two cards flaped");
	            	resetCardImages();
	            	testHandler.reset();

	            }
	        });
	        topPanel.add(startResetButton);
	        
	        // Top panel for the reset button
	        JButton saveButton = new JButton("Save");
	        saveButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {	
	            	int userId = SessionManager.getUserId(); 
	            	if (SessionManager.isUserLoggedIn()) {
	            		boolean saved = sessionDAO.saveSession(userId, score);
	            		if (saved) {
	            			JOptionPane.showMessageDialog(null, "Session saved successful!");
	    	            	System.out.println("Session: " + numSession + ", Score: " + score);
		                    System.out.println("Session saved to database successfully.");
		                    score = 0; 
		                    numSession ++ ; 
		        
		                    resetCardImages();
		                    testHandler.reset();
		                    
		                } else {
		                    System.out.println("Failed to save session to the database.");
		                }
	            	}else {
	            		System.out.println("No user logged in.");
	            		
	            	}
	            }
	        });
	        topPanel.add(saveButton);
	        
	        add(topPanel, BorderLayout.NORTH); 
		}
		
		private void initializeCardPanel() {
	        // Implementation for card panel
			// Create card panel 
	        JPanel cardPanel = new JPanel(new GridLayout(4, 1));
	        yourCards = createCardRow("Your Cards", 2);
	        flopCards = createCardRow("Flop", 3);
	        turnCard = createCardRow("Turn", 1);
	        riverCard = createCardRow("River", 1);
	        
	        cardPanel.add(createCardPanel(yourCards, "Your Cards"));
	        cardPanel.add(createCardPanel(flopCards, "Flop"));
	        cardPanel.add(createCardPanel(turnCard, "Turn"));
	        cardPanel.add(createCardPanel(riverCard, "River"));
	        add(cardPanel, BorderLayout.WEST);			
	    }
		
		private void initializeEquityPanel() {
	        JPanel equityPanel = new JPanel(new GridLayout(4, 1));
	        preFlopEquityArea = createEditableEquityArea();
	        flopEquityArea = createEditableEquityArea();
	        turnEquityArea = createEditableEquityArea();
	        riverEquityArea = createEditableEquityArea();
	        
	        equityPanel.add(createEquityPanel("Pre-flop Equity:", preFlopEquityArea));
	        equityPanel.add(createEquityPanel("Flop to River's Equity:", flopEquityArea));
	        equityPanel.add(createEquityPanel("Turn to River's Equity:", turnEquityArea));
	        equityPanel.add(createEquityPanel("Best Five Cards:", riverEquityArea));
	        add(equityPanel, BorderLayout.CENTER);
	    }
		
		private void resetCardImages() {
		    ImageIcon cardBackIcon = new ImageIcon(getClass().getResource("/pokerImages/cardBack.png"));
		    ImageIcon resizedIcon = ImageUtils.resizeIcon(cardBackIcon, 72, 96);  // Assuming these are the dimensions you want for the cards

		    // Reset images for 'Your Cards'
		    for (JButton card : yourCards) {
		        card.setIcon(resizedIcon);
		        card.setEnabled(true);
		    }

		    // Reset images for 'Flop Cards'
		    for (JButton card : flopCards) {
		        card.setIcon(resizedIcon);
		        card.setEnabled(true);
		    }

		    // Reset image for 'Turn Card'
		    for (JButton card : turnCard) {
		        card.setIcon(resizedIcon);
		        card.setEnabled(true);
		    }

		    // Reset image for 'River Card'
		    for (JButton card : riverCard) {
		        card.setIcon(resizedIcon);
		        card.setEnabled(true);
		    }
		}
		
		private JPanel createCardPanel(JButton[] cards, String label) {
		    JPanel panel = new JPanel(new BorderLayout());
		    JLabel lbl = new JLabel(label, SwingConstants.CENTER); // Center-align the label text
		    panel.add(lbl, BorderLayout.NORTH);
		    JPanel cardsLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // 10 is the gap between cards
		    for (JButton card : cards) {
		        cardsLayout.add(card);
		    }
		    panel.add(cardsLayout, BorderLayout.CENTER);
		    return panel;
		}
		
		private JButton[] createCardRow(String label, int numberOfCards) {
	        JButton[] cards = new JButton[numberOfCards];
	        for (int i = 0; i < numberOfCards; i++) {
	        	// Set card back icon here
	            cards[i] = new JButton();  
	            ImageIcon cardBackIcon = new ImageIcon(getClass().getResource("/pokerImages/cardBack.png"));
	            ImageIcon resizedIcon = ImageUtils.resizeIcon(cardBackIcon, 72, 96);
	            cards[i].setIcon(resizedIcon);
	            cards[i].setPreferredSize(new Dimension(72, 96));
	        }
	        return cards;
	    }
		
		private JPanel createEquityPanel(String label, JTextComponent equityComponent) {
		    JPanel panel = new JPanel(new BorderLayout());
		    JLabel lbl = new JLabel(label);
		    panel.add(lbl, BorderLayout.NORTH);
		    equityComponent.setPreferredSize(new Dimension(140, 40)); // Set preferred size for equity display area
		    panel.add(equityComponent, BorderLayout.CENTER);
		    return panel;
		}
		
		private JTextArea createEditableEquityArea() {
	        JTextArea equityArea = new JTextArea(2, 10);
	        equityArea.setEditable(true);  // Allow users to edit
	        equityArea.setLineWrap(true);
	        equityArea.setWrapStyleWord(true);
	        equityArea.addKeyListener(new KeyAdapter() {
	            public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                    e.consume(); // prevent text area from handling the event
	                    checkAnswer(equityArea); // Method to check the answer
	                }
	            }
	        });
	        return equityArea;
	    }

		//flopEquityArea, turnEquityArea, riverEquityArea
	    private void checkAnswer(JTextArea equityArea) {
	        // Dummy implementation, replace with your logic to check the answer
	        String userAnswer = equityArea.getText().trim();
	        String correctAnswer = "";
	        String description = "";
	        System.out.println("userAnswer: " + userAnswer);
	        if (equityArea == preFlopEquityArea) {
	        	 correctAnswer = testHandler.getLastPreFlopEquity();
	        	 description = testHandler.getLastPreFlopDescription();
	        	 System.out.println("correctAnswer: " + correctAnswer + "%");
	        }
	        if (equityArea == flopEquityArea) {
	        	correctAnswer = testHandler.getLastFlopEquity();
	        	description = testHandler.getLastFlopDescription();
	        	System.out.println("correctAnswer: " + correctAnswer + "%");
	        }
	        if (equityArea == turnEquityArea) {
	        	correctAnswer = testHandler.getLastTurnEquity();
	        	description = testHandler.getLastTurnDescription();
	        	System.out.println("correctAnswer: " + correctAnswer + "%");
	        }
	        if (equityArea == riverEquityArea) {
	        	correctAnswer = testHandler.getLastFiveCards();
	        	System.out.println("correctAnswer: " + correctAnswer);
	        }

	        // 
	        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
	        	score ++; 
	        	equityArea.setText("you are correct!\n" + description + "\n" + "Score +1");	        	
	        }else {
	        	equityArea.setText("It is not correct. The correct answer is: " + correctAnswer + "\n" + description);
	        }
	    }
	    
	    public int getSession() {
	    	return numSession; 
	    }
	    
	    public int getScore() {
	    	return score; 
	    }
	    
	    public List<Object[]> getSessionScoreData(){
	    	List<Object[]> sessionData = new ArrayList<>();
	    	sessionData.add(new Object[]{numSession, score});
	        return sessionData;
	    }
	    
//	    public static void main(String[] args) {
//	        SwingUtilities.invokeLater(() -> new TestModeScreen());
//	    }

}
