package org.douggschwind.games.boardgames.monopoly.actioncards;

/**
 * The player must go directly to Jail, not collecting $200 when passing
 * go if go is passed in the process.
 * @author Doug Gschwind
 */
public class GoDirectlyToJailCard extends ActionCard {

	public GoDirectlyToJailCard() {
		super("Get directly to Jail");
	}

	@Override
	public final boolean isGoDirectlyToJail() {
		return true;
	}
}