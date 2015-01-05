package org.douggschwind.games.cardgames.wildcardpoker;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.Card.Kind;
import org.douggschwind.games.cardgames.common.DeckFactory;
import org.douggschwind.games.cardgames.common.Player;
import org.douggschwind.games.cardgames.poker.common.Flush;
import org.douggschwind.games.cardgames.poker.common.HandStrength;
import org.douggschwind.games.cardgames.poker.common.HighCard;
import org.douggschwind.games.cardgames.poker.common.Pair;
import org.douggschwind.games.cardgames.poker.common.TwoPair;

/**
 * This is a 5 card draw poker game where the Jack of Spades and the
 * Jack of Hearts, the "one eyed" Jacks are wild. The Jack of Clubs
 * and the Jack of Diamonds are not considered wild cards.
 * For the purposes of this game, the drawing part of the game is ignored
 * in favor of simply dealing with the 5 cards dealt to each player.
 * @author Doug Gschwind
 */
public class OneEyedJacksWild extends WildCardGame {
	
	private static final Card JACK_OF_SPADES = new Card(Card.Kind.Jack, Card.Suit.Spades);
	private static final Card JACK_OF_HEARTS = new Card(Card.Kind.Jack, Card.Suit.Hearts);

	public OneEyedJacksWild() {
		super(DeckFactory.createStandardDeck());
		// Since Card instances are essentially ValueObjects, just create
		// new Card instances to specify wild cards, rather than trying
		// to obtain the correct Card references out of the deck.
		addWildCard(JACK_OF_SPADES);
		addWildCard(JACK_OF_HEARTS);
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
		Integer numberOfJacksInHand = numNonWildKindOccurrencesMap.get(Card.Kind.Jack);
		int numberOfNonWildJacksInHand = numberOfJacksInHand.intValue() - numberOfWildCardsInHand;
		if (numberOfNonWildJacksInHand == 0) {
			numNonWildKindOccurrencesMap.remove(Card.Kind.Jack);
		} else {
			numNonWildKindOccurrencesMap.put(Card.Kind.Jack, numberOfNonWildJacksInHand);
		}
		
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
				(playerHandStrength instanceof Flush));
	}
	
	/**
	 * Determine the Card.Kind instances in which every player holds in their hand.
	 * @param playerSortedDistinctCardKindsMap
	 * @return
	 */
	private Set<Card.Kind> getDistinctGameCommonCardKinds(Map<Player, List<Card.Kind>> playerSortedDistinctCardKindsMap) {
		Set<Card.Kind> result = null;
		for (Player player : playerSortedDistinctCardKindsMap.keySet()) {
			List<Kind> playerSortedDistinctCardKinds = playerSortedDistinctCardKindsMap.get(player);
			if (result == null) {
				result = new HashSet<>(playerSortedDistinctCardKinds);
			} else {
				result.retainAll(playerSortedDistinctCardKinds);
			}
		}
		return result;
	}
	
	/**
	 * Determines the List of Card.Kind instances for each Player that exist in the Player's
	 * hand that are not cards found in common among all Players in the argument set.
	 * @param players
	 * @return
	 */
	private Map<Player, List<Card.Kind>> getCommonEliminatedSortedPlayerCardKindsMap(Set<Player> players) {
		Map<Player, List<Card.Kind>> result = getPlayerSortedDistinctCardKindsMap(players);
		Set<Card.Kind> distinctGameCommonCardKinds = getDistinctGameCommonCardKinds(result);
		for (Player player : players) {
			List<Card.Kind> playerSortedDistinctCardKinds = result.get(player);
			playerSortedDistinctCardKinds.removeAll(distinctGameCommonCardKinds);
		}
		return result;
	}

	@Override
	public Set<Player> determineWinners() {
		Map<Player, HandStrength> playerHandStrengths = determinePlayerHandsStrength();
		Set<Player> leadingPlayers = determineLeadingPlayersBasedUponHandRankAlone(playerHandStrengths);
		
		// If at this point only one player remains, they are the clear winner.
		if (leadingPlayers.size() == 1) {
			return leadingPlayers;
		}
		
		Player aLeadingPLayer = playerHandStrengths.keySet().iterator().next();
		HandStrength aLeadingPlayerHandStrength = playerHandStrengths.get(aLeadingPLayer);
		if (!canPlayersHandHoldTiebreakerCards(aLeadingPlayerHandStrength)) {
			// Here, two or more players have a hand that have matching strength,
			// but comparing individual cards beyond hand strength
			// (e.g. 10 high straight flush in Spades) will not result in a
			// conclusive choice of a winner. Each of the players are then 
			// equal winners.
			return leadingPlayers;
		}
		
		// Otherwise, we have two or more players with the same hand rank (e.g. two pair)
		// that need to be evaluated further to see which one has the winning hand. The
		// only time this can happen is when more than one player has :
		// two pair, one pair, flush, four of a kind, or high card.
		Map<Player, List<Card.Kind>> playerSortedDistinctCardKindsMap = getCommonEliminatedSortedPlayerCardKindsMap(leadingPlayers);
		Player aLeadingPlayer = playerSortedDistinctCardKindsMap.keySet().iterator().next();
		List<Card.Kind> aLeadingPlayerUniqueDistinctCardKinds = playerSortedDistinctCardKindsMap.get(aLeadingPlayer);
		if (aLeadingPlayerUniqueDistinctCardKinds.isEmpty()) {
			// Here, the cards in the players hands that do not contribute to 
			// HandStrength still match. For example, two players each with two
			// pair (say Aces and Eights), with a Ten. They match in entirety,
			// so both Players are winners.
			return leadingPlayers;
		}
		
		// At this point, each player in playerSortedDistinctCardKindsMap has
		// the same number of elements in its List<Card.Kind>.
		Set<Player> eliminatedPlayers = new HashSet<Player>();
		for (int cardIndex = 0;cardIndex < aLeadingPlayerUniqueDistinctCardKinds.size();cardIndex++) {
			Card.Kind cardKindLeadingAtIndex = null;
			Player playerContributingLeadingCardKind = null;
			for (Player player : playerSortedDistinctCardKindsMap.keySet()) {
				if (eliminatedPlayers.contains(player)) {
					continue; // to next player
				}
				
				List<Card.Kind> uniqueDistinctPlayerCardKinds = playerSortedDistinctCardKindsMap.get(player);
				Kind playerCardKindAtIndex = uniqueDistinctPlayerCardKinds.get(cardIndex);
				if (cardKindLeadingAtIndex == null) {
					cardKindLeadingAtIndex = playerCardKindAtIndex;
					playerContributingLeadingCardKind = player;
					continue; // to next player
				}
				
				Boolean leadingKindBeatsPlayersKind = cardKindLeadingAtIndex.hasHigherRank(playerCardKindAtIndex);
				if (leadingKindBeatsPlayersKind == null) {
					// Both players had same Kind of card
					continue; // to next player
				} else if (leadingKindBeatsPlayersKind.booleanValue()) {
					eliminatedPlayers.add(player);
				} else {
					eliminatedPlayers.add(playerContributingLeadingCardKind);
					cardKindLeadingAtIndex = playerCardKindAtIndex;
					playerContributingLeadingCardKind = player;
				}
			}
		}
		
		leadingPlayers.removeAll(eliminatedPlayers);
		return leadingPlayers;
	}
}