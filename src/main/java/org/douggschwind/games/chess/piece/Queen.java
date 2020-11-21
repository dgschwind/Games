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

    @Override
    public boolean canMoveTo(ChessBoard chessBoard, Square from, Square to) {
        return false; //TODO
    }

    @Override
    public void moveTo(ChessBoard chessBoard, Square from, Square to) {
    }
}
