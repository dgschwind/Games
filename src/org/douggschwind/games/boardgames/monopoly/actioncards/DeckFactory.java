package org.douggschwind.games.boardgames.monopoly.actioncards;

import org.douggschwind.games.common.DeckOfCards;

/**
 * Supports the creation of the deck of ActionCards used for the Community Chest deck of cards,
 * or the deck of ActionCards used for the Chance deck of cards.
 * @author Doug Gschwind
 */
public class DeckFactory {
	
	public static DeckOfCards<ActionCard> createCommunityChestDeck() {
		DeckOfCards<ActionCard> result = new DeckOfCards<>();
		result.addCard(new AdvanceToGoCard());
		result.addCard(new PlayerCreditCard("Bank error in your favor", 200));
		result.addCard(new PlayerDebitCard("Doctor's fee", 50));
		result.addCard(new GetOutOfJailFreeCard());
		result.addCard(new GoDirectlyToJailCard());
		result.addCard(new PerOpponentCreditCard("Grand Opera Opening", 50));
		result.addCard(new PlayerCreditCard("Income Tax Refund", 20));
		result.addCard(new PlayerCreditCard("Life Insurance Matures", 100));
		result.addCard(new PlayerDebitCard("Pay Hospital", 100));
		result.addCard(new PlayerDebitCard("Pay School Tax", 150));
		result.addCard(new PlayerCreditCard("Receive for Services", 25));
		result.addCard(new PerPropertyDebitCard("You are assessed for street repairs", 40, 115));
		result.addCard(new PlayerCreditCard("You have won second place in a beauty contest", 10));
		result.addCard(new PlayerCreditCard("You inherit", 100));
		result.addCard(new PlayerCreditCard("From sale of Stock", 45));
		result.addCard(new PlayerCreditCard("Xmas fund matures", 100));
		return result;
	}
	
	public static DeckOfCards<ActionCard> createChanceDeck() {
		DeckOfCards<ActionCard> result = new DeckOfCards<>();
		return result;
	}
}