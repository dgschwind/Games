package org.douggschwind.games.boardgames.policy;

import java.util.HashMap;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.Player;
import org.douggschwind.games.boardgames.monopoly.title.Title;
import org.douggschwind.games.boardgames.monopoly.title.TitleDeed;

/**
 * @author Doug Gschwind
 */
public abstract class AssetLiquidationPolicy {
	
	public abstract Title identifyNextTitleToLiquidate(Player player);
	
	protected final Map<Title, Integer> computeTitleLiquidationValuesMap(Player player) {
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