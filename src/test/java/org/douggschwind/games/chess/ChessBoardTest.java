package org.douggschwind.games.chess;

import org.douggschwind.games.chess.moves.CommonMove;
import org.douggschwind.games.chess.piece.Bishop;
import org.douggschwind.games.chess.piece.ChessPiece;
import org.douggschwind.games.chess.piece.King;
import org.douggschwind.games.chess.piece.Knight;
import org.douggschwind.games.chess.piece.Queen;
import org.douggschwind.games.chess.piece.Rook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests interesting operations on ChessBoard and there are many.
 * @author Doug Gschwind
 */
public class ChessBoardTest {
    private ChessBoard underTest;

    @Before
    public void setUp() {
        underTest = new ChessBoard();
    }

    @Test
    public void testStartOfGameSetup() {
        Square leftBlackRook = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.a);
        Assert.assertNotNull(leftBlackRook);
        Assert.assertTrue(leftBlackRook.isOccupied());
        ChessPiece occupant = leftBlackRook.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isRook());

        Square leftBlackKnight = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.b);
        Assert.assertNotNull(leftBlackKnight);
        Assert.assertTrue(leftBlackKnight.isOccupied());
        occupant = leftBlackKnight.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isKnight());

        Square leftBlackBishop = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.c);
        Assert.assertNotNull(leftBlackBishop);
        Assert.assertTrue(leftBlackBishop.isOccupied());
        occupant = leftBlackBishop.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isBishop());

        Square blackQueen = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.d);
        Assert.assertNotNull(blackQueen);
        Assert.assertTrue(blackQueen.isOccupied());
        occupant = blackQueen.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isQueen());

        Square blackKing = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.e);
        Assert.assertNotNull(blackKing);
        Assert.assertTrue(blackKing.isOccupied());
        occupant = blackKing.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isKing());

        Square rightBlackBishop = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.f);
        Assert.assertNotNull(rightBlackBishop);
        Assert.assertTrue(rightBlackBishop.isOccupied());
        occupant = rightBlackBishop.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isBishop());

        Square rightBlackKnight = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.g);
        Assert.assertNotNull(rightBlackKnight);
        Assert.assertTrue(rightBlackKnight.isOccupied());
        occupant = rightBlackKnight.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isKnight());

        Square rightBlackRook = underTest.getSquare(BoardPosition.Row.R8, BoardPosition.Column.h);
        Assert.assertNotNull(rightBlackRook);
        Assert.assertTrue(rightBlackRook.isOccupied());
        occupant = rightBlackRook.getResident().get();
        Assert.assertEquals(Player.BLACK, occupant.getOwner());
        Assert.assertTrue(occupant.isRook());

        // Now, lets check all of the Black player Pawns
        for (BoardPosition.Column column : BoardPosition.Column.values()) {
            Square pawnSquare = underTest.getSquare(BoardPosition.Row.R7, column);
            Assert.assertNotNull(pawnSquare);
            Assert.assertTrue(pawnSquare.isOccupied());
            occupant = pawnSquare.getResident().get();
            Assert.assertEquals(Player.BLACK, occupant.getOwner());
            Assert.assertTrue(occupant.isPawn());
        }

        // Now lets check all of the empty squares
        for (int rowId = 3;rowId <= 6;rowId++) {
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                Square emptySquare = underTest.getSquare(BoardPosition.Row.getById(rowId), column);
                Assert.assertNotNull(emptySquare);
                Assert.assertFalse(emptySquare.isOccupied());
            }
        }

        // Now, lets check all of the White player Pawns
        for (BoardPosition.Column column : BoardPosition.Column.values()) {
            Square pawnSquare = underTest.getSquare(BoardPosition.Row.R2, column);
            Assert.assertNotNull(pawnSquare);
            Assert.assertTrue(pawnSquare.isOccupied());
            occupant = pawnSquare.getResident().get();
            Assert.assertEquals(Player.WHITE, occupant.getOwner());
            Assert.assertTrue(occupant.isPawn());
        }

        // Now, lets check all of the White player last row
        Square leftWhiteRook = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.a);
        Assert.assertNotNull(leftWhiteRook);
        Assert.assertTrue(leftWhiteRook.isOccupied());
        occupant = leftWhiteRook.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isRook());

        Square leftWhiteKnight = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.b);
        Assert.assertNotNull(leftWhiteKnight);
        Assert.assertTrue(leftWhiteKnight.isOccupied());
        occupant = leftWhiteKnight.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isKnight());

        Square leftWhiteBishop = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.c);
        Assert.assertNotNull(leftWhiteBishop);
        Assert.assertTrue(leftWhiteBishop.isOccupied());
        occupant = leftWhiteBishop.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isBishop());

        Square whiteQueen = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.d);
        Assert.assertNotNull(whiteQueen);
        Assert.assertTrue(whiteQueen.isOccupied());
        occupant = whiteQueen.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isQueen());

        Square whiteKing = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.e);
        Assert.assertNotNull(whiteKing);
        Assert.assertTrue(whiteKing.isOccupied());
        occupant = whiteKing.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isKing());

        Square rightWhiteBishop = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.f);
        Assert.assertNotNull(rightWhiteBishop);
        Assert.assertTrue(rightWhiteBishop.isOccupied());
        occupant = rightWhiteBishop.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isBishop());

        Square rightWhiteKnight = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.g);
        Assert.assertNotNull(rightWhiteKnight);
        Assert.assertTrue(rightWhiteKnight.isOccupied());
        occupant = rightWhiteKnight.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isKnight());

        Square rightWhiteRook = underTest.getSquare(BoardPosition.Row.R1, BoardPosition.Column.h);
        Assert.assertNotNull(rightWhiteRook);
        Assert.assertTrue(rightWhiteRook.isOccupied());
        occupant = rightWhiteRook.getResident().get();
        Assert.assertEquals(Player.WHITE, occupant.getOwner());
        Assert.assertTrue(occupant.isRook());
    }

    private void testPlayerCanMoveFrom(Player player,
                                       BoardPosition.Row fromRow,
                                       BoardPosition.Column fromColumn,
                                       boolean expectedResult) {
        Square fromSquare = underTest.getSquare(fromRow, fromColumn);
        Assert.assertEquals(expectedResult, player.canMoveFrom(fromSquare));
    }

    @Test
    public void testCanMoveFrom() {
        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            boolean expectedResult = ((row == BoardPosition.Row.R8) || (row == BoardPosition.Row.R7));
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                testPlayerCanMoveFrom(Player.BLACK, row, column, expectedResult);
            }
        }

        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            boolean expectedResult = ((row == BoardPosition.Row.R2) || (row == BoardPosition.Row.R1));
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                testPlayerCanMoveFrom(Player.WHITE, row, column, expectedResult);
            }
        }
    }

    private void testRookCanMoveFromStartOfGame(Player owner, BoardPosition.Column fromColumn) {
        BoardPosition.Row fromRow = owner.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        Square from = underTest.getSquare(fromRow, fromColumn);
        Rook subjectRook = (Rook) from.getResident().get();

        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                Square to = underTest.getSquare(row, column);
                if (!from.equals(to)) {
                    CommonMove proposedCommonMove = new CommonMove(from, to);
                    Assert.assertFalse(subjectRook.canMoveTo(underTest, proposedCommonMove));
                }
            }
        }
    }

    private void testKnightCanMoveFromStartOfGame(Player owner, BoardPosition.Column fromColumn) {
        final BoardPosition.Row fromRow = owner.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        final BoardPosition.Row allowedRow = owner.isBlack() ? BoardPosition.Row.R6 : BoardPosition.Row.R3;
        Square from = underTest.getSquare(fromRow, fromColumn);
        Knight subjectKnight = (Knight) from.getResident().get();

        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                Square to = underTest.getSquare(row, column);
                if (!from.equals(to)) {
                    CommonMove proposedCommonMove = new CommonMove(from, to);
                    if ((allowedRow == row) &&
                        ((column.getId() == fromColumn.getId() - 1) ||
                         (column.getId() == fromColumn.getId() + 1))) {
                        Assert.assertTrue(subjectKnight.canMoveTo(underTest, proposedCommonMove));
                    } else {
                        Assert.assertFalse(subjectKnight.canMoveTo(underTest, proposedCommonMove));
                    }
                }
            }
        }
    }

    private void testBishopCanMoveFromStartOfGame(Player owner, BoardPosition.Column fromColumn) {
        BoardPosition.Row fromRow = owner.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        Square from = underTest.getSquare(fromRow, fromColumn);
        Bishop subjectBishop = (Bishop) from.getResident().get();

        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                Square to = underTest.getSquare(row, column);
                if (!from.equals(to)) {
                    CommonMove proposedCommonMove = new CommonMove(from, to);
                    Assert.assertFalse(subjectBishop.canMoveTo(underTest, proposedCommonMove));
                }
            }
        }
    }

    private void testQueenCanMoveFromStartOfGame(Player owner, BoardPosition.Column fromColumn) {
        BoardPosition.Row fromRow = owner.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        Square from = underTest.getSquare(fromRow, fromColumn);
        Queen subjectQueen = (Queen) from.getResident().get();

        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                Square to = underTest.getSquare(row, column);
                if (!from.equals(to)) {
                    CommonMove proposedCommonMove = new CommonMove(from, to);
                    Assert.assertFalse(subjectQueen.canMoveTo(underTest, proposedCommonMove));
                }
            }
        }
    }

    private void testKingCanMoveFromStartOfGame(Player owner, BoardPosition.Column fromColumn) {
        BoardPosition.Row fromRow = owner.isBlack() ? BoardPosition.Row.R8 : BoardPosition.Row.R1;
        Square from = underTest.getSquare(fromRow, fromColumn);
        King subjectKing = (King) from.getResident().get();

        for (BoardPosition.Row row : BoardPosition.Row.values()) {
            for (BoardPosition.Column column : BoardPosition.Column.values()) {
                Square to = underTest.getSquare(row, column);
                if (!from.equals(to)) {
                    CommonMove proposedCommonMove = new CommonMove(from, to);
                    Assert.assertFalse(subjectKing.canMoveTo(underTest, proposedCommonMove));
                }
            }
        }
    }

    /**
     * Tests where each of the Chess Pieces can move to, fron their initial start of game state.
     */
    @Test
    public void testCanMoveToStartOfGame() {
        testRookCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.a);
        testKnightCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.b);
        testBishopCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.c);
        testQueenCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.d);
        testKingCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.e);
        testBishopCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.f);
        testKnightCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.g);
        testRookCanMoveFromStartOfGame(Player.BLACK, BoardPosition.Column.h);

        testRookCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.a);
        testKnightCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.b);
        testBishopCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.c);
        testQueenCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.d);
        testKingCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.e);
        testBishopCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.f);
        testKnightCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.g);
        testRookCanMoveFromStartOfGame(Player.WHITE, BoardPosition.Column.h);
    }

    @Test
    public void testEnPassantToLeft() {
        Square twoB = underTest.getSquare(BoardPosition.Row.R2, BoardPosition.Column.b);
        Square fourB = underTest.getSquare(BoardPosition.Row.R4, BoardPosition.Column.b);
        Square fiveB = underTest.getSquare(BoardPosition.Row.R5, BoardPosition.Column.b);
        Square sixA = underTest.getSquare(BoardPosition.Row.R6, BoardPosition.Column.a);
        ChessPiece whitePawn = twoB.getResident().get();
        CommonMove firstWhiteMove = new CommonMove(twoB, fourB);
        whitePawn.moveTo(underTest, firstWhiteMove);
        CommonMove secondWhiteMove = new CommonMove(fourB, fiveB);
        whitePawn.moveTo(underTest, secondWhiteMove);

        // Now that we have set the stage for the White Pawn, move the Black Pawn from 7a to 5a so that
        // the White Pawn should be able to capture it with a subsequent En Passant move.
        Square sevenA = underTest.getSquare(BoardPosition.Row.R7, BoardPosition.Column.a);
        Square fiveA = underTest.getSquare(BoardPosition.Row.R5, BoardPosition.Column.a);
        ChessPiece blackPawn = sevenA.getResident().get();
        CommonMove firstBlackMove = new CommonMove(sevenA, fiveA);
        Assert.assertFalse(fiveA.isOccupied());
        blackPawn.moveTo(underTest, firstBlackMove);
        Assert.assertTrue(fiveA.isOccupied());
        Assert.assertSame(blackPawn, fiveA.getResident().get());

        Assert.assertFalse(whitePawn.hasBeenCaptured());
        Assert.assertFalse(blackPawn.hasBeenCaptured());

        CommonMove enPassantByWhitePawn = new CommonMove(fiveB, sixA);
        Assert.assertTrue(whitePawn.canMoveTo(underTest, enPassantByWhitePawn));
        Assert.assertFalse(sixA.isOccupied());
        whitePawn.moveTo(underTest, enPassantByWhitePawn);
        Assert.assertTrue(sixA.isOccupied());

        // At this point, the White Pawn should be in square R6 A, and the Black Pawn has been captured
        // leaving square R5 A empty.
        Assert.assertFalse(whitePawn.hasBeenCaptured());
        Assert.assertTrue(blackPawn.hasBeenCaptured());
        Assert.assertFalse(fiveA.isOccupied());
        Assert.assertTrue(sixA.isOccupied());
        Assert.assertSame(whitePawn, sixA.getResident().get());
    }

    @Test
    public void testEnPassantToRight() {
        Square twoB = underTest.getSquare(BoardPosition.Row.R2, BoardPosition.Column.b);
        Square fourB = underTest.getSquare(BoardPosition.Row.R4, BoardPosition.Column.b);
        Square fiveB = underTest.getSquare(BoardPosition.Row.R5, BoardPosition.Column.b);
        Square sixC = underTest.getSquare(BoardPosition.Row.R6, BoardPosition.Column.c);
        ChessPiece whitePawn = twoB.getResident().get();
        CommonMove firstWhiteMove = new CommonMove(twoB, fourB);
        whitePawn.moveTo(underTest, firstWhiteMove);
        CommonMove secondWhiteMove = new CommonMove(fourB, fiveB);
        whitePawn.moveTo(underTest, secondWhiteMove);

        // Now that we have set the stage for the White Pawn, move the Black Pawn from 7c to 5c so that
        // the White Pawn should be able to capture it with a subsequent En Passant move.
        Square sevenC = underTest.getSquare(BoardPosition.Row.R7, BoardPosition.Column.c);
        Square fiveC = underTest.getSquare(BoardPosition.Row.R5, BoardPosition.Column.c);
        ChessPiece blackPawn = sevenC.getResident().get();
        CommonMove firstBlackMove = new CommonMove(sevenC, fiveC);
        Assert.assertFalse(fiveC.isOccupied());
        blackPawn.moveTo(underTest, firstBlackMove);
        Assert.assertTrue(fiveC.isOccupied());
        Assert.assertSame(blackPawn, fiveC.getResident().get());

        Assert.assertFalse(whitePawn.hasBeenCaptured());
        Assert.assertFalse(blackPawn.hasBeenCaptured());

        CommonMove enPassantByWhitePawn = new CommonMove(fiveB, sixC);
        Assert.assertTrue(whitePawn.canMoveTo(underTest, enPassantByWhitePawn));
        Assert.assertFalse(sixC.isOccupied());
        whitePawn.moveTo(underTest, enPassantByWhitePawn);
        Assert.assertTrue(sixC.isOccupied());

        // At this point, the White Pawn should be in square R6 C, and the Black Pawn has been captured
        // leaving square R5 C empty.
        Assert.assertFalse(whitePawn.hasBeenCaptured());
        Assert.assertTrue(blackPawn.hasBeenCaptured());
        Assert.assertFalse(fiveC.isOccupied());
        Assert.assertTrue(sixC.isOccupied());
        Assert.assertSame(whitePawn, sixC.getResident().get());
    }
}
