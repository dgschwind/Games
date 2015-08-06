package org.douggschwind.games.boardgames.monopoly;

import java.util.HashMap;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.title.TitleDeed;

/**
 * Houses the BuildingSummary for each TitleDeed.
 * @author Doug Gschwind
 */
public class DeedRecorder {
	
	private static final Map<TitleDeed, BuildingSummary> recordedDeeds = new HashMap<>();
	
	public static BuildingSummary getBuildingSummary(TitleDeed titleDeed) {
		return recordedDeeds.get(titleDeed);
	}
	
	public static void addDeed(TitleDeed titleDeed) {
		updateBuildingSummary(titleDeed, new BuildingSummary());
	}
	
	public static void updateBuildingSummary(TitleDeed titleDeed, BuildingSummary buildingSummary) {
		recordedDeeds.put(titleDeed, buildingSummary);
	}
	
	public static void clear() {
		recordedDeeds.clear();
	}
}