public class Card {

        private String rank;
        private String suit; 

        public Card(String rank, String suit){
            this.rank = rank;
            this.suit = suit;
        }// Ends Card()

        public String getRank() {
            return rank;
        }// Ends getRank()

        public String getSuit() {
            return suit;
        }// Ends getSuit()

        public int cardValue(Card card){
            String cardsRank = card.getRank();
            if (cardsRank.equals("Jack") || cardsRank.equals("Queen") || cardsRank.equals("King")) {
                return 10;
            }// Ends if if statement
            else {
                return 11;
            }// Ends else statement
        }// Ends cardValue()
}// Ends Card