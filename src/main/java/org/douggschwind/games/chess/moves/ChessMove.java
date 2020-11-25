package org.douggschwind.games.chess.moves;

public interface ChessMove {
    default boolean isCommonMove() {
        return false;
    }

    default boolean isCastlingMove() {
        return false;
    }
}
