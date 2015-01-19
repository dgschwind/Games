package org.douggschwind.games.boardgames.monopoly.actioncards;

/**
 * An instance of this class represents an amount that is known a priori,
 * that the Player will be paid by each opponent.
 * @author Doug Gschwind
 */
public class PerOpponentCreditCard extends AbstractCreditCard {
	public PerOpponentCreditCard(String cardName, int amount) {
		super(cardName, amount);
	}
	
	@Override
	protected final boolean appliesToOpponents() {
		return true;
	}
}