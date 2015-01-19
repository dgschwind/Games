package org.douggschwind.games.boardgames.monopoly;

/**
 * Represents the number of houses and/or hotels that have been
 * built on a given Property.
 * @author Doug Gschwind
 */
public class BuildingSummary {
	
	private int numberHouses;
	private int numberHotels;
	
	public int getNumberHouses() {
		return numberHouses;
	}

	public void setNumberHouses(int numberHouses) {
		this.numberHouses = numberHouses;
	}

	public int getNumberHotels() {
		return numberHotels;
	}

	public void setNumberHotels(int numberHotels) {
		this.numberHotels = numberHotels;
	}
}