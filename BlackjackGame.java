import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BlackjackGame {

    // Core game components
    private Scanner playerInput = new Scanner(System.in);
    private Random rand = new Random();
    private int chips = 0;
    private Deck deck;
    private boolean stillAlive = true, hasWon = false;
    private double shortPauseLength = .1;// 1
    private double mediumPauseLength = .15;// 1.5
    private double longPauseLength = .2;// 2

    // Constructor initializes the game deck
    public BlackjackGame() {
        deck = new Deck();
        playerHand();
        dealerHand();
    }

    // Checks if the player has won the game
    private void didWin() {
        System.out.println();
        System.out.println("     [Your Hand]");
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.println(playerHand.get(i).getRank() + " of " + playerHand.get(i).getSuit());
            pause(shortPauseLength);
        }
        System.out.println("Your hand total: " + handSum(playerHand));
        pause(mediumPauseLength);
        System.out.println();
        pause(mediumPauseLength);
        System.out.println("   [Dealers Hand]");
        pause(shortPauseLength);
        System.out.println();
        for (int i = 0; i < dealerHand.size(); i++) {
            System.out.println(dealerHand.get(i).getRank() + " of " + dealerHand.get(i).getSuit());
            pause(shortPauseLength);
        }
        System.out.println();
        pause(mediumPauseLength);
        System.out.println("Dealer total: " + handSum(dealerHand));
        pause(shortPauseLength);
        System.out.println();
        if (handSum(playerHand) > handSum(dealerHand) && (!(handSum(playerHand) > 21) || handSum(dealerHand) > 21)){
            System.out.println("You won! By " + (handSum(playerHand) - handSum(dealerHand)));
            System.out.println();
            chips += currentBet;
        } else if (handSum(playerHand) == handSum(dealerHand)) {
            System.out.println("Dealer won the tie");
            System.out.println();
            chips -= currentBet;
        } else if(handSum(playerHand) < handSum(dealerHand)) {
            System.out.println("Dealer won. You lost by " + (handSum(dealerHand) - handSum(playerHand)));
            System.out.println();
            chips -= currentBet;
        }
        System.out.println();
    }

    private int handSum(ArrayList<Card> hand) {
        int sum = 0;
        for (int i = 0; i < hand.size(); i++) {
            sum += hand.get(i).cardValue();
        }   if (sum > 21) {
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getRank() == "Ace") {
                    sum -= 10;
                    i += 100;
                }
            }
        }
        return sum;
    }

    // Reads input from the player
    private String getInput() {
        return playerInput.next();
    }

    // Starts the game and introduces the scenario
    public void startGame() {
        chips = rand.nextInt(347) + 20; // Random initial chip amount
        System.out.println();
        System.out.println("===========================================");
        pause(mediumPauseLength);
        System.out.println("        You owe the mafia $10,000");
        pause(mediumPauseLength);
        System.out.println("    If you don't pay the debt by tonight,");
        pause(mediumPauseLength);
        System.out.println("              you will die.");
        pause(longPauseLength);
        System.out.println("     You only have $" + chips + " to your name.");
        pause(mediumPauseLength);
        System.out.println("     Good luck, your life depends on it");
        pause(mediumPauseLength);
        System.out.println("===========================================");
        System.out.println();
        pause(longPauseLength);
        System.out.println("You approach the first blackjack table. . .");
        pause(longPauseLength);
        System.out.println("You sit down alone, it is just you and the dealer.");
        pause(longPauseLength);
        runGame();
    }

    public void pause(double seconds) {
        try {
            Thread.sleep((long) (seconds*1000)); // Pause for 1 second
        } catch (InterruptedException e) {  } 
    }

    // Main game loop
    private void runGame() {
        while (stillAlive && !hasWon) {
            System.out.println();
            System.out.println("The dealer shuffles the deck...");
            pause(mediumPauseLength);
            deck.shuffleDeck();
            System.out.println("The dealer deals the cards...");
            pause(mediumPauseLength);
            dealCards();
            System.out.println();
            bets();
            pause(longPauseLength);
            showPlayerHand();
            pause(longPauseLength);
            showDealerHand();
            System.out.println();
            pause(longPauseLength);
            insurance();
            if (insurancePlaced) {
                System.out.println();
                pause(mediumPauseLength);
            }
            hitOrStand();
            System.out.println();
            pause(mediumPauseLength);
            showDealerHand();
            System.out.println();
            pause(mediumPauseLength);
            dealerHitOrStand();
            System.out.println();
            pause(mediumPauseLength);
            didWin();
            resetVars();
            if (chips >= 10000) {
                hasWon = true;
            } else {
                runGame();
            }
        } if (!stillAlive) {
            System.out.println();
            pause(mediumPauseLength);
            System.out.println("The mafia didn't get its money...");
            pause(longPauseLength);
            System.out.println("You have died.");
        }   else {
                System.out.println();
                pause(mediumPauseLength);
                System.out.println("You got enough money to pay off the mafia. With $" + (chips - 10000) + " to spare.");
                pause(longPauseLength);
                System.out.println("Would you want to play one more hand with that money?");
                try {
                    String pushLuck = getInput();
                    if (pushLuck.equals("yes")) {
                        hasWon = false;
                     runGame();
                    } 
                }   catch (Exception e) {

                }
        }
    }
    

    // Deals two cards each to the player and dealer
    private void dealCards() {
        for (int i = 0; i < 2; i++) {
            playerHand.add(deck.drawCard());
            dealerHand.add(deck.drawCard());
        }
    }

    // Resets all variables in between hands
    private void resetVars() {
        currentBet = 0;
        validResponse = false;
        currentHandBet = 0;
        insuranceBet = 0;
        insurancePlaced = false;
        handBets.clear();
    }
