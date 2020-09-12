package org.douggschwind.games.cardgames.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a player in a card game than can be dealt an arbitrary number of cards.
 * @author Doug Gschwind
 */
public class Player {
	
	private static int playerCounter = 1;
	
	private final int playerNumber = playerCounter++;
	// For some Card games, the Player holds unique cards that cannot be present
	// in any of the other Player's hands. There are other games like Uno though,
	// where a Player could have two or more of the same card at the same time
	// other Player's also hold one or more of that same card. Thus, the List
	// type supports both needs, whereas a Set type here would not work for games
	// like Uno.
	private final List<FrenchSuitedPlayingCard> hand = new ArrayList<>();
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		} else if (!this.getClass().equals(that.getClass())) {
			return false;
		}
		
		return this.getPlayerNumber() == ((Player) that).getPlayerNumber();
	}
	
	@Override
	public int hashCode() {
		return getPlayerNumber();
	}
	
	public void acceptDealtCard(FrenchSuitedPlayingCard dealtCard) {
		hand.add(dealtCard);
	}
	
	public List<FrenchSuitedPlayingCard> getHand() {
		return hand;
	}
	
	public Set<FrenchSuitedPlayingCard.Kind> getDistinctCardKinds() {
		return FrenchSuitedPlayingCard.getDistinctCardKinds(getHand());
	}
	
	public void newHand() {
		hand.clear();
	}
}