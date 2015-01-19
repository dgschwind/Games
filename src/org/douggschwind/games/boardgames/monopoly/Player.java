package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.douggschwind.games.boardgames.monopoly.spaces.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.spaces.UtilityBoardSpace;

/**
 * An instance of this class houses the state of the game for any given Player of the game.
 * @author Doug Gschwind
 */
public class Player {
	
	public enum Avatar {
		Car,
		Dog,
		Hat,
		Iron,
		Thimble;
	}
	
	private final String name;
	private final Avatar avatar; // aka token
	private int bankAccountBalance;
	private final List<PropertyBoardSpace> ownedProperties = new ArrayList<>();
	private final List<RailroadBoardSpace> ownedRailroads = new ArrayList<>();
	private final List<UtilityBoardSpace> ownedUtilities = new ArrayList<>();
	
	public Player(String name, Avatar avatar) {
		this.name = name;
		this.avatar = avatar;
		bankAccountBalance = 1500;
	}
	
	public int rollDice() {
		return 2 + new Random().nextInt(6) + new Random().nextInt(6);
	}

	public int getBankAccountBalance() {
		return bankAccountBalance;
	}
	
	public void receivePayment(int creditedAmount) {
		bankAccountBalance += creditedAmount;
	}
	
	public void payBill(int debitedAmount) {
		bankAccountBalance -= debitedAmount;
	}

	public String getName() {
		return name;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public List<PropertyBoardSpace> getOwnedProperties() {
		return ownedProperties;
	}
	
	public void addOwnedProperty(PropertyBoardSpace property) {
		getOwnedProperties().add(property);
	}
	
	public boolean hasMonopoly(PropertyBoardSpace propertyBoardSpace) {
		return propertyBoardSpace.getMonopolyDefinition().containsMonopoly(getOwnedProperties());
	}

	public List<RailroadBoardSpace> getOwnedRailroads() {
		return ownedRailroads;
	}

	public List<UtilityBoardSpace> getOwnedUtilities() {
		return ownedUtilities;
	}
	
	public boolean isOwner(BoardSpace boardSpace) {
		return getOwnedProperties().contains(boardSpace) ||
				getOwnedRailroads().contains(boardSpace) ||
				getOwnedUtilities().contains(boardSpace);
	}
}