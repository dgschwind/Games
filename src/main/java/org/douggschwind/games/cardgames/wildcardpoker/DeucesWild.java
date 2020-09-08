package org.douggschwind.games.cardgames.wildcardpoker;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.DeckFactory;
import org.douggschwind.games.cardgames.common.Player;
import org.douggschwind.games.cardgames.poker.common.Flush;
import org.douggschwind.games.cardgames.poker.common.FourOfAKind;
import org.douggschwind.games.cardgames.poker.common.HandStrength;
import org.douggschwind.games.cardgames.poker.common.HighCard;
import org.douggschwind.games.cardgames.poker.common.Pair;
import org.douggschwind.games.cardgames.poker.common.TwoPair;

/**
 * This is a 5 card draw poker game where all four deuces in the deck
 * are each considered a wild card. For the purposes of this game,
 * the drawing part of the game is ignored in favor of simply dealing
 * with the 5 cards dealt to each player.
 * @author Doug Gschwind
 */
public class DeucesWild extends WildCardGame {

	public DeucesWild() {
		super(DeckFactory.createStandardDeck());
		// Since Card instances are essentially ValueObjects, just create
		// new Card instances to specify wild cards, rather than trying
		// to obtain the correct Card references out of the deck.
		addWildCard(new Card(Card.Kind.Two, Card.Suit.Spades));
		addWildCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		addWildCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		addWildCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
	}

	@Override
	protected final int getNumberCardsDealtToEachPlayer() {
		return 5;
	}
	
	@Override
	public HandStrength determinePlayerHandStrength(Player player) {
		int numberOfWildCardsInHand = determineNumberOfWildCardsInPlayersHand(player.getHand());
		if (numberOfWildCardsInHand == 0) {
			// player has no wild cards.
			return super.determinePlayerHandStrength(player);
		}
		
		final Map<Card.Kind, Integer> numKindOccurrencesMap = determineNumKindOccurrencesInHand(player.getHand());
		
		// Now that we know the non-zero number of wild cards in the player's hand
		// we can remove Card.Kind.Two from the map.
		Map<Card.Kind, Integer> numNonWildKindOccurrencesMap = new HashMap<>(numKindOccurrencesMap);
		numNonWildKindOccurrencesMap.remove(Card.Kind.Two);
		
		List<Card> sortedNonWildCards = eliminateWildCards(player.getHand());
		// Sort the natural non wild cards into descending order.
		Collections.sort(sortedNonWildCards);
		Map<Card.Suit,Integer> nonWildCardNumSuitOccurrencesMap = determineNumSuitOccurrencesInHand(sortedNonWildCards);
		
		WildCardHand wildCardHand = WildCardHandFactory.create(numberOfWildCardsInHand,
				                                               numNonWildKindOccurrencesMap,
				                                               nonWildCardNumSuitOccurrencesMap.size(),
				                                               sortedNonWildCards);
		return wildCardHand.determineBestPossibleHand();
	}
	
	@Override
	protected final boolean canPlayersHandHoldTiebreakerCards(HandStrength playerHandStrength) {
		return ((playerHandStrength instanceof HighCard) ||
				(playerHandStrength instanceof Pair) ||
				(playerHandStrength instanceof TwoPair) ||
				(playerHandStrength instanceof Flush) ||
				(playerHandStrength instanceof FourOfAKind));
	}
}