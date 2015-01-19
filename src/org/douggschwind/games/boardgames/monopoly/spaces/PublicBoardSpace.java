package org.douggschwind.games.boardgames.monopoly.spaces;

/**
 * Instances of this class are those board spaces which are available to the public
 * and thus cannot be bought or sold.
 * @author Doug Gschwind
 */
public abstract class PublicBoardSpace extends BoardSpace {

	public PublicBoardSpace(String spaceName) {
		super(spaceName);
	}
	
	@Override
	public final boolean isPubliclyHeld() {
		return true;
	}
	
	@Override
	public final boolean canBeBoughtOrSold() {
		return false;
	}
}
