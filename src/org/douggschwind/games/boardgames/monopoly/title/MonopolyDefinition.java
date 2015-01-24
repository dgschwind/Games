package org.douggschwind.games.boardgames.monopoly.title;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * An instance of this class defines which Titles, when all owned by the
 * same Player, constitute a monopoly by that Player concerning those specific
 * Titles.
 * @author Doug Gschwind
 */
public class MonopolyDefinition {
	
	/**
	 * Is used to identity a specific Monopoly. e.g. The Brown identifier
	 * identifies the Monopoly that is comprised of the Mediterranean and
	 * Baltic Avenue properties.
	 */
	public enum Identifier {
		Brown,
		LightBlue,
		Purple,
		Orange,
		Red,
		Yellow,
		Green,
		Blue,
		Railroads,
		Utilities;
	}
	
	private final Identifier identifier;
	
	private final List<Title> titles = new ArrayList<>();

	public MonopolyDefinition(Identifier identifier) {
		this.identifier = identifier;
	}
	
	public Identifier getIdentifier() {
		return identifier;
	}

	public void addTitle(Title title) {
		if (title != null) {
			titles.add(title);
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
		for (Title title : titles) {
			Player titleOwner = title.getOwner();
			if (titleOwner == null) {
				return false;
			}
			
			if (title.isMortgaged()) {
				return false;
			}
			
			distinctPlayerOwners.add(titleOwner);
		}
		
		return (distinctPlayerOwners.size() == 1);
	}
}
