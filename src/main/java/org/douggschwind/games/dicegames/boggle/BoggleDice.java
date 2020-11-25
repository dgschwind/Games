package org.douggschwind.games.dicegames.boggle;

/**
 * Represents the abstraction that abstracts away whether we are using a 4x4 game board or a 5x5 game board.
 * @author Doug Gschwind
 */
public abstract class BoggleDice {
    public static BoggleDice for4x4Board() {
        return new Boggle4x4Dice();
    }

    public static BoggleDice for5x5Board() {
        return new Boggle5x5Dice();
    }

    /**
     * @return The size of the supported game board, where size indicates the number of rows and the same number
     * of columns on the game board.
     */
    public abstract int size();

    /**
     * Shakes all of the dice and shuffles them such that they have all fallen into place on the game board.
     * @return The board configuration. Will be non-null.
     */
    public abstract DieLetter[][] shake();
}
