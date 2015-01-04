package org.douggschwind.games.cardgames.wildcardpoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.poker.common.FiveOfAKind;
import org.douggschwind.games.cardgames.poker.common.FourOfAKind;
import org.douggschwind.games.cardgames.poker.common.HandStrength;

/**
 * An instance of this class represents a poker hand that has at least one
 * Wild Card within it, and a total of five cards in the player's hand.
 * @author Doug Gschwind
 */
public abstract class WildCardHand {
	
	private final Map<Card.Kind, Integer> numNonWildKindOccurrencesMap;
	private final int numDistinctNonWildSuits;
	private final List<Card> sortedNonWildCardsInHand = new ArrayList<>();
	private final Card nonWildHighCard;
	private final Card nonWildLowCard;
	
	/**
	 * @param numDistinctNonWildKinds
	 * @param sortedNonWildCardsInHand Must be non-null and non-empty, and sorted by Kind
	 * in descending order.
	 */
	protected WildCardHand(Map<Card.Kind, Integer> numNonWildKindOccurrencesMap,
			               int numDistinctNonWildSuits,
			               List<Card> sortedNonWildCardsInHand) {
		super();
		this.numNonWildKindOccurrencesMap = numNonWildKindOccurrencesMap;
		this.numDistinctNonWildSuits = numDistinctNonWildSuits;
		this.sortedNonWildCardsInHand.addAll(sortedNonWildCardsInHand);
		this.nonWildHighCard = sortedNonWildCardsInHand.get(0);
		this.nonWildLowCard = sortedNonWildCardsInHand.get(sortedNonWildCardsInHand.size() - 1);
	}
	
	protected Map<Card.Kind, Integer> getNumNonWildKindOccurrencesMap() {
		return numNonWildKindOccurrencesMap;
	}
	
	/**
	 * @return The number of distinct Kinds found in the players
	 * hand that are not wild cards. If the player had a full house
	 * of Aces and Eights and Twos were wild, the number of distinct
	 * kinds in that hand is two.
	 */
	protected int getNumDistinctNonWildKinds() {
		return getNumNonWildKindOccurrencesMap().size();
	}
	
	protected int getNumDistinctNonWildSuits() {
		return numDistinctNonWildSuits;
	}
	
	protected final boolean isSingleSuited() {
		return (getNumDistinctNonWildSuits() == 1);
	}
	
	protected List<Card> getSortedNonWildCardsInHand() {
		return sortedNonWildCardsInHand;
	}
	
	protected final Card getNonWildHighCard() {
		return nonWildHighCard;
	}
	
	protected final Card getNonWildLowCard() {
		return nonWildLowCard;
	}
	
	protected FiveOfAKind createFiveOfAKind(Card.Kind kind) {
		return new FiveOfAKind(kind);
	}
	
	protected FourOfAKind createFourOfAKind(Card.Kind kind) {
		return new FourOfAKind(kind);
	}
	
	protected int computeDistanceBetweenHighAndLowCards() {
		return Card.Kind.computeDistance(getNonWildLowCard().getKind(), getNonWildHighCard().getKind());
	}
	
	/**
	 * This method assumes the player is single suited and has a wild card for each
	 * non-wild card missing from their hand for the given Suit.
	 * @return Will be non-null.
	 */
	protected final TypeOfStraightThatCanBeFormed determineTypeOfStraightThatCanBeFormed() {
		Card highCard = getNonWildHighCard();
		Card lowCard = getNonWildLowCard();
		
		int highAndLowCardDistance = computeDistanceBetweenHighAndLowCards();
		if (highAndLowCardDistance <= 4) {
			// The wild cards can be used to make at least a Straight Flush
			Boolean lowCardIsTenOrHigher = lowCard.getKind().hasHigherRank(Card.Kind.Nine);
			if ((lowCardIsTenOrHigher != null) && (lowCardIsTenOrHigher.booleanValue())) {
				return new TypeOfStraightThatCanBeFormed(TypeOfStraightThatCanBeFormed.StraightType.ACE_HIGH_STRAIGHT, Card.Kind.Ace);
			} else {
				Card.Kind straightFlushHighKind = highCard.getKind();
				switch (highAndLowCardDistance) {
					case 1:
						straightFlushHighKind = straightFlushHighKind.getNextHigherRankingKind();
					case 2:
						straightFlushHighKind = straightFlushHighKind.getNextHigherRankingKind();
					case 3:
						straightFlushHighKind = straightFlushHighKind.getNextHigherRankingKind();
					default:
				}
				return new TypeOfStraightThatCanBeFormed(TypeOfStraightThatCanBeFormed.StraightType.STRAIGHT, straightFlushHighKind);
			}
		} else {
			if (highCard.isAce()) {
				Card secondHighestCard = getSortedNonWildCardsInHand().get(1);
				Boolean secondHighestCardIsFiveOrLower = secondHighestCard.getKind().hasLowerRank(Card.Kind.Six);
				if ((secondHighestCardIsFiveOrLower != null) && (secondHighestCardIsFiveOrLower.booleanValue())) {
					// Ace low straight
					return new TypeOfStraightThatCanBeFormed(TypeOfStraightThatCanBeFormed.StraightType.ACE_LOW_STRAIGHT, Card.Kind.Five);
				}
			}
		}
		
		return new TypeOfStraightThatCanBeFormed(TypeOfStraightThatCanBeFormed.StraightType.NONE, null);
	}

	/**
	 * Concrete subclasses must provide an implementation of this method.
	 * @return Will be non-null.
	 */
	protected abstract HandStrength determineBestPossibleHand();
}