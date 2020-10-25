package org.douggschwind.games.dicegames.boggle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is the entry point into which you can see the game being setup and the possible words on
 * the game board to be found.
 * @author Doug Gschwind
 */
public class Boggle {
    private static final String GAME_BOARD_HEADER = "\t-----------";

    private Set<String> findAllWordsPresent(DieLetter[][] gameBoard) {
        Set<String> result = new HashSet<>();
        // Big TODO here!
        return result;
    }

    private void displayGameBoard(DieLetter[][] gameBoard) {
        System.out.println("Here is the game board:");
        System.out.println(GAME_BOARD_HEADER);

        for (int row = 0;row < 4;row++) {
            System.out.print("\t");
            for (int col = 0;col < 4;col++) {
                DieLetter dieLetter = gameBoard[row][col];
                if (dieLetter.isQu()) {
                    System.out.print("Qu");
                } else {
                    String letter = "" + dieLetter.getLetter();
                    System.out.print(letter.toUpperCase() + " ");
                }
                System.out.print(" ");
            }
            System.out.println("");
        }

        System.out.println(GAME_BOARD_HEADER);
    }

    public void playGame() {
        DieLetter[][] gameBoard = BoggleDice.shake();
        displayGameBoard(gameBoard);

        Set<String> validWordsPresent = findAllWordsPresent((gameBoard));
        List<String> sortedValidWords = new ArrayList(validWordsPresent);
        Collections.sort(sortedValidWords);
        System.out.println("Here are all the valid words present in this game board:");
        for (String validWordPresent : sortedValidWords) {
            System.out.println("\t" + validWordPresent);
        }
    }

    public static void main(String[] args) {
        Boggle controller = new Boggle();
        controller.playGame();

        while (true) {
            System.out.println("Play Again? y or n");
            byte[] endUserProvidedValue = new byte[80];
            try {
                System.in.read(endUserProvidedValue);
            } catch (IOException ignored) {
                System.exit(1);
            }
            String userAnswer = new String(endUserProvidedValue).trim();
            if ("n".equalsIgnoreCase(userAnswer)) {
                System.out.println("Goodbye");
                System.exit(0);
            }
            controller.playGame();
        }
    }
}
