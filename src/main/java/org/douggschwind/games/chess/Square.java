package org.douggschwind.games.chess;

import org.douggschwind.games.chess.piece.ChessPiece;

import java.util.Optional;

/**
 * Represents a given Square on the Chess board, that knows its exact location on the board and the ChessPiece, if any,
 * that occupies it.
 * @author Doug Gschwind
 */
public class Square {
    private final BoardPosition boardPosition;
    private Optional<ChessPiece> resident = Optional.empty();

    public Square(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

    public BoardPosition getBoardPosition() {
        return boardPosition;
    }

    public Optional<ChessPiece> getResident() {
        return resident;
    }

    protected void setInitialResident(ChessPiece resident) {
        setResident(resident);
        if (resident != null) {
            resident.setInitialPosition(getBoardPosition());
        }
    }

    /**
     * Allows the resident of this square to be changed.
     * @param newValue Can be null in the case where a ChessPiece is captured, but leaves this instance empty,
     *                 like in the case of the En Passant move for a Pawn.
     */
    public void setResident(ChessPiece newValue) {
        resident = Optional.ofNullable(newValue);
    }

    public boolean isOccupied() {
        return getResident().isPresent();
    }

    public BoardPosition.Row getRow() {
        return getBoardPosition().getRow();
    }

    public int getNumberRowsDistance(Square to) {
        return this.getBoardPosition().getNumberRowsDistance(to.getBoardPosition());
    }
}
