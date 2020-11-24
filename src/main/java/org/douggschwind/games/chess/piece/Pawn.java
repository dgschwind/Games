package org.douggschwind.games.chess.piece;

import org.douggschwind.games.chess.BoardPosition;
import org.douggschwind.games.chess.ChessBoard;
import org.douggschwind.games.chess.ChessMove;
import org.douggschwind.games.chess.Player;
import org.douggschwind.games.chess.Square;

/**
 * Represents one of the eight Pawn pieces in the game of Chess, for a given Player.
 * @author Doug Gschwind
 */
public class Pawn extends ChessPiece {
    private boolean initialMoveWasTwoSquares;
    private boolean reachedFarSideOfBoard;

    public Pawn(Player player) {
        super(player);
    }

    @Override
    public final boolean isPawn() {
        return true;
    }

    /**
     * Pawns move vertically forward one square, with the option to move two squares if they have not yet moved.
     * Pawns are the only piece to capture different to how they move. Pawns capture one square diagonally in a forward
     * direction. Pawns are unable to move backwards on captures or moves. Upon reaching the other side of the board a
     * pawn promotes into any other piece, except for a king. Additionally, pawns can make a special move named
     * En Passant.
     * @param chessBoard Must be non-null.
     * @param proposedMove Must be non-null and properly populated.
     * @return
     */
    @Override
    public boolean canMoveTo(ChessBoard chessBoard, ChessMove proposedMove) {
        if (hasReachedFarSideOfBoard()) {
            // Also need to consider once Pawn has in fact reached the far side of the board.
            return false; //TODO
        } else {
            final Square from = proposedMove.getFrom();
            final Square to = proposedMove.getTo();

            int fromRow = from.getRow().getId();
            int toRow = to.getRow().getId();

            int numVerticalSquaresToAdvance;
            if (getAdvancingDirection() == AdvancingDirection.TOP_TO_BOTTOM) {
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
                // Advancing mode. Can move two spaces on the first move, or just one, but since just advancing,
                // the target Square must be unoccupied since a Pawn cannot capture when advancing.
                if (to.isOccupied()) {
                    return false;
                }
                if (numVerticalSquaresToAdvance == 2) {
                    if (hasEverBeenMoved()) {
                        return false;
                    } else {
                        // Square being passed must be empty.
                        return chessBoard.isPathClear(proposedMove);
                    }
                }
                return true;
            } else {
                // Capturing mode.
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
    protected void handleMoveTo(ChessBoard chessBoard, ChessMove move) {
        basicMove(move);

        final Square from = move.getFrom();
        final Square to = move.getTo();

        attemptCaptureDueToEnPassant(getPossibleEnPassantSquare(chessBoard, from, to));

        if (!hasEverBeenMoved()) {
            initialMoveWasTwoSquares = (from.getNumberRowsDistance(to) == 2);
        } else if (!reachedFarSideOfBoard) {
            int initialRow = getInitialPosition().getRow().getId();
            reachedFarSideOfBoard =
                (((initialRow == BoardPosition.MAX_ROW) && (to.getRow().getId() == BoardPosition.MIN_ROW)) ||
                 ((initialRow == BoardPosition.MIN_ROW) && (to.getRow().getId() == BoardPosition.MAX_ROW)));
        }
    }

    public boolean canBeCapturedDueToEnPassant() {
        return ((getNumTimesMoved() == 1) && initialMoveWasTwoSquares);
    }

    private boolean hasReachedFarSideOfBoard() {
        return reachedFarSideOfBoard;
    }
}
