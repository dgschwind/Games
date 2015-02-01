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
	
	private static final List<BoardSpace> gameBoard = GameBoardFactory.createGameBoard();
	private DeckOfCards<ActionCard> chanceDeck;
	private DeckOfCards<ActionCard> communityChestDeck;
	
	private final List<Player> players = new ArrayList<>();
	private final Map<Player, Integer> playerBoardPositions = new HashMap<>();

	public Monopoly() {
		super();
		
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
	
	/**
	 * This method handles the case when one Player owes money to another Player or the Bank.
	 * @param toMakePayment The Player that owes money.
	 * @param amountToBePaid The amount owed.
	 * @param toReceivePayment The Player that is to receive the amount owed. If null, this
	 * means the payment is being made to the bank, and we ignore cash being paid to the Bank
	 * as this software assumes the Bank has unlimited cash reserves.
	 */
	private void playerMakesPaymentToOpponent(Player toMakePayment, int amountToBePaid, Player toReceivePayment) {
		if (toMakePayment.makePayment(amountToBePaid)) {
			if (toReceivePayment != null) {
				toReceivePayment.receivePayment(amountToBePaid);
			}
		} else {
			// Player could not make payment. Bankrupt!
			toMakePayment.transferAssetsToPlayer(toReceivePayment);
			// TODO : Allow receiving Player the opportunity to immediately
			// lift the mortgage on any mortgaged Titles just acquired.
			toMakePayment.setBankrupt();
			System.out.println("!!! Bankrupt Alert Bankrupt Alert Bankrupt Alert Bankrupt Alert !!!");
			System.out.println("Player " + toMakePayment.getName() + " has been Bankrupted and is eliminated from the game!");
			System.out.println("!!! Bankrupt Alert Bankrupt Alert Bankrupt Alert Bankrupt Alert !!!");
		}
	}
	
	public void playerMakesPaymentToBank(Player toMakePayment, int amountToBePaid) {
		playerMakesPaymentToOpponent(toMakePayment, amountToBePaid, null);
	}
	
	public void payPlayerFromEachOpponent(Player playerToBePaid, int amountToBePaid) {
		for (Player opponent : determineSolventPlayers()) {
			if (opponent.equals(playerToBePaid)) {
				continue;
			}
			
			playerMakesPaymentToOpponent(opponent, amountToBePaid, playerToBePaid);
		}
	}
	
	public void playerPaysToEachOpponent(Player playerThatMustMakePayment, int amountToBePaid) {
		for (Player opponent : determineSolventPlayers()) {
			if (opponent.equals(playerThatMustMakePayment)) {
				continue;
			}
			
			playerMakesPaymentToOpponent(playerThatMustMakePayment, amountToBePaid, opponent);
		}
	}
	
	public void playerHasAcquiredGetOutOfJailFreeCard(Player player) {
		player.setHoldingGetOutOfJailFreeCard(true);
	}
	
	public void playerIsAssessedAmountPerBuilding(Player player, int costPerHouse, int costPerHotel) {
		int assessmentAmount = (player.getNumberHousesOnAllProperties() * costPerHouse) + 
				               (player.getNumberHotelsOnAllProperties() * costPerHotel);
		playerMakesPaymentToBank(player, assessmentAmount);
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
		boolean playerReachedOrPassedGo = false;
		
		switch (advanceToCard.getLocation()) {
			case Go:
				newPlayerBoardSpaceIndex = GameBoardFactory.GO_SPACE_INDEX;
				playerReachedOrPassedGo = true;
				break;
			case IllinoisAve:
				newPlayerBoardSpaceIndex = GameBoardFactory.ILLINOIS_AVENUE_SPACE_INDEX;
				playerReachedOrPassedGo = (newPlayerBoardSpaceIndex < currentPlayerBoardSpaceIndex);
				break;
			case StCharlesPlace:
				newPlayerBoardSpaceIndex = GameBoardFactory.ST_CHARLES_PLACE_SPACE_INDEX;
				playerReachedOrPassedGo = (newPlayerBoardSpaceIndex < currentPlayerBoardSpaceIndex);
				break;
			case NearestUtility:
				newPlayerBoardSpaceIndex = computeBoardSpaceIndexOfNearestUtility(currentPlayerBoardSpaceIndex);
				break;
			case NearestRailroad:
				newPlayerBoardSpaceIndex = computeBoardSpaceIndexOfNearestRailroad(currentPlayerBoardSpaceIndex);
				break;
			case GoBackThreeSpaces:
				newPlayerBoardSpaceIndex = currentPlayerBoardSpaceIndex - 3;
				if (newPlayerBoardSpaceIndex < 0) {
					newPlayerBoardSpaceIndex = GameBoardFactory.NUM_BOARD_SPACES_TOTAL + newPlayerBoardSpaceIndex;
				}
				break;
			case ReadingRailroad:
				newPlayerBoardSpaceIndex = GameBoardFactory.READING_RAILROAD_SPACE_INDEX;
				playerReachedOrPassedGo = (newPlayerBoardSpaceIndex < currentPlayerBoardSpaceIndex);
				break;
			case Boardwalk:
				newPlayerBoardSpaceIndex = GameBoardFactory.BOARDWALK_SPACE_INDEX;
				playerReachedOrPassedGo = (currentPlayerBoardSpaceIndex == GameBoardFactory.GO_SPACE_INDEX);
				break;
			case Jail:
			default:
				sendPlayerToJail(player);
				return;
		}
		
		if ((playerReachedOrPassedGo) && (advanceToCard.isHolderToReceive200DollarsIfReachingOrPassingGo())) {
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
		
		System.out.println("Player has been dealt " + chanceCard.getCardName() + " Chance card");
		
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
		
		System.out.println("Player has been dealt " + communityChestCard.getCardName() + " Community Chest card");
		
		takeAction(player, communityChestCard, playerDiceRollTotal);
	}
	
	private void sendPlayerToJail(Player player) {
		setPlayerBoardSpaceIndex(player, GameBoardFactory.JAIL_SPACE_INDEX);
		player.setInJail(true);
	}
	
	public void playerLandedOnGoToJailSpace(Player player) {
		sendPlayerToJail(player);
		System.out.println("Player " + player.getName() + " has been sent to Jail!");
	}
	
	public void playerLandedOnIncomeTaxSpace(Player player) {
		playerMakesPaymentToBank(player, 200);
	}
	
	public void playerLandedOnLuxuryTaxSpace(Player player) {
		playerMakesPaymentToBank(player, 100);
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
			Player spaceOwner = privateBoardSpace.getTitle().getOwner();
			if (spaceOwner == null) {
				// space can be purchased by Player
				boolean playerWouldLikeToPurchase = player.wouldYouLikeToPurchase(privateBoardSpace);
				if (playerWouldLikeToPurchase) {
					playerMakesPaymentToBank(player, privateBoardSpace.getPurchasePrice());
					privateBoardSpace.getTitle().setOwner(player);
					System.out.println("Player " + player.getName() + " has purchased " + privateBoardSpace.getName() + " for $" + privateBoardSpace.getPurchasePrice());
					if (privateBoardSpace.getTitle().isPartOfMonopoly()) {
						System.out.println("Player has monopolized set of Titles that contains " + privateBoardSpace.getName());
					}
				}
			} else {
				if (spaceOwner.equals(player)) {
					// The player owns the space, do nothing.
				} else {
					// The player owes rent to the owner.
					int rentAmountOwed = computeOwedRent(privateBoardSpace, spaceOwner, playerDiceRollTotal);
					playerMakesPaymentToOpponent(player, rentAmountOwed, spaceOwner);
					System.out.println("Player " + player.getName() + " paid rent in the amount of $" + rentAmountOwed + " to " + spaceOwner.getName());
				}
			}
		}
	}
	
	private DiceRollResult playerTakingTurn(Player player) {
		int playerCurrentPosition = getPlayerBoardSpaceIndex(player);
		DiceRollResult diceRollResult = player.rollDice();
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Player : " + player.getName() + " starting on space : " + gameBoard.get(playerCurrentPosition).getName() + " with $" + player.getBankAccountBalance() + " cash");
		System.out.print("Player has rolled a " + diceRollResult.getDiceRollTotal());
		System.out.print(diceRollResult.wereDoublesRolled() ? " with doubles" : "");
		if (player.isInJail()) {
			if (diceRollResult.wereDoublesRolled()) {
				// Doubles were rolled, Player is out of Jail!
				player.setInJail(false);
			} else {
				// Player's turn is over
				System.out.println(", but doubles were not rolled so " + player.getName() + " remains in Jail!");
				return diceRollResult;
			}
		}
		int diceRollTotal = diceRollResult.getDiceRollTotal();
		int newBoardPosition = playerCurrentPosition + diceRollTotal;
		if (newBoardPosition >= NUM_BOARD_SPACES_TOTAL) {
			// Player passed Go!
			newBoardPosition = (newBoardPosition % NUM_BOARD_SPACES_TOTAL);
			player.receivePayment(200);
		}
		playerCurrentPosition = newBoardPosition;
		setPlayerBoardSpaceIndex(player, playerCurrentPosition);
		
		// Now we know what Space the player has landed on, lets go about
		// determining all that can or must happen as a result.
		BoardSpace playerLandedOn = gameBoard.get(playerCurrentPosition);
		System.out.println(" and has landed on " + playerLandedOn.getName());
		playerHasLandedOnBoardSpace(player, playerLandedOn, diceRollTotal);
		System.out.println("Player finishes turn with $" + player.getBankAccountBalance() + " cash");
		return diceRollResult;
	}
	
	private int computeNumberTitlesAvailableForPurchase() {
		int result = 0;
		for (BoardSpace boardSpace : gameBoard) {
			if (boardSpace.canBePrivatelyHeld()) {
				Title title = ((PrivateBoardSpace<? extends Title>) boardSpace).getTitle();
				if (title.isBankOwned()) {
					result++;
				}
			}
		}
		return result;
	}
	
	public void playGame() {
		for (Player player : players) {
			// Each player starts at Go.
			setPlayerBoardSpaceIndex(player, GameBoardFactory.GO_SPACE_INDEX);
		}
		
		// Now they each take a turn in succession until only one player
		// is not yet bankrupt.
		boolean doWeHaveAWinner = false;
		while (doWeHaveAWinner == false) {
			for (Player player : players) {
				// Skip bankrupt players.
				if (player.isBankrupt()) {
					continue;
				}
				
				int numberTimesPlayerHasRolledDoublesThisTurn = 0;
				boolean playerStartedTurnInJail = player.isInJail();
				
				if (playerStartedTurnInJail && player.isHoldingGetOutOfJailFreeCard()) {
					boolean shouldUseGetOutOfJailFreeCard = player.getUseOfGetOutOfJailFreeCardPolicy().shouldUseGetOutOfJailFreeCard(player, computeNumberTitlesAvailableForPurchase());
					if (shouldUseGetOutOfJailFreeCard) {
						player.setHoldingGetOutOfJailFreeCard(false);
						playerStartedTurnInJail = false;
					}
				}
				
				DiceRollResult diceRollResult = playerTakingTurn(player);
				while (diceRollResult.wereDoublesRolled() && !playerStartedTurnInJail && !player.isInJail()) {
					// Player rolls again! However, if the players rolls doubles
					// three times in a row, they go directly to Jail!
					numberTimesPlayerHasRolledDoublesThisTurn++;
					
					if (numberTimesPlayerHasRolledDoublesThisTurn == 3) {
						sendPlayerToJail(player);
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
					doWeHaveAWinner = true;
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					System.out.println("Player : " + solventPlayers.get(0).getName() + " is declared the Winner!");
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					break;
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