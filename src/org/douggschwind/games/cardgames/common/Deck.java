package org.douggschwind.games.cardgames.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A Deck contains some number of Card instances.
 * @author Doug Gschwind
 */
public class Deck {
	
	private final List<Card> cards = new ArrayList<>();
	private final Map<Card, Boolean> dealtCards = new HashMap<>();
	private Random random;
	
	void addCard(Card toAdd) {
		if (toAdd != null) {
			cards.add(toAdd);
			dealtCards.put(toAdd, false);
		}
	}
	
	public void shuffle() {
		random = new Random();
		for (Card key : dealtCards.keySet()) {
			dealtCards.put(key, false);
		}
	}
	
	private boolean hasCardBeenDealt(Card card) {
		return (dealtCards.get(card) == true);
	}
	
	private Card getRandomCardNotYetDealt() {
		final int randomlySelectedCardIndex = random.nextInt(cards.size());
		Card randomlySelectedCard = cards.get(randomlySelectedCardIndex);
		if (!hasCardBeenDealt(randomlySelectedCard)) {
			return randomlySelectedCard;
		}
		
		// The randomly selected card has already been dealt. Lets just
		// pick its closest neighbor that has not already been dealt.
		
		int indexDistanceFromOrigin = 1;
		while (true) {
			int candidateSelectedCardIndex = randomlySelectedCardIndex + indexDistanceFromOrigin;
			if (candidateSelectedCardIndex < cards.size()) {
				randomlySelectedCard = cards.get(candidateSelectedCardIndex);
				if (!hasCardBeenDealt(randomlySelectedCard)) {
					return randomlySelectedCard;
				}
			}
			
			candidateSelectedCardIndex = randomlySelectedCardIndex - indexDistanceFromOrigin;
			if (candidateSelectedCardIndex >= 0) {
				randomlySelectedCard = cards.get(candidateSelectedCardIndex);
				if (!hasCardBeenDealt(randomlySelectedCard)) {
					return randomlySelectedCard;
				}
			}
			
			indexDistanceFromOrigin++;
		}
	}
	
	private boolean haveAllCardsBeenDealt() {
		for (Card card : cards) {
			if (!hasCardBeenDealt(card)) {
				return false;
			}
		}
		
		return true;
	}
	
	public Card dealCard() {
		if (haveAllCardsBeenDealt()) {
			throw new IllegalStateException("All cards have already been dealt!");
		}
		Card randomlySelectedCard = getRandomCardNotYetDealt();
		dealtCards.put(randomlySelectedCard, true);
		return randomlySelectedCard;
	}
}