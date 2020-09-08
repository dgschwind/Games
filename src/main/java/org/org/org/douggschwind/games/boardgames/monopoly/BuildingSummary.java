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

	public void setNumberHouses(int newValue) {
		numberHouses = newValue;
	}
	
	public void addHouse() {
		if (numberHouses == 4) {
			addHotel();
			numberHouses = 0;
		} else {
			numberHouses++;
		}
	}
	
	public void removeHouse() {
		numberHouses--;
	}
	
	public int getNumberHotels() {
		return numberHotels;
	}
	
	public void addHotel() {
		numberHotels++;
	}
	
	public void removeHotel() {
		numberHotels--;
	}
	
	public void clear() {
		numberHouses = 0;
		numberHotels = 0;
	}
	
	public boolean isMoreImproved(BuildingSummary that) {
		return ((that != null) &&
				((this.getNumberHotels() > that.getNumberHotels()) ||
				 (this.getNumberHouses() > that.getNumberHouses())));
	}
	
	public int computeDifferenceInNumberOfHouses(int input) {
		return ((5 * getNumberHotels()) + getNumberHouses()) - input;
	}
}