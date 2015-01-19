package org.douggschwind.games.boardgames.monopoly.spaces;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * When a Player lands on this space, they incur no costs and remain on the space
 * until their next dice roll.
 * @author Doug Gschwind
 */
public class FreeParkingSpace extends PublicBoardSpace {

	public FreeParkingSpace() {
		super("Free Parking");
	}
	
	@Override
	public final boolean isFreeParking() {
		return true;
	}

	@Override
	public void takeAction(Player hasLanded) {
		// Do nothing.
	}
}
