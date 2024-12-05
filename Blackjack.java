import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

public class BlackjackGame {

    public Scanner playerInput = new Scanner(System.in);
    public Random rand = new Random();
    public int chips = 0;

    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;

    public BlackjackGame() {

        deck();

    }// BlackjackGame()

    public void startGame() {

        chips = rand.nextInt(347) + 20; // Starting $$

        System.out.println();
        System.out.println("===========================================");
        System.out.println("            This is Blackjack");
        System.out.println("        You owe the mafia $10,000");
        System.out.println("    if you don't pay the debt by tonight,");
        System.out.println("              you will die.");
        System.out.println(  " You only have $" + chips + " to your name.");
        System.out.println("     Good luck, your life depends on it");
        System.out.println("===========================================");
        System.out.println();

    }// Ends startGame()

}// Ends BlackjackGame