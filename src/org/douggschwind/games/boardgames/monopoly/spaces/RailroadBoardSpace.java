package org.douggschwind.games.boardgames.monopoly.spaces;

/**
 * An instance of this class represents one of the four Railroads available in the game.
 * @author Doug Gschwind
 */
public class RailroadBoardSpace extends PrivateBoardSpace {

	public RailroadBoardSpace(String railroadName, int costToPurchase, int mortgageValue) {
		super(railroadName, costToPurchase);
	}
	
	public int computeRent(int numberOfRailroadsOwned) {
		switch (numberOfRailroadsOwned) {
			case 4:
				return 200;
			case 3:
				return 100;
			case 2:
				return 50;
			case 1:
				return 25;
			case 0:
			default:
				return 0;
		}
	}
}