package org.douggschwind.games.chess;

/**
 * An instance of this class represents the state of the Chess board during the playing of a game.
 * @author Doug Gschwind
 */
public class ChessBoard {
    private final ChessPiece[][] squares;

    {
        squares = new ChessPiece[8][];
        for (int row = 0;row < 8;row++) {
            squares[row] = new ChessPiece[8];
        }
    }

    private void setUpInitialState() {
        squares[0][0] = new Rook(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.A));
        squares[0][1] = new Knight(ChessPiece.Player.BLACK,
                                   new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.B));
        squares[0][2] = new Bishop(ChessPiece.Player.BLACK,
                                   new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.C));
        squares[0][3] = new Queen(ChessPiece.Player.BLACK,
                                  new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.D));
        squares[0][4] = new King(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.E));
        squares[0][5] = new Bishop(ChessPiece.Player.BLACK,
                                   new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.F));
        squares[0][6] = new Knight(ChessPiece.Player.BLACK,
                                   new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.G));
        squares[0][7] = new Rook(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R8, BoardPosition.Column.H));
        squares[1][0] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.A));
        squares[1][1] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.B));
        squares[1][2] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.C));
        squares[1][3] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.D));
        squares[1][4] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.E));
        squares[1][5] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.F));
        squares[1][6] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.G));
        squares[1][7] = new Pawn(ChessPiece.Player.BLACK,
                                 new BoardPosition(BoardPosition.Row.R7, BoardPosition.Column.H));

        // Now for the opponents pieces on the opposite end of the board.
        squares[6][0] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.A));
        squares[6][1] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.B));
        squares[6][2] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.C));
        squares[6][3] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.D));
        squares[6][4] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.E));
        squares[6][5] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.F));
        squares[6][6] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.G));
        squares[6][7] = new Pawn(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R2, BoardPosition.Column.H));
        squares[7][0] = new Rook(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.A));
        squares[7][1] = new Knight(ChessPiece.Player.WHITE,
                                   new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.B));
        squares[7][2] = new Bishop(ChessPiece.Player.WHITE,
                                   new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.C));
        squares[7][3] = new Queen(ChessPiece.Player.WHITE,
                                  new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.D));
        squares[7][4] = new King(ChessPiece.Player.WHITE,
                                  new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.E));
        squares[7][5] = new Bishop(ChessPiece.Player.WHITE,
                                   new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.F));
        squares[7][6] = new Knight(ChessPiece.Player.WHITE,
                                   new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.G));
        squares[7][7] = new Rook(ChessPiece.Player.WHITE,
                                 new BoardPosition(BoardPosition.Row.R1, BoardPosition.Column.H));
    }

    public ChessBoard() {
        setUpInitialState();
    }

    public void reset() {
        setUpInitialState();
    }
}
