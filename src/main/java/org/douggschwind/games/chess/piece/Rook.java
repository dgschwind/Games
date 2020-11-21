package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Player;
import org.douggschwind.games.chess.Square;

/**
 * Represents one of the two Rook pieces in the game of Chess, for a given Player.
 * TODO: Support for castling.
 * @author Doug Gschwind
 */
public class Rook extends ChessPiece implements CaptureUponAdvance {

    public Rook(Player player) {
        super(player);
    }

    @Override
    public final boolean isRook() {
        return true;
    }

    /**
     * Rooks move horizontally or vertically any number of squares. They are unable to jump over pieces.
     * Rooks move when the king castles.
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

        if ((from.getRow() != to.getRow()) || (from.getColumn() != to.getColumn())) {
            // Rooks can only move horizontally or vertically.
            return false;
        }

        return chessBoard.isPathClear(from, to);
    }

    @Override
    public void moveTo(ChessBoard chessBoard, Square from, Square to) {
        basicMove(from, to);
    }
}
