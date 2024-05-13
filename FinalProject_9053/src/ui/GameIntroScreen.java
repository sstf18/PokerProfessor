package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class GameIntroScreen extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar; 
	JMenu menu; 
	JMenuItem homeItem;

	public GameIntroScreen() {
		setTitle("Poker Professor -Intro");
		setSize(700, 800);
		setResizable(false);
		JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        homeItem = new JMenuItem("Home");
        
        menu.add(homeItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
		
		 // Create the content panel with a vertical BoxLayout
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
  
        JScrollPane scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        //scrollPane.setBorder(null);
        
        JLabel topImageLabel = createImageLabel("PokerTexasHold'em.jpeg", 650, 290);
        mainPanel.add(topImageLabel);

        // Adding title
        JLabel titleLabel = new JLabel("Texas Hold'em");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align text to the left
        mainPanel.add(titleLabel);
        
         
        // Adding the body text
        JTextArea basicsText = createTextArea(
        		"Basics\n"
        		+ "Players: Typically 2-10 per table.\n"
        		+ "Cards: A standard deck of 52 cards is used.\n"
        		+ "Objective: The goal is to make the best five-card poker hand using any combination of the two private cards dealt to each player and the five community cards dealt on the table.\n" 
        		+ "\n"
        		);
        mainPanel.add(basicsText);
        
        JLabel flopTurnRiverLabel = createImageLabel("FlopTurnRiver.png", 500, 250);
        mainPanel.add(flopTurnRiverLabel);
        
               
        JTextArea gameplayText = createTextArea(
        		"\n"
        		+ "Gameplay\n"
                + "Blinds: The game begins with two players posting \"blinds\" — forced bets to start the action. The player immediately to the dealer's left posts the \"small blind,\" and the next player posts the \"big blind.\"\n"
                + "Hole Cards: Each player is dealt two private cards, known as \"hole cards,\" that belong to them alone.\n"
                + "Betting Rounds:\n"
                + "    -Pre-Flop: After seeing their hole cards, players can call the big blind, raise, or fold. Play proceeds clockwise.\n"
                + "    -The Flop: Three community cards are dealt face up on the board. Another round of betting occurs, starting with the player to the dealer's left.\n"
                + "    -The Turn: A fourth community card is dealt. This is followed by another round of betting.\n"
                + "    -The River: The fifth and final community card is dealt. This leads to the final round of betting.\n"
                + "Showdown: If there is more than one player remaining after the final betting round, these players reveal their cards. The player with the best five-card hand wins the pot.\n"  		
        		+ "\n"
        		);
        mainPanel.add(gameplayText);  
        
        JLabel pokerHandsImageLabel = createImageLabel("PokerHandCheatSheet.png", 650, 700);
        mainPanel.add(pokerHandsImageLabel);
        
        JTextArea handRankingText = createTextArea( 
        		"Hand Rankings\n"
        		+ "From highest to lowest, the hand rankings in Texas Hold'em are:\n"
        		+ "1. Royal Flush: Ten, Jack, Queen, King, Ace, all of the same suit.\n"
        		+ "2. Straight Flush: Five cards in a sequence, all in the same suit.\n"
        		+ "3. Four of a Kind: Four cards of the same rank.\n"
        		+ "4. Full House: Three cards of one rank and two cards of another rank.\n"
        		+ "5. Flush: Any five cards of the same suit, not in sequence.\n"
        		+ "6. Straight: Five cards in a sequence, but not of the same suit.\n"
        		+ "7. Three of a Kind: Three cards of the same rank.\n"
        		+ "8. Two Pair: Two different pairs.\n"
        		+ "9. Pair: Two cards of the same rank.\n"
        		+ "10. High Card: When you haven't made any of the hands above, the highest card plays.\n"
                + "\n"
        		);
        mainPanel.add(handRankingText);	
        
        JTextArea StrategyText = createTextArea(
        		"Strategy\n"
        		+ "Playing Texas Hold'em involves critical thinking and strategy, particularly concerning when to bet, call, fold, or raise based on the strength of your hand and your perception of other players' hands. Poker strategy also includes understanding probabilities, reading other players' actions (and bluffs), and managing your betting pattern.\n"
        		+"\n"
        		);
        mainPanel.add(StrategyText);
        
        // Adding 42 rules in title
        JLabel rule_42_Label = new JLabel("Rules of 4 and 2");
        rule_42_Label.setFont(new Font("Serif", Font.BOLD, 50));
        rule_42_Label.setAlignmentX(Component.CENTER_ALIGNMENT); // Align text to the left
        mainPanel.add(rule_42_Label);
        
        JLabel ruleOf42ImageLabel = createImageLabel("RuleOf42Icon.png", 200, 200);
        mainPanel.add(ruleOf42ImageLabel);

   
        JTextArea rule42_intro_Text = createTextArea(
        		"The \"Rule of 4 and 2\" is a quick, handy calculation method used in Texas Hold'em poker to estimate the probability of completing a drawing hand, such as a flush or a straight, by the river. It helps players make decisions about whether to call, fold, or raise based on the odds of hitting their draw.\n"       	
        		+ "\n"
        		);
        mainPanel.add(rule42_intro_Text);
        
        JLabel pokerOutsImageLabel = createImageLabel("PokerOuts.png", 400, 200);
        mainPanel.add(pokerOutsImageLabel);
        
       
        JTextArea rule42_rule_Text = createTextArea(
        		"Rule\n"
        		+ "1. After the Flop (two cards to come): Multiply the number of outs you have by 4 to estimate the percentage chance of completing your draw by the river. Outs are the cards remaining in the deck that will improve your hand to a potential winner.\n"
        		+ "\n"
        		+ "2. After the Turn (one card to come): Multiply the number of outs by 2 to estimate the percentage chance of completing your draw by the river."        		
        		+ "\n"
        		);
        mainPanel.add(rule42_rule_Text); 
        
        JLabel rule_42_example_Label = new JLabel("Exmaple: ");
        rule_42_example_Label.setFont(new Font("Serif", Font.BOLD, 30));
        rule_42_example_Label.setAlignmentX(Component.CENTER_ALIGNMENT); // Align text to the left
        mainPanel.add(rule_42_example_Label);
        
        JLabel flushDrawOutsImageLabel = createImageLabel("FlushDrawOuts.jpeg", 400, 300);
        mainPanel.add(flushDrawOutsImageLabel);
        
        JTextArea rule42_example_Text = createTextArea(
        		"\n"
        		+ "There are 13 Diamond in a deck, and four are already visible to you (including those on the board and in your hand). So, there are 9 Diamonds (outs) remaining.\n"
        		+ "\n"        		
        		+ "Apply the Rule of 4 and 2: \n"
        		+ "1. After the Flop: Multiply your outs (9) by 4.  9*4 = 36. There's approximately a 36% chance of making your flush by the river.\n"
        		+ "2. After the Turn: If the flush doesn't hit on the turn, multiply the outs (9) by 2. 9*2 = 18. There's now about an 18% chance of hitting your flush on the river.\n"
        		+ "\n"
        		);
        mainPanel.add(rule42_example_Text);
        
        JLabel moreResource_Label = new JLabel("More Resource: ");
        moreResource_Label.setFont(new Font("Serif", Font.BOLD, 50));
        moreResource_Label.setAlignmentX(Component.CENTER_ALIGNMENT); // Align text to the left
        mainPanel.add(moreResource_Label);
        
        JLabel twoCardRankImageLabel = createImageLabel("TwoCardRank.png", 500, 600);
        mainPanel.add(twoCardRankImageLabel);
        
        JTextArea handsRanking_Text = createTextArea(
        		"\n"
        		+ "This shows hand rankings (not EV!) as a percentile of the 1326 possible initial hands. So offsuited hands are counted 12 times, pairs 6, and suited hands 4. The lower the percentile number underneath, the better the hand is. \n"
        		+ "Also, in theory, the number should match the percentage number of hands that you want to be playing based on your range. For example, if you want to be playing the top 20% of hands then the worst hand you should play is 55.\n"
        		+ "\n"
        		+ "\n"
        		);
        mainPanel.add(handsRanking_Text);
        
        homeItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeScreen();
            }
        });

        setContentPane(scrollPane);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JTextArea createTextArea(String text) {
		JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font("Serif", Font.PLAIN, 20));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textArea;
	}
	
	private JLabel createImageLabel(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return imageLabel;
    }

//	public static void main(String[] args) {
//        new GameIntroScreen();
//    }
	
	
}
