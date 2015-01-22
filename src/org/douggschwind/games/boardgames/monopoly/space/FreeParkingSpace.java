package org.douggschwind.games.boardgames.monopoly.space;

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
	public void takeAction(Player hasLanded) {
		// Do nothing.
	}
}
