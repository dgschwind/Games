package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class Pair extends MatchedKind {
	
	public Pair(Card.Kind kind) {
		super(kind);
	}
	
	@Override
	public final int getHandRank() {
		return 9;
	}
}