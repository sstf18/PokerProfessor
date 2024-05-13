package service;

import model.Card;
import model.Rank;
import model.Suit;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/*
 * Check each situation, and find all the possible outs for each situation 
 * I ranking the all the outs of situation, and choose the top two
 * and sum of top two, then go the final outs. 
 * 
 * knowing outs were used for rule of 4 and 2. 
 * 
 * in calculateOuts, it could use multithreading, since these situation will be calculate independently, 
 * but in my case, only few cards will be calculated, so multithreading may not be necessary 
 * **/

public class OutsCalculator {
 
    // Calculate outs for a flush
    private int countFlushOuts(List<Card> cards) {
        Map<Suit, Integer> suitCount = new HashMap<>();
        
        for (Card card : cards) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        int maxCount = suitCount.values().stream().max(Integer::compare).orElse(0);
        int outs = 0; 
        
        if (maxCount >= 5) {
            outs = 0;  // Already have a flush or better
            System.out.println("You already got flush");
        } else if (maxCount == 4) {
            outs = 9;  // 13 - 4 = 9, there are 9 possible cards remaining of this suit
        } else if (maxCount == 3) {
        	if (cards.size() == 5) {
        		outs = 1; // it is possible but very rare
        	}else {
        		outs = 0; 
        	}
        }else {
            outs = 0;  // If less than 2 cards, flush is not possible with two more cards
        }
        return outs;
    }

    // Calculate outs for a straight
    private int countStraightOuts(List<Card> cards) {
        Set<Integer> uniqueRanks = new HashSet<>();
        Set<Integer> neededRanks = new HashSet<>();
        for (Card card : cards) {
        	int rankValue = card.getRank().ordinal() + 2; 
            uniqueRanks.add(rankValue);
            if (rankValue == 14) {
            	uniqueRanks.add(1);
            }
            //System.out.println("uniqueRanks: " + uniqueRanks);
        }
        // Check each potential starting point for a straight
        for (int i = 1; i <= 10; i++) {
        	int missingCount = 0 ; 
            
            for (int j = 0; j < 5; j++) {
                if (!uniqueRanks.contains(i + j)) {
                	missingCount++;
                	if (missingCount > 1) {
                    	break;
                	}
                }
            }
            if (missingCount == 1) {
                for (int j = 0; j < 5; j++) {
                    if (!uniqueRanks.contains(i + j)) {
                        neededRanks.add(i + j);
                    }
                }
            }
        }
        return neededRanks.size()*4; 
    }

