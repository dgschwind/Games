package org.douggschwind.games.boardgames.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.Player;
import org.douggschwind.games.boardgames.monopoly.title.Title;

/**
 * Identifies the highest valued title that can be liquidated.
 * @author Doug Gschwind
 */
public class HighestValuedAssetLiquidationPolicy extends AssetLiquidationPolicy {
	
	private static final Comparator<Integer> DESCENDING_COMPARATOR = new Comparator<Integer>() {
		@Override
		public int compare(Integer i1, Integer i2) {
			return i2.compareTo(i1);
		}
	};

	@Override
	public Title identifyNextTitleToLiquidate(Player player) {
		Map<Title, Integer> titleLiquidationValuesMap = computeTitleLiquidationValuesMap(player);
		List<Integer> liquidationValues = new ArrayList<>(titleLiquidationValuesMap.values());
		Collections.sort(liquidationValues, DESCENDING_COMPARATOR);
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
}
