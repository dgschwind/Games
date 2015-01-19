package org.douggschwind.games.boardgames.monopoly.spaces;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * When a Player lands on this space, they must simply pay $100.
 * @author Doug Gschwind
 */
public class LuxuryTaxSpace extends BoardSpace {

	public LuxuryTaxSpace() {
		super("Income Tax");
	}
	
	@Override
	public final boolean isLuxuryTax() {
		return true;
	}

	@Override
	public void takeAction(Player hasLanded) {
		hasLanded.payBill(100);
	}
}
