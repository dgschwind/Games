package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.ChessMove;
import org.douggschwind.games.chess.Player;
import org.douggschwind.games.chess.Square;

/**
 * Represents one of the two Bishop pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Bishop extends ChessPiece implements CaptureUponAdvance {

    public Bishop(Player player) {
        super(player);
    }

    @Override
    public final boolean isBishop() {
        return true;
    }

    /**
     * Bishops move diagonally any number of squares. They are unable to jump over pieces.
     * @param chessBoard Must be non-null.
     * @param proposedMove Must be non-null and properly populated.
     * @return
     */
    @Override
    public boolean canMoveTo(ChessBoard chessBoard, ChessMove proposedMove) {
        if (!basicCanMoveTo(proposedMove)) {
            return false;
        }

        // Number of rows of movement must equal number of columns to move diagonally.
        final Square from = proposedMove.getFrom();
        final Square to = proposedMove.getTo();
        final int numRowsMovement = Math.abs(from.getRow().getId() - to.getRow().getId());
        final int numColumnsMovement = Math.abs(from.getColumn().getId() - to.getColumn().getId());
        if (numRowsMovement != numColumnsMovement) {
            return false;
        }

        return chessBoard.isPathClear(proposedMove);
    }

    @Override
    public void moveTo(ChessBoard chessBoard, ChessMove move) {
        basicMove(move);
    }
}