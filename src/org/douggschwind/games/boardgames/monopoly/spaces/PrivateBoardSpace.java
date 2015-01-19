package org.douggschwind.games.boardgames.monopoly.spaces;

/**
 * Instances of this class represent board spaces that can be privately held and thus
 * purchased or sold by a player. One of the properties, railroads, or utilities.
 * @author Doug Gschwind
 */
public abstract class PrivateBoardSpace extends BoardSpace {
	
	private final int costToPurchase;
	private final int mortgageValue;

	protected PrivateBoardSpace(String spaceName, int costToPurchase) {
		super(spaceName);
		this.costToPurchase = costToPurchase;
		this.mortgageValue = costToPurchase/2;
	}
	
	@Override
	public final boolean canBePrivatelyHeld() {
		return true;
	}
	
	@Override
	public final boolean canBeBoughtOrSold() {
		return true;
	}
	
	public boolean allowsHousesOrHotelsToBeBuiltUponIt() {
		return false;
	}

	public int getCostToPurchase() {
		return costToPurchase;
	}

	public int getMortgageValue() {
		return mortgageValue;
	}
}