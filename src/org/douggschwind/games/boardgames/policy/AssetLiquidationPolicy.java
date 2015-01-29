package org.douggschwind.games.boardgames.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.Player;
import org.douggschwind.games.boardgames.monopoly.title.Title;
import org.douggschwind.games.boardgames.monopoly.title.TitleDeed;

/**
 * This defines the Strategies/Policies that a given Player can employ to
 * determine how to go about liquidating assets when a bill is due and they
 * do not have enough cash on hand to pay it.
 * @author Doug Gschwind
 */
public interface AssetLiquidationPolicy {
	
	void sortLiquidationValuesMostLiquidatableFirst(List<Integer> liquidationValues);
	
	/**
	 * Identifies the next Title that should be considered to be liquidated by the
	 * given Player to help make a Payment.
	 * @param player Must be non-null.
	 * @return Should be non-null and be an unmortgaged Title.
	 */
	default Title identifyNextTitleToLiquidate(Player player) {
		Map<Title, Integer> titleLiquidationValuesMap = computeTitleLiquidationValuesMap(player);
		if (titleLiquidationValuesMap.isEmpty()) {
			return null;
		}
		
		List<Integer> liquidationValues = new ArrayList<>(titleLiquidationValuesMap.values());
		sortLiquidationValuesMostLiquidatableFirst(liquidationValues);
		int liquidationValueToFind = liquidationValues.get(0);
		for (Title candidateTitle : titleLiquidationValuesMap.keySet()) {
			Integer liquidationValue = titleLiquidationValuesMap.get(candidateTitle);
			if ((liquidationValue != null) && (liquidationValue.intValue() == liquidationValueToFind)) {
				return candidateTitle;
			}
		}
		
		// Should never get here!
		return null;
	}
	
	/**
	 * This is really an implementation detail method and is not intended to be
	 * exposed for client use.
	 * @param player Must be non-null.
	 * @return Will be non-null. The key for each entry is a title, and its
	 * value is the liquidation value for that title.
	 */
	default Map<Title, Integer> computeTitleLiquidationValuesMap(Player player) {
		Map<Title, Integer> result = new HashMap<>();
		for (TitleDeed titleDeed : player.getOwnedProperties()) {
			result.put(titleDeed, player.computeLiquidationValue(titleDeed));
		}
		for (Title railroadTitle : player.getOwnedRailroads()) {
			result.put(railroadTitle, player.computeLiquidationValue(railroadTitle));
		}
		for (Title utilityTitle : player.getOwnedUtilities()) {
			result.put(utilityTitle, player.computeLiquidationValue(utilityTitle));
		}
		
		return result;
	}
}