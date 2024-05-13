package util;

import javax.swing.ImageIcon;

import model.*;

import java.awt.Image;
import java.util.*;

// This class to handle the poker image
public class ImageUtils {
	
	// Shuffle deck 
	public static List<Card> getShuffledDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }
	
	public static ImageIcon getCardIcon(Card card) {
        String imagePath = "/pokerImages/" + card.getImageFileName();
        ImageIcon cardImage = new ImageIcon(ImageUtils.class.getResource(imagePath));
        return resizeIcon(cardImage, 72, 96);
    }
	
	public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
