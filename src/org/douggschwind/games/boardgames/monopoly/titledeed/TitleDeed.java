package org.douggschwind.games.boardgames.monopoly.titledeed;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * An instance of this class is a property that can be owned, upon which
 * houses and hotels may be built, and rent may be charged.
 * @author Doug Gschwind
 */
public class TitleDeed {
	private final MonopolyDefinition monopolyDefinition;
	private final String propertyName;
	private final int costToPurchase;
	private final int buildingCost;
	private final int unmonopolizedRentCost;
	private final int monopolizedRentCost; // No houses
	private final int oneHouseRentCost;
	private final int twoHouseRentCost;
	private final int threeHouseRentCost;
	private final int fourHouseRentCost;
	private final int perHotelRentCost;
	
	private Player owner;
	private boolean mortgaged;

	public TitleDeed(MonopolyDefinition monopolyDefinition,
			                  String propertyName,
			                  int costToPurchase,
			                  int buildingCost,
			                  int unmonopolizedRentCost,
			                  int monopolizedRentCost,
			                  int oneHouseRentCost,
			                  int twoHouseRentCost,
			                  int threeHouseRentCost,
			                  int fourHouseRentCost,
			                  int perHotelRentCost) {
		this.monopolyDefinition = monopolyDefinition;
		this.propertyName = propertyName;
		this.costToPurchase = costToPurchase;
		this.buildingCost = buildingCost;
		this.unmonopolizedRentCost = unmonopolizedRentCost;
		this.monopolizedRentCost = monopolizedRentCost;
		this.oneHouseRentCost = oneHouseRentCost;
		this.twoHouseRentCost = twoHouseRentCost;
		this.threeHouseRentCost = threeHouseRentCost;
		this.fourHouseRentCost = fourHouseRentCost;
		this.perHotelRentCost = perHotelRentCost;
	}
	
	public MonopolyDefinition getMonopolyDefinition() {
		return monopolyDefinition;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public int getCostToPurchase() {
		return costToPurchase;
	}

	public int getBuildingCost() {
		return buildingCost;
	}

	public int getCostPerHouse() {
		return buildingCost;
	}

	public int getCostPerHotel() {
		return buildingCost;
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

	/**
	 * Determines if the given Player can buy one or more houses or hotels to
	 * place on this property.
	 * @return true if so, false otherwise.
	 */
	public boolean canPlayerBuildHouseOrHotelUpon(Player player) {
		return ((isOwner(player)) && (getMonopolyDefinition().hasBeenMonopolized()));
	}

	public int computeRent(Player guest) {
		if (!hasOwner()) {
			// No one owns this property as yet. Free Rent!
			return 0;
		}
		
		Player owner = getOwner();
		if (owner.equals(guest)) {
			// Owner is never charged rent to visit their own property!
			return 0;
		}
		
		if (isMortgaged()) {
			//TODO : Verify that the rent cost for any mortgaged
			// property is zero.
			return 0;
		}
		
		final int numberHotelsOnProperty = owner.getNumberHotelsOnProperty(this);
		int result = (numberHotelsOnProperty * getCostPerHotel());
		switch (owner.getNumberHousesOnProperty(this)) {
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
				if (numberHotelsOnProperty == 0) {
					if (isPartOfMonopoly()) {
						result = getMonopolizedRentCost();
					} else {
						result = getUnmonopolizedRentCost();
					}
				}
		}
		
		return result;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean hasOwner() {
		return (getOwner() != null);
	}
	
	/**
	 * Determines whether or not the given Player is the owner of this Property.
	 * @param player Expected to be non-null.
	 * @return true if so, false otherwise.
	 */
	public boolean isOwner(Player player) {
		return ((getOwner() != null) && (getOwner().equals(player)));
	}
	
	public boolean isPartOfMonopoly() {
		return getMonopolyDefinition().hasBeenMonopolized();
	}

	public boolean isMortgaged() {
		return mortgaged;
	}

	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
	}
}