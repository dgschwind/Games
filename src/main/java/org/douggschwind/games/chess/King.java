package org.douggschwind.games.chess;

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
    public boolean canMoveTo(BoardPosition from, BoardPosition to) {
        return false; //TODO
    }

    @Override
    public void moveTo(BoardPosition from, BoardPosition to) {
    }
}