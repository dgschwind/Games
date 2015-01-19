package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.actioncards.ActionCard;
import org.douggschwind.games.boardgames.monopoly.actioncards.DeckFactory;
import org.douggschwind.games.boardgames.monopoly.spaces.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.ChanceSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.CommunityChestSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.FreeParkingSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.GoDirectlyToJailSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.GoSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.IncomeTaxSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.LuxuryTaxSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.MonopolyDefinition;
import org.douggschwind.games.boardgames.monopoly.spaces.MonopolyDefinition.ColorDesignator;
import org.douggschwind.games.boardgames.monopoly.spaces.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.UtilityBoardSpace;
import org.douggschwind.games.common.DeckOfCards;

/**
 * This class supports the actual execution of the game including the setting up of the board,
 * allowing players to be added, the game to be played, and then a new game to be initiated.
 * @author Doug Gschwind
 */
public class Monopoly {
	
	private static final int NUM_BOARD_SPACES_TOTAL = 40;
	
	private static final MonopolyDefinition BROWN_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Brown);
	private static final MonopolyDefinition LIGHT_BLUE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.LightBlue);
	private static final MonopolyDefinition PURPLE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Purple);
	private static final MonopolyDefinition ORANGE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Orange);
	private static final MonopolyDefinition RED_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Red);
	private static final MonopolyDefinition YELLOW_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Yellow);
	private static final MonopolyDefinition GREEN_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Green);
	private static final MonopolyDefinition BLUE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Blue);
	
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
		List<BoardSpace> result = new ArrayList<>(NUM_BOARD_SPACES_TOTAL);
		result.add(new GoSpace());
		result.add(new PropertyBoardSpace(BROWN_MONOPOLY_DEFINITION, "Mediterranean Avenue", 60, 50, 2, 4, 10, 30, 90, 160, 250));
		result.add(new CommunityChestSpace());
		result.add(new PropertyBoardSpace(BROWN_MONOPOLY_DEFINITION, "Baltic Avenue", 60, 50, 4, 8, 20, 60, 180, 320, 450));
		result.add(new IncomeTaxSpace());
		result.add(new RailroadBoardSpace("Reading", 200, 100));
		result.add(new PropertyBoardSpace(LIGHT_BLUE_MONOPOLY_DEFINITION, "Oriental Avenue", 100, 50, 6, 12, 30, 90, 270, 400, 550));
		result.add(new ChanceSpace());
		result.add(new PropertyBoardSpace(LIGHT_BLUE_MONOPOLY_DEFINITION, "Vermont Avenue", 100, 50, 6, 12, 30, 90, 270, 400, 550));
		result.add(new PropertyBoardSpace(LIGHT_BLUE_MONOPOLY_DEFINITION, "Conneticut Avenue", 120, 50, 8, 16, 40, 100, 300, 450, 600));
		// Jail goes here...
		result.add(new PropertyBoardSpace(PURPLE_MONOPOLY_DEFINITION, "St Charles Place", 140, 100, 10, 20, 50, 150, 450, 625, 750));
		result.add(new UtilityBoardSpace("Electric Company", 150, 75));
		result.add(new PropertyBoardSpace(PURPLE_MONOPOLY_DEFINITION, "States Avenue", 140, 100, 10, 20, 50, 150, 450, 625, 750));
		result.add(new PropertyBoardSpace(PURPLE_MONOPOLY_DEFINITION, "Virginia Avenue", 160, 100, 12, 24, 60, 180, 500, 700, 900));
		result.add(new RailroadBoardSpace("Pennsylvania", 200, 100));
		result.add(new PropertyBoardSpace(ORANGE_MONOPOLY_DEFINITION, "St James Place", 180, 100, 14, 28, 70, 200, 550, 750, 950));
		result.add(new CommunityChestSpace());
		result.add(new PropertyBoardSpace(ORANGE_MONOPOLY_DEFINITION, "Tennesee Avenue", 180, 100, 14, 28, 70, 200, 550, 750, 950));
		result.add(new PropertyBoardSpace(ORANGE_MONOPOLY_DEFINITION, "New York Avenue", 200, 100, 16, 32, 80, 220, 600, 800, 1000));
		result.add(new FreeParkingSpace());
		result.add(new PropertyBoardSpace(RED_MONOPOLY_DEFINITION, "Kentucky Avenue", 220, 150, 18, 36, 90, 250, 700, 875, 1050));
		result.add(new ChanceSpace());
		result.add(new PropertyBoardSpace(RED_MONOPOLY_DEFINITION, "Indiana Avenue", 220, 150, 18, 36, 90, 250, 700, 875, 1050));
		result.add(new PropertyBoardSpace(RED_MONOPOLY_DEFINITION, "Illinois Avenue", 250, 150, 20, 40, 100, 300, 750, 925, 1100));
		result.add(new RailroadBoardSpace("B & O", 200, 100));
		result.add(new PropertyBoardSpace(YELLOW_MONOPOLY_DEFINITION, "Atlantic Avenue", 260, 150, 22, 44, 110, 330, 800, 975, 1150));
		result.add(new PropertyBoardSpace(YELLOW_MONOPOLY_DEFINITION, "Ventnor Avenue", 260, 150, 22, 44, 110, 330, 800, 975, 1150));
		result.add(new UtilityBoardSpace("Water Works", 150, 75));
		result.add(new PropertyBoardSpace(YELLOW_MONOPOLY_DEFINITION, "Marvin Gardens", 280, 150, 24, 48, 120, 360, 850, 1025, 1200));
		result.add(new GoDirectlyToJailSpace());
		result.add(new PropertyBoardSpace(GREEN_MONOPOLY_DEFINITION, "Pacific Avenue", 300, 200, 26, 52, 130, 390, 900, 1100, 1275));
		result.add(new PropertyBoardSpace(GREEN_MONOPOLY_DEFINITION, "North Carolina Avenue", 300, 200, 26, 52, 130, 390, 900, 1100, 1275));
		result.add(new CommunityChestSpace());
		result.add(new PropertyBoardSpace(GREEN_MONOPOLY_DEFINITION, "Pennsylvania Avenue", 320, 200, 28, 56, 150, 450, 1000, 1200, 1400));
		result.add(new RailroadBoardSpace("Short Line", 200, 100));
		result.add(new ChanceSpace());
		result.add(new PropertyBoardSpace(BLUE_MONOPOLY_DEFINITION, "Park Place", 350, 200, 35, 70, 175, 500, 1100, 1300, 1500));
		result.add(new LuxuryTaxSpace());
		result.add(new PropertyBoardSpace(BLUE_MONOPOLY_DEFINITION, "Boardwalk", 400, 200, 50, 100, 200, 600, 1400, 1700, 2000));
		return result;
	}
	
	public void addPlayer(Player toAdd) {
		if (toAdd != null) {
			players.add(toAdd);
			playerBoardPositions.put(toAdd, 0); // Everyone starts at Go
		}
	}
	
	private Player computeOwner(BoardSpace boardSpace) {
		for (Player player : players) {
			if (player.isOwner(boardSpace)) {
				return player;
			}
		}
		return null;
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
			if (playerLandedOn.isPubliclyHeld()) {
				playerLandedOn.takeAction(player);
			} else {
				Player spaceOwner = computeOwner(playerLandedOn);
				if (spaceOwner == null) {
					// space can be purchased.
				} else {
					if (spaceOwner.equals(player)) {
						// The player owns the space, do nothing.
					} else {
						// The player owes rent to the owner.
					}
				}
			}
		}
	}
	
	public void newGame() {
		players.clear();
		playerBoardPositions.clear();
		chanceDeck.shuffle();
		communityChestDeck.shuffle();
	}
}