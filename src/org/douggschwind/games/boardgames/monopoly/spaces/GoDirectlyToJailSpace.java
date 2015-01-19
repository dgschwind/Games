package org.douggschwind.games.boardgames.monopoly.spaces;

/**
 * When a player lands on this space, they must go directly to Jail, and on their
 * way passing Go, do NOT collect $200.
 * @author Doug Gschwind
 */
public class GoDirectlyToJailSpace extends PublicBoardSpace {

	public GoDirectlyToJailSpace() {
		super("Go directly to Jail");
	}
}