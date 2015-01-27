package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.actioncard.ActionCard;
import org.douggschwind.games.boardgames.monopoly.actioncard.AdvanceToCard;
import org.douggschwind.games.boardgames.monopoly.actioncard.DeckFactory;
import org.douggschwind.games.boardgames.monopoly.space.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PrivateBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.UtilityBoardSpace;
import org.douggschwind.games.boardgames.monopoly.title.Title;
import org.douggschwind.games.common.DeckOfCards;

/**
 * This class supports the actual execution of the game including the setting up of the board,
 * allowing players to be added, the game to be played, and then a new game to be initiated.
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
		
		gameBoard = GameBoardFactory.createGameBoard();
		chanceDeck = DeckFactory.createChanceDeck();
		chanceDeck.shuffle();
		communityChestDeck = DeckFactory.createCommunityChestDeck();
		communityChestDeck.shuffle();
	}
	
	public void addPlayer(Player toAdd) {
		if (toAdd != null) {
			players.add(toAdd);
			playerBoardPositions.put(toAdd, 0); // Everyone starts at Go
		}
	}
	
	private Player getOwner(BoardSpace boardSpace) {
		Title title = ((PrivateBoardSpace<? extends Title>) boardSpace).getTitle();
		return title.getOwner();
	}
	
	private int computeOwedRent(PrivateBoardSpace<? extends Title> boardSpace, Player spaceOwner, int diceRollTotal) {
		if (boardSpace.isProperty()) {
			PropertyBoardSpace propertyBoardSpace = (PropertyBoardSpace) boardSpace;
			return propertyBoardSpace.computeRent(spaceOwner);
		} else if (boardSpace.isRailroad()) {
			return ((RailroadBoardSpace) boardSpace).computeRent(spaceOwner.getNumberOwnedRailroads());
		} else {
			// It is a Utility
			return ((UtilityBoardSpace) boardSpace).computeRent(spaceOwner.getNumberOwnedUtilities(), diceRollTotal);
		}
	}
	
	public void payPlayerFromBank(Player player, int amountToBePaid) {
		player.receivePayment(amountToBePaid);
	}
	
	public void playerPaysToBank(Player player, int amountToBePaid) {
		player.makePayment(amountToBePaid);
	}
	
	public void payPlayerFromEachOpponent(Player playerToBePaid, int amountToBePaid) {
		for (Player opponent : determineSolventPlayers()) {
			if (opponent.equals(playerToBePaid)) {
				continue;
			}
			
			opponent.makePayment(amountToBePaid);
			playerToBePaid.receivePayment(amountToBePaid);
		}
	}
	
	public void playerPaysToEachOpponent(Player playerToPay, int amountToBePaid) {
		for (Player opponent : determineSolventPlayers()) {
			if (opponent.equals(playerToPay)) {
				continue;
			}
			
			playerToPay.makePayment(amountToBePaid);
			opponent.receivePayment(amountToBePaid);
		}
	}
	
	public void playerHasAcquiredGetOutOfJailFreeCard(Player player) {
		player.setHoldingGetOutOfJailFreeCard(true);
	}
	
	private void playerHasUsedGetOutOfJailFreeCard(Player player) {
		player.setHoldingGetOutOfJailFreeCard(false);
	}
	
	public void playerIsAssessedAmountPerBuilding(Player player, int costPerHouse, int costPerHotel) {
		int assessmentAmount = (player.getNumberHousesOnAllProperties() * costPerHouse) + 
				               (player.getNumberHotelsOnAllProperties() * costPerHotel);
		player.makePayment(assessmentAmount);
	}
	
	private int computeBoardSpaceIndexOfNearestUtility(int boardSpaceIndex) {
		return (boardSpaceIndex < GameBoardFactory.FREE_PARKING_SPACE_INDEX) ? GameBoardFactory.ELECTRIC_COMPANY_SPACE_INDEX : GameBoardFactory.WATER_WORKS_SPACE_INDEX;
	}
	
	private int computeBoardSpaceIndexOfNearestRailroad(int boardSpaceIndex) {
		if (boardSpaceIndex < GameBoardFactory.JAIL_SPACE_INDEX) {
			return GameBoardFactory.READING_RAILROAD_SPACE_INDEX;
		} else if (boardSpaceIndex < GameBoardFactory.FREE_PARKING_SPACE_INDEX) {
			return GameBoardFactory.PENNSYLVANIA_RAILROAD_SPACE_INDEX;
		} else if (boardSpaceIndex < GameBoardFactory.GO_DIRECTLY_TO_JAIL_SPACE_INDEX) {
			return GameBoardFactory.B_O_RAILROAD_SPACE_INDEX;
		} else {
			return GameBoardFactory.SHORT_LINE_RAILROAD_SPACE_INDEX;
		}
	}
	
	public final void advancePlayerToBoardLocation(Player player, AdvanceToCard advanceToCard, int playerDiceRollTotal) {
		int currentPlayerBoardSpaceIndex = getPlayerBoardSpaceIndex(player);
		int newPlayerBoardSpaceIndex;
		boolean playerPassedGo = false;
		
		switch (advanceToCard.getLocation()) {
			case Go:
				newPlayerBoardSpaceIndex = GameBoardFactory.GO_SPACE_INDEX;
				break;
			case IllinoisAve:
				newPlayerBoardSpaceIndex = GameBoardFactory.ILLINOIS_AVENUE_SPACE_INDEX;
				playerPassedGo = (newPlayerBoardSpaceIndex < currentPlayerBoardSpaceIndex);
				break;
			case StCharlesPlace:
				newPlayerBoardSpaceIndex = GameBoardFactory.ST_CHARLES_PLACE_SPACE_INDEX;
				playerPassedGo = (newPlayerBoardSpaceIndex < currentPlayerBoardSpaceIndex);
				break;
			case NearestUtility:
				newPlayerBoardSpaceIndex = computeBoardSpaceIndexOfNearestUtility(currentPlayerBoardSpaceIndex);
				playerPassedGo = (currentPlayerBoardSpaceIndex == GameBoardFactory.GO_SPACE_INDEX);
				break;
			case NearestRailroad:
				newPlayerBoardSpaceIndex = computeBoardSpaceIndexOfNearestRailroad(currentPlayerBoardSpaceIndex);
				playerPassedGo = (currentPlayerBoardSpaceIndex == GameBoardFactory.GO_SPACE_INDEX);
				break;
			case GoBackThreeSpaces:
				newPlayerBoardSpaceIndex = currentPlayerBoardSpaceIndex - 3;
				if (newPlayerBoardSpaceIndex < 0) {
					newPlayerBoardSpaceIndex = GameBoardFactory.NUM_BOARD_SPACES_TOTAL + newPlayerBoardSpaceIndex;
				}
				break;
			case ReadingRailroad:
				newPlayerBoardSpaceIndex = GameBoardFactory.READING_RAILROAD_SPACE_INDEX;
				playerPassedGo = (newPlayerBoardSpaceIndex < currentPlayerBoardSpaceIndex);
				break;
			case Boardwalk:
				newPlayerBoardSpaceIndex = GameBoardFactory.BOARDWALK_SPACE_INDEX;
				playerPassedGo = (currentPlayerBoardSpaceIndex == GameBoardFactory.GO_SPACE_INDEX);
				break;
			case Jail:
			default:
				newPlayerBoardSpaceIndex = GameBoardFactory.JAIL_SPACE_INDEX;
				break;
		}
		
		if ((playerPassedGo) && (advanceToCard.isHolderToReceive200DollarsIfPassingGo())) {
			player.receivePayment(200);
		}
		
		setPlayerBoardSpaceIndex(player, newPlayerBoardSpaceIndex);
		BoardSpace playerLandedOn = gameBoard.get(newPlayerBoardSpaceIndex);
		playerHasLandedOnBoardSpace(player, playerLandedOn, playerDiceRollTotal);
	}
	
	private void takeAction(Player player, ActionCard actionCard, int playerDiceRollTotal) {
		actionCard.takeAction(this, player, playerDiceRollTotal);
	}
	
	public void playerLandedOnChanceSpace(Player player, int playerDiceRollTotal) {
		ActionCard chanceCard = null;
		try {
			chanceCard = chanceDeck.dealCard();
		} catch (IllegalStateException ignored) {
			chanceDeck.shuffle();
			chanceCard = chanceDeck.dealCard();
		}
		
		if (chanceCard == null) {
			// Oops! Should never get here.
		}
		
		takeAction(player, chanceCard, playerDiceRollTotal);
	}
	
	public void playerLandedOnCommunityChestSpace(Player player, int playerDiceRollTotal) {
		ActionCard communityChestCard = null;
		try {
			communityChestCard = communityChestDeck.dealCard();
		} catch (IllegalStateException ignored) {
			communityChestDeck.shuffle();
			communityChestCard = communityChestDeck.dealCard();
		}
		
		if (communityChestCard == null) {
			// Oops! Should never get here.
		}
		
		takeAction(player, communityChestCard, playerDiceRollTotal);
	}
	
	public void playerLandedOnGoToJailSpace(Player player) {
		
	}
	
	public void playerLandedOnIncomeTaxSpace(Player player) {
		player.makePayment(200);
	}
	
	public void playerLandedOnLuxuryTaxSpace(Player player) {
		player.makePayment(100);
	}
	
	/**
	 * Determines the Player(s) that are not yet bankrupt.
	 * @return Will be non-null and contain at least one element.
	 */
	private List<Player> determineSolventPlayers() {
		List<Player> result = new ArrayList<>(players);
		for (Iterator<Player> iter = result.iterator();iter.hasNext();) {
			Player player = iter.next();
			if (player.isBankrupt()) {
				iter.remove();
			}
		}
		return result;
	}
	
	private int getPlayerBoardSpaceIndex(Player player) {
		return playerBoardPositions.get(player);
	}
	
	private void setPlayerBoardSpaceIndex(Player player, int newIndex) {
		playerBoardPositions.put(player, newIndex);
	}
	
	private void playerHasLandedOnBoardSpace(Player player, BoardSpace landingSpace, int playerDiceRollTotal) {
		if (landingSpace.isPubliclyHeld()) {
			// This space not capable of being bought or sold.
			landingSpace.takeAction(this, player, playerDiceRollTotal);
		} else {
			// This space capable of being bought or sold.
			PrivateBoardSpace<? extends Title> privateBoardSpace = (PrivateBoardSpace<? extends Title>) landingSpace;
			Player spaceOwner = getOwner(landingSpace);
			if (spaceOwner == null) {
				// space can be purchased by Player
				boolean playerWouldLikeToPurchase = player.wouldYouLikeToPurchase(privateBoardSpace);
				if (playerWouldLikeToPurchase) {
					player.makePayment(privateBoardSpace.getPurchasePrice());
					player.acceptOwnership(privateBoardSpace);
				}
			} else {
				if (spaceOwner.equals(player)) {
					// The player owns the space, do nothing.
				} else {
					// The player owes rent to the owner.
					int playerOwedRent = computeOwedRent(privateBoardSpace, spaceOwner, playerDiceRollTotal);
					if (player.canPayBillWithCash(playerOwedRent)) {
						player.makePayment(playerOwedRent);
					} else {
						
					}
				}
			}
		}
	}
	
	private DiceRollResult playerTakingTurn(Player player) {
		int playerCurrentPosition = getPlayerBoardSpaceIndex(player);
		DiceRollResult diceRollResult = player.rollDice();
		int diceRollTotal = diceRollResult.getDiceRollTotal();
		int newBoardPosition = playerCurrentPosition + diceRollTotal;
		if (newBoardPosition > NUM_BOARD_SPACES_TOTAL) {
			// Player passed Go!
			newBoardPosition = (newBoardPosition % NUM_BOARD_SPACES_TOTAL);
			player.receivePayment(200);
		}
		playerCurrentPosition = newBoardPosition;
		setPlayerBoardSpaceIndex(player, playerCurrentPosition);
		
		// Now we know what Space the player has landed on, lets go about
		// determining all that can or must happen as a result.
		BoardSpace playerLandedOn = gameBoard.get(playerCurrentPosition);
		playerHasLandedOnBoardSpace(player, playerLandedOn, diceRollTotal);
		return diceRollResult;
	}
	
	public void playGame() {
		for (Player player : players) {
			// Each player starts at Go.
			setPlayerBoardSpaceIndex(player, GameBoardFactory.GO_SPACE_INDEX);
		}
		
		final BoardSpace jailBoardSpace = gameBoard.get(GameBoardFactory.JAIL_SPACE_INDEX);
		
		// Now they each take a turn in succession until only one player
		// is not yet bankrupt.
		for (Player player : players) {
			// Skip bankrupt players.
			if (player.isBankrupt()) {
				continue;
			}
			
			int numberTimesPlayerHasRolledDoublesThisTurn = 0;
			
			DiceRollResult diceRollResult = playerTakingTurn(player);
			while (diceRollResult.wereDoublesRolled()) {
				// Player rolls again! However, if the players rolls doubles
				// three times in a row, they go directly to Jail!
				numberTimesPlayerHasRolledDoublesThisTurn++;
				
				if (numberTimesPlayerHasRolledDoublesThisTurn == 3) {
					playerHasLandedOnBoardSpace(player, jailBoardSpace, 0);
					break;
				} else {
					diceRollResult = playerTakingTurn(player);
				}
			}
			
			// After each player's turn, if that Player went bankrupt as a result of their
			// turn, check to see if there is only one Player left that is not bankrupt.
			// In that case, that remaining Player is the winner of the game!
			List<Player> solventPlayers = determineSolventPlayers();
			if (solventPlayers.size() == 1) {
				// We have a winner!
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