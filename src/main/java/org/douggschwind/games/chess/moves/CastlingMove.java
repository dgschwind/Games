package org.douggschwind.games.chess.moves;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Player;
import org.douggschwind.games.chess.piece.King;
import org.douggschwind.games.chess.piece.Rook;

/**
 * Instances of this class represent a Castling move, where two pieces actually move at once, the Rook
 * and the King. It can be a "Queen side" move where the King moves two spaces to the left, or a King side move where
 * the King moves two spaces to the right. The Rook then moves to the space on the other side of the King from where
 * it was previously.
 * @author Doug Gschwind
 */
public class CastlingMove {
    private final CommonMove<King> kingMove;
    private final CommonMove<Rook> rookMove;

    public static CastlingMove newQueenSideMove(Player player, ChessBoard chessBoard) {
        BoardPosition.Row impactedRow = player.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        CommonMove<King> kingMove = new CommonMove(King.class,
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.e),
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.c));
        CommonMove<Rook> rookMove = new CommonMove(Rook.class,
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.a),
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.d));
        return new CastlingMove(kingMove, rookMove);
    }

    public static CastlingMove newKingSideMove(Player player, ChessBoard chessBoard) {
        BoardPosition.Row impactedRow = player.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        CommonMove<King> kingMove = new CommonMove(King.class,
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.e),
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.g));
        CommonMove<Rook> rookMove = new CommonMove(Rook.class,
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.h),
                                                   chessBoard.getSquare(impactedRow, BoardPosition.Column.f));
        return new CastlingMove(kingMove, rookMove);
    }

    private CastlingMove(CommonMove<King> kingMove, CommonMove<Rook> rookMove) {
        this.kingMove = kingMove;
        this.rookMove = rookMove;
    }

    public CommonMove<King> getKingMove() {
        return kingMove;
    }

    public CommonMove<Rook> getRookMove() {
        return rookMove;
    }
}
