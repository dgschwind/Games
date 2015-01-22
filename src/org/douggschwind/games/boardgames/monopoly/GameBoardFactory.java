package org.douggschwind.games.boardgames.monopoly;

import java.util.ArrayList;
import java.util.List;

import org.douggschwind.games.boardgames.monopoly.space.BoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.ChanceSpace;
import org.douggschwind.games.boardgames.monopoly.space.CommunityChestSpace;
import org.douggschwind.games.boardgames.monopoly.space.FreeParkingSpace;
import org.douggschwind.games.boardgames.monopoly.space.GoDirectlyToJailSpace;
import org.douggschwind.games.boardgames.monopoly.space.GoSpace;
import org.douggschwind.games.boardgames.monopoly.space.IncomeTaxSpace;
import org.douggschwind.games.boardgames.monopoly.space.JailSpace;
import org.douggschwind.games.boardgames.monopoly.space.LuxuryTaxSpace;
import org.douggschwind.games.boardgames.monopoly.space.PropertyBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.RailroadBoardSpace;
import org.douggschwind.games.boardgames.monopoly.space.UtilityBoardSpace;
import org.douggschwind.games.boardgames.monopoly.titledeed.MonopolyDefinition;
import org.douggschwind.games.boardgames.monopoly.titledeed.MonopolyDefinition.ColorDesignator;
import org.douggschwind.games.boardgames.monopoly.titledeed.TitleDeed;

/**
 * Creates the game board by binding well known Monopoly Definitions to well known TitleDeed instances
 * and lastly those into BoardSpace instances.
 * @author Doug Gschwind
 */
public class GameBoardFactory {
	
	static final int NUM_BOARD_SPACES_TOTAL = 40;
	
