package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.actioncards.ActionCard;
import org.douggschwind.games.boardgames.monopoly.actioncards.DeckFactory;
import org.douggschwind.games.boardgames.monopoly.spaces.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.CommunityChestSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.GoSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.IncomeTaxSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.LuxuryTaxSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.MonopolyDefinition;
import org.douggschwind.games.boardgames.monopoly.spaces.MonopolyDefinition.ColorDesignator;
import org.douggschwind.games.boardgames.monopoly.spaces.PropertyBoardSpace;
import org.douggschwind.games.common.DeckOfCards;

/**
 * @author Doug Gschwind
 */
public class Monopoly {
	
	private static final int NUM_BOARD_SPACES_TOTAL = 40;
	
	private final List<BoardSpace> gameBoard;
	private DeckOfCards<ActionCard> chanceDeck;
	private DeckOfCards<ActionCard> communityChestDeck;
	
	private final List<Player> players = new ArrayList<>();
	private final Map<Player, Integer> playerBoardPositions = new HashMap<>();

	public Monopoly() {
		super();
		
		gameBoard = createGameBoard();
		chanceDeck = DeckFactory.createChanceDeck();
		chanceDeck.shuffle();
		communityChestDeck = DeckFactory.createCommunityChestDeck();
		communityChestDeck.shuffle();
	}
	
	private List<BoardSpace> createGameBoard() {
		MonopolyDefinition brownMonopolyDefinition = new MonopolyDefinition(ColorDesignator.Brown);
		MonopolyDefinition blueMonopolyDefinition = new MonopolyDefinition(ColorDesignator.Blue);
		List<BoardSpace> result = new ArrayList<>();
		result.add(new GoSpace());
		result.add(new PropertyBoardSpace(brownMonopolyDefinition, "Mediterranean Avenue", 60, 50, 50, 2, 4, 10, 30, 90, 160, 250, 30));
		result.add(new CommunityChestSpace());
		result.add(new PropertyBoardSpace(brownMonopolyDefinition, "Baltic Avenue", 60, 50, 50, 4, 8, 20, 60, 180, 320, 450, 30));
		result.add(new IncomeTaxSpace());
		result.add(new PropertyBoardSpace(blueMonopolyDefinition, "Park Place", 350, 200, 200, 35, 70, 175, 500, 1100, 1300, 1500, 175));
		result.add(new LuxuryTaxSpace());
		result.add(new PropertyBoardSpace(blueMonopolyDefinition, "Boardwalk", 400, 200, 200, 50, 100, 200, 600, 1400, 1700, 2000, 200));
		return result;
	}
	
	public void addPlayer(Player toAdd) {
		if (toAdd != null) {
			players.add(toAdd);
			playerBoardPositions.put(toAdd, 0); // Everyone starts at Go
		}
	}
	
	public void playGame() {
		for (Player player : players) {
			Integer playerCurrentPosition = playerBoardPositions.get(player);
			int diceRollTotal = player.rollDice();
			if (playerCurrentPosition == null) {
				playerBoardPositions.put(player, diceRollTotal);
				playerCurrentPosition = diceRollTotal;
			} else {
				int newBoardPosition = playerCurrentPosition + diceRollTotal;
				if (newBoardPosition > NUM_BOARD_SPACES_TOTAL) {
					// Player passed Go!
					newBoardPosition = (newBoardPosition % NUM_BOARD_SPACES_TOTAL);
					player.receivePayment(200);
				}
				playerCurrentPosition = newBoardPosition;
			}
			playerBoardPositions.put(player, playerCurrentPosition);
			
			// Now we know what Space the player has landed on, lets go about
			// determining all that can or must happen as a result.
			BoardSpace playerLandedOn = gameBoard.get(playerCurrentPosition);
			playerLandedOn.takeAction(player);
		}
	}
	
	public void newGame() {
		players.clear();
		playerBoardPositions.clear();
		chanceDeck.shuffle();
		communityChestDeck.shuffle();
	}
}