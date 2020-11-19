package org.douggschwind.games.chess;

/**
 * Represents one of the two Knight pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Knight extends ChessPiece {

    public Knight(Player player, BoardPosition initialPosition) {
        super(player, initialPosition);
    }

    @Override
    public final boolean isKnight() {
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
