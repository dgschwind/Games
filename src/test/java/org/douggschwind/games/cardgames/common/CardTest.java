package org.douggschwind.games.cardgames.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		Assert.assertFalse(card1.equals(card2));
		Assert.assertFalse(card2.equals(card1));
	}

	@Test
	public void testGetDistinctCardKinds2() {
		List<Card> dealtHand = new ArrayList<>();
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Ten, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Clubs));
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Hearts));
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Spades));
		Set<Card.Kind> distinctCardKinds = Card.getDistinctCardKinds(dealtHand);
		Assert.assertEquals(2, distinctCardKinds.size());
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Three));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Ten));
	}

	@Test
	public void testGetDistinctCardKinds3() {
		List<Card> dealtHand = new ArrayList<>();
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Ten, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Clubs));
		dealtHand.add(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Spades));
		Set<Card.Kind> distinctCardKinds = Card.getDistinctCardKinds(dealtHand);
		Assert.assertEquals(3, distinctCardKinds.size());
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Three));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Ten));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Ace));
	}

	@Test
	public void testGetDistinctCardKinds4() {
		List<Card> dealtHand = new ArrayList<>();
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Ten, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Clubs));
		dealtHand.add(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		dealtHand.add(new Card(Card.Kind.Two, Card.Suit.Spades));
		Set<Card.Kind> distinctCardKinds = Card.getDistinctCardKinds(dealtHand);
		Assert.assertEquals(4, distinctCardKinds.size());
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Two));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Three));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Ten));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Ace));
	}

	@Test
	public void testGetDistinctCardKinds5() {
		List<Card> dealtHand = new ArrayList<>();
		dealtHand.add(new Card(Card.Kind.Three, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Ten, Card.Suit.Spades));
		dealtHand.add(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		dealtHand.add(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		dealtHand.add(new Card(Card.Kind.Two, Card.Suit.Spades));
		Set<Card.Kind> distinctCardKinds = Card.getDistinctCardKinds(dealtHand);
		Assert.assertEquals(5, distinctCardKinds.size());
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Two));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Three));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Ten));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Queen));
		Assert.assertTrue(distinctCardKinds.contains(Card.Kind.Ace));
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
