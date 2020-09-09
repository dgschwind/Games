package org.douggschwind.games.cardgames.poker;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * @author Doug Gschwind
 */
public class WesternTestThreePlayers {
	
	private Western underTest;
	private Player player1, player2, player3;

	@Before
	public void initializeWestern() {
		underTest = new Western();
		player1 = new Player();
		player2 = new Player();
		player3 = new Player();
		underTest.addPlayer(player1);
		underTest.addPlayer(player2);
		underTest.addPlayer(player3);
	}
	
	@Test
	public void testDetermineWinnersAscendingStrengthHands() {
		// Player 1 has a ten high straight
		player1.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));

		// Player 2 has a flush
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Diamonds));

		// Player 3 has a full house
		player3.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player3.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		player3.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		player3.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		
		// In this case, player three is the winner with a full house.
		Set<Player> handWinners = underTest.determineWinners();
		Assert.assertSame(1, handWinners.size());
		Assert.assertTrue(handWinners.contains(player3));
	}
	
	@Test
	public void testDetermineWinnersDescendingStrengthHands() {
		// Player 1 has a full house
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));

		// Player 2 has a flush
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Diamonds));

		// Player 3 has a ten high straight
		player3.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Clubs));
		player3.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player3.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Hearts));
		player3.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Spades));
		
		// In this case, player one is the winner
		Set<Player> handWinners = underTest.determineWinners();
		Assert.assertSame(1, handWinners.size());
		Assert.assertTrue(handWinners.contains(player1));
		
	}
	
	@Test
	public void testDetermineWinnersThreeMatchingFlushHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Clubs));
		
		player3.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		
		// In this case, all three hands match identically, and not one of
		// them loses to the others.
		Set<Player> handWinners = underTest.determineWinners();
		Assert.assertSame(3, handWinners.size());
		Assert.assertTrue(handWinners.contains(player1));
		Assert.assertTrue(handWinners.contains(player2));
		Assert.assertTrue(handWinners.contains(player3));
	}
	
	@Test
	public void testDetermineWinnersThreeVeryCloseFlushHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Diamonds));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Clubs));
		
		player3.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Spades));
		
		// In this case, player two is the winner
		Set<Player> handWinners = underTest.determineWinners();
		Assert.assertSame(1, handWinners.size());
		Assert.assertTrue(handWinners.contains(player2));
	}
	
	@Test
	public void testDetermineWinnersThreeMatchingeHighCardHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		
		player3.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		player3.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player3.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Clubs));
		
		// In this case, all three hands match identically, and not one of
		// them loses to the others.
		Set<Player> handWinners = underTest.determineWinners();
		Assert.assertSame(3, handWinners.size());
		Assert.assertTrue(handWinners.contains(player1));
		Assert.assertTrue(handWinners.contains(player2));
		Assert.assertTrue(handWinners.contains(player3));
	}
	
	@Test
	public void testDetermineWinnersThreeVeryCloseHighCardHands() {
		player1.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Diamonds));
		player1.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Spades));
		player1.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Hearts));
		player1.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Clubs));
		player1.acceptDealtCard(new Card(Card.Kind.Three, Card.Suit.Hearts));
		
		player2.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Clubs));
		player2.acceptDealtCard(new Card(Card.Kind.Eight, Card.Suit.Diamonds));
		player2.acceptDealtCard(new Card(Card.Kind.Five, Card.Suit.Spades));
		
		player3.acceptDealtCard(new Card(Card.Kind.Ace, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Ten, Card.Suit.Hearts));
		player3.acceptDealtCard(new Card(Card.Kind.Nine, Card.Suit.Diamonds));
		player3.acceptDealtCard(new Card(Card.Kind.Seven, Card.Suit.Spades));
		player3.acceptDealtCard(new Card(Card.Kind.Six, Card.Suit.Clubs));
		
		// Player two is the winner
		Set<Player> handWinners = underTest.determineWinners();
		Assert.assertSame(1, handWinners.size());
		Assert.assertTrue(handWinners.contains(player2));
	}
}