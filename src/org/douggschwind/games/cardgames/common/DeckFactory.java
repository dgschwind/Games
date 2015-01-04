package org.douggschwind.games.cardgames.common;

import org.douggschwind.games.cardgames.common.Card.Kind;

/**
 * @author Doug Gschwind
 */
public class DeckFactory {
	
	public static Deck createStandardDeck() {
		Deck result = new Deck();
		for (Card.Kind kind : Card.Kind.values()) {
			for (Card.Suit suit : Card.Suit.values()) {
				result.addCard(new Card(kind, suit));
			}
		}
		return result;
	}
	
	public static Deck createEuchreDeck() {
		Deck result = new Deck();
		for (Card.Suit suit : Card.Suit.values()) {
			result.addCard(new Card(Kind.Ace, suit));
			result.addCard(new Card(Kind.King, suit));
			result.addCard(new Card(Kind.Queen, suit));
			result.addCard(new Card(Kind.Jack, suit));
			result.addCard(new Card(Kind.Ten, suit));
			result.addCard(new Card(Kind.Nine, suit));
		}
		return result;
	}
}