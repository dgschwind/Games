package org.douggschwind.games.cardgames.poker;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.Player;
import org.douggschwind.games.cardgames.poker.common.Flush;
import org.douggschwind.games.cardgames.poker.common.FourOfAKind;
import org.douggschwind.games.cardgames.poker.common.FullHouse;
import org.douggschwind.games.cardgames.poker.common.HandStrength;
import org.douggschwind.games.cardgames.poker.common.HighCard;
import org.douggschwind.games.cardgames.poker.common.Pair;
import org.douggschwind.games.cardgames.poker.common.RoyalFlush;
import org.douggschwind.games.cardgames.poker.common.Straight;
import org.douggschwind.games.cardgames.poker.common.StraightFlush;
import org.douggschwind.games.cardgames.poker.common.ThreeOfAKind;
import org.douggschwind.games.cardgames.poker.common.TwoPair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Doug Gschwind
 */
public class WesternHandStrengthTest {
	
	private Western underTest;
	private Player player;
	
	@Before
	public void initializeWestern() {
		underTest = new Western();
		player = new Player();
		underTest.addPlayer(player);
	}
	
	@Test
	public void testIsStrongerThan() {
		RoyalFlush royalFlush = new RoyalFlush();
		StraightFlush jackHighStraightFlush = new StraightFlush(Card.Kind.Jack);
		FourOfAKind fourTens = new FourOfAKind(Card.Kind.Ten);
		FullHouse acesBasedFullHouse = new FullHouse(Card.Kind.Ace);
		Flush queenHighFlush = new Flush(Card.Kind.Queen);
		Straight tenHighStraight = new Straight(Card.Kind.Ten);
		ThreeOfAKind tripleFours = new ThreeOfAKind(Card.Kind.Four);
		TwoPair acesAndEights = new TwoPair(Card.Kind.Ace, Card.Kind.Eight);
		Pair pairJacks = new Pair(Card.Kind.Jack);
		HighCard queenHigh = new HighCard(Card.Kind.Queen);
		
		Assert.assertNull(royalFlush.isStrongerThan(royalFlush));
		Assert.assertTrue(royalFlush.isStrongerThan(jackHighStraightFlush));
		Assert.assertTrue(royalFlush.isStrongerThan(fourTens));
		Assert.assertTrue(royalFlush.isStrongerThan(acesBasedFullHouse));
		Assert.assertTrue(royalFlush.isStrongerThan(queenHighFlush));
		Assert.assertTrue(royalFlush.isStrongerThan(tenHighStraight));
		Assert.assertTrue(royalFlush.isStrongerThan(tripleFours));
		Assert.assertTrue(royalFlush.isStrongerThan(acesAndEights));
		Assert.assertTrue(royalFlush.isStrongerThan(pairJacks));
		Assert.assertTrue(royalFlush.isStrongerThan(queenHigh));
		
		Assert.assertFalse(jackHighStraightFlush.isStrongerThan(royalFlush));
		Assert.assertNull(jackHighStraightFlush.isStrongerThan(jackHighStraightFlush));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(fourTens));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(acesBasedFullHouse));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(queenHighFlush));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(tenHighStraight));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(tripleFours));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(acesAndEights));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(pairJacks));
		Assert.assertTrue(jackHighStraightFlush.isStrongerThan(queenHigh));
		
		Assert.assertFalse(fourTens.isStrongerThan(royalFlush));
		Assert.assertFalse(fourTens.isStrongerThan(jackHighStraightFlush));
		Assert.assertNull(fourTens.isStrongerThan(fourTens));
		Assert.assertTrue(fourTens.isStrongerThan(acesBasedFullHouse));
		Assert.assertTrue(fourTens.isStrongerThan(queenHighFlush));
		Assert.assertTrue(fourTens.isStrongerThan(tenHighStraight));
		Assert.assertTrue(fourTens.isStrongerThan(tripleFours));
		Assert.assertTrue(fourTens.isStrongerThan(acesAndEights));
		Assert.assertTrue(fourTens.isStrongerThan(pairJacks));
		Assert.assertTrue(fourTens.isStrongerThan(queenHigh));
		
		Assert.assertFalse(acesBasedFullHouse.isStrongerThan(royalFlush));
		Assert.assertFalse(acesBasedFullHouse.isStrongerThan(jackHighStraightFlush));
		Assert.assertFalse(acesBasedFullHouse.isStrongerThan(fourTens));
		Assert.assertNull(acesBasedFullHouse.isStrongerThan(acesBasedFullHouse));
		Assert.assertTrue(acesBasedFullHouse.isStrongerThan(queenHighFlush));
		Assert.assertTrue(acesBasedFullHouse.isStrongerThan(tenHighStraight));
		Assert.assertTrue(acesBasedFullHouse.isStrongerThan(tripleFours));
		Assert.assertTrue(acesBasedFullHouse.isStrongerThan(acesAndEights));
		Assert.assertTrue(acesBasedFullHouse.isStrongerThan(pairJacks));
		Assert.assertTrue(acesBasedFullHouse.isStrongerThan(queenHigh));
		
		Assert.assertFalse(queenHighFlush.isStrongerThan(royalFlush));
		Assert.assertFalse(queenHighFlush.isStrongerThan(jackHighStraightFlush));
		Assert.assertFalse(queenHighFlush.isStrongerThan(fourTens));
		Assert.assertFalse(queenHighFlush.isStrongerThan(acesBasedFullHouse));
		Assert.assertNull(queenHighFlush.isStrongerThan(queenHighFlush));
		Assert.assertTrue(queenHighFlush.isStrongerThan(tenHighStraight));
		Assert.assertTrue(queenHighFlush.isStrongerThan(tripleFours));
		Assert.assertTrue(queenHighFlush.isStrongerThan(acesAndEights));
		Assert.assertTrue(queenHighFlush.isStrongerThan(pairJacks));
		Assert.assertTrue(queenHighFlush.isStrongerThan(queenHigh));
		
		Assert.assertFalse(tenHighStraight.isStrongerThan(royalFlush));
		Assert.assertFalse(tenHighStraight.isStrongerThan(jackHighStraightFlush));
		Assert.assertFalse(tenHighStraight.isStrongerThan(fourTens));
		Assert.assertFalse(tenHighStraight.isStrongerThan(acesBasedFullHouse));
		Assert.assertFalse(tenHighStraight.isStrongerThan(queenHighFlush));
		Assert.assertNull(tenHighStraight.isStrongerThan(tenHighStraight));
		Assert.assertTrue(tenHighStraight.isStrongerThan(tripleFours));
		Assert.assertTrue(tenHighStraight.isStrongerThan(acesAndEights));
		Assert.assertTrue(tenHighStraight.isStrongerThan(pairJacks));
		Assert.assertTrue(tenHighStraight.isStrongerThan(queenHigh));
		
		Assert.assertFalse(tripleFours.isStrongerThan(royalFlush));
		Assert.assertFalse(tripleFours.isStrongerThan(jackHighStraightFlush));
		Assert.assertFalse(tripleFours.isStrongerThan(fourTens));
		Assert.assertFalse(tripleFours.isStrongerThan(acesBasedFullHouse));
		Assert.assertFalse(tripleFours.isStrongerThan(queenHighFlush));
		Assert.assertFalse(tripleFours.isStrongerThan(tenHighStraight));
		Assert.assertNull(tripleFours.isStrongerThan(tripleFours));
		Assert.assertTrue(tripleFours.isStrongerThan(acesAndEights));
		Assert.assertTrue(tripleFours.isStrongerThan(pairJacks));
		Assert.assertTrue(tripleFours.isStrongerThan(queenHigh));
		
		Assert.assertFalse(acesAndEights.isStrongerThan(royalFlush));
		Assert.assertFalse(acesAndEights.isStrongerThan(jackHighStraightFlush));
		Assert.assertFalse(acesAndEights.isStrongerThan(fourTens));
		Assert.assertFalse(acesAndEights.isStrongerThan(acesBasedFullHouse));
		Assert.assertFalse(acesAndEights.isStrongerThan(queenHighFlush));
		Assert.assertFalse(acesAndEights.isStrongerThan(tenHighStraight));
		Assert.assertFalse(acesAndEights.isStrongerThan(tripleFours));
		Assert.assertNull(acesAndEights.isStrongerThan(acesAndEights));
		Assert.assertTrue(acesAndEights.isStrongerThan(pairJacks));
		Assert.assertTrue(acesAndEights.isStrongerThan(queenHigh));
		
		Assert.assertFalse(pairJacks.isStrongerThan(royalFlush));
		Assert.assertFalse(pairJacks.isStrongerThan(jackHighStraightFlush));
		Assert.assertFalse(pairJacks.isStrongerThan(fourTens));
		Assert.assertFalse(pairJacks.isStrongerThan(acesBasedFullHouse));
		Assert.assertFalse(pairJacks.isStrongerThan(queenHighFlush));
		Assert.assertFalse(pairJacks.isStrongerThan(tenHighStraight));
		Assert.assertFalse(pairJacks.isStrongerThan(tripleFours));
		Assert.assertFalse(pairJacks.isStrongerThan(acesAndEights));
		Assert.assertNull(pairJacks.isStrongerThan(pairJacks));
		Assert.assertTrue(pairJacks.isStrongerThan(queenHigh));
		
		Assert.assertFalse(queenHigh.isStrongerThan(royalFlush));
		Assert.assertFalse(queenHigh.isStrongerThan(jackHighStraightFlush));
		Assert.assertFalse(queenHigh.isStrongerThan(fourTens));
		Assert.assertFalse(queenHigh.isStrongerThan(acesBasedFullHouse));
		Assert.assertFalse(queenHigh.isStrongerThan(queenHighFlush));
		Assert.assertFalse(queenHigh.isStrongerThan(tenHighStraight));
		Assert.assertFalse(queenHigh.isStrongerThan(tripleFours));
		Assert.assertFalse(queenHigh.isStrongerThan(acesAndEights));
		Assert.assertFalse(queenHigh.isStrongerThan(pairJacks));
		Assert.assertNull(queenHigh.isStrongerThan(queenHigh));
	}

	@Test
	public void testDetermineHandStrengthRoyalFlush() {
		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Spades));
		Assert.assertEquals(RoyalFlush.class, underTest.determinePlayerHandStrength(player).getClass());
	}
	
	@Test
	public void testDetermineHandStrengthStraightFlush() {
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Ten, ((StraightFlush) actualHandStrength).getHighCardKind());
	}
	
	@Test
	public void testDetermineHandStrengthAceLowStraightFlush() {
		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Hearts));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Five, ((StraightFlush) actualHandStrength).getHighCardKind());
	}
	
	@Test
	public void testDetermineHandStrengthFourOfAKind() {
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(FourOfAKind.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Eight, ((FourOfAKind) actualHandStrength).getKind());
	}
	
	@Test
	public void testDetermineHandStrengthFullHouse() {
		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Clubs));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(FullHouse.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.King, ((FullHouse) actualHandStrength).getKind());
	}
	
	@Test
	public void testDetermineHandStrengthFlush() {
		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(Flush.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Jack, ((Flush) actualHandStrength).getHighCardKind());
	}
	
	@Test
	public void testDetermineHandStrengthStraight() {
		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Diamonds));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(Straight.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Eight, ((Straight) actualHandStrength).getHighCardKind());
	}
	
	@Test
	public void testDetermineHandStrengthAceLowStraight() {
		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(Straight.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Five, ((Straight) actualHandStrength).getHighCardKind());
	}
	
	@Test
	public void testDetermineHandStrengthThreeOfAKind() {
		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Hearts));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(ThreeOfAKind.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Four, ((ThreeOfAKind) actualHandStrength).getKind());
	}
	
	@Test
	public void testDetermineHandStrengthTwoPair() {
		// Dead man's hand, A's and 8's.
		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(TwoPair.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Ace, ((TwoPair) actualHandStrength).getHigherValuedKind());
		Assert.assertEquals(Card.Kind.Eight, ((TwoPair) actualHandStrength).getLowerValuedKind());
	}
	
	@Test
	public void testDetermineHandStrengthPair() {
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(Pair.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Ten, ((Pair) actualHandStrength).getKind());
	}
	
	@Test
	public void testDetermineHandStrengthHighCard() {
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(HighCard.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Ten, ((HighCard) actualHandStrength).getHighCardKind());
	}
}