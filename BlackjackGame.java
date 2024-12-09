import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

public class BlackjackGame {

    public Scanner playerInput = new Scanner(System.in);
    public Random rand = new Random();
    public int chips = 0;

    public BlackjackGame() {
        Deck deck = new Deck();
    }

    public void didWin() {
        // Placeholder for win logic
        System.out.println("Win check logic here");
    }

    public String getInput() {
        return playerInput.next();
    }

    public int getChips() {
        return chips;
    }

    public void startGame() {
        chips = rand.nextInt(347) + 20;
        System.out.println();
        System.out.println("===========================================");
        System.out.println("        You owe the mafia $10,000");
        System.out.println("    if you don't pay the debt by tonight,");
        System.out.println("              you will die.");
        System.out.println("            This is Blackjack");
        System.out.println("     You only have $" + chips + " to your name.");
        System.out.println("     Good luck, your life depends on it");
        System.out.println("===========================================");
        System.out.println();
        playerHand();
        dealerHand();

    }
// ===============Player=Hand===================================================
    public ArrayList<Card> playerHand;
    private int numHands = 0;
    private boolean splitHappened = false;
    private boolean splitAsked = false;
    private int currentBet = 0;
    private boolean validResponse = false;
    private int currentHandBet = 0;
    ArrayList<Integer> handBets;


    public void playerHand() {
        
        playerHand = new ArrayList<>();
        numHands += 1;

    }// End of PlayerHand()

    private void hitOrStand(ArrayList <Card> varArray) {
        splitHappened = false;
        splitAsked = false;
        handBets = new ArrayList<>();
        currentHandBet = currentBet;
        handBets.add(currentHandBet);
        meatAndPotatoes(varArray, true);
    }// End of hitOrStand()

    private void hitOrStandSplit(ArrayList <Card> varArray) {
    
        validResponse = false;
        meatAndPotatoes(varArray, false);
    }// End of hitOrStandSplit()
    private void meatAndPotatoes(ArrayList <Card> varArray, boolean gravy) {
        String decision;
        while (!validResponse) {
            if (varArray.size() == 2 && (varArray.get(0).getRank()).equals(varArray.get(1).getRank())) { 
                while (!validResponse) {
                    splitAsked = true;
                    System.out.println("You can: hit, stand, double down, or split.");
                    System.out.println("You choose to ");
                    try {
                        decision = getInput();
    
                    if (decision.toLowerCase() == "split" && isBalanceEnough(currentBet*2)) {
                        splitHappened = true;
                        validResponse = true;
                        split();
                        handBets.add(currentHandBet);
                    }// End of if statement
                    else if (decision.toLowerCase() == "split") {
                        System.out.println("You would need $" + currentBet*2 - getChips() + " to split.");
                    }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid answer, enter a valid amount.");
                    }
                }// End of while loop
    
            }// End of if statement for if a split occured
                 //                         |                          //
        // =============IF NO SPLIT V CODE OCCURS============ //
        
            if (!splitAsked) {
                System.out.println("You can: hit, stand, or double down.");
                System.out.println("You choose to ");
                decision = getInput();
            }
            if (!splitHappened) {
                if (decision.toLowerCase().equals("stand")) {
                    didWin();
                } 
            
            else if (decision.toLowerCase().equals("hit")) {
                varArray.add(drawCard());
                hitOrStand(varArray);
            }// End of hit
            
            else if (decision.toLowerCase().equals("double down") && isBalanceEnough(currentBet*2)) {
                varArray.add(drawCard());
                currentBet *= 2;
            } 
            else if (decision.toLowerCase().equals("double down") && !isBalanceEnough(currentBet*2)) {
                System.out.println("You would need $" + currentBet - getChips() + " more to double down.");
            }// End of double down
            
                else {
                    System.out.println("Please enter a valid response");
                    System.out.println();
                }
            }
            if (splitHappened && gravy) {
                for (int i = 0; i < numHands; i++) {
                    System.out.println("Hand " + (i + 1) + " with bet $" + handBets.get(i));

                    hitOrStand(varArray);
                    // Add additional handling for hit, stand, or double down for each hand
                    // Make sure you call the appropriate methods based on each hand's state
                }
            } 
        }
    }

//  =====================================================================================================
    private void bets() {
        int currentBet = 0;
        System.out.println("You have $" + getChips());
        while (currentBet <= 0) {
            System.out.print("What is your bet? $");
                try {
                    Sring bet = getInput();  // Read user input
                    currentBet = Integer.parseInt(bet);
                    if(isBalanceEnough(currentBet) && currentBet > 0){
                        System.out.println("You have $" + getChips() + " left.");
                }// End of if statement
                else if (currentBet <= 0) {
                    System.out.println("Time is not on your side, you must place a bet or face death.");
                }
            }// End of try() 
            catch (NumberFormatException e) {
                System.out.println("Invalid Bet, enter a valid amount. Try something funny like this again and you shall swim with the fishes");
                currentBet = 0;
            }// End of catch()
        }
        
    }// End of bets()

