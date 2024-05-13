package service;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ImageIcon; 
import model.*;
import service.PokerEquityCalculator.FlopEquityResult;
import service.PokerEquityCalculator.TurnEquityResult;
import util.ImageUtils;

import java.util.ArrayList;
import java.util.List; 
import java.awt.event.ActionListener;

public class CardButtonLearningHandler {
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
    private String lastFlopDescription = "";
    private String lastTurnDescription = "";

    public CardButtonLearningHandler(JTextArea pre, JTextArea flop, JTextArea turn, JTextArea river) {
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
    

    private void setupListeners(JButton[] cards, JTextArea equityArea, int maxCards, List<Card> selectedCards) {
        for (JButton card : cards) {
            card.addActionListener(getCardActionListener(card, equityArea, maxCards, selectedCards));
        }
    }

    private ActionListener getCardActionListener(JButton card, JTextArea equityArea, int maxCards, List<Card> selectedCards) {
        return e -> {

            if (cardIndex < deck.size()) {
            	System.out.println("cardIndex" + cardIndex);
                Card drawnCard = deck.get(cardIndex);
                ImageIcon cardIcon = ImageUtils.getCardIcon(drawnCard);
                card.setIcon(cardIcon);
                equityArea.setText("Card: " + drawnCard.toString());  // Placeholder for actual equity calculation
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
                        	
                            double equityPercentage = PokerEquityCalculator.calculatePreflopEquity(
                            		selectedYourCards.get(0), 
                            		selectedYourCards.get(1)
                            );
                            //equityArea.setText("Equity: " + equityPercentage);
                            
                            String description = PreflopHandEquities.getDescription(equityPercentage) ;
                            equityArea.setText(String.format("Equity: %.2f%%\n%s", equityPercentage, description));
                        	
                        	// Calculate the equity using PokerSimulator
//                            double equity = PokerSimulator.calculatePreflopEquity(
//                                    new PokerSimulator.Card(selectedYourCards.get(0).getSuit().toString(), selectedYourCards.get(0).getRank().toString()),
//                                    new PokerSimulator.Card(selectedYourCards.get(1).getSuit().toString(), selectedYourCards.get(1).getRank().toString()),
//                                    100000);
//                            
//                            equityArea.setText("Equity: " + equity + "%");

                        }  
                    }
                    System.out.println("Here is your hands");
                    break;
                case 3:
                    if (flippedFlopCards < 3) {
                        flippedFlopCards++;
                        System.out.println("flippedFlopcards: " + flippedFlopCards);
                        if (flippedFlopCards == 3) {
                            FlopEquityResult flopEquityDetails = PokerEquityCalculator.calculateFlopEquity(
                            		selectedYourCards.get(0),
                            		selectedYourCards.get(1),
                            		selectedFlopCards.get(0),
                            		selectedFlopCards.get(1),
                            		selectedFlopCards.get(2)	
                            );
                            //String description = flopEquity
                            lastFlopDescription = flopEquityDetails.description;
                            equityArea.setText(lastFlopDescription);
                        }
                    }
                    break;
                case 1:
                    if (flippedTurnCards < 1) {
                        flippedTurnCards++;
                        System.out.println("flippedTurncards: " + flippedTurnCards);
                        if (flippedTurnCards == 1) {
                            TurnEquityResult turnEquityDetails = PokerEquityCalculator.calculateTurnEquity(
                            		selectedYourCards.get(0),
                            		selectedYourCards.get(1),
                            		selectedFlopCards.get(0),
                            		selectedFlopCards.get(1),
                            		selectedFlopCards.get(2),
                            		selectedTurnCard.get(0)
                            		
                            );
                            lastTurnDescription = turnEquityDetails.description; 
                            equityArea.setText(lastTurnDescription);
                        }
                    } else if (flippedRiverCards < 1) {
                        flippedRiverCards++;
                        System.out.println("flippedRivercards: " + flippedRiverCards);
                        if (flippedRiverCards == 1) {
                            String equity = PokerEquityCalculator.calculateRiverEquity(
                            		selectedYourCards.get(0),
                            		selectedYourCards.get(1),
                            		selectedFlopCards.get(0),
                            		selectedFlopCards.get(1),
                            		selectedFlopCards.get(2),
                            		selectedTurnCard.get(0),
                            		selectedRiverCard.get(0)
                            );
                            equityArea.setText("Best Hand:\n" + equity );
                        }
                    }
                    break;
                }
            } else {
                equityArea.setText("No more cards available.");
            }
        };
    }
    
    public void reset() {
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
