package org.douggschwind.games.cardgames.poker;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.douggschwind.games.cardgames.common.Player;
import org.douggschwind.games.cardgames.common.StandardDeckCardGame;
import org.douggschwind.games.cardgames.poker.common.HandStrength;
import org.douggschwind.games.cardgames.wildcardpoker.DeucesWild;
import org.douggschwind.games.cardgames.wildcardpoker.OneEyedJacksWild;

/**
 * A simple Java application for allowing an end user to simulate any of the supported
 * games with a variable number of players.
 * @author Doug Gschwind
 */
public class Simulator {
	
	private static final String WESTERN_GAME_IDENTIFIER = "w";
	private static final String DEUCES_WILD_GAME_IDENTIFIER = "dw";
	private static final String ONE_EYED_JACKS_IDENTIFIER = "oe";
	private static final String EXIT_IDENTIFIER = "E";
	
	private static int MINIMUM_NUMBER_OF_PLAYERS = 4;
	private static int MAXIMUM_NUMBER_OF_PLAYERS = 10;
	
	private final StandardDeckCardGame gameToPlay;
	
	public Simulator(StandardDeckCardGame cardGame, int numberOfPlayers) {
		gameToPlay = cardGame;
		gameToPlay.newGame();
		for (int i = 0;i < numberOfPlayers;i++) {
			gameToPlay.addPlayer(new Player());
		}
	}
	
	private void playGame() {
		gameToPlay.dealCardsToPlayers();
		
		Map<Player, HandStrength> playerHandsStrength = gameToPlay.determinePlayerHandsStrength();
		Set<Player> winners = gameToPlay.determineWinners();
		
		Consumer<? super Player> reportPlayerHand = player -> {
			System.out.printf("Player %2d ", player.getPlayerNumber());
			System.out.print(" Hand :");
			player.getHand().stream().forEach(card -> System.out.printf(" %3s", card.toString()));
			System.out.print(" (" + playerHandsStrength.get(player).getClass().getSimpleName() + ") ");
			System.out.println(winners.contains(player) ? "Winner!" : "");
		};
		
		gameToPlay.getPlayers().stream().forEach(reportPlayerHand);
	}
	
	private static StandardDeckCardGame askUserWhichGameTheyWishToSimulate()
	throws IOException {
		System.out.println("Which game would you like to simulate?");
		System.out.println("\tWestern (" + WESTERN_GAME_IDENTIFIER + ")");
		System.out.println("\tDeuces Wild (" + DEUCES_WILD_GAME_IDENTIFIER + ")");
		System.out.println("\tOne Eyed Jacks Wild (" + ONE_EYED_JACKS_IDENTIFIER + ")");
		System.out.println("\tExit (" + EXIT_IDENTIFIER + ")");
		byte[] endUserProvidedValue = new byte[80];
		System.in.read(endUserProvidedValue);
		
		String desiredGameIdentifier = new String(endUserProvidedValue).trim();
		if (WESTERN_GAME_IDENTIFIER.equalsIgnoreCase(desiredGameIdentifier)) {
			return new Western();
		} else if (DEUCES_WILD_GAME_IDENTIFIER.equalsIgnoreCase(desiredGameIdentifier)) {
			return new DeucesWild();
		} else if (ONE_EYED_JACKS_IDENTIFIER.equalsIgnoreCase(desiredGameIdentifier)) {
			return new OneEyedJacksWild();
		} else if (EXIT_IDENTIFIER.equals(desiredGameIdentifier)) {
			System.out.println("Exiting...");
			System.exit(0);
		}
		
		return null;
	}
	
	private static int askUserTheDesiredNumberOfPlayers()
	throws IOException {
		System.out.println("How many players? Enter a number between 4 and 10 : ");
		byte[] endUserProvidedValue = new byte[80];
		System.in.read(endUserProvidedValue);
		
		try {
			Integer desiredNumberOfPlayersInt = Integer.valueOf(new String(endUserProvidedValue).trim());
			if (desiredNumberOfPlayersInt == null) {
				return askUserTheDesiredNumberOfPlayers();
			}
			
			int desiredNumberOfPlayers = desiredNumberOfPlayersInt.intValue();
			
			if ((desiredNumberOfPlayers >= MINIMUM_NUMBER_OF_PLAYERS) &&
				(desiredNumberOfPlayers <= MAXIMUM_NUMBER_OF_PLAYERS)) {
				return desiredNumberOfPlayers;
			}
		} catch (NumberFormatException ignored) {
		}
		
		return askUserTheDesiredNumberOfPlayers();
	}

	public static void main(String[] args) throws Exception {
		while (true) {
			StandardDeckCardGame instantiatedGame = null;
			int desiredNumberOfPlayers = 0;
			
			while (true) {
				try {
					instantiatedGame = askUserWhichGameTheyWishToSimulate();
				} catch (Throwable ignored) {
				}
				
				if (instantiatedGame == null) {
					continue;
				}
				
				desiredNumberOfPlayers = askUserTheDesiredNumberOfPlayers();
				break;
			}
			
			Simulator simulator = new Simulator(instantiatedGame, desiredNumberOfPlayers);
			simulator.playGame();
			System.out.println("================= End of Game =======================");
		}
	}
}