package org.douggschwind.games.boardgames.monopoly.space;

import org.douggschwind.games.boardgames.monopoly.title.Title;

/**
 * Instances of this class represent board spaces that can be privately held and thus
 * purchased or sold by a player. One of the properties, railroads, or utilities.
 * @author Doug Gschwind
 * @param T Identifies the type of Title.
 */
public abstract class PrivateBoardSpace<T extends Title> extends BoardSpace {
	
	private final T title;

	protected PrivateBoardSpace(T title) {
		super(title.getName());
		this.title = title;
	}
	
	@Override
	public final boolean canBePrivatelyHeld() {
		return true;
	}
	
	public boolean allowsHousesOrHotelsToBeBuiltUponIt() {
		return false;
	}
	
	public final T getTitle() {
		return title;
	}
	
	public int getPurchasePrice() {
		return getTitle().getPurchasePrice();
	}
	
	public int getMortgageValue() {
		return getTitle().getMortgageValue();
	}
	
	public boolean isProperty() {
		return false;
	}
	
	public boolean isRailroad() {
		return false;
	}
	
	public boolean isUtility() {
		return false;
	}
}