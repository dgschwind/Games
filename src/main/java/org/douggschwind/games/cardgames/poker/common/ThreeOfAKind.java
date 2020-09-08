package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class ThreeOfAKind extends MatchedKind {

	public ThreeOfAKind(Card.Kind kind) {
		super(kind);
	}

	@Override
	public final int getHandRank() {
		return 7;
	}
}