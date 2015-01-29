package org.douggschwind.games.boardgames.monopoly.title;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * An instance of this class represents something that can be purchased and owned by
 * a given Player : property, railroad, or utility.
 * @author Doug Gschwind
 */
public class Title {
	private final MonopolyDefinition monopolyDefinition;
	private final String name;
	private final int purchasePrice;
	private final int mortgageValue;
	private Player owner;
	private boolean mortgaged;
	
	public Title(MonopolyDefinition monopolyDefinition,
			     String name,
			     int purchasePrice) {
		this.monopolyDefinition = monopolyDefinition;
		this.name = name;
		this.purchasePrice = purchasePrice;
		this.mortgageValue = purchasePrice / 2;
	}
	
	public boolean isTitleDeed() {
		return false;
	}
	
	public MonopolyDefinition getMonopolyDefinition() {
		return monopolyDefinition;
	}
	
	public String getName() {
		return name;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public int getMortgageValue() {
		return mortgageValue;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean hasOwner() {
		return (getOwner() != null);
	}
	
	/**
	 * Determines whether or not the given Player is the owner of this Property.
	 * @param player Expected to be non-null.
	 * @return true if so, false otherwise.
	 */
	public boolean isOwner(Player player) {
		return ((getOwner() != null) && (getOwner().equals(player)));
	}
	
	public boolean isPartOfMonopoly() {
		return getMonopolyDefinition().hasBeenMonopolized();
	}

	public boolean isMortgaged() {
		return mortgaged;
	}

	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
	}
	
	public int computeBuildingsLiquidationValue(int numberHousesOnProperty, int numberHotelsOnProperty) {
		return 0;
	}
}