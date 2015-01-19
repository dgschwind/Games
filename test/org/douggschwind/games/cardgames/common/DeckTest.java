package org.douggschwind.games.cardgames.common;

import java.util.HashSet;
import java.util.Set;

import org.douggschwind.games.common.DeckOfCards;
import org.junit.Assert;
import org.junit.Test;

public class DeckTest {

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
			} catch (Exception ignored) {
				Assert.assertSame(52, dealtCards.size());
			}
		}
	}
}