package org.douggschwind.games.boardgames.monopoly.space;

/**
 * Represents a Chance space on the Monopoly board.
 * @author Doug Gschwind
 */
public class ChanceSpace extends PublicBoardSpace {

	public ChanceSpace() {
		super("Chance");
	}

	@Override
	public final boolean isChance() {
		return true;
	}
}