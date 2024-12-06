import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class PlayerHand {

    private ArrayList<Card> deck;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private Random rand = new Random();



    public PlayerHand() {
        
        playerHand = new ArrayList<>();

    }// End of PlayerHand()

    private void hitOrStand(ArrayList <Card> varArray) {
        vaildResponse = false;
        //Split
        while (!validResponse) {
            if ((varArray.get(0).getRank()).equals(varArray.get(1).getRank())) { 
                while (!validResponse) {
                    splitAsked = true;
                    System.out.println("You can: hit, stand, double down, or split.");
                    System.out.println("You choose to ");
                    try {
                        decision = playerInput.next();
    
                    if (decision.toLowerCase() == "split" && isBalanceEnough(currentBet*2)) {
                        splitHappened = true;
                        validResponse = true;
                        split();
                    }// End of if statement
                    else if (decision.toLowerCase() == "split") {
                        System.out.println("You would need $" + currentBet*2 - chips + " to split.");
                    }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid answer, enter a valid amount.");
                    }
                }// End of while loop
    
            }// End of if statement for if a split occured
                 //                         |                          //
        // =============IF NO SPLIT V CODE OCCURS============ //
        
            if (splitAsked == false) {
                System.out.println("You can: hit, stand, or double down.");
                System.out.println("You choose to ");
                decision = playerInput.next();
            }
            if (splitHappened == false) {
                if (decision.toLowerCase().equals("stand")) {
                    didWin();
                } 
            
            else if (decision.toLowerCase().equals("hit")) {
                varArray.add(deck.get(0));
                deck.remove(0);
                hitOrStand(varArray);
            }// End of hit
            
            else if (decision.toLowerCase().equals("double down") && isBalanceEnough(currentBet*2)) {
                varArray.add(deck.get(0));
                deck.remove(0);
                currentBet += currentBet;
            } 
            else if (decision.toLowerCase().equals("double down") && !isBalanceEnough(currentBet*2)) {
                System.out.println("You would need $" + currentBet-chips + " more to double down.");
            }// End of double down
            
                else {
                    System.out.println("Please enter a valid response");
                    System.out.println();
                }
            }
        }// End of validResponse while loop
    }
//  =====================================================================================================
    private void bets() {
        int currentBet = 0;
        System.out.println("You have $" + chips);
        while (currentBet <= 0) {
            System.out.print("What is your bet? $");
                try {
                    Sring bet = playerInput.next();  // Read user input
                    currentBet = Integer.parseInt(bet);
                    if(isBalanceEnough(currentBet) && currentBet > 0){
                        System.out.println("You have $" + chips + " left.");
                }// End of if statement
                else if (currentBet <= 0) {
                    System.out.println("Time is not on your side, you must bet.");
                }
            }// End of try() 
            catch (NumberFormatException e) {
                System.out.println("Invalid Bet, enter a valid amount.");
                currentBet = 0;
            }// End of catch()
        }
        
    }// End of bets()

    public boolean isBalanceEnough(int wager) {
        if (wager <= chips) {
            chips -= wager;
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
        if (dealerHand.get(0).getRank().equals("Ace")){ //if dealer is showing an Ace
            System.out.println("Dealer has an Ace showing, would you like to side bet insurance? Payout is 3 to 1");
        }// End of if statement
        while (!vaildResponse){
            String insuranceDecision = playerInput.next();
            if (insuranceDecision.toLowerCase().equals("yes")){
                try {
                    System.out.print("What is your insurance bet? $");
                    String bet = playerInput.next();
                    insuranceBet = Integer.parseInt(this.bet);
                    if (isBalanceEnough(insuranceBet)) {
                        System.out.println("Your balance is now $" + chips);
                        System.out.println();
                        vaildResponse = true;
                    }// End of if statement #2
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Bet, enter a valid amount.");
                }  
            }// End of if statement #1
            else if (insuranceDecision.toLowerCase().equals("no")) {
                System.out.println("You chose not to place an insurance bet.");
                System.out.println();
                vaildResponse = true;
            }// End of else-if statement
            else {
                System.out.println("Please enter a yes or a no.");
                System.out.println();
            }// End of else statement
        }// End of while loop
    }// End of insurance()
//  =====================================================================================================
    private void split() {
        if (splitHasBeenDone) {
            preformSplit(2);
            splitHasBeeDone = false;
        }// End of if statement
        else {
            preformSplit(0);
            preformSplit(1);
        }// End of else statement
    }// End of split()

    private void preformSplit(int handIndex) {
        ArrayList <Card> splitHand = new ArrayList<>();
        splitHand.add(playerHand.get(handIndex));
        split.add(deck.get(0));
        deck.remove(0);

        System.out.println("Split " + (handIndex + 1) + ":");
        System.out.println(splitHand.get(0).getRank() + " of " + splitHand.get(0).getRank());
        System.out.println(splitHand.get(1).getRank() + " of " + splitHand.get(1).getRank());

        hitOrStand(splitHand);
    }// End of preformSplit()


}// End of PlayerHand