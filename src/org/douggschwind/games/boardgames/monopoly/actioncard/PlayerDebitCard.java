package org.douggschwind.games.boardgames.monopoly.actioncard;

/**
 * An instance of this class represents an amount that is known a priori,
 * that the Player must pay to the game's bank.
 * @author Doug Gschwind
 */
public class PlayerDebitCard extends AbstractDebitCard {
	public PlayerDebitCard(String cardName, int amount) {
		super(cardName, amount);
	}
	
	@Override
	protected final boolean appliesToPlayer() {
		return true;
	}
}