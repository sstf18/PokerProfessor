package model;

public class Card {
	private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + "_of_" + suit;
    }

    public String getImageFileName() {
        return rank + "_of_" + suit + ".png";
    }
}
