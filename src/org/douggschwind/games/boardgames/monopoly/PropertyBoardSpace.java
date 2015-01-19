package org.douggschwind.games.boardgames.monopoly;

/**
 * An instance of this class is a property that can be owned, upon which
 * houses and hotels may be built, and rent may be charged.
 * @author Doug Gschwind
 */
public class PropertyBoardSpace extends BoardSpace {
	
	private final int costToPurchase;
	private final int costToBuildHouse;
	private final int costToRentHouse;
	private final int costToBuildHotel;
	private final int costToRentHotel;

	public PropertyBoardSpace(String spaceName,
			                  int costToPurchase,
			                  int costToBuildHouse,
			                  int costToRentHouse,
			                  int costToBuildHotel,
			                  int costToRentHotel) {
		super(spaceName);
		this.costToPurchase = costToPurchase;
		this.costToBuildHouse = costToBuildHouse;
		this.costToRentHouse = costToRentHouse;
		this.costToBuildHotel = costToBuildHotel;
		this.costToRentHotel = costToRentHotel;
	}
	
	@Override
	public final boolean isProperty() {
		return true;
	}

	public int getCostToPurchase() {
		return costToPurchase;
	}

	public int getCostToBuildHouse() {
		return costToBuildHouse;
	}

	public int getCostToRentHouse() {
		return costToRentHouse;
	}

	public int getCostToBuildHotel() {
		return costToBuildHotel;
	}

	public int getCostToRentHotel() {
		return costToRentHotel;
	}
}