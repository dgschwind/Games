package org.douggschwind.games.cardgames.wildcardpoker;

import java.util.Set;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The tests here follow what is done in WesternTestTwoPlayers with the following twists :
 * 1) Five of a Kind is possible in Deuces Wild
 * 2) Two players can have the same Four of a Kind hand in terms of rank. e.g. Two players
 *    can both have four Tens by each having a pair of Tens and a pair of Twos.
 * @author Doug Gschwind
 */
public class DeucesWildTestTwoPlayers {
	
	private DeucesWild underTest;
	private Player player1, player2;
	
	@Before
	public void initializeWestern() {
		underTest = new DeucesWild();
		player1 = new Player();
		player2 = new Player();
		underTest.addPlayer(player1);
		underTest.addPlayer(player2);
	}
	
	private void assertPlayersHaveMatchingHands(Set<Player> declaredWinnersOfHand) {
		Assert.assertSame(2, declaredWinnersOfHand.size());
		Assert.assertTrue(declaredWinnersOfHand.contains(player1));
		Assert.assertTrue(declaredWinnersOfHand.contains(player2));
	}
	
	private void assertGivenPlayerIsWinner(Set<Player> declaredWinnersOfHand, Player declaredWinnerOfHand) {
		Assert.assertSame(1, declaredWinnersOfHand.size());
		Assert.assertTrue(declaredWinnersOfHand.contains(declaredWinnerOfHand));
	}
	
	@Test
	public void testDetermineWinnerWithFiveOfAKindWithFourWildCards() {
		player1.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
		
		// Player two is the winner with five Jacks
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnerWithFiveOfAKindWithThreeWildCards() {
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		
		
		// Player one is the winner with five Tens
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnerWithFiveOfAKindWithTwoWildCards() {
		player1.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Hearts));
		
		// Player two is the winner with five Queens
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnerWithFiveOfAKindWithOneWildCard() {
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		
		
		// Player one is the winner with five Sevens
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingRoyalFlushHands() {
		player1.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		// In this case, the two players have matching hands, and since there
		// are no other competitors, they both are declared winners.
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingStraightFlushHands() {
		player1.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		// In this case, the two players have matching hands, and since there
		// are no other competitors, they both are declared winners.
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingStraightFlushHands() {
		player1.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		// Player one is the winner with a King high straight flush
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingButAceBasedStraightFlushHands() {
		player1.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		
		// Player one is the winner with a royal flush
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoDifferentFourOfAKindHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
		
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		
		// Player one is the winner with four 9s.
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingFourOfAKindHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
		
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		
		// In this case, the two players have matching hands, and since there
		// are no other competitors, they both are declared winners.
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingFourOfAKindHandsWithDecidingTiebreakerCard1() {
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
		
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		
		// Player one is the winner with four Nines, with a Queen.
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingFourOfAKindHandsWithDecidingTiebreakerCard2() {
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Spades));
		
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		
		// Player two is the winner with four Nines, with a Jack.
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersTwoFullHouseHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		
		// Player two is the winner with Tens over Fours.
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingFlushHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		// In this case, the two players have matching hands, and since there
		// are no other competitors, they both are declared winners.
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingFlushHands1() {
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		// Player 1 is the winner with a Queen high flush
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingFlushHands2() {
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		// Player 2 is the winner with the third highest card being an eight
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingFlushHands3() {
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		
		// Player 1 is the winner with the stronger least high card.
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingStraightHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		
		// In this case, the two players have matching hands, and since there
		// are no other competitors, they both are declared winners.
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingStraightHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Spades));
		
		// Player 1 is the winner with a Jack high straight
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingButAceBasedStraightHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		
		// Player 2 is the winner with an Ace high straight
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersTwoNotMatchingThreeOfAKindHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
		
		// Player 2 is the winner with three Sevens
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingPairMatchingHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		
		// In this case, the two players have matching hands, and since there
		// are no other competitors, they both are declared winners.
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersTwoMatchingPairNotMatchingHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		
		// In this case, the two players have matching hands, but only one
		// winner since the odd card is used for the tiebreaker.
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersTwoPairNotMatchingHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Spades));
		
		// Player two is the winner
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersMatchingPairMatchingHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		
		// In this case, the two players have matching hands, and since there
		// are no other competitors, they both are declared winners.
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersMatchingPairNotMatchingHands1() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		
		// Player one wins with a pair of Jacks, Ace high
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersMatchingPairNotMatchingHands2() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		
		// Player two wins with a pair of Jacks, Ace, Ten, Three high
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersNotMatchingPairHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Two, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		
		// Player one wins with a pair of Jacks
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersMatchingHighCardHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		
		// Player one wins with a pair of Jacks
		Set<Player> handWinners = underTest.determineWinners();
		assertPlayersHaveMatchingHands(handWinners);
	}
	
	@Test
	public void testDetermineWinnersNotMatchingHighCardHands1() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.King, Card.Suit.Diamonds));
		
		// Player two wins with King high
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
	
	@Test
	public void testDetermineWinnersNotMatchingHighCardHands2() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		
		// Player one wins
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player1);
	}
	
	@Test
	public void testDetermineWinnersNotMatchingHighCardHands3() {
		player1.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Jack, Card.Suit.Hearts));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Four, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Queen, Card.Suit.Diamonds));
		
		// Player two wins
		Set<Player> handWinners = underTest.determineWinners();
		assertGivenPlayerIsWinner(handWinners, player2);
	}
}