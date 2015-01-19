package org.douggschwind.games.boardgames.monopoly.actioncards;

import org.douggschwind.games.boardgames.monopoly.spaces.BoardSpace;
import org.douggschwind.games.common.AbstractCard;

/**
 * ActionCards in Monopoly form the basis for the Community Chest and Chance cards.
 * @author Doug Gschwind
 */
public abstract class ActionCard implements AbstractCard {
	private final String cardName; // e.g. Go to Jail
	private final boolean advanceToWellKnownSpace;
	private final BoardSpace advanceToSpace; // non-null if known
	
	protected ActionCard(String cardName) {
		this(cardName, false, null);
	}
	
	protected ActionCard(String cardName,
			             boolean advanceToWellKnownSpace,
			             BoardSpace advanceToSpace) {
		this.cardName = cardName;
		this.advanceToWellKnownSpace = advanceToWellKnownSpace;
		this.advanceToSpace = advanceToSpace;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public boolean isGoDirectlyToJail() {
		return false;
	}

	public boolean isGetOutOfJailFree() {
		return false;
	}

	public boolean isAdvanceToWellKnownSpace() {
		return advanceToWellKnownSpace;
	}

	public BoardSpace getAdvanceToSpace() {
		return advanceToSpace;
	}
}