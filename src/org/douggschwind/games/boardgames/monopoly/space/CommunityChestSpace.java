package org.douggschwind.games.boardgames.monopoly.space;

/**
 * Represents a Community Chest space on the Monopoly board.
 * @author Doug Gschwind
 */
public class CommunityChestSpace extends PublicBoardSpace {

	public CommunityChestSpace() {
		super("Community Chest");
	}

	@Override
	public final boolean isCommunityChest() {
		return true;
	}
}