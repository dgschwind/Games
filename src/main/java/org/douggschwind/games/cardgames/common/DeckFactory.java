package org.douggschwind.games.cardgames.common;

import org.douggschwind.games.cardgames.common.Card.Kind;
import org.douggschwind.games.common.DeckOfCards;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Can create a standard deck of 52 cards, or an subset deck of standard cards for use in playing the
 * game Euchre, which only uses Ace through Nine of all four suits.
 * @author Doug Gschwind
 */
public class DeckFactory {
	
	public static DeckOfCards<Card> createStandardDeck() {
		DeckOfCards<Card> result = new DeckOfCards<>();
		for (Card.Kind kind : Card.Kind.values()) {
			Arrays.stream(Card.Suit.values()).forEach(suit -> result.addCard(new Card(kind, suit)));
		}
		return result;
	}
	
	public static DeckOfCards<Card> createEuchreDeck() {
		DeckOfCards<Card> result = new DeckOfCards<>();
		
		Consumer<? super Card.Suit> addCardsFromSuit = suit -> {
			result.addCard(new Card(Kind.Ace, suit));
			result.addCard(new Card(Kind.King, suit));
			result.addCard(new Card(Kind.Queen, suit));
			result.addCard(new Card(Kind.Jack, suit));
			result.addCard(new Card(Kind.Ten, suit));
			result.addCard(new Card(Kind.Nine, suit));
		};
		
		Arrays.stream(Card.Suit.values()).forEach(addCardsFromSuit);
		
		return result;
	}
}