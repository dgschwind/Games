package org.douggschwind.games.boardgames.monopoly.actioncard;

import org.douggschwind.games.common.DeckOfCards;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class DeckFactoryTest {
	
	@Test
	public void testChanceDeck() {
		DeckOfCards<ActionCard> fullDeck = DeckFactory.createChanceDeck();
		
		for (int i = 0;i < 3;i++) {
			fullDeck.shuffle();
			Set<ActionCard> dealtCards = new HashSet<>();
			try {
				while (true) {
					dealtCards.add(fullDeck.dealCard());
				}
			} catch (IllegalStateException ignored) {
				Assert.assertSame(16, dealtCards.size());
			}
		}
	}

	@Test
	public void testCommunityChestDeck() {
		DeckOfCards<ActionCard> fullDeck = DeckFactory.createCommunityChestDeck();
		
		for (int i = 0;i < 3;i++) {
			fullDeck.shuffle();
			Set<ActionCard> dealtCards = new HashSet<>();
			try {
				while (true) {
					dealtCards.add(fullDeck.dealCard());
				}
			} catch (IllegalStateException ignored) {
				Assert.assertSame(17, dealtCards.size());
			}
		}
	}
}