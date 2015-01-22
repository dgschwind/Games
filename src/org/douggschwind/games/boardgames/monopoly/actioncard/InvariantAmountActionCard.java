package org.douggschwind.games.boardgames.monopoly.actioncard;

/**
 * The action on a card of this type represents an amount known a priori and thus
 * is invariant, that the player will have to pay or be paid.
 * @author Doug Gschwind
 */
public abstract class InvariantAmountActionCard extends ActionCard {
	
	private final int amount;

	protected InvariantAmountActionCard(String cardName, int amount) {
		super(cardName);
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	protected boolean isCredit() {
		return false;
	}
	
	protected boolean isDebit() {
		return false;
	}
	
	protected boolean appliesToPlayer() {
		return false;
	}
	
	protected boolean appliesToOpponents() {
		return false;
	}
}