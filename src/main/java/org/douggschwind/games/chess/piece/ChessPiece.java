package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.ChessBoard;
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
     * @param chessBoard Must be non-null.
     * @param from The piece's current BoardPosition.
     * @param to The piece's proposed destination square.
     * @return true if so, false if the piece is not allowed to be moved to the destination.
     */
    public abstract boolean canMoveTo(ChessBoard chessBoard, Square from, Square to);

    /**
     * Demands that the ChessPiece in the from Square be moved to the to Square.
     * @param from Must be non-null and occupied.
     * @param to Must be non-null.
     */
    public abstract void moveTo(Square from, Square to);

    protected final void basicMove(Square from, Square to) {
        boolean isAttackingToCapture = to.isOccupied();
        if (isAttackingToCapture) {
            to.getResident().get().markCaptured();
        }
        to.setResident(from.getResident().get());
        from.empty();
    }

    public boolean hasBeenCaptured() {
        return captured;
    }

    private void markCaptured() {
        captured = true;
    }
}
