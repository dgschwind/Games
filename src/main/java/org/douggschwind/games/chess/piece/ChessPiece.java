package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.moves.CommonMove;
import org.douggschwind.games.chess.Player;
import org.douggschwind.games.chess.Square;

/**
 * Represents the abstraction of which all pieces in a game of Chess are.
 * @author Doug Gschwind
 */
public abstract class ChessPiece {
    private final Player owner;
    private BoardPosition initialPosition;
    private int numTimesMoved;
    private AdvancingDirection advancingDirection;
    private boolean captured;

    // Notes the direction of movement that a ChessPiece makes when advancing
    public enum AdvancingDirection {
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP;
    }

    protected ChessPiece(Player owner) {
        this.owner = owner;
        if (owner.isBlack()) {
            advancingDirection = AdvancingDirection.TOP_TO_BOTTOM;
        } else {
            advancingDirection = AdvancingDirection.BOTTOM_TO_TOP;
        }
    }

    public Player getOwner() {
        return owner;
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

    public AdvancingDirection getAdvancingDirection() {
        return advancingDirection;
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

    protected int getNumTimesMoved() {
        return numTimesMoved;
    }

    public boolean hasEverBeenMoved() {
        return getNumTimesMoved() > 0;
    }

    /**
     * Determines if the given player can move the ChessPiece located in the from Square.
     * @param player Must be non-null.
     * @param from Must be non-null.
     * @return
     */
    public final boolean canMoveFrom(Player player, Square from) {
        return from.isOccupied() && (player == from.getResident().get().getOwner());
    }

    /**
     * Determines if the given piece can be moved from its current square to a target destination square.
     * @param chessBoard Must be non-null.
     * @param proposedMove Must be non-null and properly populated.
     * @return true if so, false if the piece is not allowed to be moved to the destination.
     */
    public abstract boolean canMoveTo(ChessBoard chessBoard, CommonMove proposedMove);

    protected final void basicMove(CommonMove move) {
        final Square from = move.getFrom();
        final Square to = move.getTo();

        boolean isCapturing = to.isOccupied();
        if (isCapturing) {
            to.getResident().get().markCaptured();
        }
        to.setResident(from.getResident().get());
        from.empty();
    }

    // Concrete subclasses must implement.
    protected abstract void handleMoveTo(ChessBoard chessBoard, CommonMove move);
    /**
     * Demands that the ChessPiece in the from Square be moved to the to Square.
     * @param chessBoard Must be non-null.
     * @param move Must be non-null and properly populated.
     */
    public final void moveTo(ChessBoard chessBoard, CommonMove move) {
        handleMoveTo(chessBoard, move);
        numTimesMoved++;
    }

    public boolean hasBeenCaptured() {
        return captured;
    }

    public void markCaptured() {
        captured = true;
    }

    public String getPrintAbbreviation() {
        if (isPawn()) {
            return "p";
        } else if (isRook()) {
            return "r";
        } else if (isKnight()) {
            return "k";
        } else if (isBishop()) {
            return "b";
        } else if (isQueen()) {
            return "Q";
        } else {
            return "K";
        }
    }
}