// ================================================================
// PLAYER HAND LOGIC
// ================================================================

    private ArrayList<Card> playerHand;
    private int currentBet = 0;
    private boolean validResponse = false;
    private int currentHandBet = 0;
    private ArrayList<Integer> handBets;

    // Initializes the player's hand
    private void playerHand() {
        playerHand = new ArrayList<>();
    }

    // Prints the player's hand
    private void showPlayerHand() {
        System.out.println("     [Your hand]");
        pause(shortPauseLength);
        System.out.println(playerHand.get(0).getRank() + " of " + playerHand.get(0).getSuit());
        pause(shortPauseLength);
        System.out.println(playerHand.get(1).getRank() + " of " + playerHand.get(1).getSuit());
        pause(shortPauseLength);
        System.out.println("Total: " + handSum(playerHand));
        pause(shortPauseLength);
        System.out.println("");

    }

    // Handles the player's choice to hit or stand
    private void hitOrStand() {
        while (!validResponse) {
            System.out.println();
            pause(shortPauseLength);
            System.out.println("You can: hit, stand, or double down.");
            pause(shortPauseLength);
            System.out.print("You choose to: ");
            String choice = getInput();
            System.out.println();
            processDecision(choice);
        }
    }   


    // Handles the player's decision based on their input
    private void processDecision(String decision) {
        if (decision.equalsIgnoreCase("stand")) {
            System.out.println("You chose to stand");
            didWin();
        } else if (decision.equalsIgnoreCase("hit")) {
            System.out.println("You chose to hit");
            playerHand.add(deck.drawCard());
            System.out.println("You get a " + playerHand.get(playerHand.size() - 1).getRank() + " of " + playerHand.get(playerHand.size() - 1).getSuit());
            System.out.println("Your total is now " + handSum(playerHand) + ".");
            hitOrStand();
        } else if (decision.equalsIgnoreCase("double down") && isBalanceEnough(currentBet * 2)) {
            playerHand.add(deck.drawCard());
            currentBet *= 2;
        } else {
            System.out.println("Please enter a valid response.");
        }
    }

    // Places the player's bet
    private void bets() {
        currentBet = 0;
        boolean placedBet = false;
        pause(mediumPauseLength);
        System.out.println("You have $" + chips);
        while ((currentBet <= 0 || !(chips <= currentBet)) && !placedBet) {
            pause(shortPauseLength);
            System.out.print("What is your bet? $");
            try {
                String bet = getInput();
                currentBet = Integer.parseInt(bet);
                if (isBalanceEnough(currentBet) && currentBet > 0) {
                    chips -= currentBet;
                    pause(shortPauseLength);
                    System.out.println("You have $" + chips + " left.");
                    pause(shortPauseLength);
                    System.out.println();
                    placedBet = true;
                } else {
                    System.out.println("Invalid bet amount. Please try again.");
                    pause(shortPauseLength);
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid amount.");
                pause(shortPauseLength);
                System.out.println();
                currentBet = 0;
            }
            System.out.println();
        }
    }

    // Checks if the player has enough balance for a bet
    public boolean isBalanceEnough(int wager) {
        if (wager <= chips) {
            return true;
        } else {
            return false;
        }
    }

    // Handles insurance side bets
    private int insuranceBet = 0;
    private boolean insurancePlaced = false;
    private void insurance() {
        boolean validResponse = false;
        if (getDealerCard().getRank().equalsIgnoreCase("Ace")) {
            System.out.println("Dealer has an Ace showing. Would you like to place an insurance bet? Payout is 3 to 1.");
            pause(mediumPauseLength);
            System.out.println("You can place between $0-$" + currentBet);
            pause(shortPauseLength);
            System.out.print("Enter your insurance bet: $");
            try {
                insuranceBet = Integer.parseInt(getInput());
                if (isBalanceEnough(insuranceBet) && insuranceBet > 0 && insuranceBet <= currentBet) {
                    System.out.println("Your balance is now $" + chips);
                    insurancePlaced = true;
                    validResponse = true;
                }   else if (!isBalanceEnough(insuranceBet)) {
                    System.out.println("Your balance is too low.");
                }   else if (insuranceBet == 0) {
                    System.out.println("You chose to not place an insurance bet");
                    validResponse = true;
                }   else if (insuranceBet > currentBet) {
                    System.out.println("The maximum insurance bet is your current bet. $" + currentBet);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid amount.");
            }
        }
    }

    // ============================
    // DEALER HAND LOGIC
    // ============================

    private ArrayList<Card> dealerHand;

    // Initializes the dealer's hand
    private void dealerHand() {
        dealerHand = new ArrayList<>();
    }

    private void showDealerHand() {
        System.out.println();
        pause(shortPauseLength);
        System.out.println("   [Dealers hand]");
        pause(shortPauseLength);
        System.out.println(dealerHand.get(0).getRank() + " of " + dealerHand.get(0).getSuit());
        pause(shortPauseLength);
        System.out.println("Hidden Card");
        pause(shortPauseLength);
        System.out.println();
    }

    // Handles the dealer's decisions to hit or stand
    private void dealerHitOrStand() {
        System.out.println();
        System.out.println("Dealer shows his second card.");
        System.out.println(dealerHand.get(0).getRank() + " of " + dealerHand.get(0).getSuit());
        System.out.println(dealerHand.get(1).getRank() + " of " + dealerHand.get(1).getSuit());
        System.out.println("Dealer's total: $" + (dealerHand.get(0).getRank() + dealerHand.get(1).getRank()));
        
        int total = 0;
        for (Card card : dealerHand) {
            int cardValue = card.cardValue();
            total += cardValue;
        }
        
        while (total < 17) {
            System.out.println("The dealer hits");
            dealerHand.add(deck.drawCard());
            System.out.println("And gets a " + dealerHand.get(dealerHand.size()).getRank() + " of " + dealerHand.get(dealerHand.size()).getSuit());
            total = 0;
            for (Card card : dealerHand) {
                int cardValue = card.cardValue();
                total += cardValue;
            }
        } 
    }

    // Returns the dealer's visible card
    private Card getDealerCard() {
        return dealerHand.get(0);
    }
}
