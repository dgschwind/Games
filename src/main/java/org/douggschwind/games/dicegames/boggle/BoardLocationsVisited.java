package org.douggschwind.games.dicegames.boggle;

/**
 * Records the board locations that have been visited. Allows an instance of this class to be easily cloned.
 * @author Doug Gschwind
 */
public class BoardLocationsVisited {
    private boolean[][] state;

    BoardLocationsVisited(int boardSize) {
        state = new boolean[boardSize][];
        for (int row = 0;row < boardSize;row++) {
            state[row] = new boolean[boardSize];
        }
    }

    boolean hasBeenVisited(int row, int column) {
        return state[row][column];
    }

    void markVisited(int row, int column) {
        state[row][column] = true;
    }
}
