package org.douggschwind.games.boardgames.monopoly.spaces;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * When a Player lands on this space, they must simply pay $200.
 * @author Doug Gschwind
 */
public class IncomeTaxSpace extends PublicBoardSpace {

	public IncomeTaxSpace() {
		super("Income Tax");
	}
	
	@Override
	public final boolean isIncomeTax() {
		return true;
	}

	@Override
	public void takeAction(Player hasLanded) {
		hasLanded.payBill(200);
	}
}
