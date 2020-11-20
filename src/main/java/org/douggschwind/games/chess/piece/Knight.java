package org.douggschwind.games.chess.piece;

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

    @Override
    public boolean canMoveTo(Square from, Square to) {
        return false; //TODO
    }

    @Override
    public void moveTo(Square from, Square to) {
    }
}
