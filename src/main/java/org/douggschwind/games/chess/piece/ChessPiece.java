package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.Square;

/**
 * Represents the abstraction of which all pieces in a game of Chess are.
 * @author Doug Gschwind
 */
public abstract class ChessPiece {
    public enum Player {
        BLACK,
        WHITE;
    }

    private final Player owner;
    private BoardPosition initialPosition;
    private boolean captured;

    protected ChessPiece(Player owner) {
        this.owner = owner;
    }

    public boolean isOpponent(ChessPiece that) {
        return this.owner != that.owner;
    }

    protected BoardPosition getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(BoardPosition initialPosition) {
        this.initialPosition = initialPosition;
    }

    public boolean isPawn() {
        return false;
    }

    public boolean isRook() {
        return false;
    }

    public boolean isKnight() {
        return false;
    }

    public boolean isBishop() {
        return false;
    }

    public boolean isQueen() {
        return false;
    }

    public boolean isKing() {
        return false;
    }

    /**
     * Determines if the given piece can be moved from its current square to a target destination square.
     * @param from The piece's current BoardPosition.
     * @param to The piece's proposed destination square.
     * @return true if so, false if the piece is not allowed to be moved to the destination.
     */
    public abstract boolean canMoveTo(Square from, Square to);
    public abstract void moveTo(Square from, Square to);

    public void capture() {
        captured = true;
    }

    public boolean hasBeenCaptured() {
        return captured;
    }
}
