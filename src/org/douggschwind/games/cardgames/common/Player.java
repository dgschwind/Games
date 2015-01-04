package org.douggschwind.games.cardgames.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Doug Gschwind
 */
public class Player {
	
	private static int playerCounter = 1;
	
	private final int playerNumber = playerCounter++;
	private final List<Card> hand = new ArrayList<>();
	
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
	
	public void acceptDealtCard(Card dealtCard) {
		hand.add(dealtCard);
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public Set<Card.Kind> getDistinctCardKinds() {
		return Card.getDistinctCardKinds(getHand());
	}
	
	public void newGame() {
		hand.clear();
	}
}