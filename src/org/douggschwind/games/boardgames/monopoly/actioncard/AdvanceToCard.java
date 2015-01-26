package org.douggschwind.games.boardgames.monopoly.actioncard;

import org.douggschwind.games.boardgames.monopoly.Monopoly;
import org.douggschwind.games.boardgames.monopoly.Player;

/**
 * Instances of this class indicate a board space on the game board which a player
 * must advance to.
 * @author Doug Gschwind
 */
public class AdvanceToCard extends ActionCard {
	
	public enum Location {
		Go,
		IllinoisAve,
		StCharlesPlace,
		NearestUtility,
		NearestRailroad,
		GoBackThreeSpaces,
		ReadingRailroad,
		Boardwalk,
		Jail;
	}
	
	private final Location location;
	private final boolean holderToReceive200DollarsIfPassingGo;

	/**
	 * @param location Expected to be non-null.
	 */
	public AdvanceToCard(Location location, boolean holderToReceive200DollarsIfPassingGo) {
		super("Advance to...");
		this.location = location;
		this.holderToReceive200DollarsIfPassingGo = holderToReceive200DollarsIfPassingGo;
	}
	
	public Location getLocation() {
		return location;
	}

	public boolean isHolderToReceive200DollarsIfPassingGo() {
		return holderToReceive200DollarsIfPassingGo;
	}
	
	@Override
	public boolean isGoDirectlyToJail() {
		return (getLocation() == Location.Jail);
	}

	@Override
	public final boolean isAdvanceToSpace() {
		return true;
	}
	
	@Override
	public final void takeAction(Monopoly gameInProgress, Player player, int playerDiceRollTotal) {
		gameInProgress.advancePlayerToBoardLocation(player, getLocation(), playerDiceRollTotal);
	}
}
