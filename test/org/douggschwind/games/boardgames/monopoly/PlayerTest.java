package org.douggschwind.games.boardgames.monopoly;

import java.util.HashMap;
import java.util.Map;

import org.douggschwind.games.boardgames.monopoly.Player.Avatar;
import org.douggschwind.games.boardgames.policy.ConservativeUseOfGetOutOfJailFreeCardPolicy;
import org.douggschwind.games.boardgames.policy.LowestValuedAssetLiquidationPolicy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the interesting methods on the Player class
 * @author Doug Gschwind
 */
public class PlayerTest {
	
	private Player underTest;
	
	@Before
	public void beforeTest() {
		underTest = new Player("Gurn", Avatar.Dog, new ConservativeUseOfGetOutOfJailFreeCardPolicy(), new LowestValuedAssetLiquidationPolicy());
	}
	
	@Test
	public void testInitialBankAccountBalance() {
		Assert.assertEquals(1500, underTest.getBankAccountBalance());
	}
	
	@Test
	public void testRollDice() {
		final int NUM_TIMES_TO_ROLL_DICE = 1000;
		
		Map<Integer, Integer> diceRollSummaryMap = new HashMap<>();
		int numTimesDoublesRolled = 0;
		
		for (int i = 0;i < NUM_TIMES_TO_ROLL_DICE;i++) {
			DiceRollResult diceRollResult = underTest.rollDice();
			int diceRollTotal = diceRollResult.getDiceRollTotal();
			if ((diceRollTotal < 2) || (diceRollTotal > 12))  {
				Assert.fail("Not supported dice roll total : " + diceRollTotal);
			}
			
			Integer numTimesThisNumberRolled = diceRollSummaryMap.get(diceRollTotal);
			if (numTimesThisNumberRolled == null) {
				diceRollSummaryMap.put(diceRollTotal, 1);
			} else {
				diceRollSummaryMap.put(diceRollTotal, numTimesThisNumberRolled + 1);
			}
			
			if (diceRollResult.wereDoublesRolled()) {
				numTimesDoublesRolled++;
			}
		}
		
		// Rough expectations, statistically, concerning dice roll probability
		// 2 : 1 in 36
		// 3 : 2 in 36
		// 4 : 3 in 36
		// 5 : 4 in 36
		// 6 : 5 in 36
		// 7 : 6 in 36
		// 8 : 5 in 36
		// 9 : 4 in 36
		// 10 : 3 in 36
		// 11 : 2 in 36
		// 12 : 1 in 36
		// Doubles being rolled : 6 in 36
		
		System.out.println("Dice Roll Results :");
		System.out.println("\t2 : num times rolled : " + diceRollSummaryMap.get(2) + " for " + 100.0 * (diceRollSummaryMap.get(2) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t3 : num times rolled : " + diceRollSummaryMap.get(3) + " for " + 100.0 * (diceRollSummaryMap.get(3) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t4 : num times rolled : " + diceRollSummaryMap.get(4) + " for " + 100.0 * (diceRollSummaryMap.get(4) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t5 : num times rolled : " + diceRollSummaryMap.get(5) + " for " + 100.0 * (diceRollSummaryMap.get(5) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t6 : num times rolled : " + diceRollSummaryMap.get(6) + " for " + 100.0 * (diceRollSummaryMap.get(6) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t7 : num times rolled : " + diceRollSummaryMap.get(7) + " for " + 100.0 * (diceRollSummaryMap.get(7) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t8 : num times rolled : " + diceRollSummaryMap.get(8) + " for " + 100.0 * (diceRollSummaryMap.get(8) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t9 : num times rolled : " + diceRollSummaryMap.get(9) + " for " + 100.0 * (diceRollSummaryMap.get(9) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t10 : num times rolled : " + diceRollSummaryMap.get(10) + " for " + 100.0 * (diceRollSummaryMap.get(10) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t11 : num times rolled : " + diceRollSummaryMap.get(11) + " for " + 100.0 * (diceRollSummaryMap.get(11) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		System.out.println("\t12 : num times rolled : " + diceRollSummaryMap.get(12) + " for " + 100.0 * (diceRollSummaryMap.get(12) / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
		
		System.out.println("\tDoubles : num times rolled : " + numTimesDoublesRolled + " for " + 100.0 * (numTimesDoublesRolled / (float) NUM_TIMES_TO_ROLL_DICE) + "%");
	}
	
	@Test
	public void testReceivePayment() {
		int startingBankAccountBalance = underTest.getBankAccountBalance();
		underTest.receivePayment(200);
		Assert.assertEquals(startingBankAccountBalance + 200, underTest.getBankAccountBalance());
	}
}