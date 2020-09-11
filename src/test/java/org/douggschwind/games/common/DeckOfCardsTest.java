package org.douggschwind.games.common;

import org.douggschwind.games.cardgames.common.Card;
import org.junit.Assert;
import org.junit.Test;

public class DeckOfCardsTest {
    @Test
    public void testDeckOfCardsLifecycle() {
        DeckOfCards<Card> underTest = new DeckOfCards<>();

        underTest.addCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
        underTest.addCard(new Card(Card.Kind.King, Card.Suit.Spades));
        underTest.addCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
        underTest.addCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
        underTest.addCard(new Card(Card.Kind.Ten, Card.Suit.Spades));

        Assert.assertEquals(5, underTest.size());

        underTest.shuffle();
        Assert.assertFalse(underTest.haveAllCardsBeenDealt());

        Card card1 = underTest.dealCard();
        Assert.assertEquals(Card.Suit.Spades, card1.getSuit());
        Assert.assertTrue(underTest.hasCardBeenDealt(card1));
        Assert.assertFalse(underTest.haveAllCardsBeenDealt());

        Card card2 = underTest.dealCard();
        Assert.assertEquals(Card.Suit.Spades, card2.getSuit());
        Assert.assertTrue(underTest.hasCardBeenDealt(card2));
        Assert.assertFalse(underTest.haveAllCardsBeenDealt());

        Card card3 = underTest.dealCard();
        Assert.assertEquals(Card.Suit.Spades, card3.getSuit());
        Assert.assertTrue(underTest.hasCardBeenDealt(card3));
        Assert.assertFalse(underTest.haveAllCardsBeenDealt());

        Card card4 = underTest.dealCard();
        Assert.assertEquals(Card.Suit.Spades, card4.getSuit());
        Assert.assertTrue(underTest.hasCardBeenDealt(card4));
        Assert.assertFalse(underTest.haveAllCardsBeenDealt());

        Card card5 = underTest.dealCard();
        Assert.assertEquals(Card.Suit.Spades, card5.getSuit());
        Assert.assertTrue(underTest.hasCardBeenDealt(card5));
        Assert.assertTrue(underTest.haveAllCardsBeenDealt());

        try {
            underTest.dealCard();
            Assert.fail("All cards should have been dealt at this point");
        } catch (IllegalStateException e) {
        }

        underTest.shuffle();
        Assert.assertFalse(underTest.haveAllCardsBeenDealt());
        Assert.assertEquals(5, underTest.size());
    }
}
