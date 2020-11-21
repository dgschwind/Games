package org.douggschwind.games.chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class allows for a very rudimentary playing of the game of chess.
 * @author Doug Gschwind
 */
public class Chess {
    private final ChessBoard chessBoard = new ChessBoard();

    private Chess() {
    }

    private void playGame() {
        Player toMakeMove = Player.BLACK;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // TODO: Check for checkmate
        while (true) { // TODO
            chessBoard.print();
            System.out.println("Player " + toMakeMove.name() + " move : ");
            try {
                String moveInstruction = reader.readLine();
                System.exit(0);
            } catch (IOException ignored) {
            }
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
