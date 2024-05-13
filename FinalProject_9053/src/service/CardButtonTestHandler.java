package service;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;

import model.Card;
import model.PreflopHandEquities;
import service.PokerEquityCalculator.FlopEquityResult;
import service.PokerEquityCalculator.TurnEquityResult;
import util.ImageUtils;
/*
 * This class handle all the card's button in testMode
 * by pressed the card button, user will get a randomly card
 * but, i set a limitation that if card was clicked, then it will not be press again. 
 * 
 * Also, in testMode, after clicked, it will not directly show the answer,
 * it will save the correct answer in equity and description which will used in testModeScreen. 
 */
public class CardButtonTestHandler {
	private JTextArea preFlopEquityArea, flopEquityArea, turnEquityArea, riverEquityArea;
	private List<Card> deck; 
	private int cardIndex = 0 ; 
	
	private int flippedYourCards = 0;
    private int flippedFlopCards = 0;
    private int flippedTurnCards = 0;
    private int flippedRiverCards = 0;
    private List<Card> selectedYourCards = new ArrayList<>();
    private List<Card> selectedFlopCards = new ArrayList<>();
    private List<Card> selectedTurnCard = new ArrayList<>();
    private List<Card> selectedRiverCard = new ArrayList<>();
    private String lastPreFlopEquity = ""; 
    private String lastPreFlopDescription = "";
    private String lastFlopEquity = "";
    private String lastFlopDescription = "";
    private String lastTurnEquity = "";
    private String lastTurnDescription = "";
    private String lastFiveCards = "";
    
    public String getLastPreFlopEquity() {
        return lastPreFlopEquity;
    }
    public String getLastPreFlopDescription() {
    	return lastPreFlopDescription;
    }
    public String getLastFlopEquity() {
        return lastFlopEquity;
    }
    public String getLastFlopDescription() {
    	return lastFlopDescription;
    }
    public String getLastTurnEquity() {
        return lastTurnEquity;
    }
    public String getLastTurnDescription() {
    	return lastTurnDescription;
    }
    public String getLastFiveCards() {
    	return lastFiveCards;
    }
    
    
	
    public CardButtonTestHandler(JTextArea pre, JTextArea flop, JTextArea turn, JTextArea river) {
        this.preFlopEquityArea = pre;
        this.flopEquityArea = flop;
        this.turnEquityArea = turn;
        this.riverEquityArea = river;
        this.deck = ImageUtils.getShuffledDeck();
    }

    public void setupCardListeners(JButton[] yourCards, JButton[] flopCards, JButton[] turnCard, JButton[] riverCard) {
        setupListeners(yourCards, preFlopEquityArea, 2, selectedYourCards);
        setupListeners(flopCards, flopEquityArea, 3, selectedFlopCards);
        setupListeners(turnCard, turnEquityArea, 1, selectedTurnCard);
        setupListeners(riverCard, riverEquityArea, 1, selectedRiverCard);
    }
    
