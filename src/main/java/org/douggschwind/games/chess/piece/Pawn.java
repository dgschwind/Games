package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.Square;

/**
 * Represents one of the eight Pawn pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Pawn extends ChessPiece {
    private boolean initialMoveWasTwoSquares;
    private int numTimesMoved;
    private boolean reachedFarSideOfBoard;

    public Pawn(Player player) {
        super(player);
    }

    @Override
    public final boolean isPawn() {
        return true;
    }

    @Override
    public boolean canMoveTo(ChessBoard chessBoard, Square from, Square to) {
        if (hasReachedFarSideOfBoard()) {
            // Also need to consider once Pawn has in fact reached the far side of the board.
            return false; //TODO
        } else {
            int initialRow = getInitialPosition().getRow().getId();
            int fromRow = from.getRow().getId();
            int toRow = to.getRow().getId();

            int numVerticalSquaresToAdvance;
            if (initialRow == BoardPosition.MAX_ROW) {
                // Can only move top to bottom down the board.
                numVerticalSquaresToAdvance = fromRow - toRow;
            } else {
                // Can only move bottom to top up the board.
                numVerticalSquaresToAdvance = toRow - fromRow;
            }
            if (numVerticalSquaresToAdvance <= 0) {
                return false;
            } else if (numVerticalSquaresToAdvance > 2) {
                return false;
            }

            int numHorizontalSquaresToMove = to.getColumn().getId() - from.getColumn().getId();
            if ((numHorizontalSquaresToMove < -1) || (numHorizontalSquaresToMove > 1)) {
                return false;
            } else if (numHorizontalSquaresToMove == 0) {
                // Just advancing. Can move two spaces on the first move, or just one, but since just advancing,
                // the target Square must be unoccupied since a Pawn cannot capture when advancing.
                if (to.isOccupied()) {
                    return false;
                }
                if (numVerticalSquaresToAdvance == 2) {
                    if (hasEverBeenMoved()) {
                        return false;
                    } else {
                        // Square being passed must be empty.
                        return true; //TODO!
                    }
                }
                return true;
            } else {
                // Attacking to capture.
                if (to.isOccupied()) {
                    return to.isOccupiedByMyOpponent(from);
                } else {
                    // The square being moved to is unoccupied. The only way this move is possible for a Pawn is
                    // if the En Passant rule comes into play.
                    Square possibleEnPassantSquare = getPossibleEnPassantSquare(chessBoard, from, to);
                    if (!possibleEnPassantSquare.isOccupied()) {
                        return false;
                    } else {
                        ChessPiece resident = possibleEnPassantSquare.getResident().get();
                        return resident.isPawn() && ((Pawn) resident).canBeCapturedDueToEnPassant();
                    }
                }
            }
        }
    }

    /**
     * Finds the Square that could house an En Passant victim.
     * @param chessBoard
     * @param from Must be non-null.
     * @param to Must be non-null.
     * @return Will be non-null.
     */
    private Square getPossibleEnPassantSquare(ChessBoard chessBoard, Square from, Square to) {
        return chessBoard.getSquare(from.getRow(), to.getColumn());
    }

    private void attemptCaptureDueToEnPassant(Square possibleEnPassantSquare) {
        if (possibleEnPassantSquare.isOccupied()) {
            ChessPiece residentSubject = possibleEnPassantSquare.getResident().get();
            if (residentSubject.isPawn() && ((Pawn) residentSubject).canBeCapturedDueToEnPassant()) {
                // Boom. The Pawn in the possibleEnPassantSquare can in fact be captured
                // as a result of this move.
                residentSubject.markCaptured();
                possibleEnPassantSquare.empty();
            }
        }
    }

    @Override
    public void moveTo(ChessBoard chessBoard, Square from, Square to) {
        basicMove(from, to);

        attemptCaptureDueToEnPassant(getPossibleEnPassantSquare(chessBoard, from, to));

        if (!hasEverBeenMoved()) {
            initialMoveWasTwoSquares = (from.getNumberRowsDistance(to) == 2);
        } else if (!reachedFarSideOfBoard) {
            int initialRow = getInitialPosition().getRow().getId();
            reachedFarSideOfBoard =
                (((initialRow == BoardPosition.MAX_ROW) && (to.getRow().getId() == BoardPosition.MIN_ROW)) ||
                 ((initialRow == BoardPosition.MIN_ROW) && (to.getRow().getId() == BoardPosition.MAX_ROW)));
        }

        numTimesMoved++;
    }

    public boolean hasEverBeenMoved() {
        return numTimesMoved > 0;
    }

    public boolean canBeCapturedDueToEnPassant() {
        return ((numTimesMoved == 1) && initialMoveWasTwoSquares);
    }

    private boolean hasReachedFarSideOfBoard() {
        return reachedFarSideOfBoard;
    }
}
