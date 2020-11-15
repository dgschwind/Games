package org.douggschwind.games.dicegames.boggle;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author Doug Gschwind
 */
public class BoggleTest {
    @Test
    public void testBoggleWordFindingNoQuPresent() {
        DieLetter[][] gameBoard = new DieLetter[4][];
        gameBoard[0] = new DieLetter[4];
        gameBoard[0][0] = new DieLetter('s');
        gameBoard[0][1] = new DieLetter('u');
        gameBoard[0][2] = new DieLetter('m');
        gameBoard[0][3] = new DieLetter('e');
        gameBoard[1] = new DieLetter[4];
        gameBoard[1][0] = new DieLetter('r');
        gameBoard[1][1] = new DieLetter('e');
        gameBoard[1][2] = new DieLetter('i');
        gameBoard[1][3] = new DieLetter('n');
        gameBoard[2] = new DieLetter[4];
        gameBoard[2][0] = new DieLetter('s');
        gameBoard[2][1] = new DieLetter('a');
        gameBoard[2][2] = new DieLetter('e');
        gameBoard[2][3] = new DieLetter('i');
        gameBoard[3] = new DieLetter[4];
        gameBoard[3][0] = new DieLetter('e');
        gameBoard[3][1] = new DieLetter('r');
        gameBoard[3][2] = new DieLetter('t');
        gameBoard[3][3] = new DieLetter('f');

        Boggle subject = new Boggle();
        Set<String> wordsFound = subject.findWordsPresent(gameBoard);
        Assert.assertEquals(24, wordsFound.size());
        Assert.assertTrue(wordsFound.contains("ate"));
        Assert.assertTrue(wordsFound.contains("eat"));
        Assert.assertTrue(wordsFound.contains("eaten"));
        Assert.assertTrue(wordsFound.contains("fin"));
        Assert.assertTrue(wordsFound.contains("fine"));
        Assert.assertTrue(wordsFound.contains("fit"));
        Assert.assertTrue(wordsFound.contains("meat"));
        Assert.assertTrue(wordsFound.contains("meet"));
        Assert.assertTrue(wordsFound.contains("net"));
        Assert.assertTrue(wordsFound.contains("rat"));
        Assert.assertTrue(wordsFound.contains("rate"));
        Assert.assertTrue(wordsFound.contains("resume"));
        Assert.assertTrue(wordsFound.contains("sat"));
        Assert.assertTrue(wordsFound.contains("sea"));
        Assert.assertTrue(wordsFound.contains("seas"));
        Assert.assertTrue(wordsFound.contains("see"));
        Assert.assertTrue(wordsFound.contains("seen"));
        Assert.assertTrue(wordsFound.contains("tar"));
        Assert.assertTrue(wordsFound.contains("tare"));
        Assert.assertTrue(wordsFound.contains("tea"));
        Assert.assertTrue(wordsFound.contains("teas"));
        Assert.assertTrue(wordsFound.contains("tee"));
        Assert.assertTrue(wordsFound.contains("tees"));
        Assert.assertTrue(wordsFound.contains("ten"));
    }

    @Test
    public void testBoggleWordFindingQuPresent() {
        DieLetter[][] gameBoard = new DieLetter[4][];
        gameBoard[0] = new DieLetter[4];
        gameBoard[0][0] = new DieLetter('k');
        gameBoard[0][1] = new DieLetter('p');
        gameBoard[0][2] = new DieLetter('t');
        gameBoard[0][3] = new DieLetter('a');
        gameBoard[1] = new DieLetter[4];
        gameBoard[1][0] = new DieLetter('r');
        gameBoard[1][1] = new DieLetter('i');
        gameBoard[1][2] = new DieLetter('n');
        gameBoard[1][3] = new DieLetter('s');
        gameBoard[2] = new DieLetter[4];
        gameBoard[2][0] = new DieLetter('n');
        gameBoard[2][1] = new DieLetter('h');
        gameBoard[2][2] = new DieLetter('q', true);
        gameBoard[2][3] = new DieLetter('c');
        gameBoard[3] = new DieLetter[4];
        gameBoard[3][0] = new DieLetter('i');
        gameBoard[3][1] = new DieLetter('l');
        gameBoard[3][2] = new DieLetter('d');
        gameBoard[3][3] = new DieLetter('h');

        Boggle subject = new Boggle();
        Set<String> wordsFound = subject.findWordsPresent(gameBoard);
        Assert.assertEquals(7, wordsFound.size());
        Assert.assertTrue(wordsFound.contains("kit"));
        Assert.assertTrue(wordsFound.contains("kits"));
        Assert.assertTrue(wordsFound.contains("pin"));
        Assert.assertTrue(wordsFound.contains("pins"));
        Assert.assertTrue(wordsFound.contains("quit"));
        Assert.assertTrue(wordsFound.contains("quits"));
        Assert.assertTrue(wordsFound.contains("sat"));
    }
}
