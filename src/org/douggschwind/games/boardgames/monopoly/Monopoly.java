package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.douggschwind.games.boardgames.monopoly.actioncard.ActionCard;
import org.douggschwind.games.boardgames.monopoly.actioncard.AdvanceToCard;
import org.douggschwind.games.boardgames.monopoly.space.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PrivateBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.UtilityBoardSpace;
import org.douggschwind.games.boardgames.monopoly.title.Title;

/**
 * This class supports the actual execution of the game including the setting up of the board,
 * allowing players to be added, the game to be played, and then a new game to be initiated.
 * @author Doug Gschwind
 */
public class Monopoly {
	
	private final GameBoard gameBoard = new GameBoard();
	private final List<Player> players = new ArrayList<>();

	public void addPlayer(Player toAdd) {
		if (toAdd != null) {
			players.add(toAdd);
			gameBoard.addPlayer(toAdd);
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
	
	public final void advancePlayerToBoardLocation(Player player, AdvanceToCard advanceToCard, int playerDiceRollTotal) {
		boolean playerReachedOrPassedGo = false;
		BoardSpace playerLandedOn;
		
		switch (advanceToCard.getLocation()) {
			case Go:
				playerLandedOn = gameBoard.getGoBoardSpace();
				playerReachedOrPassedGo = true;
				break;
			case IllinoisAve:
				playerLandedOn = gameBoard.getIllinoisAveBoardSpace();
				playerReachedOrPassedGo = gameBoard.didPlayerLandOnOrPassGo(gameBoard.getPlayerBoardSpace(player), playerLandedOn);
				break;
			case StCharlesPlace:
				playerLandedOn = gameBoard.getStCharlesPlaceBoardSpace();
				playerReachedOrPassedGo = gameBoard.didPlayerLandOnOrPassGo(gameBoard.getPlayerBoardSpace(player), playerLandedOn);
				break;
			case NearestUtility:
				playerLandedOn = gameBoard.getNearestUtility(player);
				break;
			case NearestRailroad:
				playerLandedOn = gameBoard.getNearestRailroad(player);
				break;
			case GoBackThreeSpaces:
				playerLandedOn = gameBoard.goBackThreeSpaces(player);
				break;
			case ReadingRailroad:
				playerLandedOn = gameBoard.getReadingRailroadBoardSpace();
				playerReachedOrPassedGo = gameBoard.didPlayerLandOnOrPassGo(gameBoard.getPlayerBoardSpace(player), playerLandedOn);
				break;
			case Boardwalk:
				playerLandedOn = gameBoard.getBoardwalkBoardSpace();
				break;
			case Jail:
			default:
				sendPlayerToJail(player);
				return;
		}
		
		if ((playerReachedOrPassedGo) && (advanceToCard.isHolderToReceive200DollarsIfReachingOrPassingGo())) {
			player.receivePayment(200);
		}
		
		playerHasLandedOnBoardSpace(player, playerLandedOn, playerDiceRollTotal);
	}
	
	private void takeAction(Player player, ActionCard actionCard, int playerDiceRollTotal) {
		actionCard.takeAction(this, player, playerDiceRollTotal);
	}
	
	public void playerLandedOnChanceSpace(Player player, int playerDiceRollTotal) {
		ActionCard chanceCard = gameBoard.dealChanceCard();
		System.out.println("Player has been dealt " + chanceCard.getCardName() + " Chance card");
		takeAction(player, chanceCard, playerDiceRollTotal);
	}
	
	public void playerLandedOnCommunityChestSpace(Player player, int playerDiceRollTotal) {
		ActionCard communityChestCard = gameBoard.dealCommunityChestCard();
		System.out.println("Player has been dealt " + communityChestCard.getCardName() + " Community Chest card");
		takeAction(player, communityChestCard, playerDiceRollTotal);
	}
	
	private void sendPlayerToJail(Player player) {
		gameBoard.sendPlayerToJail(player);
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
	
	private void playerHasLandedOnBoardSpace(Player player, BoardSpace landingSpace, int playerDiceRollTotal) {
		gameBoard.setPlayerBoardSpace(player, landingSpace);
		if (landingSpace.isPubliclyHeld()) {
			// This space not capable of being bought nor sold.
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
		DiceRollResult diceRollResult = player.rollDice();
		BoardSpace playerStartingBoardSpace = gameBoard.getPlayerBoardSpace(player);
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Player " + player.getName() + " starting on space : " + playerStartingBoardSpace.getName() + " with $" + player.getBankAccountBalance() + " cash");
		System.out.print("Player has rolled a " + diceRollResult.getDiceRollTotal());
		System.out.println(diceRollResult.wereDoublesRolled() ? " with doubles" : "");
		if (player.isInJail()) {
			if (diceRollResult.wereDoublesRolled()) {
				// Doubles were rolled, Player is out of Jail!
				player.setInJail(false);
			} else {
				player.incrementNumFailedAttemptsToGetOutOfJail();
				if (player.getNumFailedAttemptsToGetOutOfJail() < 3) {
					// Player's turn is over
					System.out.println(", but doubles were not rolled so " + player.getName() + " remains in Jail!");
					return diceRollResult;
				} else {
					// Otherwise, the Player must pay a fine of $50 and advance
					// the number of spots on the dice they have thrown.
					playerMakesPaymentToBank(player, 50);
					player.setInJail(false);
					System.out.println("Player " + player.getName() + " fined $50 for failing to get out of Jail after 3 attempts");
				}
			}
		}
		// Now we know what Space the player has landed on, lets go about
		// determining all that can or must happen as a result.
		BoardSpace playerLandedOn = gameBoard.findBoardSpaceWherePlayerHasLanded(player, diceRollResult);
		int diceRollTotal = diceRollResult.getDiceRollTotal();
		if (gameBoard.didPlayerLandOnOrPassGo(playerStartingBoardSpace, playerLandedOn)) {
			// Player landed on or passed Go!
			player.receivePayment(200);
			System.out.println("Player " + player.getName() + " has landed on or passed Go and has been paid $200");
		}
		
		System.out.println("Player has landed on " + playerLandedOn.getName());
		playerHasLandedOnBoardSpace(player, playerLandedOn, diceRollTotal);
		System.out.println("Player finishes turn with $" + player.getBankAccountBalance() + " cash");
		return diceRollResult;
	}
	
	private int computeNumberTitlesAvailableForPurchase() {
		int result = 0;
		for (BoardSpace boardSpace : gameBoard.getBoardSpaces()) {
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
		// Now they each take a turn in succession until only one player
		// is not yet bankrupt.
		boolean doWeHaveAWinner = false;
		while (doWeHaveAWinner == false) {
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
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
		// Assumes the same players will be playing again.
		gameBoard.reset();
	}
}