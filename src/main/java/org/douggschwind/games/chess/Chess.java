package org.douggschwind.games.chess;

import org.douggschwind.games.chess.moves.CommonMove;
import org.douggschwind.games.chess.piece.ChessPiece;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * This class allows for a very rudimentary playing of the game of chess.
 * @author Doug Gschwind
 */
public class Chess {
    private final ChessBoard chessBoard = new ChessBoard();

    private Chess() {
    }

    /**
     * Interpret the String representation of a BoardPosition.
     * @param squareIdentifier Of the form nm, where n is a number between 1 and 8
     *                         and m is a letter between a and h.
     * @return Will be non-null.
     * @throws IllegalArgumentException If the argument squareIdentifier cannot be resolved.
     */
    private BoardPosition resolveSquareIdentifier(String squareIdentifier)
    throws IllegalArgumentException {
        if ((squareIdentifier == null) || (squareIdentifier.length() != 2)) {
            throw new IllegalArgumentException("Square identifier is malformed <" + squareIdentifier + ">");
        }

        char rowIdentifier = squareIdentifier.charAt(0);
        if ((rowIdentifier < '1') || (rowIdentifier > '8')) {
            String message = "Invalid row identifier <" + rowIdentifier + ">. Must be between 1 and 8 inclusive";
            throw new IllegalArgumentException(message);
        }
        char columnIdentifier = squareIdentifier.charAt(1);
        if ((columnIdentifier < 'a') || (columnIdentifier > 'h')) {
            String message = "Invalid column identifier <" + columnIdentifier + ">. Must be between a and h inclusive";
            throw new IllegalArgumentException(message);
        }

        return new BoardPosition(BoardPosition.Row.getById(rowIdentifier - '0'),
                                 BoardPosition.Column.getById((columnIdentifier - 'a') + 1));
    }

    private Optional<CommonMove<ChessPiece>> interpretMoveInstruction(String moveInstruction) {
        if ((moveInstruction == null) || (moveInstruction.isEmpty())) {
            System.err.println("Move instruction malformed. Should be of form bp1 bp2");
            return Optional.empty();
        }
        String[] squareIdentifiers = moveInstruction.split(" ");
        if (squareIdentifiers.length != 2) {
            System.err.println("Move instruction malformed, wrong number of board positions. Should be of form bp1 bp2");
            return Optional.empty();
        }

        String fromSquareIdentifier = squareIdentifiers[0];
        String toSquareIdentifier = squareIdentifiers[1];

        BoardPosition position1;
        try {
            position1 = resolveSquareIdentifier(fromSquareIdentifier);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }

        BoardPosition position2;
        try {
            position2 = resolveSquareIdentifier(toSquareIdentifier);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }

        Square from = chessBoard.getSquare(position1.getRow(), position1.getColumn());
        if (!from.isOccupied()) {
            System.err.println("Invalid move instruction, square " + fromSquareIdentifier + " is not occupied");
            return Optional.empty();
        }
        Square to = chessBoard.getSquare(position2.getRow(), position2.getColumn());

        Class<? extends ChessPiece> chessPieceClass = from.getResident().get().getClass();
        return Optional.of(new CommonMove<ChessPiece>(chessPieceClass, from, to));
    }

    private Optional<CommonMove<ChessPiece>> getPlayerMoveInstruction(BufferedReader reader) {
        try {
            String moveInstruction = reader.readLine();
            return interpretMoveInstruction(moveInstruction);
        } catch (IOException ignored) {
            // Never expect to get here.
            System.err.println(ignored.getMessage());
            return Optional.empty();
        }
    }

    private Optional<CommonMove<ChessPiece>> getValidPlayerMoveInstruction(BufferedReader reader, Player toMakeMove) {
        Optional<CommonMove<ChessPiece>> chessMoveOptional = getPlayerMoveInstruction(reader);
        while (!chessMoveOptional.isPresent()) {
            chessMoveOptional = getPlayerMoveInstruction(reader);
        }

        CommonMove<ChessPiece> proposedMove = chessMoveOptional.get();
        if (!proposedMove.getFrom().isOccupied()) {
            System.err.println("The from square is not occupied, cannot be moved from");
        } else {
            ChessPiece toMove = proposedMove.getFrom().getResident().get();
            if (!toMakeMove.canMoveFrom(proposedMove.getFrom())) {
                System.err.println("Player " + toMakeMove + " cannot move from Piece");
                return Optional.empty();
            }
            if (!toMove.canMoveTo(chessBoard, proposedMove)) {
                System.err.println("This move is not allowed");
                return Optional.empty();
            }
        }

        return Optional.of(proposedMove);
    }

    private void playGame() {
        Player toMakeMove = Player.BLACK;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // TODO: Check for checkmate
        while (true) { // TODO
            chessBoard.print();
            System.out.println("Player " + toMakeMove.name() + " move : ");

            Optional<CommonMove<ChessPiece>> validPlayerMoveOptional = getValidPlayerMoveInstruction(reader, toMakeMove);
            while (!validPlayerMoveOptional.isPresent()) {
                validPlayerMoveOptional = getValidPlayerMoveInstruction(reader, toMakeMove);
            }

            CommonMove<ChessPiece> validatedPlayerMove = validPlayerMoveOptional.get();
            ChessPiece toMove = validatedPlayerMove.getFrom().getResident().get();
            toMove.moveTo(chessBoard, validatedPlayerMove);

            toMakeMove = (toMakeMove == Player.BLACK) ? Player.WHITE : Player.BLACK;
        }
    }

    private void playAgain() {
        chessBoard.reset();
        playGame();
    }

    public static void main(String[] args) {
        Chess game = new Chess();
        game.playGame();
    }
}
