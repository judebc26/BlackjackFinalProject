public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int cardValue() {
        if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
            return 10;
        } else if (rank.equals("Ace")) {
            return 11;
        } else {
            return Integer.parseInt(rank);
        }
    }
}
