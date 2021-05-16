/* TreeDriver: This program uses a binary tree to play a game with the user. The user picks a video game character, and the program asks the user
a series of questions to guess the choice. This Driver class contains a command line user interface to play the game, as well as the method to play the
game using the tree. The user can choose from the command line to play the game, save the game to a file, and load the game to the file. Methods
are then invoked in the TreeGame class.
Assignment #3: CS2610, Dr. Andrew Godbout
Author: Luke Schipper (142196)
Date: November 10, 2017
*/

import examples.Position;

import java.io.IOException;
import java.util.Scanner;

public class TreeDriver {

    public static void main(String[] args) {
        // set default game
        TreeGame game = new TreeGame("Is it a Nintendo character?", "Mario", "Sonic");
        Scanner keybd = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Video Game Character Guessing Game!");
        // user inteface
        do {
            choice = 4;
            System.out.println("Choose an option number:\n1)Play the game\n2)Read from a file\n3)Write to a file\n4)Quit");
            if (keybd.hasNextInt()) choice = keybd.nextInt(); // get choice of number
            keybd.nextLine();
            switch (choice) {
                case 1: // play the game
                    do {
                        play(game.root(), game, keybd); // play game, asking if user wants to play again
                        System.out.println("Would you like to play again? (yes/no)");
                    }
                    while (keybd.nextLine().trim().equalsIgnoreCase("yes"));
                    break;
                case 2: // read from a file
                    System.out.println("What file?");
                    try {
                        game.read(keybd.nextLine().trim()); // read from specified file, refreshing the game if error occurs
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        game = new TreeGame("Is it a Nintendo character?", "Mario", "Sonic");
                    }
                    break;
                case 3: // write to a file
                    System.out.println("What file?");
                    try {
                        game.write(keybd.nextLine().trim()); // write to specified file, catching IOExceptions
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
            }
        }
        while (choice >= 1 && choice <= 3); // loop while user does not enter option 4
        System.out.print("Game ended.");
    }

    // recursive method to play game, traversing through each node to ask each question and give the answer
    private static void play(Position<String> p, TreeGame game, Scanner keybd) {
        String answer;
        if (game.isInternal(p)) {  // checks to see if node is a question
            System.out.println(p.getElement() + " (yes/no)"); // asks question
            answer = keybd.nextLine().trim();  // gets user input (y/n)
            if (answer.equalsIgnoreCase("yes")) play(game.left(p), game, keybd); // if yest, go left down the tree
            else play(game.right(p), game, keybd); // else, go right
        } else { // node is an answer
            System.out.println("Is it " + p.getElement() + "? (yes/no)"); // ask if guess is correct?
            answer = keybd.nextLine().trim();
            if (answer.equalsIgnoreCase("yes")) System.out.println("I win!");
            else { // if incorrect
                String question;
                do {
                    System.out.println("I'm stumped. Who is it?"); // asks who user's character is
                    answer = keybd.nextLine().trim();
                    System.out.println("Please type a question that is yes for " + p.getElement() // asks for new descriptive question
                            + " but is no for " + answer + ".");
                    question = keybd.nextLine().trim();
                }
                while (answer.equals(""));
                game.addQuestion(p, question, answer); // adds new question to tree
                System.out.println("Thanks!");
            }
        }
    }
}
