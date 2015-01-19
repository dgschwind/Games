package org.douggschwind.games.boardgames.monopoly.spaces;

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class defines which BoardSpaces, when all owned by the
 * same Player, constitute a monopoly by that Player concerning those specific
 * BoardSpaces.
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
	
	private final List<BoardSpace> monopoly = new ArrayList<>();

	public MonopolyDefinition(ColorDesignator colorDesignator) {
		this.colorDesignator = colorDesignator;
	}
	
	public ColorDesignator getColorDesignator() {
		return colorDesignator;
	}

	public void addBoardSpace(BoardSpace boardSpace) {
		if (boardSpace != null) {
			monopoly.add(boardSpace);
		}
	}
	
	/**
	 * Determines if all of the properties defined by this instance are
	 * in fact present in all of the properties owned by the given Player.
	 * @param playerOwnedProperties
	 * @return true if so, false otherwise.
	 */
	public boolean containsMonopoly(List<PropertyBoardSpace> playerOwnedProperties) {
		for (BoardSpace propertyThatMustBeOwned : monopoly) {
			if (!playerOwnedProperties.contains(propertyThatMustBeOwned)) {
				return false;
			}
		}
		
		return true;
	}
}
