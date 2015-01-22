package org.douggschwind.games.boardgames.monopoly.titledeed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * An instance of this class defines which TitleDeeds, when all owned by the
 * same Player, constitute a monopoly by that Player concerning those specific
 * TitleDeeds.
 * @author Doug Gschwind
 */
public class MonopolyDefinition {
	
	public enum ColorDesignator {
		Brown,
		LightBlue,
		Purple,
		Orange,
		Red,
		Yellow,
		Green,
		Blue;
	}
	
	private final ColorDesignator colorDesignator;
	
	private final List<TitleDeed> titleDeeds = new ArrayList<>();

	public MonopolyDefinition(ColorDesignator colorDesignator) {
		this.colorDesignator = colorDesignator;
	}
	
	public ColorDesignator getColorDesignator() {
		return colorDesignator;
	}

	public void addTitleDeed(TitleDeed titleDeed) {
		if (titleDeed != null) {
			titleDeeds.add(titleDeed);
		}
	}
	
	/**
	 * Determines if all of the properties defined by this instance are
	 * in fact all owned by the same Player and none of those TitleDeeds
	 * are in the mortgaged state.
	 * @return true if so, false otherwise.
	 */
	public boolean hasBeenMonopolized() {
		Set<Player> distinctPlayerOwners = new HashSet<>();
		for (TitleDeed titleDeed : titleDeeds) {
			Player titleDeedOwner = titleDeed.getOwner();
			if (titleDeedOwner == null) {
				return false;
			}
			
			if (titleDeed.isMortgaged()) {
				return false;
			}
			
			distinctPlayerOwners.add(titleDeedOwner);
		}
		
		return (distinctPlayerOwners.size() == 1);
	}
}
