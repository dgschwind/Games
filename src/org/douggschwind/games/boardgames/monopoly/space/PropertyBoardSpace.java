package org.douggschwind.games.boardgames.monopoly.space;

import org.douggschwind.games.boardgames.monopoly.Player;
import org.douggschwind.games.boardgames.monopoly.titledeed.TitleDeed;

/**
 * An instance of this class is a property that can be owned, upon which
 * houses and hotels may be built, and rent may be charged.
 * @author Doug Gschwind
 */
public class PropertyBoardSpace extends PrivateBoardSpace {
	private final TitleDeed titleDeed;

	public PropertyBoardSpace(TitleDeed titleDeed) {
		super(titleDeed.getPropertyName(), titleDeed.getCostToPurchase());
		this.titleDeed = titleDeed;
	}
	
	@Override
	public final boolean isProperty() {
		return true;
	}
	
	public TitleDeed getTitleDeed() {
		return titleDeed;
	}

	public int getCostPerHouse() {
		return getTitleDeed().getCostPerHouse();
	}

	public int getCostPerHotel() {
		return getTitleDeed().getCostPerHotel();
	}

	public int getUnmonopolizedRentCost() {
		return getTitleDeed().getUnmonopolizedRentCost();
	}

	public int getMonopolizedRentCost() {
		return getTitleDeed().getMonopolizedRentCost();
	}

	public int getOneHouseRentCost() {
		return getTitleDeed().getOneHouseRentCost();
	}

	public int getTwoHouseRentCost() {
		return getTitleDeed().getTwoHouseRentCost();
	}

	public int getThreeHouseRentCost() {
		return getTitleDeed().getThreeHouseRentCost();
	}

	public int getFourHouseRentCost() {
		return getTitleDeed().getFourHouseRentCost();
	}

	public int getPerHotelRentCost() {
		return getTitleDeed().getPerHotelRentCost();
	}

	public int computeRent(Player player) {
		if (!getTitleDeed().hasOwner()) {
			// No owner, Free Rent!
			return 0;
		}
		return getTitleDeed().computeRent(player);
	}
}