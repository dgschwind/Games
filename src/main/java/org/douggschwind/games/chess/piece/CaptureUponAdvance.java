package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.Square;

/**
 * This is mix-in behavior essentially so that all that implement this interface
 * have the ability to capture a ChessPiece upon advance, which applies to all
 * ChessPiece instances with the exception of Pawn.
 * @author Doug Gschwind
 */
public interface CaptureUponAdvance {
    default boolean basicCanMoveTo(Square from, Square to) {
        if (to.isOccupied()) {
            // Cannot move to a Square that is occupied by the same Player
            return from.isOccupiedByMyOpponent(to);
        } else {
            return true;
        }
    }
}
