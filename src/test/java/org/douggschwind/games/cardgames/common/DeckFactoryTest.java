package org.douggschwind.games.cardgames.common;

import org.douggschwind.games.common.DeckOfCards;
import org.junit.Assert;
import org.junit.Test;
import sun.nio.cs.ext.EUC_CN;

import java.util.HashSet;
import java.util.Set;

public class DeckFactoryTest {

	@Test
	public void testStandardDeck() {
		DeckOfCards<Card> standardDeck = DeckFactory.createStandardDeck();
		final int EXPECTED_STANDARD_DECK_SIZE = 52;
		Assert.assertEquals(EXPECTED_STANDARD_DECK_SIZE, standardDeck.size());

		boolean executedSpecificCardAssertions = false;

		for (int i = 0;i < 3;i++) {
			standardDeck.shuffle();
			Set<Card> dealtCards = new HashSet<>();
			try {
				while (true) {
					dealtCards.add(standardDeck.dealCard());
				}
			} catch (IllegalStateException ignored) {
				executedSpecificCardAssertions = true;
			    for (Card.Suit suit : Card.Suit.values()) {
			    	for (Card.Kind kind : Card.Kind.values()) {
						Assert.assertTrue(dealtCards.contains(new Card(kind, suit)));
					}
				}
				// Lets just verify the size of the deck has not changed.
				Assert.assertEquals(EXPECTED_STANDARD_DECK_SIZE, standardDeck.size());
			}
		}

		Assert.assertTrue(executedSpecificCardAssertions);
	}

	@Test
	public void testEuchreDeck() {
		DeckOfCards<Card> euchreDeck = DeckFactory.createEuchreDeck();
		final int EXPECTED_EUCHRE_DECK_SIZE = 24;
		Assert.assertEquals(EXPECTED_EUCHRE_DECK_SIZE, euchreDeck.size());

		boolean executedSpecificCardAssertions = false;

		for (int i = 0;i < 3;i++) {
			euchreDeck.shuffle();
			Set<Card> dealtCards = new HashSet<>();
			try {
				int numCardsDealt = 0;
				// We will deal all 24 cards in the deck for good measure.
				while (numCardsDealt < EXPECTED_EUCHRE_DECK_SIZE) {
					dealtCards.add(euchreDeck.dealCard());
				}
			} catch (IllegalStateException ignored) {
				executedSpecificCardAssertions = true;
				for (Card.Suit suit : Card.Suit.values()) {
					Assert.assertTrue(dealtCards.contains(new Card(Card.Kind.Ace, suit)));
					Assert.assertTrue(dealtCards.contains(new Card(Card.Kind.King, suit)));
					Assert.assertTrue(dealtCards.contains(new Card(Card.Kind.Queen, suit)));
					Assert.assertTrue(dealtCards.contains(new Card(Card.Kind.Jack, suit)));
					Assert.assertTrue(dealtCards.contains(new Card(Card.Kind.Ten, suit)));
					Assert.assertTrue(dealtCards.contains(new Card(Card.Kind.Nine, suit)));
					// Lets just verify the size of the deck has not changed.
					Assert.assertEquals(EXPECTED_EUCHRE_DECK_SIZE, euchreDeck.size());
				}
			}
		}

		Assert.assertTrue(executedSpecificCardAssertions);
	}
}