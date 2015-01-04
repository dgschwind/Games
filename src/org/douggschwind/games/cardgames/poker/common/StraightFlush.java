package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class StraightFlush extends Straight {

	public StraightFlush(Card.Kind highCardKind) {
		super(highCardKind);
	}
	
	@Override
	public int getHandRank() {
		return 2;
	}
}