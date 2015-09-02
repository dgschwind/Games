package org.douggschwind.games.boardgames.monopoly;

import java.io.IOException;

import org.douggschwind.games.boardgames.monopoly.Player.Avatar;
import org.douggschwind.games.boardgames.monopoly.policy.AggressiveUseOfGetOutOfJailFreeCardPolicy;
import org.douggschwind.games.boardgames.monopoly.policy.AssetLiquidationPolicy;
import org.douggschwind.games.boardgames.monopoly.policy.ConservativeUseOfGetOutOfJailFreeCardPolicy;
import org.douggschwind.games.boardgames.monopoly.policy.HighestValuedAssetLiquidationPolicy;
import org.douggschwind.games.boardgames.monopoly.policy.LowestValuedAssetLiquidationPolicy;
import org.douggschwind.games.boardgames.monopoly.policy.UseOfGetOutOfJailFreeCardPolicy;


/**
 * A simulator for the game of Monopoly. Allows number of Players to be chosen,
 * then starts the game, informing an observer of the Players advancement and actions
 * taken per turn.
 * @author Doug Gschwind
 */
public class Simulator {
	
	private static int MINIMUM_NUMBER_OF_PLAYERS = 4;
	private static int MAXIMUM_NUMBER_OF_PLAYERS = 8;
	
	private static final String[] FAKE_PLAYER_NAMES = { "Warren Buffet",
		                                                "Elizabeth Warren",
		                                                "Donald Trump",
		                                                "Carly Fiorina",
		                                                "Mark Cuban",
		                                                "Meg Whitman",
		                                                "Barack H Obama",
		                                                "Michele Obama" };
	
	private Monopoly gameBeingPlayed;

	private Simulator(int numberOfPlayers) {
		gameBeingPlayed = new Monopoly();
		for (int i = 0;i < numberOfPlayers;i++) {
			boolean evenNumberedIndex = ((i % 2) == 0);
			UseOfGetOutOfJailFreeCardPolicy useOfGetOutOfJailFreeCardPolicy = evenNumberedIndex ? new AggressiveUseOfGetOutOfJailFreeCardPolicy() : new ConservativeUseOfGetOutOfJailFreeCardPolicy();
			AssetLiquidationPolicy assetLiquidationPolicy = evenNumberedIndex ? new HighestValuedAssetLiquidationPolicy() : new LowestValuedAssetLiquidationPolicy();
			gameBeingPlayed.addPlayer(new Player(FAKE_PLAYER_NAMES[i], Avatar.values()[i], useOfGetOutOfJailFreeCardPolicy, assetLiquidationPolicy));
		}
	}
	
	private void playGame() {
		gameBeingPlayed.playGame();
	}
	
	private static int askUserTheDesiredNumberOfPlayers()
	throws IOException {
		System.out.println("How many players? Enter a number between " + MINIMUM_NUMBER_OF_PLAYERS + " and " + MAXIMUM_NUMBER_OF_PLAYERS + " : ");
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
			int desiredNumberOfPlayers = 0;
			
			while (true) {
				desiredNumberOfPlayers = askUserTheDesiredNumberOfPlayers();
				break;
			}
			
			Simulator simulator = new Simulator(desiredNumberOfPlayers);
			simulator.playGame();
			System.out.println("================= End of Game =======================");
		}
	}
}