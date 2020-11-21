package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.ChessMove;
import org.douggschwind.games.chess.Player;
import org.douggschwind.games.chess.Square;

/**
 * Represents the King piece in the game of Chess, for a given Player.
 * TODO: Support for castling.
 * @author Doug Gschwind
 */
public class King extends ChessPiece implements CaptureUponAdvance {

    public King(Player player) {
        super(player );
    }

    @Override
    public final boolean isKing() {
        return true;
    }

    /**
     * Kings move one square in any direction, so long as that square is not attacked by an enemy piece.
     * Additionally, kings are able to make a special move, know as castling.
     * @param chessBoard Must be non-null.
     * @param from The piece's current BoardPosition.
     * @param to The piece's proposed destination square.
     * @return
     */
    @Override
    public boolean canMoveTo(ChessBoard chessBoard, Square from, Square to) {
        if (!basicCanMoveTo(from, to)) {
            return false;
        }

        if (to.isOccupied() && from.isOccupiedByMyOpponent(to)) {
            return false;
        }

        final int numRowsMovement = Math.abs(from.getRow().getId() - to.getRow().getId());
        final int numColumnsMovement = Math.abs(from.getColumn().getId() - to.getColumn().getId());

        if (numRowsMovement == 0) {
            return numColumnsMovement == 1;
        } else if (numColumnsMovement == 0) {
            return numRowsMovement == 1;
        } else {
            return ((numRowsMovement == 1) && (numColumnsMovement == 1));
        }
    }

    @Override
    public void moveTo(ChessBoard chessBoard, ChessMove move) {
        basicMove(move);
    }
}
