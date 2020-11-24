package org.douggschwind.games.chess.moves;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Player;

/**
 * Instances of this class represent a Castling move, where two pieces actually move at once, the Rook
 * and the King. It can be a "Queen side" move where the King moves two spaces to the left, or a King side move where
 * the King moves two spaces to the right. The Rook then moves to the space on the other side of the King from where
 * it was previously.
 * @author Doug Gschwind
 */
public class CastlingMove {
    private final CommonMove rookMove;
    private final CommonMove kingMove;

    public static CastlingMove newQueenSideMove(Player player, ChessBoard chessBoard) {
        BoardPosition.Row impactedRow = player.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        CommonMove kingMove = new CommonMove(chessBoard.getSquare(impactedRow, BoardPosition.Column.e),
                                             chessBoard.getSquare(impactedRow, BoardPosition.Column.c));
        CommonMove rookMove = new CommonMove(chessBoard.getSquare(impactedRow, BoardPosition.Column.a),
                                             chessBoard.getSquare(impactedRow, BoardPosition.Column.d));
        return new CastlingMove(rookMove, kingMove);
    }

    public static CastlingMove newKingSideMove(Player player, ChessBoard chessBoard) {
        BoardPosition.Row impactedRow = player.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        CommonMove kingMove = new CommonMove(chessBoard.getSquare(impactedRow, BoardPosition.Column.e),
                                             chessBoard.getSquare(impactedRow, BoardPosition.Column.g));
        CommonMove rookMove = new CommonMove(chessBoard.getSquare(impactedRow, BoardPosition.Column.h),
                                             chessBoard.getSquare(impactedRow, BoardPosition.Column.f));
        return new CastlingMove(rookMove, kingMove);
    }

    private CastlingMove(CommonMove rookMove, CommonMove kingMove) {
        this.rookMove = rookMove;
        this.kingMove = kingMove;
    }

    public CommonMove getKingMove() {
        return kingMove;
    }

    public CommonMove getRookMove() {
        return rookMove;
    }
}
