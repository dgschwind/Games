package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.piece.ChessPiece;

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
    public boolean canMoveTo(BoardPosition from, BoardPosition to) {
        return false; //TODO
    }

    @Override
    public void moveTo(BoardPosition from, BoardPosition to) {
    }
}
