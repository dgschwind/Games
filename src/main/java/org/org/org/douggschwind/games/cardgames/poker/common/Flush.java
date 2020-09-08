package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class Flush extends HighCard {

	public Flush(Card.Kind highCardKind) {
		super(highCardKind);
	}
	
	@Override
	public final int getHandRank() {
		return 5;
	}
}