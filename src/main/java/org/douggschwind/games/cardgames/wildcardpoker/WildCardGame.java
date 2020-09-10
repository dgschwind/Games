package org.douggschwind.games.cardgames.wildcardpoker;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.StandardDeckCardGame;
import org.douggschwind.games.common.DeckOfCards;

/**
 * This is a 5 card draw poker game where all four deuces in the deck
 * are each considered a wild card.
 * @author Doug Gschwind
 */
public abstract class WildCardGame extends StandardDeckCardGame {
	
	private Set<Card> wildCards = new HashSet<>();

	protected WildCardGame(DeckOfCards<Card> deck) {
		super(deck);
	}
	
	protected void addWildCard(Card wildCard) {
		if (wildCard != null) {
			wildCards.add(wildCard);
		}
	}

	protected final boolean isWildCard(Card card) {
		return wildCards.contains(card);
	}
	
	/**
	 * Determines the number of wild cards present in the given Collection
	 * of Cards present in a player's hand.
	 * @param playersHand Must be non-null and expected to be non-empty.
	 * @return Will be non-negative.
	 */
	protected int determineNumberOfWildCardsInPlayersHand(Collection<Card> playersHand) {
		return (int) playersHand.stream().filter(cardInHand -> isWildCard(cardInHand)).count();
	}
	
	protected List<Card> eliminateWildCards(List<Card> cards) {
		return cards.stream().filter(card -> !isWildCard(card)).collect(Collectors.toList());
	}
}