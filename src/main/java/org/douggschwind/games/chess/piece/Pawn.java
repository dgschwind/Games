package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.Square;

/**
 * Represents one of the eight Pawn pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Pawn extends ChessPiece {
    private Boolean initialMoveWasTwoSquares;
    private boolean reachedFarSideOfBoard;

    public Pawn(Player player) {
        super(player);
    }

    @Override
    public final boolean isPawn() {
        return true;
    }

    @Override
    public boolean canMoveTo(Square from, Square to) {
        if (hasReachedFarSideOfBoard()) {
            // Also need to consider once Pawn has in fact reached the far side of the board.
        } else {
        }
        return false; //TODO
    }

    @Override
    public void moveTo(Square from, Square to) {
        if (!hasEverBeenMoved()) {
            initialMoveWasTwoSquares = (from.getNumberRowsDistance(to) == 2);
        } else if (!reachedFarSideOfBoard) {
            int initialRow = getInitialPosition().getRow().getId();
            reachedFarSideOfBoard =
                    (((initialRow == BoardPosition.MAX_ROW) && (to.getRow().getId() == BoardPosition.MIN_ROW)) ||
                     ((initialRow == BoardPosition.MIN_ROW) && (to.getRow().getId() == BoardPosition.MAX_ROW)));
        }
    }

    public boolean hasEverBeenMoved() {
        return initialMoveWasTwoSquares != null;
    }

    public boolean isSubjectToEnPassant() {
        return hasEverBeenMoved() && initialMoveWasTwoSquares.booleanValue();
    }

    public boolean hasReachedFarSideOfBoard() {
        return reachedFarSideOfBoard;
    }

    public void farSideOfBoardReached() {
        reachedFarSideOfBoard = true;
    }
}
