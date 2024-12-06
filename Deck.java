import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
//  =====================================================================================================
public class Deck {

    public ArrayList<Card> deck;

    public Deck() {
        
        deck = new ArrayList<>();
        initializeDeck();
        shuffleDeck(); // Might be better to put this somewhere else

    }// Ends deck()
//  =====================================================================================================
    private void initializeDeck() {

        deck.clear();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }// Ends for-loop #2
        }// Ends for-loop #1
    }// Ends initializeDeck()
//  =====================================================================================================
    private void shuffleDeck() {

        for (int x = 0; x < deck.size(); x++) {

            int y = rand.nextInt(deck.size());
            Card temp = deck.get(x);
            deck.set(x, deck.get(y));
            deck.set(y, temp);

        }// Ends for-loop
    }// Ends shuffleDeck()
}// Ends Deck