package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Square;

/**
 * Represents one of the two Knight pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Knight extends ChessPiece {

    public Knight(Player player) {
        super(player);
    }

    @Override
    public final boolean isKnight() {
        return true;
    }

    /**
     * Knights move in an ‘L’ shape’: two squares in a horizontal or vertical direction, then move one square
     * horizontally or vertically. They are the only piece able to jump over other pieces.
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

        final int rowDifference = Math.abs(from.getRow().getId() - to.getRow().getId());
        final int columnDifference = Math.abs(from.getColumn().getId() - to.getColumn().getId());

        return (((rowDifference == 2) && (columnDifference == 1)) ||
                ((rowDifference == 1) && (columnDifference == 2)));
    }

    @Override
    public void moveTo(ChessBoard chessBoard, Square from, Square to) {
        basicMove(from, to);
    }
}
