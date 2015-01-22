package org.douggschwind.games.boardgames.monopoly.actioncard;

import java.util.HashSet;
import java.util.Set;

import org.douggschwind.games.boardgames.monopoly.actioncard.ActionCard;
import org.douggschwind.games.boardgames.monopoly.actioncard.DeckFactory;
import org.douggschwind.games.common.DeckOfCards;
import org.junit.Assert;
import org.junit.Test;

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
			} catch (Exception ignored) {
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
			} catch (Exception ignored) {
				Assert.assertSame(17, dealtCards.size());
			}
		}
	}
}