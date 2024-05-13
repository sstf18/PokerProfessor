package ui;

import util.ImageUtils;
import util.SessionManager;
import service.CardButtonLearningHandler;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class LearningModeScreen extends JFrame {
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar; 
	JMenu menu; 
	JMenuItem homeItem, historyItem, logoutItem; 

	//private JTextField preFlopEquity, postFlopEquity, turnEquity, riverEquity;
	private JTextArea preFlopEquityArea, flopEquityArea, turnEquityArea, riverEquityArea;
	private JButton[] yourCards, flopCards, turnCard, riverCard;
	private CardButtonLearningHandler learningHandler;
	
	public LearningModeScreen() {
        setTitle("Learning Mode");
        setSize(700, 800);
        setLayout(new BorderLayout());
        setResizable(false);
        
        initializeMenu();
        initializeTopPanel();
        initializeCardPanel();
        initializeEquityPanel();
        
        learningHandler = new CardButtonLearningHandler(preFlopEquityArea, flopEquityArea, turnEquityArea, riverEquityArea);
        learningHandler.setupCardListeners(yourCards, flopCards, turnCard, riverCard);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
        logoutItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		SessionManager.logout();
                dispose();
                new LoginScreen();
            }
        });
        
	}
	
	// Add Top panel
	private void initializeTopPanel() {
		// Create a top panel for the start button 
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
        // Top panel for the start/reset button
        JButton startResetButton = new JButton("Start/Reset");
        startResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	resetCardImages();
                learningHandler.reset();
                
                preFlopEquityArea.setText("");
                flopEquityArea.setText("");
                turnEquityArea.setText("");
                riverEquityArea.setText("");
                
            }
        });
        topPanel.add(startResetButton);
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
        // Implementation for equity panel
    	// Create equity panel 
        JPanel equityPanel = new JPanel(new GridLayout(4, 1));
        preFlopEquityArea = createEquityArea();
        flopEquityArea = createEquityArea();
        turnEquityArea = createEquityArea();
        riverEquityArea = createEquityArea();
                         
        equityPanel.add(createEquityPanel("Pre-flop Equity:", preFlopEquityArea));
        equityPanel.add(createEquityPanel("Flop to River Equity:", flopEquityArea));
        equityPanel.add(createEquityPanel("Turn to River Equity:", turnEquityArea));
        equityPanel.add(createEquityPanel("Best Five Cards:", riverEquityArea));
        add(equityPanel, BorderLayout.CENTER);	
    }
	
	// Reset card back for all cards 
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
	    JLabel lbl = new JLabel(label, SwingConstants.CENTER); 
	    panel.add(lbl, BorderLayout.NORTH);
	    JPanel cardsLayout = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); 
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
	    equityComponent.setPreferredSize(new Dimension(140, 40)); 
	    panel.add(equityComponent, BorderLayout.CENTER);
	    return panel;
	}
	
	private JTextArea createEquityArea() {
		JTextArea equityArea = new JTextArea(2,10);
		equityArea.setEditable(false); 
		equityArea.setLineWrap(true);
		equityArea.setWrapStyleWord(true);
		return equityArea; 
		
	}

//	public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new LearningModeScreen());
//    }	
}
