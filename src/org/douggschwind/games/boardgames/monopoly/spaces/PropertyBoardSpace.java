package org.douggschwind.games.boardgames.monopoly.spaces;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * An instance of this class is a property that can be owned, upon which
 * houses and hotels may be built, and rent may be charged.
 * @author Doug Gschwind
 */
public class PropertyBoardSpace extends BoardSpace {
	private final MonopolyDefinition monopolyDefinition;
	private final int costToPurchase;
	private final int costPerHouse;
	private final int costPerHotel;
	private final int unmonopolizedRentCost;
	private final int monopolizedRentCost; // No houses
	private final int oneHouseRentCost;
	private final int twoHouseRentCost;
	private final int threeHouseRentCost;
	private final int fourHouseRentCost;
	private final int perHotelRentCost;
	private final int mortgageValue;

	public PropertyBoardSpace(MonopolyDefinition monopolyDefinition,
			                  String spaceName,
			                  int costToPurchase,
			                  int costPerHouse,
			                  int costPerHotel,
			                  int unmonopolizedRentCost,
			                  int monopolizedRentCost,
			                  int oneHouseRentCost,
			                  int twoHouseRentCost,
			                  int threeHouseRentCost,
			                  int fourHouseRentCost,
			                  int perHotelRentCost,
			                  int mortgageValue) {
		super(spaceName);
		this.monopolyDefinition = monopolyDefinition;
		this.costToPurchase = costToPurchase;
		this.costPerHouse = costPerHouse;
		this.costPerHotel = costPerHotel;
		this.unmonopolizedRentCost = unmonopolizedRentCost;
		this.monopolizedRentCost = monopolizedRentCost;
		this.oneHouseRentCost = oneHouseRentCost;
		this.twoHouseRentCost = twoHouseRentCost;
		this.threeHouseRentCost = threeHouseRentCost;
		this.fourHouseRentCost = fourHouseRentCost;
		this.perHotelRentCost = perHotelRentCost;
		this.mortgageValue = mortgageValue;
		
		monopolyDefinition.addBoardSpace(this);
	}
	
	@Override
	public final boolean isProperty() {
		return true;
	}
	
	public MonopolyDefinition getMonopolyDefinition() {
		return monopolyDefinition;
	}

	public int getCostToPurchase() {
		return costToPurchase;
	}

	public int getCostPerHouse() {
		return costPerHouse;
	}

	public int getCostPerHotel() {
		return costPerHotel;
	}

	public int getUnmonopolizedRentCost() {
		return unmonopolizedRentCost;
	}

	public int getMonopolizedRentCost() {
		return monopolizedRentCost;
	}

	public int getOneHouseRentCost() {
		return oneHouseRentCost;
	}

	public int getTwoHouseRentCost() {
		return twoHouseRentCost;
	}

	public int getThreeHouseRentCost() {
		return threeHouseRentCost;
	}

	public int getFourHouseRentCost() {
		return fourHouseRentCost;
	}

	public int getPerHotelRentCost() {
		return perHotelRentCost;
	}

	public int getMortgageValue() {
		return mortgageValue;
	}
	
	/**
	 * Determines if the given Player can buy one or more houses or hotels to
	 * place on this property.
	 * @return true if so, false otherwise.
	 */
	public boolean canPlayerBuyHouseOrHotelFor(Player player) {
		return player.hasMonopoly(this);
	}

	public int computeRentCost(int numberOfHotelsOnProperty, int numberOfHousesOnProperty, Player guest) {
		int result = (numberOfHotelsOnProperty * getPerHotelRentCost());
		switch (numberOfHousesOnProperty) {
			case 4:
				result += getFourHouseRentCost();
				break;
			case 3:
				result += getThreeHouseRentCost();
				break;
			case 2:
				result += getTwoHouseRentCost();
				break;
			case 1:
				result += getOneHouseRentCost();
				break;
			case 0:
			default:
				if (numberOfHotelsOnProperty == 0) {
					if (guest.hasMonopoly(this)) {
						result = getMonopolizedRentCost();
					} else {
						result = getUnmonopolizedRentCost();
					}
				}
		}
		
		return result;
	}
}