    public boolean isBalanceEnough(int wager) {
        if (wager <= getChips()) {
            getChips() -= wager;
            return true;
        }// End of if statement
        else {
            return false;
        }// End of else statement
    }// End of isBalanceEnough()
//  =====================================================================================================
        private void insurance() { // Insurance only happens if dealer is showing an Ace
        int insuranceBet = 0;
        boolean validResponse;
        if (getDealerCard().getRank().equals("Ace")){ //if dealer is showing an Ace
            System.out.println("Dealer has an Ace showing, would you like to side bet insurance? Payout is 3 to 1");
        }// End of if statement
        while (!validResponse){
            String insuranceDecision = getInput();
            if (insuranceDecision.toLowerCase().equals("yes")){
                try {
                    System.out.print("What is your insurance bet? $");
                    String bet = getInput();
                    insuranceBet = Integer.parseInt(this.bet);
                    if (isBalanceEnough(insuranceBet)) {
                        System.out.println("Your balance is now $" + getChips());
                        System.out.println();
                        validResponse = true;
                    }// End of if statement #2
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Bet, enter a valid amount.");
                }  
            }// End of if statement #1
            else if (insuranceDecision.toLowerCase().equals("no")) {
                System.out.println("You chose not to place an insurance bet.");
                System.out.println();
                validResponse = true;
            }// End of else-if statement
            else {
                System.out.println("Please enter a yes or a no.");
                System.out.println();
            }// End of else statement
        }// End of while loop
    }// End of insurance()
//  =====================================================================================================
    private void split() {
        if (splitHappened) {
            preformSplit(2);
            splitHappened = false;
        }// End of if statement
        else {
            preformSplit(0);
            preformSplit(1);
        }// End of else statement
    }// End of split()

    private void preformSplit(int handIndex) {
        ArrayList <Card> splitHand = new ArrayList<>();
        splitHand.add(playerHand.get(handIndex));
        splitHand.add(drawCard());

        System.out.println("Split " + (handIndex + 1) + ":");
        System.out.println(splitHand.get(0).getRank() + " of " + splitHand.get(0).getRank());
        System.out.println(splitHand.get(1).getRank() + " of " + splitHand.get(1).getRank());

        hitOrStand(splitHand);
    }// End of preformSplit()

// =======================================================================================================================================================
//              DEALER HAND
// =======================================================================================================================================================
    public ArrayList<Card> dealerHand;    
    public void dealerHand() {
        dealerHand = new ArrayList<>();
        System.out.println();
        System.out.println(dealerHand.get(0).getRank() + " of " + dealerHand.get(0).getSuit());
        System.out.println("Hidden Card");
    }

    private void dealerHitOrStand() {
        int tempTotal = 0;
        for (int i = 0; i < dealerHand.size(); i++) {
            tempTotal += dealerHand.get(i).cardValue();
        }
        if (tempTotal < 17) {
            dealerHand.add(deck.get(0));
            deck.remove(0);
        }
    }

    public Card getDealerCard() {
        return dealerHand.get(0);
    }
// =======================================================================================================================================================
//              DEALER HAND
// =======================================================================================================================================================
    public ArrayList<Card> deck;

    public void Deck() {
        deck = new ArrayList<>();
        rand = new Random();
        initializeDeck();
        shuffleDeck();
    }

    public void initializeDeck() {
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
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        Card temp = deck.get(0);
        deck.remove(0);
        return temp;
    }
}//End of BlackjackGame