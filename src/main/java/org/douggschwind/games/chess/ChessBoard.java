package org.douggschwind.games.chess;

import org.douggschwind.games.chess.piece.Bishop;
import org.douggschwind.games.chess.piece.ChessPiece;
import org.douggschwind.games.chess.piece.King;
import org.douggschwind.games.chess.piece.Knight;
import org.douggschwind.games.chess.piece.Pawn;
import org.douggschwind.games.chess.piece.Queen;
import org.douggschwind.games.chess.piece.Rook;

/**
 * An instance of this class represents the state of the Chess board during the playing of a game.
 * @author Doug Gschwind
 */
public class ChessBoard {
    private final Square[] squares;

    {
        squares = new Square[64];

        int index = 0;
        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                squares[index++] = new Square(new BoardPosition(row, column));
            }
        }
    }

    private void setUpInitialState() {
        squares[0].setInitialResident(new Rook(ChessPiece.Player.BLACK));
        squares[1].setInitialResident(new Knight(ChessPiece.Player.BLACK));
        squares[2].setInitialResident(new Bishop(ChessPiece.Player.BLACK));
        squares[3].setInitialResident(new Queen(ChessPiece.Player.BLACK));
        squares[4].setInitialResident(new King(ChessPiece.Player.BLACK));
        squares[5].setInitialResident(new Bishop(ChessPiece.Player.BLACK));
        squares[6].setInitialResident(new Knight(ChessPiece.Player.BLACK));
        squares[7].setInitialResident(new Rook(ChessPiece.Player.BLACK));

        for (int index = 8;index <= 15;index++) {
            squares[index].setInitialResident(new Pawn(ChessPiece.Player.BLACK));
        }

        // Now for the opponents pieces on the opposite end of the board.
        for (int index = 48;index <= 55;index++) {
            squares[index].setInitialResident(new Pawn(ChessPiece.Player.WHITE));
        }

        squares[56].setInitialResident(new Rook(ChessPiece.Player.WHITE));
        squares[57].setInitialResident(new Knight(ChessPiece.Player.WHITE));
        squares[58].setInitialResident(new Bishop(ChessPiece.Player.WHITE));
        squares[59].setInitialResident(new Queen(ChessPiece.Player.WHITE));
        squares[60].setInitialResident(new King(ChessPiece.Player.WHITE));
        squares[61].setInitialResident(new Bishop(ChessPiece.Player.WHITE));
        squares[62].setInitialResident(new Knight(ChessPiece.Player.WHITE));
        squares[63].setInitialResident(new Rook(ChessPiece.Player.WHITE));
    }

    public ChessBoard() {
        setUpInitialState();
    }

    public Square getSquare(BoardPosition.Row row, BoardPosition.Column column) {
        return squares[(row.getId() - 8) + (column.getId() - 1)];
    }

    /**
     * Determines if the path that exists between from and to is clear, or if one or more ChessPieces occupy
     * Squares along the path.
     * @param from Must be non-null.
     * @param to Must be non-null.
     * @return true if the path is clear, false otherwise.
     */
    public boolean isPathClear(Square from, Square to) {
        boolean isHorizontalOnlyPath = (from.getRow() == to.getRow());
        if (isHorizontalOnlyPath) {
        }

        boolean isVerticalOnlyPath = (from.getColumn() == to.getColumn());
        if (isVerticalOnlyPath) {
        }

        // Otherwise, this is a Knight or a Bishop.
        return true; //TODO
    }

    private void clearSquareResidents() {
        for (Square square : squares) {
            square.setResident(null);
        }
    }

    public void reset() {
        clearSquareResidents();
        setUpInitialState();
    }
}
