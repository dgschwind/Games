package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.actioncard.ActionCard;
import org.douggschwind.games.boardgames.monopoly.actioncard.DeckFactory;
import org.douggschwind.games.boardgames.monopoly.space.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PrivateBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.UtilityBoardSpace;
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
	
	private Player computeOwner(BoardSpace boardSpace) {
		for (Player player : players) {
			if (player.isOwner(boardSpace)) {
				return player;
			}
		}
		return null;
	}
	
	private int computeOwedRent(PrivateBoardSpace boardSpace, Player spaceOwner, int diceRollTotal) {
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
				// This space not capable of being bought or sold.
				playerLandedOn.takeAction(player);
			} else {
				// This space capable of being bought or sold.
				PrivateBoardSpace privateBoardSpace = (PrivateBoardSpace) playerLandedOn;
				Player spaceOwner = computeOwner(playerLandedOn);
				if (spaceOwner == null) {
					// space can be purchased by Player
					boolean playerWouldLikeToPurchase = player.wouldYouLikeToPurchase(privateBoardSpace);
					if (playerWouldLikeToPurchase) {
						player.payBill(privateBoardSpace.getCostToPurchase());
						player.acceptOwnership(privateBoardSpace);
					}
				} else {
					if (spaceOwner.equals(player)) {
						// The player owns the space, do nothing.
					} else {
						// The player owes rent to the owner.
						int playerOwedRent = computeOwedRent(privateBoardSpace, spaceOwner, diceRollTotal);
						if (player.canPayBillWithCash(playerOwedRent)) {
							player.payBill(playerOwedRent);
						} else {
							
						}
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