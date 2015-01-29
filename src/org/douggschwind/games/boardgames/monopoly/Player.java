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
import org.douggschwind.games.boardgames.policy.AssetLiquidationPolicy;

/**
 * An instance of this class houses the state of the game for any given Player of the game.
 * @author Doug Gschwind
 */
public class Player {
	
	public enum Avatar {
		Car,
		Dog,
		TopHat,
		Iron,
		Ship,
		Shoe,
		Thimble,
		Wheelbarrow;
	}
	
	private final String name;
	private final Avatar avatar; // aka token
	private final AssetLiquidationPolicy assetLiquidationPolicy;
	private int bankAccountBalance;
	private final Map<TitleDeed, BuildingSummary> ownedPropertiesMap = new HashMap<>();
	private final List<Title> ownedRailroads = new ArrayList<>();
	private final List<Title> ownedUtilities = new ArrayList<>();
	private boolean inJail;
	private boolean holdingGetOutOfJailFreeCard;
	private boolean bankrupt;
	
	public Player(String name, Avatar avatar, AssetLiquidationPolicy assetLiquidationPolicy) {
		this.name = name;
		this.avatar = avatar;
		this.assetLiquidationPolicy = assetLiquidationPolicy;
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
	
	public DiceRollResult rollDice() {
		int die1Value = new Random().nextInt(6); // 0 to 5
		int die2Value = new Random().nextInt(6); // 0 to 5
		return new DiceRollResult(2 + die1Value + die2Value, die1Value == die2Value);
	}

	public int getBankAccountBalance() {
		return bankAccountBalance;
	}
	
	private void setBankAccountBalance(int newValue) {
		bankAccountBalance = newValue;
	}
	
	public AssetLiquidationPolicy getAssetLiquidationPolicy() {
		return assetLiquidationPolicy;
	}

	public void receivePayment(int creditedAmount) {
		bankAccountBalance += creditedAmount;
	}
	
	public boolean canPayBillWithCash(int billAmount) {
		return getBankAccountBalance() > billAmount;
	}
	
	/**
	 * Since a Player can sell houses or hotels back to the Bank
	 * or mortgage a property at any time, those assets are included
	 * in the result. Mortgaged properties are ignored since those
	 * can only be sold to other Players.
	 * @return Will be non-negative.
	 */
	private int computePlayerLiquidWorth() {
		int result = getBankAccountBalance();
		for (TitleDeed titleDeed : getOwnedProperties()) {
			if (titleDeed.isMortgaged()) {
				// This does not contribute to liquid worth.
				continue;
			}
			int numberHousesOnProperty = getNumberHousesOnProperty(titleDeed);
			result += (numberHousesOnProperty * titleDeed.getBankHouseBuybackPrice());
			int numberHotelsOnProperty = getNumberHotelsOnProperty(titleDeed);
			result += (numberHotelsOnProperty * titleDeed.getBankHotelBuybackPrice());
		}
		
		for (Title railroadTitle : getOwnedRailroads()) {
			if (railroadTitle.isMortgaged()) {
				// This does not contribute to liquid worth.
			}
			result += railroadTitle.getMortgageValue();
		}
		
		for (Title utilityTitle : getOwnedUtilities()) {
			if (utilityTitle.isMortgaged()) {
				// This does not contribute to liquid worth.
			}
			result += utilityTitle.getMortgageValue();
		}
		
		return result;
	}
	
	/**
	 * This method resolves any amount that the Player owes to another
	 * Player or the Bank.
	 * @param billedAmount The amount of the payment to be made.
	 * @return true if the payment could be made, false if the payment
	 * would bankrupt the Player.
	 */
	boolean makePayment(int billedAmount) {
		if (computePlayerLiquidWorth() < billedAmount) {
			return false;
		}
		if (billedAmount <= getBankAccountBalance()) {
			setBankAccountBalance(getBankAccountBalance() - billedAmount);
		} else {
			int liquidValue = getBankAccountBalance();
			//TODO: Must sell off Hotels, Houses, mortgage
			// properties to pay the bill. Consider Strategy
			// pattern here where some players sell off their
			// lower valued assets where others sell off their
			// higher valued assets.
			List<Title> titlesToMortgage = new ArrayList<>();
			while (liquidValue < billedAmount) {
				Title titleToMortgage = getAssetLiquidationPolicy().identifyNextTitleToLiquidate(this);
				if (titleToMortgage == null) {
					// Huh? How could this happen? If we get here,
					// this is entirely unexpected.
					return false;
				}
				titlesToMortgage.add(titleToMortgage);
				//TODO: What about the buildings on this property?
				liquidValue += titleToMortgage.getMortgageValue();
			}
			
			for (Title titleToMortgage : titlesToMortgage) {
				titleToMortgage.setMortgaged(true);
			}
			
			setBankAccountBalance(liquidValue - billedAmount);
		}
		return true;
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
	
	public boolean isInJail() {
		return inJail;
	}

	public void setInJail(boolean newValue) {
		inJail = newValue;
	}

	public boolean isHoldingGetOutOfJailFreeCard() {
		return holdingGetOutOfJailFreeCard;
	}

	public void setHoldingGetOutOfJailFreeCard(boolean newValue) {
		holdingGetOutOfJailFreeCard = newValue;
	}
	
	public int computeLiquidationValue(Title title) {
		if (title.isTitleDeed()) {
			TitleDeed titleDeed = (TitleDeed) title;
			return titleDeed.getMortgageValue() +
					(getNumberHousesOnProperty(titleDeed) * titleDeed.getBankHouseBuybackPrice()) +
					(getNumberHotelsOnProperty(titleDeed) * titleDeed.getBankHotelBuybackPrice());
			
		} else {
			// Its a railroad or utility
			for (Title railroadTitle : getOwnedRailroads()) {
				if (railroadTitle == title) {
					return railroadTitle.getMortgageValue();
				}
			}
			
			for (Title utilityTitle : getOwnedUtilities()) {
				if (utilityTitle == title) {
					return utilityTitle.getMortgageValue();
				}
			}
		}
		
		// Should never get here
		return 0;
	}

	public boolean isBankrupt() {
		return bankrupt;
	}

	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}
}