package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Square;

/**
 * Represents one of the two Rook pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Rook extends ChessPiece {

    public Rook(Player player) {
        super(player);
    }

    @Override
    public final boolean isRook() {
        return true;
    }

    @Override
    public boolean canMoveTo(ChessBoard chessBoard, Square from, Square to) {
        return false; //TODO
    }

    @Override
    public void moveTo(Square from, Square to) {
    }
}
