package org.douggschwind.games.boardgames.monopoly.actioncard;

/**
 * An instance of this card entitles the bearer to be able to extricate themselves
 * from the Jail BoardSpace should they land there.
 * @author Doug Gschwind
 */
public class GetOutOfJailFreeCard extends ActionCard {

	public GetOutOfJailFreeCard() {
		super("Get out of Jail Free!");
	}

	@Override
	public final boolean isGetOutOfJailFree() {
		return true;
	}
}