package org.douggschwind.games.chess;

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
}
