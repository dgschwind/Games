package org.douggschwind.games.cardgames.common;

import org.junit.Assert;
import org.junit.Test;

public class CardTest {

	@Test
	public void testEquals() {
		Card card1 = new Card(Card.Kind.Ten, Card.Suit.Hearts);
		Card card2 = new Card(Card.Kind.Ten, Card.Suit.Hearts);
		Assert.assertEquals(card1, card2);
		Assert.assertEquals(card2, card1);
	}
	
	@Test
	public void testNotEquals() {
		Card card1 = new Card(Card.Kind.Ten, Card.Suit.Hearts);
		Card card2 = new Card(Card.Kind.Ten, Card.Suit.Clubs);
		Assert.assertTrue(!card1.equals(card2));
		Assert.assertTrue(!card2.equals(card1));
	}
	
	@Test
	public void testBeats() {
		for (Card.Kind outerKind : Card.Kind.values()) {
			for (Card.Kind innerKind : Card.Kind.values()) {
				if (outerKind.equals(innerKind)) {
					Assert.assertSame(null, outerKind.hasHigherRank(innerKind));
				} else if (outerKind.getRank() < innerKind.getRank()) {
					Assert.assertTrue(outerKind.hasHigherRank(innerKind));
				} else {
					Assert.assertFalse(outerKind.hasHigherRank(innerKind));
				}
			}
		}
	}
	
	@Test
	public void testHasHigherRank() {
		Card card1 = new Card(Card.Kind.Ten, Card.Suit.Spades);
		Card card2 = new Card(Card.Kind.Jack, Card.Suit.Hearts);
		Assert.assertTrue(card2.getKind().hasHigherRank(card1.getKind()));
		Assert.assertFalse(card1.getKind().hasHigherRank(card2.getKind()));
		Assert.assertNull(card1.getKind().hasHigherRank(card1.getKind()));
		Assert.assertNull(card2.getKind().hasHigherRank(card2.getKind()));
	}
	
	@Test
	public void testHasLowerRank() {
		Card card1 = new Card(Card.Kind.Six, Card.Suit.Spades);
		Card card2 = new Card(Card.Kind.Five, Card.Suit.Hearts);
		Assert.assertFalse(card1.getKind().hasLowerRank(card2.getKind()));
		Assert.assertTrue(card2.getKind().hasLowerRank(card1.getKind()));
		Assert.assertNull(card1.getKind().hasLowerRank(card1.getKind()));
		Assert.assertNull(card2.getKind().hasLowerRank(card2.getKind()));
	}
}