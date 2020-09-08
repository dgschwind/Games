package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public abstract class MatchedKind extends HandStrength {
	
	private final Card.Kind kind;

	protected MatchedKind(Card.Kind kind) {
		super();
		this.kind = kind;
	}
	
	public Card.Kind getKind() {
		return kind;
	}
	
	@Override
	protected Boolean isStrongerThanSameHandRank(HandStrength that) {
		return this.getKind().hasHigherRank(((MatchedKind) that).getKind());
	}
}