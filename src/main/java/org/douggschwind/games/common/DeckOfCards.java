package org.douggschwind.games.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A DeckOfCards contains some number of AbstractCard instances. This abstraction
 * can support games like Poker, Uno, Euchre, Monopoly, etc.
 * @author Doug Gschwind
 * @param C Must be an AbstractCard.
 */
public class DeckOfCards <C extends AbstractCard> {
	
	private final List<C> cards = new ArrayList<>();
	private final Map<C, Boolean> dealtCards = new HashMap<>();
	private Random random;
	
	public void addCard(C toAdd) {
		if (toAdd != null) {
			cards.add(toAdd);
			dealtCards.put(toAdd, false);
		}
	}

	public int size() {
		return cards.size();
	}
	
	public void shuffle() {
		random = new Random();
		dealtCards.keySet().stream().forEach(key -> dealtCards.put(key, false));
	}
	
	private boolean hasCardBeenDealt(AbstractCard card) {
		return (dealtCards.get(card) == true);
	}
	
	private C getRandomCardNotYetDealt() {
		final int randomlySelectedCardIndex = random.nextInt(cards.size());
		C randomlySelectedCard = cards.get(randomlySelectedCardIndex);
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
		return cards.stream().allMatch(card -> hasCardBeenDealt(card));
	}
	
	public C dealCard() {
		if (haveAllCardsBeenDealt()) {
			throw new IllegalStateException("All cards have already been dealt!");
		}
		C randomlySelectedCard = getRandomCardNotYetDealt();
		dealtCards.put(randomlySelectedCard, true);
		return randomlySelectedCard;
	}
}