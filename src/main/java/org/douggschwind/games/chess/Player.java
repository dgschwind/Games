package org.douggschwind.games.chess;

import org.douggschwind.games.chess.piece.ChessPiece;

/**
 * @author Doug Gschwind
 */
public enum Player {
    BLACK("B"),
    WHITE("W");

    private String abbreviation;

    Player(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public boolean canMove(ChessPiece subject, Square from) {
        return ((subject != null) && (from != null) && subject.canMove(this, from));
    }
}