    // Calculate outs for a full house
    public int countFullHouseOuts(List<Card> cards) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
            //System.out.println("rankCount: " + rankCount);
        }

        int pairOuts = 0, tripletOuts = 0;
        for (int count : rankCount.values()) {
            if (count == 2) pairOuts++;
            else if (count == 3) tripletOuts++;
        }

        if (tripletOuts > 0 && pairOuts > 0) { 
        	System.out.print("You already got a full house");	
        	return 0;
        }

        int outs = 0;
        if (cards.size() == 5) {
            if (tripletOuts > 0) {
                // Needs to pair one of the other cards to complete a full house
                outs += (3 * 2); // Two remaining ranks that could each pair up, each has 3 cards left in the deck
            }
            if (pairOuts >= 2) {
                // Needs to turn one pair into a three of a kind
                outs += 4; // Four outs per pair to become a three of a kind
            }
        } else if (cards.size() == 6) {
            if (tripletOuts > 0) {
                // Needs to pair one of the other two cards to complete a full house
                outs += (3 * 3); // Three remaining ranks that could each pair up, each has 3 cards left in the deck
            }
            if (pairOuts >= 2) {
                // Needs to turn one pair into a three of a kind
                outs += 4; // Only one more card needed for each pair to become a three of a kind
            }
        }
        return outs;
    }
    
    // Calculate outs for four of a kind
    public int countFourOfAKindOuts(List<Card> cards) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
            //System.out.println("rankCount: " + rankCount);
        }
        for (int count : rankCount.values()) {
            if (count == 3) return 1;
        }

        return 0;
    }

    // Calculate outs for three of a kind
    public int countThreeOfAKindOuts(List<Card> cards) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }
        int pairs = 0 ; 
        int pairOuts = 0; 
        boolean hasThreeOfAKind = false; 

        for (int count : rankCount.values()) {
            if (count == 2) {
            	pairs ++; 
            }
            else if (count == 3) { 
            	hasThreeOfAKind = true;
            }
        }
        
        if (pairs == 1 && !hasThreeOfAKind) {
        	pairOuts = 2; 
        }

        return pairOuts;
    }
    
    // Calculate outs for two pair
    public int countTwoPairOuts(List<Card> cards) {
    	// Splitting the cards list into myCards and communityCards
        List<Card> myCards = cards.subList(0, 2);
        List<Card> communityCards = cards.subList(2, cards.size());

        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
        	
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
            //System.out.println("rankCount: " + rankCount);
        }

        int pairOuts = 0, myPairCount = 0;
        Map<Rank, Integer> myRankCount = new HashMap<>();
        for (Card card : myCards) {
        	
            myRankCount.put(card.getRank(), myRankCount.getOrDefault(card.getRank(), 0) + 1);
            //System.out.println("myPairCount: " + myRankCount);
        }

        for (int count : rankCount.values()) {
            if (count == 2) pairOuts++;
        }

        for (int count : myRankCount.values()) {
            if (count == 2) myPairCount++;
        }

        // Already have two pairs
        if (pairOuts > 1) {
        	System.out.println("You already got two pairs");
            return 0; 
        } else if (myPairCount == 1) {
            // Hole cards already form a pair
            return communityCards.size() * 3; // Possible pairs with each non-pair community card
        } else if (pairOuts == 1) {
            // One pair in the community cards only
            if (communityCards.size() >= 3) {
                // Check if the pair is in the community cards
                Map<Rank, Integer> communityRankCount = new HashMap<>();
                for (Card card : communityCards) {
                    communityRankCount.put(card.getRank(), communityRankCount.getOrDefault(card.getRank(), 0) + 1);
                }

                boolean communityPair = false;
                for (int count : communityRankCount.values()) {
                    if (count == 2) {
                        communityPair = true;
                        break;
                    }
                }

                if (communityPair) {
                    // Each hole card can pair up with the rest of the deck
                    return 2 * 2; // 2 outs for each hole card to pair with remaining cards
                } else {
                    // One hole card can pair up with the pair in the community cards
                	System.out.println("one in hands, one in commond cards");
                    return ((myCards.size() + communityCards.size()) - 2) * 3; // 2 outs for each hole card to pair with remaining cards
                }
            }
        } 
        System.out.println("do not have pair");
        return 0;
    }

    // Calculate outs for one pair
    private int countOnePairOuts(List<Card> cards) {
    	Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }

        // Check if there is already a pair
        boolean hasPair = false;
        for (int count : rankCount.values()) {
            if (count == 2) {
                hasPair = true;
                break;
            }
        }

        if (hasPair) {
            // If there is already a pair, return 3 (3 remaining cards of the same rank)
            return 0;
        } else {
        	int outs = 6; 
            return outs;
        }
    }
    
    public String calculateOuts(List<Card> yourCards, List<Card> commonCards) {
        ExecutorService executor = Executors.newFixedThreadPool(7); // One thread for each type of hand calculation
        List<Card> cards = new ArrayList<>(yourCards);
        cards.addAll(commonCards);

        Map<String, Future<Integer>> futureResults = new LinkedHashMap<>();
        futureResults.put("Flush", executor.submit(() -> countFlushOuts(cards)));
        futureResults.put("Straight", executor.submit(() -> countStraightOuts(cards)));
        futureResults.put("Full House", executor.submit(() -> countFullHouseOuts(cards)));
        futureResults.put("Four of a Kind", executor.submit(() -> countFourOfAKindOuts(cards)));
        futureResults.put("Three of a Kind", executor.submit(() -> countThreeOfAKindOuts(cards)));
        futureResults.put("Two Pair", executor.submit(() -> countTwoPairOuts(cards)));
        futureResults.put("One Pair", executor.submit(() -> countOnePairOuts(cards)));

        Map<String, Integer> outCounts = new LinkedHashMap<>();
        for (Map.Entry<String, Future<Integer>> entry : futureResults.entrySet()) {
            try {
                outCounts.put(entry.getKey(), entry.getValue().get());  // Blocking call
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Correct usage of printStackTrace()
            }
        }
        executor.shutdown();  // Important to shut down the executor

        return getOutsDescription(outCounts);
    }

    
    
    
    private String getOutsDescription(Map<String, Integer> outs) {
        StringBuilder description = new StringBuilder();
        List<Map.Entry<String, Integer>> sortedOuts = outs.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .collect(Collectors.toList());

        int sumOfTopTwoOuts = sortedOuts.stream().limit(2).mapToInt(Map.Entry::getValue).sum();

        for (int i = 0; i < 2 && i < sortedOuts.size(); i++) {
            Map.Entry<String, Integer> out = sortedOuts.get(i);
            description.append(String.format("For achieving a %s, there are %d outs.\n", out.getKey(), out.getValue()));
        }

        description.append("Total significant outs: ").append(sumOfTopTwoOuts);
        return description.toString();
    }
    

    public double getProbabilityBy42Rule(int outs, boolean twoCards) {
        return twoCards ? outs * 4 : outs * 2;
    }
}
