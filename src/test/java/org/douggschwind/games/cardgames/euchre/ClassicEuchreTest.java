package org.douggschwind.games.cardgames.euchre;

import org.douggschwind.games.cardgames.common.Player;
import org.junit.Assert;
import org.junit.Test;

public class ClassicEuchreTest {
    @Test
    public void testHand() {
        ClassicEuchre underTest = new ClassicEuchre();

        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();

        underTest.addPlayer(player1);
        underTest.addPlayer(player2);
        underTest.addPlayer(player3);
        underTest.addPlayer(player4);

        underTest.newHand();
        underTest.dealCardsToPlayers();

        Assert.assertEquals(5, player1.getHand().size());
        Assert.assertEquals(5, player2.getHand().size());
        Assert.assertEquals(5, player3.getHand().size());
        Assert.assertEquals(5, player4.getHand().size());

        underTest.newHand();

        Assert.assertTrue(player1.getHand().isEmpty());
        Assert.assertTrue(player2.getHand().isEmpty());
        Assert.assertTrue(player3.getHand().isEmpty());
        Assert.assertTrue(player4.getHand().isEmpty());
    }
}
