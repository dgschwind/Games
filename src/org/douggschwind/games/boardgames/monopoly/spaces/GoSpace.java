package org.douggschwind.games.boardgames.monopoly.spaces;

/**
 * Represents the Go space on the Monopoly board, the space from which all
 * contestants begin the game.
 * @author Doug Gschwind
 */
public class GoSpace extends BoardSpace {

	public GoSpace() {
		super("Go");
	}

	@Override
	public final boolean isGo() {
		return true;
	}
}