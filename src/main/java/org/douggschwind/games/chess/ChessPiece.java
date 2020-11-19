package org.douggschwind.games.chess;

/**
 * Represents the abstraction of which all pieces in a game of Chess are.
 * @author Doug Gschwind
 */
public abstract class ChessPiece {
    public enum Player {
        BLACK,
        WHITE;
    }

    private Player owner;
    private final BoardPosition initialPosition;
    private boolean captured;

    protected ChessPiece(Player owner, BoardPosition initialPosition) {
        this.initialPosition = initialPosition;
    }

    protected BoardPosition getInitialPosition() {
        return initialPosition;
    }

    /**
     * Determines if the given piece can be moved from its current square to a target destination square.
     * @param from The piece's current BoardPosition.
     * @param to The piece's proposed destination square.
     * @return true if so, false if the piece is not allowed to be moved to the destination.
     */
    public abstract boolean canMoveTo(BoardPosition from, BoardPosition to);
    public abstract void moveTo(BoardPosition from, BoardPosition to);

    public void capture() {
        captured = true;
    }

    public boolean hasBeenCaptured() {
        return captured;
    }
}
