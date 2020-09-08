package org.douggschwind.games.cardgames.poker.common;

import org.douggschwind.games.cardgames.common.Card;

/**
 * @author Doug Gschwind
 */
public class TwoPair extends MatchedKind {
	
	private final Card.Kind lowerValuedKind;

	public TwoPair(Card.Kind higherValuedKind, Card.Kind lowerValuedKind) {
		super(higherValuedKind);
		this.lowerValuedKind = lowerValuedKind;
	}
	
	@Override
	public final int getHandRank() {
		return 8;
	}
	
	public Card.Kind getHigherValuedKind() {
		return getKind();
	}
	
	public Card.Kind getLowerValuedKind() {
		return lowerValuedKind;
	}

	@Override
	protected final Boolean isStrongerThanSameHandRank(HandStrength that) {
		Boolean highPairResult = this.getHigherValuedKind().hasHigherRank(((TwoPair) that).getHigherValuedKind());
		if (highPairResult != null) {
			return highPairResult.booleanValue();
		}
		
		return this.getLowerValuedKind().hasHigherRank(((TwoPair) that).getLowerValuedKind());
	}
}