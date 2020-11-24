package org.douggschwind.games.chess;

/**
 * Represents the most common of moves in Chess, one piece being moved from one Square to another.
 * @author Doug Gschwind
 */
public class ChessMove {
    private final Square from;
    private final Square to;

    public ChessMove(Square from, Square to) {
        if ((from == null) || (to == null)) {
            throw new IllegalArgumentException("From and To Square must both be non-null");
        } else if (from.equals(to)) {
            throw new IllegalArgumentException("From and To Square cannot be the same");
        }

        this.from = from;
        this.to = to;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }
}
