package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.douggschwind.games.boardgames.monopoly.space.PrivateBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.UtilityBoardSpace;
import org.douggschwind.games.boardgames.monopoly.title.Title;
import org.douggschwind.games.boardgames.monopoly.title.TitleDeed;

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
	private final List<Title> ownedRailroads = new ArrayList<>();
	private final List<Title> ownedUtilities = new ArrayList<>();
	private boolean holdingGetOutOfJailFreeCard;
	private boolean bankrupt;
	
	public Player(String name, Avatar avatar) {
		this.name = name;
		this.avatar = avatar;
		bankAccountBalance = 1500;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null) {
			return false;
		}
		
		if (!this.getClass().equals(that.getClass())) {
			return false;
		}
		
		return this.getName().equals(((Player) that).getName());
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
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
	
	public void makePayment(int billedAmount) {
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
	
	public boolean wouldYouLikeToPurchase(PrivateBoardSpace<? extends Title> privateBoardSpace) {
		// TODO. Add more sophistication here later.
		return privateBoardSpace.getPurchasePrice() < getBankAccountBalance();
	}
	
	public void acceptOwnership(PrivateBoardSpace<? extends Title> privateBoardSpace) {
		if (privateBoardSpace.isProperty()) {
			addOwnedProperty(((PropertyBoardSpace) privateBoardSpace).getTitle());
		} else if (privateBoardSpace.isRailroad()) {
			getOwnedRailroads().add(((RailroadBoardSpace) privateBoardSpace).getTitle());
		} else if (privateBoardSpace.isUtility()) {
			getOwnedUtilities().add(((UtilityBoardSpace) privateBoardSpace).getTitle());
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
	
	public int getNumberHousesOnAllProperties() {
		int result = 0;
		for (TitleDeed titleDeed : ownedPropertiesMap.keySet()) {
			BuildingSummary propertyBuildingSummary = ownedPropertiesMap.get(titleDeed);
			result += propertyBuildingSummary.getNumberHouses();
		}
		return result;
	}
	
	public int getNumberHotelsOnProperty(TitleDeed titleDeed) {
		BuildingSummary propertyBuildingSummary = ownedPropertiesMap.get(titleDeed);
		return (propertyBuildingSummary == null) ? 0 : propertyBuildingSummary.getNumberHotels();
	}
	
	public int getNumberHotelsOnAllProperties() {
		int result = 0;
		for (TitleDeed titleDeed : ownedPropertiesMap.keySet()) {
			BuildingSummary propertyBuildingSummary = ownedPropertiesMap.get(titleDeed);
			result += propertyBuildingSummary.getNumberHotels();
		}
		return result;
	}
	
	public List<Title> getOwnedRailroads() {
		return ownedRailroads;
	}
	
	public int getNumberOwnedRailroads() {
		return getOwnedRailroads().size();
	}
	
	public List<Title> getOwnedUtilities() {
		return ownedUtilities;
	}
	
	public int getNumberOwnedUtilities() {
		return getOwnedUtilities().size();
	}

	public boolean isHoldingGetOutOfJailFreeCard() {
		return holdingGetOutOfJailFreeCard;
	}

	public void setHoldingGetOutOfJailFreeCard(boolean newValue) {
		holdingGetOutOfJailFreeCard = newValue;
	}

	public boolean isBankrupt() {
		return bankrupt;
	}

	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}
}