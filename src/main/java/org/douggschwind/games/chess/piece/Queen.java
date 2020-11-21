package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.ChessMove;
import org.douggschwind.games.chess.Player;
import org.douggschwind.games.chess.Square;

/**
 * Represents the Queen piece in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Queen extends ChessPiece implements CaptureUponAdvance {

    public Queen(Player player) {
        super(player);
    }

    @Override
    public final boolean isQueen() {
        return true;
    }

    /**
     * Queens move diagonally, horizontally, or vertically any number of squares. They are unable to jump over pieces.
     * @param chessBoard Must be non-null.
     * @param proposedMove Must be non-null and properly populated.
     * @return
     */
    @Override
    public boolean canMoveTo(ChessBoard chessBoard, ChessMove proposedMove) {
        if (!basicCanMoveTo(proposedMove)) {
            return false;
        }

        final Square from = proposedMove.getFrom();
        final Square to = proposedMove.getTo();

        // Number of rows of movement must equal number of columns to move diagonally.
        final int numRowsMovement = Math.abs(from.getRow().getId() - to.getRow().getId());
        final int numColumnsMovement = Math.abs(from.getColumn().getId() - to.getColumn().getId());
        boolean isMovingDiagonally = (numRowsMovement == numColumnsMovement);

        boolean isMovingHorizontally = (from.getRow() == to.getRow());
        boolean isMovingVertically = (from.getColumn() == to.getColumn());

        if (!isMovingHorizontally && !isMovingVertically && !isMovingDiagonally) {
            return false;
        }

        return chessBoard.isPathClear(proposedMove);
    }

    @Override
    public void moveTo(ChessBoard chessBoard, ChessMove move) {
        basicMove(move);
    }
}
