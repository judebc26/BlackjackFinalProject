import java.util.ArrayList;
import java.util.Random;

public class Deck {
    public ArrayList<Card> deck;
    private Random rand;

    public Deck() {
        deck = new ArrayList<>();
        rand = new Random();
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        deck.clear();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        }
    }

    public void shuffleDeck() {
        deck.clear();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        } for (int x = 0; x < deck.size(); x++) {
            int y = rand.nextInt(deck.size());
            Card temp = deck.get(x);
            deck.set(x, deck.get(y));
            deck.set(y, temp);
        }// Ends for-loop
    }

    public Card drawCard() {
        Card temp = deck.get(0);
        deck.remove(0);
        return temp;
    }
}