	private static final MonopolyDefinition BROWN_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Brown);
	private static final MonopolyDefinition LIGHT_BLUE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.LightBlue);
	private static final MonopolyDefinition PURPLE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Purple);
	private static final MonopolyDefinition ORANGE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Orange);
	private static final MonopolyDefinition RED_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Red);
	private static final MonopolyDefinition YELLOW_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Yellow);
	private static final MonopolyDefinition GREEN_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Green);
	private static final MonopolyDefinition BLUE_MONOPOLY_DEFINITION = new MonopolyDefinition(ColorDesignator.Blue);
	
	private static TitleDeed MEDITERRANEAN_AVENUE = new TitleDeed(BROWN_MONOPOLY_DEFINITION, "Mediterranean Avenue", 60, 50, 2, 4, 10, 30, 90, 160, 250);
	private static TitleDeed BALTIC_AVENUE = new TitleDeed(BROWN_MONOPOLY_DEFINITION, "Baltic Avenue", 60, 50, 4, 8, 20, 60, 180, 320, 450);
	
	private static TitleDeed ORIENTAL_AVENUE = new TitleDeed(LIGHT_BLUE_MONOPOLY_DEFINITION, "Oriental Avenue", 100, 50, 6, 12, 30, 90, 270, 400, 550);
	private static TitleDeed VERMONT_AVENUE = new TitleDeed(LIGHT_BLUE_MONOPOLY_DEFINITION, "Vermont Avenue", 100, 50, 6, 12, 30, 90, 270, 400, 550);
	private static TitleDeed CONNETICUT_AVENUE = new TitleDeed(LIGHT_BLUE_MONOPOLY_DEFINITION, "Conneticut Avenue", 120, 50, 8, 16, 40, 100, 300, 450, 600);
	
	private static TitleDeed ST_CHARLES_PLACE = new TitleDeed(PURPLE_MONOPOLY_DEFINITION, "St Charles Place", 140, 100, 10, 20, 50, 150, 450, 625, 750);
	private static TitleDeed STATES_AVENUE = new TitleDeed(PURPLE_MONOPOLY_DEFINITION, "States Avenue", 140, 100, 10, 20, 50, 150, 450, 625, 750);
	private static TitleDeed VIRGINIA_AVENUE = new TitleDeed(PURPLE_MONOPOLY_DEFINITION, "Virginia Avenue", 160, 100, 12, 24, 60, 180, 500, 700, 900);
	
	private static TitleDeed ST_JAMES_PLACE = new TitleDeed(ORANGE_MONOPOLY_DEFINITION, "St James Place", 180, 100, 14, 28, 70, 200, 550, 750, 950);
	private static TitleDeed TENNESEE_AVENUE = new TitleDeed(ORANGE_MONOPOLY_DEFINITION, "Tennesee Avenue", 180, 100, 14, 28, 70, 200, 550, 750, 950);
	private static TitleDeed NEW_YORK_AVENUE = new TitleDeed(ORANGE_MONOPOLY_DEFINITION, "New York Avenue", 200, 100, 16, 32, 80, 220, 600, 800, 1000);
	
	private static TitleDeed KENTUCKY_AVENUE = new TitleDeed(RED_MONOPOLY_DEFINITION, "Kentucky Avenue", 220, 150, 18, 36, 90, 250, 700, 875, 1050);
	private static TitleDeed INDIANA_AVENUE = new TitleDeed(RED_MONOPOLY_DEFINITION, "Indiana Avenue", 220, 150, 18, 36, 90, 250, 700, 875, 1050);
	private static TitleDeed ILLINOIS_AVENUE = new TitleDeed(RED_MONOPOLY_DEFINITION, "Illinois Avenue", 250, 150, 20, 40, 100, 300, 750, 925, 1100);
	
	private static TitleDeed ATLANTIC_AVENUE = new TitleDeed(YELLOW_MONOPOLY_DEFINITION, "Atlantic Avenue", 260, 150, 22, 44, 110, 330, 800, 975, 1150);
	private static TitleDeed VENTNOR_AVENUE = new TitleDeed(YELLOW_MONOPOLY_DEFINITION, "Ventnor Avenue", 260, 150, 22, 44, 110, 330, 800, 975, 1150);
	private static TitleDeed MARVIN_GARDENS = new TitleDeed(YELLOW_MONOPOLY_DEFINITION, "Marvin Gardens", 280, 150, 24, 48, 120, 360, 850, 1025, 1200);
	
	private static TitleDeed PACIFIC_AVENUE = new TitleDeed(GREEN_MONOPOLY_DEFINITION, "Pacific Avenue", 300, 200, 26, 52, 130, 390, 900, 1100, 1275);
	private static TitleDeed NORTH_CAROLINA_AVENUE = new TitleDeed(GREEN_MONOPOLY_DEFINITION, "North Carolina Avenue", 300, 200, 26, 52, 130, 390, 900, 1100, 1275);
	private static TitleDeed PENNSYLVANIA_AVENUE = new TitleDeed(GREEN_MONOPOLY_DEFINITION, "Pennsylvania Avenue", 320, 200, 28, 56, 150, 450, 1000, 1200, 1400);
	
	private static TitleDeed PARK_PLACE = new TitleDeed(BLUE_MONOPOLY_DEFINITION, "Park Place", 350, 200, 35, 70, 175, 500, 1100, 1300, 1500);
	private static TitleDeed BOARDWALK = new TitleDeed(BLUE_MONOPOLY_DEFINITION, "Boardwalk", 400, 200, 50, 100, 200, 600, 1400, 1700, 2000);

	static List<BoardSpace> createGameBoard() {
		List<BoardSpace> result = new ArrayList<>(NUM_BOARD_SPACES_TOTAL);
		result.add(new GoSpace());
		result.add(new PropertyBoardSpace(MEDITERRANEAN_AVENUE));
		result.add(new CommunityChestSpace());
		result.add(new PropertyBoardSpace(BALTIC_AVENUE));
		result.add(new IncomeTaxSpace());
		result.add(new RailroadBoardSpace("Reading", 200, 100));
		result.add(new PropertyBoardSpace(ORIENTAL_AVENUE));
		result.add(new ChanceSpace());
		result.add(new PropertyBoardSpace(VERMONT_AVENUE));
		result.add(new PropertyBoardSpace(CONNETICUT_AVENUE));
		result.add(new JailSpace());
		result.add(new PropertyBoardSpace(ST_CHARLES_PLACE));
		result.add(new UtilityBoardSpace("Electric Company", 150, 75));
		result.add(new PropertyBoardSpace(STATES_AVENUE));
		result.add(new PropertyBoardSpace(VIRGINIA_AVENUE));
		result.add(new RailroadBoardSpace("Pennsylvania", 200, 100));
		result.add(new PropertyBoardSpace(ST_JAMES_PLACE));
		result.add(new CommunityChestSpace());
		result.add(new PropertyBoardSpace(TENNESEE_AVENUE));
		result.add(new PropertyBoardSpace(NEW_YORK_AVENUE));
		result.add(new FreeParkingSpace());
		result.add(new PropertyBoardSpace(KENTUCKY_AVENUE));
		result.add(new ChanceSpace());
		result.add(new PropertyBoardSpace(INDIANA_AVENUE));
		result.add(new PropertyBoardSpace(ILLINOIS_AVENUE));
		result.add(new RailroadBoardSpace("B & O", 200, 100));
		result.add(new PropertyBoardSpace(ATLANTIC_AVENUE));
		result.add(new PropertyBoardSpace(VENTNOR_AVENUE));
		result.add(new UtilityBoardSpace("Water Works", 150, 75));
		result.add(new PropertyBoardSpace(MARVIN_GARDENS));
		result.add(new GoDirectlyToJailSpace());
		result.add(new PropertyBoardSpace(PACIFIC_AVENUE));
		result.add(new PropertyBoardSpace(NORTH_CAROLINA_AVENUE));
		result.add(new CommunityChestSpace());
		result.add(new PropertyBoardSpace(PENNSYLVANIA_AVENUE));
		result.add(new RailroadBoardSpace("Short Line", 200, 100));
		result.add(new ChanceSpace());
		result.add(new PropertyBoardSpace(PARK_PLACE));
		result.add(new LuxuryTaxSpace());
		result.add(new PropertyBoardSpace(BOARDWALK));
		return result;
	}
}