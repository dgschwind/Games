package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.Square;

/**
 * Represents one of the two Bishop pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Bishop extends ChessPiece {

    public Bishop(Player player) {
        super(player);
    }

    @Override
    public boolean canMoveTo(Square from, Square to) {
        return false; //TODO
    }

    @Override
    public void moveTo(Square from, Square to) {
    }
}
