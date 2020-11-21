package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Square;

/**
 * Represents the Queen piece in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Queen extends ChessPiece {

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
     * @param from The piece's current BoardPosition.
     * @param to The piece's proposed destination square.
     * @return
     */
    @Override
    public boolean canMoveTo(ChessBoard chessBoard, Square from, Square to) {
        if (!basicCanMoveTo(from, to)) {
            return false;
        }

        // Number of rows of movement must equal number of columns to move diagonally.
        final int numRowsMovement = Math.abs(from.getRow().getId() - to.getRow().getId());
        final int numColumnsMovement = Math.abs(from.getColumn().getId() - to.getColumn().getId());
        boolean isMovingDiagonally = (numRowsMovement == numColumnsMovement);

        boolean isMovingHorizontally = (from.getRow() == to.getRow());
        boolean isMovingVertically = (from.getColumn() == to.getColumn());

        if (!isMovingHorizontally && !isMovingVertically && !isMovingDiagonally) {
            return false;
        }

        return chessBoard.isPathClear(from, to);
    }

    @Override
    public void moveTo(ChessBoard chessBoard, Square from, Square to) {
        basicMove(from, to);
    }
}
