package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Square;

/**
 * Represents the King piece in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class King extends ChessPiece {

    public King(Player player) {
        super(player );
    }

    @Override
    public final boolean isKing() {
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
