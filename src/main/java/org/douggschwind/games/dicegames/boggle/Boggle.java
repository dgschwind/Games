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

    private static final Set<String> validWords = new HashSet<>();
    private static final WordTree wordTree = new WordTree();

    static {
        // Obviously a heavily abbreviated set of valid words from those of the English language, just to keep
        // this reasonable and allow for testing to be considered conclusive.
        validWords.add("act");
        validWords.add("acted");
        validWords.add("actor");
        validWords.add("any");
        validWords.add("anything");
        validWords.add("apple");
        validWords.add("ask");
        validWords.add("axe");
        validWords.add("axed");
        validWords.add("axes");
        validWords.add("bat");
        validWords.add("bats");
        validWords.add("bet");
        validWords.add("bets");
        validWords.add("borrow");
        validWords.add("bought");
        validWords.add("orange");
        validWords.add("part");
        validWords.add("parts");
        validWords.add("port");
        validWords.add("ports");
        validWords.add("pot");
        validWords.add("pots");
        validWords.add("put");
        validWords.add("puts");
        validWords.add("queen");
        validWords.add("queens");
        validWords.add("quiet");
        validWords.add("quiets");
        validWords.add("quit");
        validWords.add("quits");
        validWords.add("quite");
        validWords.add("quote");
        validWords.add("quoted");
        validWords.add("quotes");
        validWords.add("zap");
        validWords.add("zaps");
        validWords.add("zebra");
        validWords.add("zebras");
        validWords.add("zest");
        validWords.add("zesty");
        validWords.add("zip");
        validWords.add("zips");
        validWords.add("zipped");
        validWords.add("zipper");
        validWords.add("zippers");
        validWords.add("zone");
        validWords.add("zoned");
        validWords.add("zones");
        validWords.add("zoo");
        validWords.add("zoos");

        wordTree.seed(validWords);
    }

    private boolean canAdvanceToBoardLocation(int row, int column, BoardLocationsVisited boardLocationsVisited) {
        return (((row >= 0) && (row < BoggleDice.ROWS_X_COLS)) &&
                ((column >= 0) && (column < BoggleDice.ROWS_X_COLS)) &&
                !boardLocationsVisited.hasBeenVisited(row, column));
    }

    private Set<String> findWordsPresent(DieLetter[][] gameBoard,
                                         String partialWordInProgress,
                                         int row,
                                         int column,
                                         BoardLocationsVisited boardLocationsVisited) {
        Set<String> result = new HashSet<>();

        // Lets first determine if the partial word in progress can possibly lead to valid words.
        // If not, we can just stop right here.
        if (!wordTree.hasWordsThatBeginWith(partialWordInProgress)) {
            return result;
        }

        if (canAdvanceToBoardLocation(row - 1, column - 1, boardLocationsVisited)) {
            boardLocationsVisited.markVisited(row - 1, column - 1);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row - 1][column - 1].getLetter(),
                                           row - 1,
                                           column - 1,
                                           boardLocationsVisited));
        }
        if (canAdvanceToBoardLocation(row - 1, column, boardLocationsVisited)) {
        }
        if (canAdvanceToBoardLocation(row - 1, column + 1, boardLocationsVisited)) {
        }

        return result;
    }

    private Set<String> findWordsPresent(DieLetter[][] gameBoard) {
        Set<String> result = new HashSet<>();

        for (int row = 0;row < BoggleDice.ROWS_X_COLS;row++) {
            for (int column = 0;column < BoggleDice.ROWS_X_COLS;column++) {
                String firstLetterOfWord = "" + gameBoard[row][column];
                BoardLocationsVisited boardLocationsVisited = new BoardLocationsVisited(BoggleDice.ROWS_X_COLS);
                boardLocationsVisited.markVisited(row, column);
                result.addAll(findWordsPresent(gameBoard, firstLetterOfWord, row, column, boardLocationsVisited));
            }
        }

        return result;
    }

    private void displayGameBoard(DieLetter[][] gameBoard) {
        System.out.println("Here is the game board:");
        System.out.println(GAME_BOARD_HEADER);

        for (int row = 0;row < BoggleDice.ROWS_X_COLS;row++) {
            System.out.print("\t");
            for (int col = 0;col < BoggleDice.ROWS_X_COLS;col++) {
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

        Set<String> validWordsPresent = findWordsPresent((gameBoard));
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
