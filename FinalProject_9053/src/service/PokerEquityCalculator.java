package service;

import java.util.Arrays;
import java.util.List;

import model.Card; 
import model.PreflopHandEquities;

/*
 * This class mainly to combine the different calculators for the 4 textAreas. 
 * in Pre-flop, I created specific method for pre-flop hands equities
 * in flop and turn, used the outsCaluclator 
 * in riverTextArea, i design a method to get the bestFiveCard in total 7 cards. 
 * 
 * **/
public class PokerEquityCalculator {
	
	private static final OutsCalculator outscalculator = new OutsCalculator();
	
	public static double calculatePreflopEquity(Card yourCard1, Card yourCard2) {
		
		// Determine if the cards are suited or off suit
        boolean suited = yourCard1.getSuit() == yourCard2.getSuit();
        String suitedOrOffsuit = suited ? "s" : "o";

        // Create a representation of the hand
        String hand1 = yourCard1.getRank().toString() + yourCard2.getRank().toString() + suitedOrOffsuit;
        String hand2 = yourCard2.getRank().toString() + yourCard1.getRank().toString() + suitedOrOffsuit;
        
        // Get the equity from PreflopHandEquities
        double equityPercentage = PreflopHandEquities.getEquity(hand1, hand2);
        return equityPercentage;
    }

    public static FlopEquityResult calculateFlopEquity(Card yourCard1, Card yourCard2, Card flopCard1, Card flopCard2, Card flopCard3) {
		
		List<Card> yourCards = Arrays.asList(yourCard1, yourCard2);
        List<Card> commonCards = Arrays.asList(flopCard1, flopCard2, flopCard3);
        
        String outsDescription = outscalculator.calculateOuts(yourCards, commonCards);
        int lastSpaceIndex = outsDescription.lastIndexOf(' ');
        int sumOfTopTwoOuts = Integer.parseInt(outsDescription.substring(lastSpaceIndex + 1));
        double probabilityBy42Rule = outscalculator.getProbabilityBy42Rule(sumOfTopTwoOuts, true);

        return new FlopEquityResult(probabilityBy42Rule, String.format("Equity: %.2f%%\n%s", probabilityBy42Rule, outsDescription));
    }
    
    public static TurnEquityResult calculateTurnEquity(Card yourCard1, Card yourCard2, Card flopCard1, Card flopCard2, Card flopCard3, Card turnCard) {
        
    	List<Card> yourCards = Arrays.asList(yourCard1, yourCard2);
        List<Card> commonCards = Arrays.asList(flopCard1, flopCard2, flopCard3, turnCard);
        
        String outsDescription = outscalculator.calculateOuts(yourCards, commonCards);
        int lastSpaceIndex = outsDescription.lastIndexOf(' ');
        int sumOfTopTwoOuts = Integer.parseInt(outsDescription.substring(lastSpaceIndex + 1));
        double probabilityBy42Rule = outscalculator.getProbabilityBy42Rule(sumOfTopTwoOuts, false);

        return new TurnEquityResult(probabilityBy42Rule, String.format("Equity: %.2f%%\n%s", probabilityBy42Rule, outsDescription));

    }
    
    public static String calculateRiverEquity(Card yourCard1, Card yourCard2, Card flopCard1, Card flopCard2, Card flopCard3, Card turnCard, Card riverCard) {
    	
    	BestFivePokers bestHands = new BestFivePokers();
    	List<Card> yourCards = Arrays.asList(yourCard1, yourCard2);
        List<Card> commonCards = Arrays.asList(flopCard1, flopCard2, flopCard3, turnCard, riverCard);
    	
        try {
        	return bestHands.calculateBestHandDescription(yourCards, commonCards);
        }catch (Exception e) {
        	return "Error calculating hand: " + e.getMessage();
        }
    
    }
    
    public static class FlopEquityResult {
        public double equity;
        public String description;

        public FlopEquityResult(double equity, String description) {
            this.equity = equity;
            this.description = description;
        }
    }
    
    public static class TurnEquityResult {
        public double equity;
        public String description;

        public TurnEquityResult(double equity, String description) {
            this.equity = equity;
            this.description = description;
        }
    }

}
