package org.douggschwind.games.chess.moves;

import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Player;

public interface ChessMove {
    default boolean isCommonMove() {
        return false;
    }

    default boolean isCastlingMove() {
        return false;
    }

    /**
     * Determines if this move by the given attempting player is permitted or not.
     * @param player Must be non-null.
     * @param chessBoard Must be non-null.
     * @return true if the given Player is in fact permitted to make this move, false otherwise.
     */
    boolean isPermitted(Player player, ChessBoard chessBoard);
}
