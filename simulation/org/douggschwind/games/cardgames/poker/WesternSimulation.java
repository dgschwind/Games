package org.douggschwind.games.cardgames.poker;

import java.util.Map;
import java.util.Set;

import org.douggschwind.games.cardgames.common.Card;
import org.douggschwind.games.cardgames.common.Player;
import org.douggschwind.games.cardgames.poker.common.HandStrength;

public class WesternSimulation {

	public static void main(String[] args) {
		Western underTest = new Western();
		underTest.newGame();
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		underTest.addPlayer(new Player());
		
		underTest.dealCardsToPlayers();
		
		Map<Player, HandStrength> playerHandsStrength = underTest.determinePlayerHandsStrength();
		Set<Player> winners = underTest.determineWinners();
		
		for (Player player : underTest.getPlayers()) {
			System.out.printf("Player %2d ", player.getPlayerNumber());
			System.out.print(" Hand :");
			for (Card card : player.getHand()) {
				System.out.printf(" %3s", card.toString());
			}
			System.out.print(" (" + playerHandsStrength.get(player).getClass().getSimpleName() + ") ");
			System.out.println(winners.contains(player) ? "Winner!" : "");
		}
	}
}