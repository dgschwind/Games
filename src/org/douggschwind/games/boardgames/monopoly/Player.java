package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.douggschwind.games.boardgames.monopoly.space.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PrivateBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.UtilityBoardSpace;
import org.douggschwind.games.boardgames.monopoly.titledeed.TitleDeed;

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
	private final Map<TitleDeed, BuildingSummary> ownedPropertiesMap = new HashMap<>();
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
	
	public boolean canPayBillWithCash(int billAmount) {
		return getBankAccountBalance() > billAmount;
	}
	
	public void payBill(int billedAmount) {
		bankAccountBalance -= billedAmount;
		//TODO. May need to sell off properties to raise enough funds
		// to not be bankrupted. If that cannot be done, may make
		// sense to throw BankruptedException or the like.
	}

	public String getName() {
		return name;
	}

	public Avatar getAvatar() {
		return avatar;
	}
	
	public boolean wouldYouLikeToPurchase(PrivateBoardSpace privateBoardSpace) {
		// TODO. Add more sophistication here later.
		return privateBoardSpace.getCostToPurchase() < getBankAccountBalance();
	}
	
	public void acceptOwnership(PrivateBoardSpace privateBoardSpace) {
		if (privateBoardSpace.isProperty()) {
			addOwnedProperty(((PropertyBoardSpace) privateBoardSpace).getTitleDeed());
		} else if (privateBoardSpace.isRailroad()) {
			getOwnedRailroads().add((RailroadBoardSpace) privateBoardSpace);
		} else if (privateBoardSpace.isUtility()) {
			getOwnedUtilities().add((UtilityBoardSpace) privateBoardSpace);
		}
	}

	public Collection<TitleDeed> getOwnedProperties() {
		return ownedPropertiesMap.keySet();
	}
	
	private void addOwnedProperty(TitleDeed property) {
		ownedPropertiesMap.put(property, new BuildingSummary());
	}
	
	public int getNumberHousesOnProperty(TitleDeed titleDeed) {
		BuildingSummary propertyBuildingSummary = ownedPropertiesMap.get(titleDeed);
		return (propertyBuildingSummary == null) ? 0 : propertyBuildingSummary.getNumberHouses();
	}
	
	public int getNumberHotelsOnProperty(TitleDeed titleDeed) {
		BuildingSummary propertyBuildingSummary = ownedPropertiesMap.get(titleDeed);
		return (propertyBuildingSummary == null) ? 0 : propertyBuildingSummary.getNumberHotels();
	}
	
	public List<RailroadBoardSpace> getOwnedRailroads() {
		return ownedRailroads;
	}
	
	public int getNumberOwnedRailroads() {
		return getOwnedRailroads().size();
	}
	
	public List<UtilityBoardSpace> getOwnedUtilities() {
		return ownedUtilities;
	}
	
	public int getNumberOwnedUtilities() {
		return getOwnedUtilities().size();
	}
	
	public boolean isOwner(BoardSpace boardSpace) {
		return getOwnedProperties().contains(boardSpace) ||
				getOwnedRailroads().contains(boardSpace) ||
				getOwnedUtilities().contains(boardSpace);
	}
}