package org.douggschwind.games.dicegames.boggle;

import java.util.Arrays;

/**
 * Records the board locations that have been visited. Allows an instance of this class to be easily cloned.
 * @author Doug Gschwind
 */
public class BoardLocationsVisited implements Cloneable {
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

    @Override
    public BoardLocationsVisited clone() {
        BoardLocationsVisited clone = null;
        try {
            clone = (BoardLocationsVisited) super.clone();
            int boardSize = this.state.length;
            clone.state = Arrays.copyOf(this.state, boardSize);
            for (int row = 0;row < boardSize;row++) {
                clone.state[row] = Arrays.copyOf(this.state[row], boardSize);
            }
        } catch (CloneNotSupportedException ignored) {
        }

        return clone;
    }
}
