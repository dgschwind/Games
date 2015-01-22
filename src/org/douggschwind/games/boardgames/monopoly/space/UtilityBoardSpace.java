package org.douggschwind.games.boardgames.monopoly.space;

/**
 * An instance of this class represents one of the two Utilities available in the game.
 * @author Doug Gschwind
 */
public class UtilityBoardSpace extends PrivateBoardSpace {

	public UtilityBoardSpace(String utilityName, int costToPurchase, int mortgageValue) {
		super(utilityName, costToPurchase);
	}
	
	public int computeRent(int numberOfUtilitiesOwned, int valueOfDiceRoll) {
		int multiplier = 0;
		if (numberOfUtilitiesOwned == 2) {
			multiplier = 10;
		} else if (numberOfUtilitiesOwned == 1) {
			multiplier = 4;
		}
		return multiplier * valueOfDiceRoll;
	}
}