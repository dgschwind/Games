package org.douggschwind.games.cardgames.wildcardpoker;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.Player;
import org.douggschwind.games.cardgames.poker.common.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that the hand strength of different hands with one eyed Jacks as being
 * wild is in fact what is expected.
 * @author Doug Gschwind
 */
public class OneEyedJacksWildHandStrengthTest {
	
	private OneEyedJacksWild underTest;
	private Player player;
	
	@Before
	public void initializeDeucesWild() {
		underTest = new OneEyedJacksWild();
		player = new Player();
		underTest.addPlayer(player);
	}

	@Test
	public void testDetermineHandStrengthFiveOfAKindViaOneWildCard() {
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(FiveOfAKind.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Ten, ((FiveOfAKind) actualHandStrength).getKind());
	}
	
	@Test
	public void testDetermineHandStrengthFiveOfAKindViaTwoWildCards() {
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
		Assert.assertEquals(FiveOfAKind.class, actualHandStrength.getClass());
		Assert.assertEquals(Card.Kind.Ten, ((FiveOfAKind) actualHandStrength).getKind());
	}
	
	@Test
	public void testDetermineHandStrengthNaturalRoyalFlush() {
		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Clubs));
		Assert.assertEquals(RoyalFlush.class, underTest.determinePlayerHandStrength(player).getClass());
	}
	
	@Test
	public void testDetermineHandStrengthRoyalFlushViaOneWildCard() {
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Clubs));
		Assert.assertEquals(RoyalFlush.class, underTest.determinePlayerHandStrength(player).getClass());
	}
	
	@Test
	public void testDetermineHandStrengthRoyalFlushViaTwoWildCards() {
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Clubs));
		Assert.assertEquals(RoyalFlush.class, underTest.determinePlayerHandStrength(player).getClass());
	}
	
//	@Test
//	public void testDetermineHandStrengthNaturalStraightFlush() {
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Jack, ((StraightFlush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthInsideStraightFlushViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Jack, ((StraightFlush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthInsideStraightFlushViaTwoWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.King, ((StraightFlush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthInsideStraightFlushViaThreeWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Queen, ((StraightFlush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthOutsideStraightFlushViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Queen, ((StraightFlush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthOutsideStraightFlushViaTwoWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.King, ((StraightFlush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthOutsideStraightFlushViaThreeWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(StraightFlush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Jack, ((StraightFlush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthNaturalFourOfAKind() {
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(FourOfAKind.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ten, ((FourOfAKind) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthFourOfAKindViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(FourOfAKind.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Nine, ((FourOfAKind) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthFourOfAKindViaTwoWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(FourOfAKind.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ten, ((FourOfAKind) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthFourOfAKindViaThreeWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(FourOfAKind.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Seven, ((FourOfAKind) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthNaturalFullHouse() {
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(FullHouse.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Queen, ((FullHouse) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthFullHouseViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(FullHouse.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Queen, ((FullHouse) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthNaturalFlush() {
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Flush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Jack, ((Flush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthFlushViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Spades));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Flush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ace, ((Flush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthFlushViaTwoWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Flush.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ace, ((Flush) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthNaturalStraight() {
//		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Straight.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ten, ((Straight) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthInsideStraightViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Straight.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ten, ((Straight) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthInsideStraightViaTwoWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Straight.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ten, ((Straight) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthOutsideStraightViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Straight.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ten, ((Straight) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthOutsideStraightViaTwoWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Straight.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ten, ((Straight) actualHandStrength).getHighCardKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthNaturalThreeOfAKind() {
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(ThreeOfAKind.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Seven, ((ThreeOfAKind) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthThreeOfAKindViaOneWildCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(ThreeOfAKind.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Seven, ((ThreeOfAKind) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthThreeOfAKindViaTwoWildCards() {
//		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(ThreeOfAKind.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Queen, ((ThreeOfAKind) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthNaturalPair() {
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Pair.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.King, ((Pair) actualHandStrength).getKind());
//	}
//
//		@Test
//	public void testDetermineHandStrengthPairViaOneWildCard1() {
//		player.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Pair.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.King, ((Pair) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthPairViaOneWildCard2() {
//		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Pair.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ace, ((Pair) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthPairViaOneWildCard3() {
//		player.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Pair.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ace, ((Pair) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthPairViaOneWildCard4() {
//		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(Pair.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.Ace, ((Pair) actualHandStrength).getKind());
//	}
//
//	@Test
//	public void testDetermineHandStrengthNaturalHighCard() {
//		player.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
//		player.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Hearts));
//		player.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
//		player.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
//
//		HandStrength actualHandStrength = underTest.determinePlayerHandStrength(player);
//		Assert.assertEquals(HighCard.class, actualHandStrength.getClass());
//		Assert.assertEquals(Card.Kind.King, ((HighCard) actualHandStrength).getHighCardKind());
//	}
}