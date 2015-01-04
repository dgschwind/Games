package org.douggschwind.games.cardgames.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.douggschwind.games.cardgames.common.Card.Kind;
import org.douggschwind.games.cardgames.poker.common.Flush;
import org.douggschwind.games.cardgames.poker.common.FourOfAKind;
import org.douggschwind.games.cardgames.poker.common.FullHouse;
import org.douggschwind.games.cardgames.poker.common.HandStrength;
import org.douggschwind.games.cardgames.poker.common.HighCard;
import org.douggschwind.games.cardgames.poker.common.Pair;
import org.douggschwind.games.cardgames.poker.common.RoyalFlush;
import org.douggschwind.games.cardgames.poker.common.Straight;
import org.douggschwind.games.cardgames.poker.common.StraightFlush;
import org.douggschwind.games.cardgames.poker.common.ThreeOfAKind;
import org.douggschwind.games.cardgames.poker.common.TwoPair;

/**
 * An instance of this class assumes one standard deck of 52 total cards,
 * four suits of 13 distinct cards each.
 * @author Doug Gschwind
 */
public abstract class StandardDeckCardGame {
	
	private final Deck deck;
	private final List<Player> players = new ArrayList<>();
	
	protected StandardDeckCardGame(Deck deck) {
		this.deck = deck;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public void addPlayer(Player toAdd) {
		if (toAdd != null) {
			players.add(toAdd);
		}
	}
	
	public final List<Player> getPlayers() {
		return players;
	}
	
	public void newGame() {
		getDeck().shuffle();
	}
	
	protected abstract int getNumberCardsDealtToEachPlayer();
	
	// Each game is allowed to vary how cards are dealt to players.
	public void dealCardsToPlayers() {
		for (int i = 1;i <= getNumberCardsDealtToEachPlayer();i++) {
			// Each Player gets 5 cards in this game.
			for (Player player : getPlayers()) {
				player.acceptDealtCard(getDeck().dealCard());
			}
		}
	}
	
	protected final Map<Card.Kind, Integer> determineNumKindOccurrencesInHand(List<Card> playersHand) {
		Map<Card.Kind, Integer> result = new HashMap<>();
		for (Card cardInHand : playersHand) {
			Kind cardKind = cardInHand.getKind();
			Integer numOccurrencesOfKind = result.get(cardKind);
			if (numOccurrencesOfKind == null) {
				result.put(cardKind, 1);
			} else {
				result.put(cardKind, numOccurrencesOfKind + 1);
			}
		}
		return result;
	}
	
	protected final Map<Card.Suit, Integer> determineNumSuitOccurrencesInHand(List<Card> cards) {
		Map<Card.Suit, Integer> result = new HashMap<>();
		for (Card cardInHand : cards) {
			Card.Suit cardSuit = cardInHand.getSuit();
			Integer numOccurrencesOfSuit = result.get(cardSuit);
			if (numOccurrencesOfSuit == null) {
				result.put(cardSuit, 1);
			} else {
				result.put(cardSuit, numOccurrencesOfSuit + 1);
			}
		}
		return result;
	}
	
	protected final Map<Card.Suit, Integer> determineNumSuitOccurrencesInHand(Player player) {
		return determineNumSuitOccurrencesInHand(player.getHand());
	}
	
	protected final int determineMaximumNumberMatchingKind(Map<Card.Kind, Integer> numKindOccurrences) {
		int result = 0;
		
		for (Card.Kind cardKind : numKindOccurrences.keySet()) {
			Integer numMatching = numKindOccurrences.get(cardKind);
			if (numMatching > result) {
				result = numMatching.intValue();
			}
		}
		
		return result;
	}
	
	protected final Map<Player, List<Card.Kind>> getPlayerSortedDistinctCardKindsMap(Set<Player> players) {
		Map<Player, List<Card.Kind>> result = new HashMap<>();
		for (Player player : players) {
			List<Kind> playerSortedDistinctKinds = new ArrayList<>(player.getDistinctCardKinds());
			Collections.sort(playerSortedDistinctKinds);
			result.put(player, playerSortedDistinctKinds);
		}
		return result;
	}
	
	protected final Card.Kind determineDominantMatchingKind(Map<Card.Kind, Integer> numKindOccurrences, int numDominantMatchesExpected) {
		for (Card.Kind cardKind : numKindOccurrences.keySet()) {
			if (numKindOccurrences.get(cardKind) == numDominantMatchesExpected) {
				return cardKind;
			}
		}
		return null; // Should never get here!
	}
	
	protected final Pair determinePlayerHandPair(Map<Card.Kind, Integer> numKindOccurrences) {
		for (Card.Kind cardKind : numKindOccurrences.keySet()) {
			if (numKindOccurrences.get(cardKind) == 2) {
				return new Pair(cardKind);
			}
		}
		
		return null; // Neve expect to get here!
	}
	
	private HandStrength determinePlayerHandStrengthNoMatchingKinds(Player player) {
		Map<Card.Suit, Integer> numSuitOccurrences = determineNumSuitOccurrencesInHand(player);
		// Make a copy of the player's hand so that we leave the players hand in
		// its originally dealt form.
		List<Card> playerHand = new ArrayList<>(player.getHand());
		Collections.sort(playerHand);
		Card lowCard = playerHand.get(4);
		Card highCard = playerHand.get(0);
		Card secondHighestCard = playerHand.get(1);
		int lowToHighDistance = Card.Kind.computeDistance(lowCard.getKind(), highCard.getKind());	
		if (numSuitOccurrences.size() == 1) {
			// Player has a flush, straight flush, or royal flush
			if (lowToHighDistance == 4) {
				// Player has a straight flush or a royal flush
				return highCard.isAce() ? new RoyalFlush() : new StraightFlush(highCard.getKind());
			} else if (highCard.isAce()) {
				lowToHighDistance = Card.Kind.computeDistance(lowCard.getKind(), secondHighestCard.getKind());
				if ((lowToHighDistance == 3) &&
					(lowCard.getKind() == Card.Kind.Two)) {
					// Ace low straight flush
					return new StraightFlush(secondHighestCard.getKind());
				}
			}
			
			// Player has a flush
			return new Flush(highCard.getKind());
		} else {
			// Player has a high card hand or a straight
			if (lowToHighDistance == 4) {
				return new Straight(highCard.getKind());
			} else if (highCard.isAce()) {
				lowToHighDistance = Card.Kind.computeDistance(lowCard.getKind(), secondHighestCard.getKind());
				if ((lowToHighDistance == 3) &&
					(lowCard.getKind() == Card.Kind.Two)) {
					// Ace low straight
					return new Straight(secondHighestCard.getKind());
				}
			}
			return new HighCard(highCard.getKind());
		}
	}
	
	protected final HandStrength determinePlayerHandStrengthThreeDistinctKindsInHand(int maxNumberMatchingKind, Map<Card.Kind, Integer> numKindOccurrences) {
		if (maxNumberMatchingKind == 3) {
			// Player has three of a kind.
			return new ThreeOfAKind(determineDominantMatchingKind(numKindOccurrences, 3));
		} else {
			// Player has two pair.
			Card.Kind pair1 = null;
			Card.Kind pair2 = null;
			for (Card.Kind cardKind : numKindOccurrences.keySet()) {
				if (numKindOccurrences.get(cardKind) == 2) {
					if (pair1 == null) {
						pair1 = cardKind;
					} else {
						pair2 = cardKind;
					}
				}
			}
			if (pair1.hasHigherRank(pair2)) {
				return new TwoPair(pair1, pair2);
			} else {
				return new TwoPair(pair2, pair1);
			}
		}
	}
	
	/**
	 * This method determines the strength of the players hand ignoring the possibility
	 * of any wild cards.
	 * @param player Must be non-null.
	 * @return Will be non-null.
	 */
	public HandStrength determinePlayerHandStrength(Player player) {
		Map<Card.Kind, Integer> numKindOccurrences = determineNumKindOccurrencesInHand(player.getHand());
		
		if (numKindOccurrences.size() == getNumberCardsDealtToEachPlayer()) {
			// Player has no matching kinds, but instead could have a
			// Straight, Flush, Straight Flush, Royal Flush, or simply
			// a HighCard hand.
			return determinePlayerHandStrengthNoMatchingKinds(player);
		} else if (numKindOccurrences.size() == (getNumberCardsDealtToEachPlayer() - 1)) {
			// With four distinct kind cards out of five, the player
			// simply has a Pair.
			return determinePlayerHandPair(numKindOccurrences);
		}
		
		int maxNumberMatchingKind = determineMaximumNumberMatchingKind(numKindOccurrences);
		if (numKindOccurrences.size() == 3) {
			// Player could have three of a kind or two pair.
			return determinePlayerHandStrengthThreeDistinctKindsInHand(maxNumberMatchingKind, numKindOccurrences);
		}
		
		// Player has a four of a kind or a full house.
		if (maxNumberMatchingKind == 4) {
			// Player has four of a kind
			return new FourOfAKind(determineDominantMatchingKind(numKindOccurrences, 4));
		} else {
			// Player has a full house
			return new FullHouse(determineDominantMatchingKind(numKindOccurrences, 3));
		}
	}
	
	public final Map<Player, HandStrength> determinePlayerHandsStrength() {
		Map<Player, HandStrength> result = new HashMap<>();
		
		for (Player player : getPlayers()) {
			// Since HandStrength computation is a little involved, lets
			// cache the result for future reference in this method.
			result.put(player, determinePlayerHandStrength(player));
		}
		
		return result;
	}
	
	/**
	 * Determines the Player or Players that have the strongest hand.
	 * @param playerHandStrengths Must be non-null.
	 * @return The Set will be non-null and non-empty, and most often only
	 * house a single Player. However, there are times when hand rank alone
	 * cannot be used to determine the winner of a hand, such as when the
	 * two best hands are a Ten high Straight.
	 */
	protected final Set<Player> determineLeadingPlayersBasedUponHandRankAlone(Map<Player, HandStrength> playerHandStrengths) {
		// Lets make a quick first pass attempting to identify the winner
		// based upon HandRank alone.
		Set<Player> result = new HashSet<>();
		for (Player currentPlayer : playerHandStrengths.keySet()) {
			HandStrength playerHandStrength = playerHandStrengths.get(currentPlayer);
			if (result.isEmpty()) {
				result.add(currentPlayer);
			} else {
				// Only need to compare against the first leading player. If there are two
				// or more leading players at a given time, they all have the same hand rank.
				Player leadingPlayer = result.iterator().next();
				HandStrength leadingPlayerHandStrength = playerHandStrengths.get(leadingPlayer);
				Boolean playerBeatsLeadingPlayer = playerHandStrength.isStrongerThan(leadingPlayerHandStrength);
				boolean currentPlayerNowALeadingPlayer = false;
				if (playerBeatsLeadingPlayer != null) {
					if (playerBeatsLeadingPlayer.booleanValue()) {
						result.clear();
						currentPlayerNowALeadingPlayer = true;
					}
				} else {
					// The current Player has tied the leading Player(s)!
					currentPlayerNowALeadingPlayer = true;
				}
				
				if (currentPlayerNowALeadingPlayer) {
					result.add(currentPlayer);
				}
			}
		}
		return result;
	}
	
	/**
	 * Each game must implement this as certain hands in certain games are unique,
	 * but not necessarily in all other games. e.g. In Western, a Four of a Kind
	 * hand is unique enough, the other card in the player's hand will not be used
	 * to determine the winner of a given hand. In DeucesWild, two players can have
	 * the same Four of a Kind, so the fifth card in each player's hand in that case
	 * will be used to attempt to determine which player won the hand.
	 * @param playerHandStrength Expected to be non-null.
	 * @return true if so, false otherwise.
	 */
	protected abstract boolean canPlayersHandHoldTiebreakerCards(HandStrength playerHandStrength);
	
	/**
	 * Some games will have one winner. Others may allow two or more.
	 * This signature supports both modes.
	 * @return The Player(s) determined to have won a given hand.
	 */
	public abstract Set<Player> determineWinners();
}