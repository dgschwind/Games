package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class RoyalFlush extends StraightFlush {

	public RoyalFlush() {
		super(Card.Kind.Ace);
	}
	
	@Override
	public final int getHandRank() {
		return 1;
	}
}