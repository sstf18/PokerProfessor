package service;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import model.Card;
import model.Rank;
import model.Suit;

/*
 * Calculate best 5 cards out of 7
 * by checking each situation, to find the which situation will be satisfy
 * 
 * this code also could use multithreading, but it is not neccessary 
 * **/
public class BestFivePokers {
	// Card Comparator to sort cards by rank descending
    private Comparator<Card> cardComparator = (c1, c2) -> Integer.compare(c2.getRank().ordinal(), c1.getRank().ordinal());

    // Method to find the best 5-card poker hand
    public HandResult findBestHand(List<Card> cards) throws Exception {
    	System.out.println("in findBestHand");
        if (cards.size() != 7)
            throw new Exception("Expected exactly 7 cards, received: " + cards.size());

        Collections.sort(cards, cardComparator);

        // Try to find the highest ranking hand
        HandResult bestHand = checkStraightFlush(cards);
        if (bestHand != null) return bestHand;

        bestHand = checkFourOfAKind(cards);
        if (bestHand != null) return bestHand;

        bestHand = checkFullHouse(cards);
        if (bestHand != null) return bestHand;
        
        bestHand = checkFlush(cards);
        if (bestHand != null) return bestHand;

        bestHand = checkStraight(cards);
        if (bestHand != null) return bestHand;

        bestHand = checkThreeOfAKind(cards);
        if (bestHand != null) return bestHand;

        bestHand = checkTwoPairs(cards);
        if (bestHand != null) return bestHand;

        bestHand = checkOnePair(cards);
        if (bestHand != null) return bestHand;

        return checkHighCard(cards); // if all else fials, return high card.
    }
    

	// Implementations of each checking method (placeholders)
    private HandResult checkStraightFlush(List<Card> cards) {
        // Placeholder: Implement the logic to check for Straight Flush
    	int consecutiveCount = 1;
        List<Card> bestHand = new ArrayList<>();

        for (int i = 0; i < cards.size() - 1; i++) {
            // Reset if the suit changes or the sequence breaks
            if (cards.get(i).getSuit() != cards.get(i + 1).getSuit() ||
                cards.get(i).getRank().ordinal() != cards.get(i + 1).getRank().ordinal() + 1) {
                
                if (consecutiveCount >= 5) {
                    break; // We've already found a straight flush
                }

                bestHand.clear();
                consecutiveCount = 1;
            } else {
                if (consecutiveCount == 1) {
                    bestHand.add(cards.get(i)); // Add the starting card of potential straight flush
                }
                bestHand.add(cards.get(i + 1)); // Add the next card in sequence
                consecutiveCount++;
            }
        }

        // Check for the special case of a low-Ace straight flush (5-4-3-2-Ace)
        if (consecutiveCount < 5 && cards.stream().anyMatch(card -> card.getRank() == Rank.ACE && card.getSuit() == cards.get(0).getSuit())) {
            if (bestHand.size() >= 4 && bestHand.get(bestHand.size() - 1).getRank() == Rank.TWO) {
                bestHand.add(new Card(Rank.ACE, cards.get(0).getSuit())); // Add Ace if it completes the wheel straight flush
                consecutiveCount++;
            }
        }

        if (consecutiveCount >= 5) {
            return new HandResult("Straight Flush", bestHand.subList(0, 5));
        }
        
        return null; // No straight flush found
    }

    private HandResult checkFourOfAKind(List<Card> cards) {
        // Placeholder: Implement the logic to check for Four of a Kind
    	int fourOfAKindIndex = -1;
        int kickerIndex = -1;
        List<Card> bestHand = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            int count = 1;
            for (int j = i + 1; j < cards.size() && cards.get(j).getRank() == cards.get(i).getRank(); j++) {
                count++;
            }
            if (count == 4) {
                fourOfAKindIndex = i;
                // Add all four cards to the bestHand
                bestHand.addAll(cards.subList(i, i + 4));
                break;
            }
        }

        // Find the highest kicker (the highest card not included in the four of a kind)
        for (int i = 0; i < cards.size(); i++) {
            if (fourOfAKindIndex != -1 && (i < fourOfAKindIndex || i >= fourOfAKindIndex + 4)) {
                kickerIndex = i;
                bestHand.add(cards.get(kickerIndex));
                break;
            }
        }

        if (!bestHand.isEmpty()) {
            return new HandResult("Four of a Kind", bestHand);
        }

