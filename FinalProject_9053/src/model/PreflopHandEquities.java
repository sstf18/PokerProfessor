package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PreflopHandEquities {

    private static final Map<Set<String>, Double> HAND_CATEGORIES = new HashMap<>();
    private static final Map<Double, String> CATEGORY_DESCRIPTIONS = new HashMap<>();
    
    
    static {
    	initializeHands();
    }
    
    private static void initializeHands() {
		// TODO Auto-generated method stub
    	//Top Tier Hands (70% and above)
        Set<String> topTierHands = new HashSet<>();
        topTierHands.add("AAo");
        topTierHands.add("KKo");
        topTierHands.add("QQo");
        topTierHands.add("JJo");
        topTierHands.add("TTo");
        
        topTierHands.add("AKs");
        topTierHands.add("AQs");
        topTierHands.add("AJs");
        topTierHands.add("AKo");
        topTierHands.add("ATs");
        topTierHands.add("KQs");
        topTierHands.add("KJs");
        topTierHands.add("KTs");
        
        topTierHands.add("AQo");
        topTierHands.add("QJs");
        topTierHands.add("QTs");
        topTierHands.add("JTs");
        
        HAND_CATEGORIES.put(topTierHands, 0.70);
        CATEGORY_DESCRIPTIONS.put(0.70,
        		"Top Tier Hands:\n" +
        		"Win Rate: Above 70%\n" +
        	    "Description: These are very starting hands, usually worth a raise to 3 bet.");

        // Strong Hands( 55% to 69%)
        Set<String> strongHands = new HashSet<>();
        strongHands.add("99o");
        strongHands.add("88o");
        strongHands.add("77o");
        strongHands.add("66o");
        strongHands.add("55o");
        strongHands.add("KQo");
        strongHands.add("AJo");
        strongHands.add("T9s");
        strongHands.add("98s");
        strongHands.add("87s");
        strongHands.add("76s");
        strongHands.add("A5s");
        strongHands.add("A4s");
        strongHands.add("A3s");
        strongHands.add("A2s");
             
        HAND_CATEGORIES.put(strongHands, 0.55);
        CATEGORY_DESCRIPTIONS.put(0.55, 
                "Strong Hands\n" +
                "Win Rate: between 55% to 69%\n" +
                "Description: These are strong hands, generally good for calling or raising.");

        // Medium Hands (45% to 54%)
        Set<String> mediumHands = new HashSet<>();
        
        strongHands.add("44o");
        strongHands.add("33o");
        strongHands.add("22o");
        
        strongHands.add("KJo");
        strongHands.add("QJo");
        strongHands.add("ATo");
        strongHands.add("KTo");
        strongHands.add("QTo");
        strongHands.add("JTo");
        
        strongHands.add("A9s");
        strongHands.add("A8s");
        strongHands.add("A7s");
        strongHands.add("A6s");
        
        strongHands.add("K9s");
        strongHands.add("K8s");
        strongHands.add("K7s");
        
        strongHands.add("Q9s");
        strongHands.add("Q8s");
        
        strongHands.add("J9s");
        strongHands.add("J8s");
        
        strongHands.add("T8s");
        
        strongHands.add("97s");
        strongHands.add("86s");
        strongHands.add("75s");
        strongHands.add("65s");
        strongHands.add("64s");
        strongHands.add("54s");
        strongHands.add("53s");
        strongHands.add("43s");

        HAND_CATEGORIES.put(mediumHands, 0.45);
        CATEGORY_DESCRIPTIONS.put(0.45, 
                "Medium Hands\n" +
                "Win Rate: between 45% to 54%\n" +
                "Description: These are fairly strong hands, but should be played with caution, call or rise if player seats at good position.");

        // Weak Hands (35% to 48%)
        Set<String> weakHands = new HashSet<>();
        weakHands.add("K6s");
        weakHands.add("K5s");
        weakHands.add("K4s");
        weakHands.add("K3s");
        weakHands.add("K2s");
        
        weakHands.add("Q7s");
        weakHands.add("Q6s");
        weakHands.add("Q5s");
        weakHands.add("Q4s");
        weakHands.add("Q3s");
        weakHands.add("Q2s");
       
        
        weakHands.add("J7s");
        weakHands.add("T7s");
        weakHands.add("T6s");
        
        weakHands.add("96s");
        weakHands.add("85s");
        weakHands.add("74s");
        weakHands.add("63s");
        weakHands.add("52s");
        weakHands.add("42s");
        weakHands.add("32s");
        
        weakHands.add("K9o");
        weakHands.add("Q9o");
        weakHands.add("J9o");
        weakHands.add("T9o");
        
        weakHands.add("A9o");
        weakHands.add("A8o");
        weakHands.add("A7o");
        weakHands.add("A6o");
        weakHands.add("A5o");
        weakHands.add("A4o");
        weakHands.add("A3o");
        weakHands.add("A2o");

        HAND_CATEGORIES.put(weakHands, 0.35);
        CATEGORY_DESCRIPTIONS.put(0.35, 
                "Weak Hands\n" +
                "Win Rate: between 30% to 50%\n" +
                "Description: These are weake hands, usually worth playing only in good positions.");

        // Trash Hands (25% to 34%)
        Set<String> trashHands = new HashSet<>();
        trashHands.add("K8o");
        trashHands.add("Q8o");
        trashHands.add("J8o");
        trashHands.add("T8o");
        trashHands.add("98o");
        
        trashHands.add("K7o");
        trashHands.add("Q7o");
        trashHands.add("J7o");
        trashHands.add("T7o");
        trashHands.add("97o");
        trashHands.add("87o");
        
        trashHands.add("K6o");
        trashHands.add("K5o");
        trashHands.add("K4o");
        trashHands.add("Q6o");
        trashHands.add("Q5o");
        
        trashHands.add("J6s");
        trashHands.add("J5s");
        trashHands.add("J4s");
        trashHands.add("J3s");
        trashHands.add("J2s");
        
        HAND_CATEGORIES.put(trashHands, 0.25);
        CATEGORY_DESCRIPTIONS.put(0.25, 
                "Trash Hands\n" +
                "Win Rate: between 25% to 34%\n" +
                "Description: These are the weaker hands, usually not worth playing.");		
	}
    
    public static double getEquity(String hand1, String hand2) {
        for (Map.Entry<Set<String>, Double> entry : HAND_CATEGORIES.entrySet()) {
            if (entry.getKey().contains(hand1) || entry.getKey().contains(hand2)) {
                return entry.getValue() * 100;  // Convert to percentage
            }
        }
        return 10.0;  // Default equity if no match is found
    }

    public static String getDescription(double equity) {
        String description = CATEGORY_DESCRIPTIONS.get(equity / 100); // Convert percentage back to fraction for lookup
        return description != null ? description : "These are weak hands and generally not recommended to play. Fold is your best choice.";
    }

	
}
