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
        validWords.add("and");
        validWords.add("any");
        validWords.add("anything");
        validWords.add("apple");
        validWords.add("ask");
        validWords.add("axe");
        validWords.add("axed");
        validWords.add("axes");
        validWords.add("bail");
        validWords.add("bait");
        validWords.add("ban");
        validWords.add("banana");
        validWords.add("bane");
        validWords.add("bans");
        validWords.add("banned");
        validWords.add("bar");
        validWords.add("bare");
        validWords.add("bars");
        validWords.add("bares");
        validWords.add("bat");
        validWords.add("bats");
        validWords.add("bear");
        validWords.add("bears");
        validWords.add("bee");
        validWords.add("been");
        validWords.add("bet");
        validWords.add("bets");
        validWords.add("boat");
        validWords.add("boats");
        validWords.add("boo");
        validWords.add("booed");
        validWords.add("boos");
        validWords.add("boot");
        validWords.add("boots");
        validWords.add("borrow");
        validWords.add("bought");
        validWords.add("cat");
        validWords.add("cot");
        validWords.add("cut");
        validWords.add("dane");
        validWords.add("danes");
        validWords.add("dig");
        validWords.add("digs");
        validWords.add("dog");
        validWords.add("dogs");
        validWords.add("duck");
        validWords.add("ducks");
        validWords.add("easy");
        validWords.add("fat");
        validWords.add("fate");
        validWords.add("fit");
        validWords.add("fits");
        validWords.add("fitter");
        validWords.add("fun");
        validWords.add("gem");
        validWords.add("gems");
        validWords.add("get");
        validWords.add("gets");
        validWords.add("gone");
        validWords.add("hand");
        validWords.add("handsome");
        validWords.add("handy");
        validWords.add("hello");
        validWords.add("help");
        validWords.add("hide");
        validWords.add("jail");
        validWords.add("kit");
        validWords.add("kits");
        validWords.add("kite");
        validWords.add("kites");
        validWords.add("knob");
        validWords.add("life");
        validWords.add("list");
        validWords.add("live");
        validWords.add("lives");
        validWords.add("log");
        validWords.add("logs");
        validWords.add("orange");
        validWords.add("net");
        validWords.add("oat");
        validWords.add("part");
        validWords.add("parts");
        validWords.add("poor");
        validWords.add("port");
        validWords.add("ports");
        validWords.add("pour");
        validWords.add("pours");
        validWords.add("pot");
        validWords.add("pots");
        validWords.add("put");
        validWords.add("puts");
        validWords.add("quaint");
        validWords.add("queen");
        validWords.add("queens");
        validWords.add("quiet");
        validWords.add("quiets");
        validWords.add("quick");
        validWords.add("quickly");
        validWords.add("quit");
        validWords.add("quits");
        validWords.add("quite");
        validWords.add("quote");
        validWords.add("quoted");
        validWords.add("quotes");
        validWords.add("rat");
        validWords.add("rate");
        validWords.add("rated");
        validWords.add("rates");
        validWords.add("return");
        validWords.add("rob");
        validWords.add("rot");
        validWords.add("safe");
        validWords.add("safety");
        validWords.add("sane");
        validWords.add("sanity");
        validWords.add("sat");
        validWords.add("saw");
        validWords.add("saws");
        validWords.add("see");
        validWords.add("seen");
        validWords.add("sees");
        validWords.add("set");
        validWords.add("sets");
        validWords.add("settle");
        validWords.add("settles");
        validWords.add("sit");
        validWords.add("sits");
        validWords.add("slog");
        validWords.add("slogs");
        validWords.add("soon");
        validWords.add("ten");
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

        // Lets see if the partial word we have already formed is found in the list of valid words. Recall in Boggle
        // that words must be at least three letters in length and cannot be pronouns.
        if (partialWordInProgress.length() >= 3 && wordTree.isValidWord(partialWordInProgress)) {
            result.add(partialWordInProgress);
        }

        // If the partial word in progress cannot possibly lead to valid words, we can just stop right here.
        if (!wordTree.hasWordsThatBeginWith(partialWordInProgress)) {
            return result;
        }

        // In each of the blocks below, we clone the argument boardLocationsVisited so as to not disturb the other
        // consumers of it in this method.
        if (canAdvanceToBoardLocation(row - 1, column - 1, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row - 1, column - 1);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row - 1][column - 1].asWordPart(),
                                           row - 1,
                                           column - 1,
                                           clone));
        }
        if (canAdvanceToBoardLocation(row - 1, column, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row - 1, column);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row - 1][column].asWordPart(),
                                           row - 1,
                                           column,
                                           clone));
        }
        if (canAdvanceToBoardLocation(row - 1, column + 1, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row - 1, column + 1);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row - 1][column + 1].asWordPart(),
                                           row - 1,
                                           column + 1,
                                           clone));
        }
        if (canAdvanceToBoardLocation(row, column - 1, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row, column - 1);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row][column - 1].asWordPart(),
                                           row,
                                           column - 1,
                                           clone));
        }
        if (canAdvanceToBoardLocation(row, column + 1, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row, column + 1);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row][column + 1].asWordPart(),
                                           row,
                                           column + 1,
                                           clone));
        }
        if (canAdvanceToBoardLocation(row + 1, column - 1, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row + 1, column - 1);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row + 1][column - 1].asWordPart(),
                                           row + 1,
                                           column - 1,
                                           clone));
        }
        if (canAdvanceToBoardLocation(row + 1, column, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row + 1, column);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row + 1][column].asWordPart(),
                                           row + 1,
                                           column,
                                           clone));
        }
        if (canAdvanceToBoardLocation(row + 1, column + 1, boardLocationsVisited)) {
            BoardLocationsVisited clone = boardLocationsVisited.clone();
            clone.markVisited(row + 1, column + 1);
            result.addAll(findWordsPresent(gameBoard,
                                           partialWordInProgress + gameBoard[row + 1][column + 1].asWordPart(),
                                           row + 1,
                                           column + 1,
                                           clone));
        }

        return result;
    }

    private Set<String> findWordsPresent(DieLetter[][] gameBoard) {
        Set<String> result = new HashSet<>();

        for (int row = 0;row < BoggleDice.ROWS_X_COLS;row++) {
            for (int column = 0;column < BoggleDice.ROWS_X_COLS;column++) {
                String firstLetterOfWord = "" + gameBoard[row][column].asWordPart();
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

        Set<String> validWordsFound = findWordsPresent((gameBoard));
        List<String> sortedValidWordsFound = new ArrayList(validWordsFound);
        Collections.sort(sortedValidWordsFound);
        System.out.println("Found " + sortedValidWordsFound.size() + " of " + validWords.size() + " valid words present in this game board:");
        for (String validWordFound : sortedValidWordsFound) {
            System.out.println("\t" + validWordFound);
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