    // this is the main step to handle the clicked card. 
    private void setupListeners(JButton[] cards, JTextArea equityArea, int maxCards, List<Card> selectedCards) {
        for (JButton card : cards) {
            card.addActionListener(getCardActionListener(card, equityArea, maxCards, selectedCards));
        }
    }
    ActionListener getCardActionListener(JButton card, JTextArea equityArea, int maxCards, List<Card> selectedCards) {
    	return e -> {
    		if (cardIndex < deck.size()) {
            	System.out.println("cardIndex" + cardIndex);
                Card drawnCard = deck.get(cardIndex);
                ImageIcon cardIcon = ImageUtils.getCardIcon(drawnCard);
                card.setIcon(cardIcon);
                cardIndex++;
                
                card.setDisabledIcon(cardIcon);
                card.setEnabled(false);
                card.setContentAreaFilled(false);
                card.setBorderPainted(false);
                
                if (selectedCards != null) {
                    selectedCards.add(drawnCard);
                }
                
                switch (maxCards) {
                case 2:
                    if (flippedYourCards < 2) {
                        flippedYourCards++;
                        System.out.println("flippedYourcards: " + flippedYourCards);
                        if (flippedYourCards == 2) {
                        	Double equity  = PokerEquityCalculator.calculatePreflopEquity(
                        			selectedYourCards.get(0),
                        			selectedYourCards.get(1)
                        	);

                        	lastPreFlopEquity = String.format("%.2f", equity);
                        	lastPreFlopDescription = PreflopHandEquities.getDescription(equity);
                        	//equityArea.setText("Input your answer: ");
                        	
                        	System.out.println("lastPreFlopEquity: " + lastPreFlopEquity);
                        	
                        	//equityArea.setText("Predict the pre-flop equity");
                        	
                        }
                    }
                    System.out.println("Here should be your hands");
                    break;
                case 3:
                    if (flippedFlopCards < 3) {
                        flippedFlopCards++;
                        if (flippedFlopCards == 3) {
                        	FlopEquityResult flopEquityResult = PokerEquityCalculator.calculateFlopEquity(
                            		selectedYourCards.get(0),
                            		selectedYourCards.get(1),
                            		selectedFlopCards.get(0),
                            		selectedFlopCards.get(1),
                            		selectedFlopCards.get(2)	
                            );
                        	
                        	lastFlopEquity =  String.format("%.2f", flopEquityResult.equity); 
                        	lastFlopDescription = flopEquityResult.description; 
                        	
                        	System.out.println("lastFlopEquity: " + lastFlopEquity);
                        	//equityArea.setText("lastFlopEquity: " + lastFlopEquity);
                        	//equityArea.setText("lastFlopDescription: " + lastFlopDescription);
                        }
                        System.out.println("flippedFlopcards: " + flippedFlopCards);
                        
                    }
                    break;
                  
                case 1:
                    if (flippedTurnCards < 1) {
                        flippedTurnCards++;
                        System.out.println("flippedTurncards: " + flippedTurnCards);
                        
                        if (flippedTurnCards == 1) {
                            TurnEquityResult turnEquityResult = PokerEquityCalculator.calculateTurnEquity(
                            		selectedYourCards.get(0),
                            		selectedYourCards.get(1),
                            		selectedFlopCards.get(0),
                            		selectedFlopCards.get(1),
                            		selectedFlopCards.get(2),
                            		selectedTurnCard.get(0)
                            		
                            );
                            
                            lastTurnEquity = String.format("%.2f", turnEquityResult.equity);
                            lastTurnDescription = turnEquityResult.description; 
                            //equityArea.setText(lastTurnDescription);           
                            System.out.println("lastTurnEquity: " + lastTurnEquity);
                        }
                    } else if (flippedRiverCards < 1) {
                        flippedRiverCards++;
                        System.out.println("flippedRivercards: " + flippedRiverCards);
                        if (flippedRiverCards == 1) {
                            String bestFiveCards = PokerEquityCalculator.calculateRiverEquity(
                            		selectedYourCards.get(0),
                            		selectedYourCards.get(1),
                            		selectedFlopCards.get(0),
                            		selectedFlopCards.get(1),
                            		selectedFlopCards.get(2),
                            		selectedTurnCard.get(0),
                            		selectedRiverCard.get(0)
                            );
                            lastFiveCards = bestFiveCards; 
                            System.out.println("bestFiveCards: " + bestFiveCards );
                            //equityArea.setText("Best Hand:\n" + bestFiveCards );
                        }
                    }
                    break;
                } 
    		}
    	};
    }
  
	public void reset() {
		// TODO Auto-generated method stub
		deck = ImageUtils.getShuffledDeck();
    	cardIndex = 0; 
    	flippedYourCards = 0;
        flippedFlopCards = 0;
        flippedTurnCards = 0;
        flippedRiverCards = 0;
        
        selectedYourCards.clear();
        selectedFlopCards.clear();
        selectedTurnCard.clear();
        selectedRiverCard.clear();
        // Clear the equity areas
        preFlopEquityArea.setText("");
        flopEquityArea.setText("");
        turnEquityArea.setText("");
        riverEquityArea.setText("");
		
	}

}
