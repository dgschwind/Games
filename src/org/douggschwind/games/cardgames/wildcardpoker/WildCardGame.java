package org.douggschwind.games.cardgames.wildcardpoker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.Deck;
import org.douggschwind.games.cardgames.common.StandardDeckCardGame;

/**
 * This is a 5 card draw poker game where all four deuces in the deck
 * are each considered a wild card.
 * @author Doug Gschwind
 */
public abstract class WildCardGame extends StandardDeckCardGame {
	
	private Set<Card> wildCards = new HashSet<>();

	protected WildCardGame(Deck deck) {
		super(deck);
	}
	
	protected void addWildCard(Card wildCard) {
		if (wildCard != null) {
			wildCards.add(wildCard);
		}
	}
	
	private boolean isWildCard(Card card) {
		return wildCards.contains(card);
	}
	
	protected List<Card> eliminateWildCards(List<Card> cards) {
		List<Card> result = new ArrayList<>(cards);
		for (Iterator<Card> iter = result.iterator();iter.hasNext();) {
			Card card = iter.next();
			if (isWildCard(card)) {
				iter.remove();
			}
		}
		return result;
	}
}