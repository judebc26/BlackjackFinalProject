import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

public class BlackjackGame {

    private ArrayList<Card> deck;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private Random rand = new Random();
    Scanner playerInput = new Scanner(System.in);
    
    private boolean vaildResponse = false; // <== never change from false
    private int chips = rand.nextInt(347) + 20; //starting chips
    boolean splitHasBeenDone;
    boolean splitHappened;
    boolean splitAsked;
    boolean goalMet;
    String decision;

        playerHand = new ArrayList<>();

    private void hitOrStand(ArrayList <Card> varArray) {
        
        vaildResponse = false;
        if (varArray.get(0).getRank() == varArray.get(1).getRank()) { //if 2 of the same number (split?)

            while (vaildResponse == false) {

                splitAsked = true;
                System.out.println("You can: hit, stand, double down, or split.");
                System.out.println("You choose to ");
                decision = playerInput.next();

                if (decision.toLowerCase() == "split" && isBalanceEnough(currentBet*2)) {
                    splitHappened = true;
                    split();
                }

            }

        }
// WORK ON THIS NEXT <---------------------------------------------------------------------------- UNFINISHED DO NOW

        if (splitAsked == false) {

            System.out.println("You can: hit, stand, or double down.");
            System.out.println("You choose to ");
            decision = playerInput.next();
        }
        if (splitHappened == false) {

            if (decision.toLowerCase() == "stand") {
                didWin();
            } 
            
            else if (decision.toLowerCase() == "hit") {

                varArray.add(deck.get(0));
                deck.remove(0);

            } 
            
            else if (decision.toLowerCase() == "double down" && isBalanceEnough(currentBet*2)) {
                //double down here
            }
            
            else {
                System.out.println("Please enter a valid response");
                System.out.println();
            }
        }
    }
        

    //TAKES BETS FROM PLAYER
    int currentBet = 0;
    private void bets() {
       
        System.out.println("You have $" + chips);
        System.out.print("What is your first bet? $");
        String bet = playerInput.next();  // Read user input
        currentBet = Integer.parseInt(bet);
        chips -= currentBet;
        System.out.println("You have $" + chips + " left.");
        
    }
    //FAST WAY TO CHECK IF WAGER IS TOO BIG
    public boolean isBalanceEnough(int wager) {
        if (wager > chips) {
            System.out.println("Your bet is $" + (wager - chips) + "too large");
            return false;
        }
        else {
            return true;
        }
            
    }
    //INSURANCE (IF DEALER SHOWS ACE)
    int insuranceBet = 0;
    private void insurance() {
        
        while (vaildResponse == false){
            if (dealerHand.get(0).getRank() == "Ace"){ //if dealer is showing an Ace
                System.out.println("Dealer has an Ace showing, would you like to bet insurance? Payout is 3 to 1");
                //player says yes or no
                String insuranceDecision = playerInput.next();
                if (insuranceDecision.toLowerCase() == "yes"){
                    System.out.print("What is your insurance bet? $");
                    String bet = playerInput.next(); //this is the string the player inputs
                    insuranceBet = Integer.parseInt(bet); //this is the integer the game uses
                    if (isBalanceEnough(insuranceBet)) {
                        System.out.println();
                        vaildResponse = true;
                    }
                } else if (insuranceDecision.toLowerCase() == "no") {
                    System.out.println();
                    vaildResponse = true;
                } else {
                    System.out.println("Please enter a valid response");
                    System.out.println();
                }
            }
        }
    }
    //SPLITS
    private void split() {

        if (splitHasBeenDone == true) {

            ArrayList<Card> split3;
            split3 = new ArrayList<>();
            split3.add(playerHand.get(0));
            split3.add(deck.get(0));
            deck.remove(0);

            System.out.println("Third Split:");
            System.out.println(split3.get(0).getRank() + " of " + split3.get(0).getSuit());
            System.out.println(split3.get(1).getRank() + " of " + split3.get(1).getSuit());

            hitOrStand(split3);
            splitHasBeenDone = false;
        }

        else {
            ArrayList<Card> split1;
            split1 = new ArrayList<>();
            split1.add(playerHand.get(0));
            split1.add(deck.get(0));
            deck.remove(0);

            ArrayList<Card> split2;
            split2 = new ArrayList<>();
            split2.add(playerHand.get(1));
            split2.add(deck.get(0));
            deck.remove(0);

            System.out.println("First Split:");
            System.out.println(split1.get(0).getRank() + " of " + split1.get(0).getSuit());
            System.out.println(split1.get(1).getRank() + " of " + split1.get(1).getSuit());

            hitOrStand(split1);

            System.out.println("Second Split: ");
            System.out.println(split2.get(0).getRank() + " of " + split2.get(0).getSuit());
            System.out.println(split2.get(1).getRank() + " of " + split2.get(1).getSuit());

            hitOrStand(split2);
        }
        
    }
