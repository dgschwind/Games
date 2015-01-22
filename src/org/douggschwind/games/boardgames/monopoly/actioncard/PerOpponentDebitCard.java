package org.douggschwind.games.boardgames.monopoly.actioncard;

/**
 * An instance of this class represents an amount that is known a priori,
 * that the Player must pay to each opponent.
 * @author Doug Gschwind
 */
public class PerOpponentDebitCard extends AbstractDebitCard {
	public PerOpponentDebitCard(String cardName, int amount) {
		super(cardName, amount);
	}
	
	@Override
	protected final boolean appliesToOpponents() {
		return true;
	}
}