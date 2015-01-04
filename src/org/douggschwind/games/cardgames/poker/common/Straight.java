package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class Straight extends HandStrength {
	
	private final Card.Kind highCardKind;

	public Straight(Card.Kind highCardKind) {
		super();
		this.highCardKind = highCardKind;
	}
	
	@Override
	public int getHandRank() {
		return 6;
	}
	
	public final Card.Kind getHighCardKind() {
		return highCardKind;
	}
	
	@Override
	protected Boolean isStrongerThanSameHandRank(HandStrength that) {
		return this.getHighCardKind().hasHigherRank(((Straight) that).getHighCardKind());
	}
}