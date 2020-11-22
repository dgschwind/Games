package org.douggschwind.games.chess;

import org.douggschwind.games.chess.piece.ChessPiece;
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
}
