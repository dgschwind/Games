package org.douggschwind.games.boardgames.monopoly.spaces;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * The Monopoly gameboard consists of 40 spaces containing 28 properties (22 colored streets, four railway stations and two utilities), three Chance spaces,
 * three Community Chest spaces, a Luxury Tax space, an Income Tax space, and the four corner squares: GO, (In) Jail/Just Visiting, Free Parking, and Go to Jail.
 * 
 * An instance of this class represents one of those 40 board spaces.
 * @author Doug Gschwind
 */
public abstract class BoardSpace {
	private final String name;
	
	protected BoardSpace(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean canBePrivatelyHeld() {
		return false;
	}
	
	public boolean isPubliclyHeld() {
		return false;
	}
	
	public abstract boolean canBeBoughtOrSold();
	
	
	public boolean isProperty() {
		return false;
	}
	
	public boolean isRailwayStation() {
		return false;
	}
	
	public boolean isUtility() {
		return false;
	}
	
	public boolean isChance() {
		return false;
	}
	
	public boolean isCommunityChest() {
		return false;
	}
	
	public boolean isLuxuryTax() {
		return false;
	}
	
	public boolean isIncomeTax() {
		return false;
	}
	
	public boolean isGo() {
		return false;
	}
	
	public boolean isJail() {
		return false;
	}
	
	public boolean isFreeParking() {
		return false;
	}
	
	public boolean isGoToJail() {
		return false;
	}
	
	/**
	 * Takes action now that it is known that the given Player has landed on
	 * this given instance.
	 * @param hasLanded
	 */
	public void takeAction(Player hasLanded) {
	}
}