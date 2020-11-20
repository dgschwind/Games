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

    /**
     * Empties or clears this instance to note that a ChessPiece no longer resides here.
     */
    public void empty() {
        setResident(null);
    }

    public boolean isOccupied() {
        return getResident().isPresent();
    }

    public boolean isOccupiedByMyOpponent(Square that) {
        if (!this.isOccupied() || !that.isOccupied()) {
            return false;
        } else {
            ChessPiece occupant = this.getResident().get();
            return occupant.isOpponent(that.getResident().get());
        }
    }

    public BoardPosition.Row getRow() {
        return getBoardPosition().getRow();
    }

    public BoardPosition.Column getColumn() {
        return getBoardPosition().getColumn();
    }

    public int getNumberRowsDistance(Square to) {
        return this.getBoardPosition().getNumberRowsDistance(to.getBoardPosition());
    }

    public int getNumberColumnsDistance(Square to) {
        return this.getBoardPosition().getNumberColumnsDistance(to.getBoardPosition());
    }
}
