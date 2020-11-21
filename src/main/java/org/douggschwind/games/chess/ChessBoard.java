package org.douggschwind.games.chess;

import org.douggschwind.games.chess.piece.Bishop;
import org.douggschwind.games.chess.piece.ChessPiece;
import org.douggschwind.games.chess.piece.King;
import org.douggschwind.games.chess.piece.Knight;
import org.douggschwind.games.chess.piece.Pawn;
import org.douggschwind.games.chess.piece.Queen;
import org.douggschwind.games.chess.piece.Rook;

import java.util.Optional;

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
        squares[0].setInitialResident(new Rook(Player.BLACK));
        squares[1].setInitialResident(new Knight(Player.BLACK));
        squares[2].setInitialResident(new Bishop(Player.BLACK));
        squares[3].setInitialResident(new Queen(Player.BLACK));
        squares[4].setInitialResident(new King(Player.BLACK));
        squares[5].setInitialResident(new Bishop(Player.BLACK));
        squares[6].setInitialResident(new Knight(Player.BLACK));
        squares[7].setInitialResident(new Rook(Player.BLACK));

        for (int index = 8;index <= 15;index++) {
            squares[index].setInitialResident(new Pawn(Player.BLACK));
        }

        // Now for the opponents pieces on the opposite end of the board.
        for (int index = 48;index <= 55;index++) {
            squares[index].setInitialResident(new Pawn(Player.WHITE));
        }

        squares[56].setInitialResident(new Rook(Player.WHITE));
        squares[57].setInitialResident(new Knight(Player.WHITE));
        squares[58].setInitialResident(new Bishop(Player.WHITE));
        squares[59].setInitialResident(new Queen(Player.WHITE));
        squares[60].setInitialResident(new King(Player.WHITE));
        squares[61].setInitialResident(new Bishop(Player.WHITE));
        squares[62].setInitialResident(new Knight(Player.WHITE));
        squares[63].setInitialResident(new Rook(Player.WHITE));
    }

    public ChessBoard() {
        setUpInitialState();
    }

    /**
     * Prints the current state of the board to System.out.
     */
    public void print() {
        final String ROW_DELIMITER = "-----------------------------";
        final String SQUARE_DELIMITER = "|";
        System.out.println(ROW_DELIMITER);
        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            System.out.print(SQUARE_DELIMITER + " " + row.getId() + " ");
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                System.out.print(SQUARE_DELIMITER);
                Optional<String> printAbbreviation = getSquare(row, column).getPrintAbbreviation();
                System.out.print(printAbbreviation.map(i -> i).orElse("  "));
            }
            System.out.println(SQUARE_DELIMITER);
            System.out.println(ROW_DELIMITER);
        }
    }

    public Square getSquare(BoardPosition.Row row, BoardPosition.Column column) {
        return squares[BoardPosition.MAX_ROW*(BoardPosition.MAX_ROW - row.getId()) + (column.getId() - BoardPosition.MIN_ROW)];
    }

    private boolean isHorizontalPathClear(Square from, Square to) {
        int startColumnId;
        int endColumnId;
        if (from.getColumn().getId() > to.getColumn().getId()) {
            startColumnId = to.getColumn().getId();
            endColumnId = from.getColumn().getId();
        } else {
            startColumnId = from.getColumn().getId();
            endColumnId = to.getColumn().getId();
        }

        for (int columnId = startColumnId;columnId <= endColumnId;columnId++) {
            Square onPath = getSquare(from.getRow(), BoardPosition.Column.getById(columnId));
            if (onPath.isOccupied()) {
                return false;
            }
        }

        return true;
    }

    private boolean isVerticalPathClear(Square from, Square to) {
        int startRowId;
        int endRowId;
        if (from.getRow().getId() > to.getRow().getId()) {
            // Walk from bottom to top
            startRowId = to.getRow().getId();
            endRowId = from.getRow().getId();
        } else {
            // Also walk from bottom to top
            startRowId = from.getRow().getId();
            endRowId = to.getRow().getId();
        }

        for (int rowId = startRowId;rowId <= endRowId;rowId++) {
            Square onPath = getSquare(BoardPosition.Row.getById(rowId), to.getColumn());
            if (onPath.isOccupied()) {
                return false;
            }
        }

        return true;
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
            return isHorizontalPathClear(from, to);
        }

        boolean isVerticalOnlyPath = (from.getColumn() == to.getColumn());
        if (isVerticalOnlyPath) {
            return isVerticalPathClear(from, to);
        }

        // Otherwise, the path is diagonal.
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
