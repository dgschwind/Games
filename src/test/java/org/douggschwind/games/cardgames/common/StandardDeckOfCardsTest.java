package org.douggschwind.games.cardgames.common;

import org.douggschwind.games.common.DeckOfCards;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class StandardDeckOfCardsTest {

	@Test
	public void testFullDeck() {
		DeckOfCards<Card> fullDeck = DeckFactory.createStandardDeck();
		
		for (int i = 0;i < 3;i++) {
			fullDeck.shuffle();
			Set<Card> dealtCards = new HashSet<>();
			try {
				while (true) {
					dealtCards.add(fullDeck.dealCard());
				}
			} catch (IllegalStateException ignored) {
				Assert.assertSame(52, dealtCards.size());
			}
		}
	}
}