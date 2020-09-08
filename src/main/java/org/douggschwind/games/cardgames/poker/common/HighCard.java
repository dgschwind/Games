package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class HighCard extends HandStrength {
	
	private final Card.Kind highCardKind;

	public HighCard(Card.Kind highCardKind) {
		super();
		this.highCardKind = highCardKind;
	}
	
	@Override
	public int getHandRank() {
		return 10;
	}
	
	public final Card.Kind getHighCardKind() {
		return highCardKind;
	}
	
	@Override
	protected Boolean isStrongerThanSameHandRank(HandStrength that) {
		return this.getHighCardKind().hasHigherRank(((HighCard) that).getHighCardKind());
	}
}