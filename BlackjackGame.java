import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BlackjackGame {

    // Core game components
    private Scanner playerInput = new Scanner(System.in);
    private Random rand = new Random();
    private int chips = 0;
    private Deck deck;
    private boolean stillAlive = true, hasWon = false, playerBusted = false, roundWon = false;
    private double shortPauseLength = .75;// 1
    private double mediumPauseLength = 1.25;// 1.5
    private double longPauseLength = 1.75;// 2

    // Constructor initializes the game deck
    public BlackjackGame() {
        deck = new Deck();
        playerHand();
        dealerHand();
    }

    private void youLose() {
        
    }

    // Checks if the player has won the game
    private void didWin() {
        System.out.println();
        if (playerBusted) {
            chips -= currentBet;
            System.out.println("You busted and lost $" + currentBet);
        } else if (handSum(playerHand) > handSum(dealerHand) && (!(handSum(playerHand) > 21) || handSum(dealerHand) > 21)){
            roundWon = true;
            chips += currentBet*2;
            System.out.println("You won by " + (handSum(playerHand) - handSum(dealerHand)) + "!");
            pause(shortPauseLength);
            System.out.println("You won $" + currentBet + "!");
            pause(longPauseLength);
            
        } else if (handSum(playerHand) == handSum(dealerHand)) {
            chips -= currentBet / 2;
            System.out.println("You tied with the dealer");
            pause(shortPauseLength);
            System.out.println("You lost $" + currentBet);
            pause(longPauseLength);
        } else if(handSum(playerHand) < handSum(dealerHand) && handSum(dealerHand) <= 21) {
            chips -= currentBet;
            System.out.println("Dealer won. You lost by " + (handSum(dealerHand) - handSum(playerHand)));
            pause(shortPauseLength);
            System.out.println("You lost $" + currentBet);
            pause(longPauseLength);
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

    private int safeGetIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(getInput());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void speedSetters(double slow, double med, double fast) {
        shortPauseLength = fast;
        mediumPauseLength = med;
        longPauseLength = slow;
    }
    private void gameSpeed() {
        validResponse = false;
        int gameSpeed = 0;
        System.out.println("At what speed do you want the game to play?");
        System.out.println("1: Cinematic");
        System.out.println("2: Brisk");
        System.out.println("3: Fast");
        while (!validResponse) {
            System.out.println("What is your choice? ");
            gameSpeed = safeGetIntInput(getInput());
            if (gameSpeed == 1 || gameSpeed == 2 || gameSpeed == 3) {
                validResponse = true;
                if (gameSpeed == 1) {
                    speedSetters(1.75, 1.25, 0.75);
                    System.out.println("You chose the Cinematic game speed.");
                } else if (gameSpeed == 2) {
                    speedSetters(1.0, 0.75, 0.5);
                    shortPauseLength = .5;
                    mediumPauseLength = .75;
                    longPauseLength = 1;
                    System.out.println("You chose the Brisk game speed.");
                } else {
                    speedSetters(0.1, 0.05, 0.0);
                    System.out.println("You chose the Fast game speed.");
                }
            } else {
                System.out.println("Please enter a valid response.");
            }
        }
    }

    



    public void startGame() {
        chips = rand.nextInt(980) + 20;
        ArrayList<String> openingDialogue = new ArrayList<>();
        openingDialogue.add("");
        openingDialogue.add("===========================================");
        openingDialogue.add("        You owe the mafia $10,000");
        openingDialogue.add("    If you don't pay the debt by tonight,");
        openingDialogue.add("              you will die.");
        openingDialogue.add("     You only have $" + chips + " to your name.");
        openingDialogue.add("     Good luck, your life depends on it");
        openingDialogue.add("===========================================");
        openingDialogue.add("You approach the first blackjack table. . .");
        openingDialogue.add("You sit down alone, it is just you and the dealer.");
        gameSpeed();
        for (int i = 0; i < openingDialogue.size(); i++){
            System.out.println(openingDialogue.get(i));
            pause(mediumPauseLength);
        }
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
            pause(longPauseLength);
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
            pause(shortPauseLength);
            System.out.println();
            pause(longPauseLength);
            showDealerHand();
            pause(longPauseLength);
            insurance();
            if (insurancePlaced) {
                System.out.println();
                pause(mediumPauseLength);
            }
            hitOrStand();
            pause(mediumPauseLength);
            dealerHitOrStand();
            System.out.println();
            pause(mediumPauseLength);
            didWin();
            if (insurancePlaced) {
                if (insurancePlaced && dealerHand.get(1).cardValue() == 10) {
                    System.out.println("You may have lost your hand, but insurance paid out!");
                    System.out.println("You get $" + insuranceBet*3);
                    chips += insuranceBet*3;
                    if (insuranceBet*3 > currentBet) {
                        System.out.println("At least you still made a profit of $" + (insuranceBet*3 - currentBet));
                    }   else if (insuranceBet*3 == currentBet) {
                        System.out.println("At least you didn't lose any money because you placed an insurnace bet");
                    }   else {
                        System.out.println("Even with your insurance bet you still lost $" + (currentBet - insuranceBet*3));
                    }
                } if (insurancePlaced && !roundWon) {
                    System.out.println();
                }
            }
            resetVars();
            if (chips <= 0) {
                stillAlive = false;
            } else if (chips >= 10000) {
                hasWon = true;
            } else {
                runGame();
            }
        } if (!stillAlive) {
            pause(mediumPauseLength);
            System.out.println("The mafia didn't get its money...");
            pause(longPauseLength);
            System.out.println("You have died.");
        }   
        else {
            
            System.out.println();
            pause(mediumPauseLength);
            System.out.println("You got enough money to pay off the mafia. With $" + (chips - 10000) + " to spare.");
            pause(longPauseLength);
            System.out.println("Would you want to play one more hand with that money?");
            System.out.println("1: Yes, I'll just play one more hand");
            System.out.println("2: No, I'll leave now that I can pay the mob off");
            
            try {
                int pushLuck = safeGetIntInput(getInput());
                if (pushLuck == 1) {
                    hasWon = false;
                    runGame();
                } 
                
                else if (pushLuck == 2) {
                    pause(mediumPauseLength);
                    System.out.println("You paid off your debts, you life another day.");
                    pause(mediumPauseLength);
                    System.out.println("Your balance is $" + (chips - 10000));
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
        insuranceBet = 0;
        insurancePlaced = false;
        roundWon = false;
        playerBusted = false;
        playerHand.clear();
        dealerHand.clear();
    }
// ================================================================
// PLAYER HAND LOGIC
// ================================================================

    private ArrayList<Card> playerHand;
    private int currentBet = 0;
    private boolean validResponse = false;

    // Initializes the player's hand
    private void playerHand() {
        playerHand = new ArrayList<>();
    }

    // Prints the player's hand
    private void showPlayerHand() {
        System.out.println("     [Your Hand]");
        pause(shortPauseLength);
        System.out.println(playerHand.get(0).getRank() + " of " + playerHand.get(0).getSuit());
        pause(shortPauseLength);
        System.out.println(playerHand.get(1).getRank() + " of " + playerHand.get(1).getSuit());
        pause(shortPauseLength);
        System.out.println("Total: " + handSum(playerHand));
        pause(shortPauseLength);
    }

    // Handles the player's choice to hit or stand
    private void hitOrStand() {
        validResponse = false;
        while (!validResponse) {
            System.out.println();
            pause(shortPauseLength);
            System.out.println("You can: hit(1), stand(2), or double down(3).");
            pause(shortPauseLength);
            System.out.print("You choose to: ");
            String choice = getInput();
            processDecision(choice);
        }
    }   

    private void didPlayerBust() {
        if (handSum(playerHand) > 21) {
            for (int i = 0; i < playerHand.size(); i++) {
                if (playerHand.get(i).getRank().equals("Ace")) {
                    playerHand.set(i, new Card("1", playerHand.get(i).getSuit()));
                    if (handSum(playerHand) <= 21) {
                        playerBusted = false;
                        break;
                    }
                }
            }
            playerBusted = true;
        }
    }

    // Handles the player's decision based on their input
    private void processDecision(String decision) {
        if (decision.equalsIgnoreCase("2") || decision.equalsIgnoreCase("stand")) {
            validResponse = true;
            System.out.println("You chose to stand");
        } else if (decision.equalsIgnoreCase("1") || decision.equalsIgnoreCase("hit")) {
            validResponse = true;
            pause(shortPauseLength);
            System.out.println("You chose to hit");
            pause(shortPauseLength);
            System.out.println();
            playerHand.add(deck.drawCard());
            System.out.println("     [Your Hand]");
            for (int i = 0; i < playerHand.size(); i++) {
                pause(shortPauseLength);
                System.out.println(playerHand.get(i).getRank() + " of " + playerHand.get(i).getSuit());
            }
            pause(shortPauseLength);
            System.out.println("Your total is now " + handSum(playerHand) + ".");
            didPlayerBust();
            if (!playerBusted) {
                hitOrStand();
            }
        } else if ((decision.equals("3") || decision.equalsIgnoreCase("double down")) && isBalanceEnough(currentBet * 2)) {
            validResponse = true;
            chips -= currentBet;
            currentBet *= 2;
            System.out.println("You chose to double down, your bet is now $" + currentBet);
            playerHand.add(deck.drawCard());
            System.out.println("     [Your Hand]");
            for (int i = 0; i < playerHand.size(); i++) {
                pause(shortPauseLength);
                System.out.println(playerHand.get(i).getRank() + " of " + playerHand.get(i).getSuit());
            }
            pause(shortPauseLength);
            System.out.println("Your total is now " + handSum(playerHand) + ".");
            System.out.println();
        } else if (decision.equalsIgnoreCase("double down")) {
            System.out.println("somthn up with double down");
        }   else {
            System.out.println("Please enter a valid response.");
        }
    }

    // Places the player's bet
    private void bets() {
        currentBet = 0;
        boolean placedBet = false;
        pause(mediumPauseLength);
        System.out.println("You have $" + chips);
        while ((currentBet <= 0 || chips <= currentBet) && !placedBet) {
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
        if (dealerHand.get(0).getRank().equalsIgnoreCase("Ace")) {
            System.out.println("Dealer has an Ace showing. Would you like to place an insurance bet? Payout is 3 to 1.");
            pause(mediumPauseLength);
            System.out.println("You can place between $0 and your current bet of $" + currentBet);
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
        didPlayerBust();
        System.out.println();
        pause(shortPauseLength);
        System.out.println("Dealer shows his second card.");
        pause(mediumPauseLength);
        System.out.println(dealerHand.get(0).getRank() + " of " + dealerHand.get(0).getSuit());
        pause(mediumPauseLength);
        System.out.println(dealerHand.get(1).getRank() + " of " + dealerHand.get(1).getSuit());
        pause(mediumPauseLength);
        System.out.println("Dealer's total: " + (dealerHand.get(0).cardValue() + dealerHand.get(1).cardValue()));
        pause(mediumPauseLength);
        System.out.println();
        pause(shortPauseLength);
        if (!playerBusted) {
            int total = 0;
            for (Card card : dealerHand) {
                int cardValue = card.cardValue();
                total += cardValue;
            }
            while (total < 17) {
                System.out.println();
                pause(shortPauseLength);
                System.out.println("The dealer hits");
                dealerHand.add(deck.drawCard());
                pause(shortPauseLength);
                System.out.println("And gets a " + dealerHand.get(dealerHand.size() - 1).getRank() + " of " + dealerHand.get(dealerHand.size() - 1).getSuit());
                total = 0;
                for (Card card : dealerHand) {
                    int cardValue = card.cardValue();
                    total += cardValue;
            }
        } if (total <= 21) {
            System.out.println("Dealer stands with a total of " + total);
        }
        System.out.println();
        }
    }
}
