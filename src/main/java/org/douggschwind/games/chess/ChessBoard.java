package org.douggschwind.games.chess;

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