        return null; // No four of a kind found

    }
    
    private HandResult checkFullHouse(List<Card> cards) {
		// TODO Auto-generated method stub
    	if (cards.size() != 7)
            throw new IllegalArgumentException("Expected exactly 7 cards, received: " + cards.size());

        List<Card> triplet = null;
        List<Card> pair = null;

        // First, group cards by rank and sort groups by size and rank
        Map<Rank, List<Card>> groupedCards = cards.stream()
            .collect(Collectors.groupingBy(Card::getRank));

        List<List<Card>> groups = new ArrayList<>(groupedCards.values());
        // Sort groups by size descending, and rank descending within the same size
        groups.sort((g1, g2) -> {
            int sizeCompare = Integer.compare(g2.size(), g1.size()); // Larger groups first
            if (sizeCompare != 0) return sizeCompare;
            return g2.get(0).getRank().compareTo(g1.get(0).getRank()); // Higher ranks first
        });

        // Find the triplet and the pair
        for (List<Card> group : groups) {
            if (group.size() >= 3 && triplet == null) {
                triplet = group;
            } else if (group.size() >= 2 && pair == null) {
                pair = group;
            }
            if (triplet != null && pair != null) {
                break;
            }
        }

        // Check if both triplet and pair are found
        if (triplet != null && pair != null) {
            List<Card> bestHand = new ArrayList<>();
            bestHand.addAll(triplet.subList(0, 3)); // Take only three cards from the triplet
            bestHand.addAll(pair.subList(0, 2)); // Take only two cards from the pair
            return new HandResult("Full House", bestHand);
        }

        return null; // No full house found
	}
    
    private HandResult checkFlush(List<Card> cards) {
    	if (cards.size() != 7)
            throw new IllegalArgumentException("Expected exactly 7 cards, received: " + cards.size());

        // Group cards by suit
        Map<Suit, List<Card>> cardsBySuit = cards.stream()
            .collect(Collectors.groupingBy(Card::getSuit));

        // Find any suit with 5 or more cards
        for (Map.Entry<Suit, List<Card>> entry : cardsBySuit.entrySet()) {
            List<Card> suitedCards = entry.getValue();
            if (suitedCards.size() >= 5) {
                // Sort the cards of the flush suit by rank descending
                suitedCards.sort((c1, c2) -> Integer.compare(c2.getRank().ordinal(), c1.getRank().ordinal()));
                // Take the top 5 cards for the flush
                List<Card> flushCards = suitedCards.subList(0, 5);
                return new HandResult("Flush", flushCards);
            }
        }

        return null; // No flush found
    	
    }
    
    private HandResult checkStraight(List<Card> cards) {
        List<Card> sortedCards = new ArrayList<>(cards);
        sortedCards.sort((c1, c2) -> Integer.compare(c2.getRank().ordinal(), c1.getRank().ordinal()));

        boolean aceHighPresent = sortedCards.stream().anyMatch(card -> card.getRank() == Rank.ACE);
        if (aceHighPresent) {
            // Treat Ace as both high and low by adding a virtual card with rank "0"
            sortedCards.add(new Card(Rank.values()[0], sortedCards.get(0).getSuit()));  // Same suit as the first Ace found
        }

        List<Card> straightCards = new ArrayList<>();
        int sequenceCount = 1;

        for (int i = 0; i < sortedCards.size() - 1; i++) {
            if (sortedCards.get(i).getRank().ordinal() - sortedCards.get(i + 1).getRank().ordinal() == 1) {
                if (sequenceCount == 1) {
                    straightCards.add(sortedCards.get(i));
                }
                straightCards.add(sortedCards.get(i + 1));
                sequenceCount++;
            } else {
                if (sequenceCount >= 5) break;
                straightCards.clear();
                sequenceCount = 1;
            }
        }

        if (sequenceCount >= 5) {
            return new HandResult("Straight", straightCards.subList(0, 5));
        }

        return null;
    }
    
    private HandResult checkThreeOfAKind(List<Card> cards) {
    	if (cards.size() != 7)
            throw new IllegalArgumentException("Expected exactly 7 cards, received: " + cards.size());

        Map<Rank, List<Card>> rankGroups = new HashMap<>();
        for (Card card : cards) {
            rankGroups.computeIfAbsent(card.getRank(), k -> new ArrayList<>()).add(card);
        }

        List<Card> threeOfAKind = null;
        List<Card> allCards = new ArrayList<>(cards);

        // Check for any three of a kind
        for (List<Card> group : rankGroups.values()) {
            if (group.size() >= 3) {
                if (threeOfAKind == null || group.get(0).getRank().ordinal() > threeOfAKind.get(0).getRank().ordinal()) {
                    threeOfAKind = group;
                }
            }
        }

        if (threeOfAKind != null) {
            List<Card> bestHand = new ArrayList<>(threeOfAKind.subList(0, 3)); // Take three cards from the best three of a kind

            // Sort all cards to find the highest kickers not part of the three of a kind
            allCards.sort((a, b) -> b.getRank().ordinal() - a.getRank().ordinal());
            int kickersAdded = 0;
            for (Card card : allCards) {
                if (!bestHand.contains(card)) {
                    bestHand.add(card);
                    kickersAdded++;
                    if (kickersAdded == 2) break; // Only need two kickers
                }
            }

            return new HandResult("Three of a Kind", bestHand);
        }

        return null; // No three of a kind found
	}

	private HandResult checkTwoPairs(List<Card> cards) {
		if (cards.size() != 7)
            throw new IllegalArgumentException("Expected exactly 7 cards, received: " + cards.size());

        Map<Rank, List<Card>> rankGroups = new HashMap<>();
        for (Card card : cards) {
            rankGroups.computeIfAbsent(card.getRank(), k -> new ArrayList<>()).add(card);
        }

        List<List<Card>> pairs = new ArrayList<>();
        List<Card> allCards = new ArrayList<>();

        // Collect groups and single cards
        for (List<Card> group : rankGroups.values()) {
            if (group.size() >= 2) {
                pairs.add(group);
            }
            allCards.addAll(group);
        }

        // Ensure we have at least two pairs
        if (pairs.size() >= 2) {
            // Sort pairs by highest rank and pick the top two
            pairs.sort((a, b) -> b.get(0).getRank().ordinal() - a.get(0).getRank().ordinal());
            List<Card> bestHand = new ArrayList<>();
            bestHand.addAll(pairs.get(0).subList(0, 2)); // Take two cards from the best pair
            bestHand.addAll(pairs.get(1).subList(0, 2)); // Take two cards from the second best pair

            // Sort all cards to find the highest kicker not part of the pairs
            allCards.sort((a, b) -> b.getRank().ordinal() - a.getRank().ordinal());
            for (Card card : allCards) {
                if (!bestHand.contains(card)) {
                    bestHand.add(card);
                    break; // Only need one kicker
                }
            }

            return new HandResult("Two Pairs", bestHand);
        }

        return null; // No two pairs found
	}
	
	private HandResult checkOnePair(List<Card> cards) {
		// TODO Auto-generated method stub
		if (cards.size() != 7)
	        throw new IllegalArgumentException("Expected exactly 7 cards, received: " + cards.size());

	    Map<Rank, List<Card>> rankGroups = cards.stream()
	        .collect(Collectors.groupingBy(Card::getRank));

	    AtomicReference<List<Card>> pairRef = new AtomicReference<>();
	    List<Card> kickers = new ArrayList<>();

	    // Process each group to find a pair and collect kickers
	    rankGroups.values().forEach(group -> {
	        if (group.size() == 2 && pairRef.get() == null) {
	            pairRef.set(group);
	        } else {
	            kickers.addAll(group);
	        }
	    });

	    List<Card> pair = pairRef.get();
	    if (pair != null) {
	        // Sort kickers by descending rank to select the highest ones
	        kickers.sort((c1, c2) -> c2.getRank().ordinal() - c1.getRank().ordinal());

	        if (kickers.size() >= 3) {
	            List<Card> bestHand = new ArrayList<>(pair);
	            bestHand.addAll(kickers.subList(0, 3));  // Add top three kickers
	            return new HandResult("One Pair", bestHand);
	        }
	    }

	    return null; // No valid one pair found
		
	}
	
	private HandResult checkHighCard(List<Card> cards) {
		if (cards.size() < 5) {
	        throw new IllegalArgumentException("Not enough cards to determine a high card hand.");
	    }
	    
	    // Select the top 5 cards since they are already sorted by rank.
	    List<Card> highCards = new ArrayList<>(cards.subList(0, 5));
	    
	    return new HandResult("High Card", highCards);
	    }


    // Helper class to return hand result
    public class HandResult {
        private String handType;
        private List<Card> hand;

        public HandResult(String handType, List<Card> hand) {
            this.handType = handType;
            this.hand = hand;
        }

        public String getHandType() {
            return handType;
        }

        public List<Card> getHand() {
            return hand;
        }
    }
    
    public String calculateBestHandDescription(List<Card> holeCards, List<Card> communityCards) throws Exception {
        List<Card> allCards = new ArrayList<>(holeCards);
        allCards.addAll(communityCards);
        System.out.println("allCards: " + allCards);
        HandResult bestHand = findBestHand(allCards);  // Method to find the best hand
        System.out.println("bestHand: " + bestHand);

        if (bestHand != null) {
            return formatHandDescription(bestHand);
        }
        return "Unable to determine best hand.";
    }

	private String formatHandDescription(HandResult bestHand) {
		// TODO Auto-generated method stub
		// Create a descriptive string of the hand type and the cards
        return bestHand.getHandType() + " with " + bestHand.getHand().stream()
                        .map(card -> card.toString())
                        .collect(Collectors.joining(", "));
	}
    
    
}
