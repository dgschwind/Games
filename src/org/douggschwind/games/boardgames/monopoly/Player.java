package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.douggschwind.games.boardgames.monopoly.policy.AssetLiquidationPolicy;
import org.douggschwind.games.boardgames.monopoly.policy.UseOfGetOutOfJailFreeCardPolicy;
import org.douggschwind.games.boardgames.monopoly.space.PrivateBoardSpace;
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
		TopHat,
		Iron,
		Ship,
		Shoe,
		Thimble,
		Wheelbarrow;
	}
	
	private final String name;
	private final Avatar avatar; // aka token
	private final UseOfGetOutOfJailFreeCardPolicy useOfGetOutOfJailFreeCardPolicy;
	private final AssetLiquidationPolicy assetLiquidationPolicy;
	private int bankAccountBalance;
	private final Map<TitleDeed, BuildingSummary> ownedPropertiesMap = new HashMap<>();
	private final List<Title> ownedRailroads = new ArrayList<>();
	private final List<Title> ownedUtilities = new ArrayList<>();
	private boolean inJail;
	private int numFailedAttemptsToGetOutOfJail;
	private boolean holdingGetOutOfJailFreeCard;
	private boolean bankrupt;
	
	public Player(String name,
			      Avatar avatar,
			      UseOfGetOutOfJailFreeCardPolicy useOfGetOutOfJailFreeCardPolicy,
			      AssetLiquidationPolicy assetLiquidationPolicy) {
		this.name = name;
		this.avatar = avatar;
		this.useOfGetOutOfJailFreeCardPolicy = useOfGetOutOfJailFreeCardPolicy;
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
	
	public UseOfGetOutOfJailFreeCardPolicy getUseOfGetOutOfJailFreeCardPolicy() {
		return useOfGetOutOfJailFreeCardPolicy;
	}
	
	public AssetLiquidationPolicy getAssetLiquidationPolicy() {
		return assetLiquidationPolicy;
	}

	public void receivePayment(int paymentAmount) {
		bankAccountBalance += paymentAmount;
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
	 * Liquidate houses, hotels, titles as needed to make the necessary payment
	 * @param billedAmount
	 * @return The amount in dollars of titles that have been liquidated.
	 */
	private int liquidateTitlesToGenerateCash(int billedAmount) {
		int liquidatedValue = 0;
		while (liquidatedValue + getBankAccountBalance() < billedAmount) {
			Title titleToLiquidate = getAssetLiquidationPolicy().identifyNextTitleToLiquidate(this);
			if (titleToLiquidate == null) {
				// Huh? How could this happen? If we get here,
				// this is entirely unexpected.
				return 0;
			}
			
			if (titleToLiquidate.isTitleDeed()) {
				TitleDeed titleDeedToLiquidate = (TitleDeed) titleToLiquidate;
				int buildingsLiquidationValue = titleToLiquidate.computeBuildingsLiquidationValue(getNumberHousesOnProperty(titleDeedToLiquidate), getNumberHotelsOnProperty(titleDeedToLiquidate));
				if ((liquidatedValue + getBankAccountBalance() + buildingsLiquidationValue) >= billedAmount) {
					// We don't need to mortgage the Title, but rather just sell
					// off at least one house that has been used to improve the
					// TitleDeed.
					int amountExceeded = (liquidatedValue + getBankAccountBalance() + buildingsLiquidationValue) - billedAmount;
					int numberOfHousesThatCanBeRetained = (amountExceeded / titleDeedToLiquidate.getBankHouseBuybackPrice());
					if (numberOfHousesThatCanBeRetained >= 1) {
						BuildingSummary buildingSummary = getBuildingSummary(titleDeedToLiquidate);
						int numberOfHousesLiquidated = buildingSummary.computeDifferenceInNumberOfHouses(numberOfHousesThatCanBeRetained);
						buildingSummary.clear();
						buildingSummary.setNumberHouses(numberOfHousesThatCanBeRetained);
						liquidatedValue += (numberOfHousesLiquidated * titleDeedToLiquidate.getBankHouseBuybackPrice());
					}
					
					return liquidatedValue;
				} else {
					// Have to sell off all the buildings as well as mortgage the Title
					BuildingSummary buildingSummary = getBuildingSummary((TitleDeed) titleToLiquidate);
					buildingSummary.clear();
					liquidatedValue += buildingsLiquidationValue;
				}
			}
			
			titleToLiquidate.setMortgaged(true);
			liquidatedValue += titleToLiquidate.getMortgageValue();
		}
		
		return liquidatedValue;
	}
	
	/**
	 * This method resolves any amount that the Player owes to another
	 * Player or the Bank.
	 * @param paymentAmount The amount of the payment to be made.
	 * @return true if the payment could be made, false if the payment
	 * would bankrupt the Player.
	 */
	boolean makePayment(int paymentAmount) {
		if (computePlayerLiquidWorth() < paymentAmount) {
			return false;
		}
		if (paymentAmount <= getBankAccountBalance()) {
			setBankAccountBalance(getBankAccountBalance() - paymentAmount);
		} else {
			int liquidatedValue = liquidateTitlesToGenerateCash(paymentAmount);
			setBankAccountBalance((getBankAccountBalance() + liquidatedValue) - paymentAmount);
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
	
	public void acceptOwnership(Title title) {
		if (title.isProperty()) {
			addOwnedProperty((TitleDeed) title);
		} else if (title.isRailroad()) {
			getOwnedRailroads().add(title);
		} else if (title.isUtility()) {
			getOwnedUtilities().add(title);
		}
	}

	public Collection<TitleDeed> getOwnedProperties() {
		return ownedPropertiesMap.keySet();
	}
	
	private void addOwnedProperty(TitleDeed property) {
		ownedPropertiesMap.put(property, new BuildingSummary());
	}
	
	private BuildingSummary getBuildingSummary(TitleDeed titleDeed) {
		return ownedPropertiesMap.get(titleDeed);
	}
	
	public int getNumberHousesOnProperty(TitleDeed titleDeed) {
		BuildingSummary propertyBuildingSummary = getBuildingSummary(titleDeed);
		return (propertyBuildingSummary == null) ? 0 : propertyBuildingSummary.getNumberHouses();
	}
	
	public int getNumberHousesOnAllProperties() {
		int result = 0;
		for (TitleDeed titleDeed : ownedPropertiesMap.keySet()) {
			BuildingSummary propertyBuildingSummary = getBuildingSummary(titleDeed);
			result += propertyBuildingSummary.getNumberHouses();
		}
		return result;
	}
	
	public void addHouseOnProperty(TitleDeed titleDeed) {
		BuildingSummary propertyBuildingSummary = getBuildingSummary(titleDeed);
		propertyBuildingSummary.addHouse();
	}
	
	public int getNumberHotelsOnProperty(TitleDeed titleDeed) {
		BuildingSummary propertyBuildingSummary = getBuildingSummary(titleDeed);
		return (propertyBuildingSummary == null) ? 0 : propertyBuildingSummary.getNumberHotels();
	}
	
	public int getNumberHotelsOnAllProperties() {
		int result = 0;
		for (TitleDeed titleDeed : ownedPropertiesMap.keySet()) {
			BuildingSummary propertyBuildingSummary = getBuildingSummary(titleDeed);
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
		if (newValue == false) {
			numFailedAttemptsToGetOutOfJail = 0;
		}
	}

	public int getNumFailedAttemptsToGetOutOfJail() {
		return numFailedAttemptsToGetOutOfJail;
	}

	public void incrementNumFailedAttemptsToGetOutOfJail() {
		numFailedAttemptsToGetOutOfJail++;
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
			return titleDeed.getMortgageValue() + titleDeed.computeBuildingsLiquidationValue(getNumberHousesOnProperty(titleDeed), getNumberHotelsOnProperty(titleDeed));
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
	
	/**
	 * Transfers all assets from this Player to the recipient. This method
	 * only called when this Player has been bankrupted.
	 * @param recipient Can be null to indicate the new owner is actually
	 * the Bank again.
	 */
	public void transferAssetsToPlayer(Player recipient) {
		for (TitleDeed titleDeed : this.getOwnedProperties()) {
			titleDeed.setOwner(recipient);
		}
		for (Title railroadTitle : this.getOwnedRailroads()) {
			railroadTitle.setOwner(recipient);
		}
		for (Title utilityTitle : this.getOwnedUtilities()) {
			utilityTitle.setOwner(recipient);
		}
		
		if (recipient != null) {
			// Allow the acquiring Player to make decisions about "lifting" the
			// mortgage on any newly acquired mortgaged properties.
			recipient.receivePayment(this.getBankAccountBalance());
		}
	}

	public boolean isBankrupt() {
		return bankrupt;
	}
	
	public void setBankrupt() {
		// A Player cannot recover from bankruptcy, so when this method is called
		// the Player is bankrupt and no longer active in the game.
		ownedPropertiesMap.clear();
		ownedRailroads.clear();
		ownedUtilities.clear();
		setBankAccountBalance(0);
		bankrupt = true;
	}
}