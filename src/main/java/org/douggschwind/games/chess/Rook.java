package org.douggschwind.games.chess;

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
    public boolean canMoveTo(BoardPosition from, BoardPosition to) {
        return false; //TODO
    }

    @Override
    public void moveTo(BoardPosition from, BoardPosition to) {
    }
}
