package org.douggschwind.games.chess;

import org.douggschwind.games.chess.moves.CastlingMove;
import org.douggschwind.games.chess.moves.ChessMove;
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
    private static final String QUEEN_SIDE_CASTLING_MOVE_INSTRUCTION = "qsc";
    private static final String KING_SIDE_CASTLING_MOVE_INSTRUCTION = "ksc";

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

    private Optional<CommonMove<ChessPiece>> interpretCommonMoveInstruction(String moveInstruction) {
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

    private Optional<CastlingMove> interpretCastlingInstruction(Player toMakeMove, String moveInstruction) {
        if (QUEEN_SIDE_CASTLING_MOVE_INSTRUCTION.equals(moveInstruction)) {
            return Optional.of(CastlingMove.newQueenSideMove(toMakeMove, chessBoard));
        } else if (KING_SIDE_CASTLING_MOVE_INSTRUCTION.equals(moveInstruction)) {
            return Optional.of(CastlingMove.newKingSideMove(toMakeMove, chessBoard));
        }

        return Optional.empty();
    }

    private Optional<? extends ChessMove> interpretMoveInstruction(Player toMakeMove, String moveInstruction) {
        if ((moveInstruction == null) || (moveInstruction.isEmpty())) {
            String message = "Move instruction malformed. Should be of form " +
                    QUEEN_SIDE_CASTLING_MOVE_INSTRUCTION +
                    " or " +
                    KING_SIDE_CASTLING_MOVE_INSTRUCTION +
                    " or bp1 bp2";
            System.err.println(message);
            return Optional.empty();
        }

        if (QUEEN_SIDE_CASTLING_MOVE_INSTRUCTION.equals(moveInstruction) ||
            KING_SIDE_CASTLING_MOVE_INSTRUCTION.equals(moveInstruction)) {
            return interpretCastlingInstruction(toMakeMove, moveInstruction);
        } else {
            return interpretCommonMoveInstruction(moveInstruction);
        }
    }

    private Optional<? extends ChessMove> getPlayerMoveInstruction(BufferedReader reader, Player toMakeMove) {
        try {
            String moveInstruction = reader.readLine();
            return interpretMoveInstruction(toMakeMove, moveInstruction);
        } catch (IOException ignored) {
            // Never expect to get here.
            System.err.println(ignored.getMessage());
            return Optional.empty();
        }
    }

    private Optional<? extends ChessMove> getValidPlayerMoveInstruction(BufferedReader reader, Player toMakeMove) {
        Optional<? extends ChessMove> chessMoveOptional = getPlayerMoveInstruction(reader, toMakeMove);
        while (!chessMoveOptional.isPresent()) {
            chessMoveOptional = getPlayerMoveInstruction(reader, toMakeMove);
        }

        ChessMove proposedMove = chessMoveOptional.get();
        if (proposedMove.isPermitted(toMakeMove, chessBoard)) {
            return Optional.of(proposedMove);
        } else {
            System.err.println("This move is not permitted");
            return Optional.empty();
        }
    }

    private void printChessBoardState() {
        for (String rowOutput : chessBoard.print()) {
            System.out.println(rowOutput);
        }
    }

    private void playGame() {
        Player toMakeMove = Player.BLACK;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // TODO: Check for checkmate
        while (true) { // TODO
            printChessBoardState();
            System.out.print("Player " + toMakeMove.name() + " move : ");

            Optional<? extends ChessMove> validPlayerMoveOptional = getValidPlayerMoveInstruction(reader, toMakeMove);
            System.out.println();
            while (!validPlayerMoveOptional.isPresent()) {
                validPlayerMoveOptional = getValidPlayerMoveInstruction(reader, toMakeMove);
            }

            ChessMove proposedMove = validPlayerMoveOptional.get();
            if (proposedMove.isCastlingMove()) {
                CastlingMove validatedCastlingMove = (CastlingMove) proposedMove;
                validatedCastlingMove.handleMove(chessBoard);
            } else if (proposedMove.isCommonMove()) {
                CommonMove<ChessPiece> validatedCommonMove = (CommonMove) proposedMove;
                ChessPiece toMove = validatedCommonMove.getFrom().getResident().get();
                toMove.moveTo(chessBoard, validatedCommonMove);
            }

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
