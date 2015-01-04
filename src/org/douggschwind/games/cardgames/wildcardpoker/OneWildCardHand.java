package org.douggschwind.games.cardgames.wildcardpoker;

import java.util.List;
import java.util.Map;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.poker.common.Flush;
import org.douggschwind.games.cardgames.poker.common.FullHouse;
import org.douggschwind.games.cardgames.poker.common.HandStrength;
import org.douggschwind.games.cardgames.poker.common.Pair;
import org.douggschwind.games.cardgames.poker.common.RoyalFlush;
import org.douggschwind.games.cardgames.poker.common.Straight;
import org.douggschwind.games.cardgames.poker.common.StraightFlush;
import org.douggschwind.games.cardgames.poker.common.ThreeOfAKind;

/**
 * An instance of this class represents that the player has a
 * hand with one wild card within it and four natural cards.
 * @author Doug Gschwind
 */
public class OneWildCardHand extends WildCardHand {

	/**
	 * @param numNonWildKindOccurrencesMap A Map that houses the distinct
	 * non wild Kinds found in the player's hand and the number of occurrences
	 * per Kind in the hand.
	 * @param numDistinctNonWildSuits One of : 1, 2, 3, or 4.
	 * @param sortedNonWildCardsInHand Must be non-null.
	 */
	public OneWildCardHand(Map<Card.Kind, Integer> numNonWildKindOccurrencesMap,
			               int numDistinctNonWildSuits,
			               List<Card> sortedNonWildCardsInHand) {
		super(numNonWildKindOccurrencesMap, numDistinctNonWildSuits, sortedNonWildCardsInHand);
	}
	
	@Override
	protected final HandStrength determineBestPossibleHand() {
		if (getNumDistinctNonWildKinds() == 1) {
			// Here the player has a natural four of a kind. With one wild
			// card that yields Five of a Kind.
			return createFiveOfAKind(getSortedNonWildCardsInHand().iterator().next().getKind());
		}
		
		if (getNumDistinctNonWildKinds() == 2) {
			// The player has a natural Three of a Kind and one odd card with their
			// non-wild cards, or two pair. Thus, the player has at best Four of a Kind,
			// and at worst a Full House.
			for (Card.Kind cardKind : getNumNonWildKindOccurrencesMap().keySet()) {
				Integer numOccurrencesOfKind = getNumNonWildKindOccurrencesMap().get(cardKind);
				if (numOccurrencesOfKind == 3) {
					// Player has natural three of a kind.
					return createFourOfAKind(cardKind);
				} else if (numOccurrencesOfKind == 2) {
					// Player has two natural pair. Find the higher valued pair
					// to determine the strength of their Full House hand.
					Card.Kind firstPairKind = cardKind;
					Card.Kind secondPairKind = null;
					for (Card.Kind innerCardKind : getNumNonWildKindOccurrencesMap().keySet()) {
						if ((getNumNonWildKindOccurrencesMap().get(innerCardKind) == 2) &&
							(!innerCardKind.equals(firstPairKind))) {
							secondPairKind = innerCardKind;
						}
					}
					
					return new FullHouse(firstPairKind.hasHigherRank(secondPairKind).booleanValue() ? firstPairKind : secondPairKind);
				}
			}
			return null; // Should never get here
		}
		
		if (getNumDistinctNonWildKinds() == 3) {
			// The player has a natural pair and thus simply a Three of a Kind hand.
			for (Card.Kind cardKind : getNumNonWildKindOccurrencesMap().keySet()) {
				Integer numOccurrencesOfKind = getNumNonWildKindOccurrencesMap().get(cardKind);
				if (numOccurrencesOfKind == 2) {
					return new ThreeOfAKind(cardKind);
				}
			}
			return null; // Should never get here
		} else {
			// The player has four distinct natural kinds.
			if (isSingleSuited()) {
				TypeOfStraightThatCanBeFormed typeOfStraightThatCanBeFormed = determineTypeOfStraightThatCanBeFormed();
				if (typeOfStraightThatCanBeFormed.isAceHighStraight()) {
					return new RoyalFlush();
				} else if (typeOfStraightThatCanBeFormed.isAceLowStraight()) {
					return new StraightFlush(Card.Kind.Five);
				} else if (typeOfStraightThatCanBeFormed.isStraight()) {
					return new StraightFlush(typeOfStraightThatCanBeFormed.getHighCardKind());
				}
				
				// Player didn't have a Straight, so the best they have is a Flush.
				return new Flush(Card.Kind.Ace);
			} else {
				// Player is at least two suited and thus can have at best a
				// Straight and at worst a Pair.
				TypeOfStraightThatCanBeFormed typeOfStraightThatCanBeFormed = determineTypeOfStraightThatCanBeFormed();
				if (typeOfStraightThatCanBeFormed.isAceHighStraight()) {
					return new Straight(Card.Kind.Ace);
				} else if (typeOfStraightThatCanBeFormed.isAceLowStraight()) {
					return new Straight(Card.Kind.Five);
				} else if (typeOfStraightThatCanBeFormed.isStraight()) {
					return new Straight(typeOfStraightThatCanBeFormed.getHighCardKind());
				}
				
				return new Pair(getNonWildHighCard().getKind());
			}
		}
	}
}