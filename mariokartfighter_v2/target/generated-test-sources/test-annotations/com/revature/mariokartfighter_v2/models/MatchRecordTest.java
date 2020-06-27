package com.revature.mariokartfighter_v2.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;

public class MatchRecordTest {

	@Test
	public void constructorShouldInitializeValuesProperly() {
		MatchRecord newMatchRecord = new MatchRecord("mr001", "player1", 
				"dk001", "sp001", "player2", "p001", "b001", false, "player1");
		
		assertEquals("mr001", newMatchRecord.getMatchID());
		assertEquals("player1", newMatchRecord.getPlayer1ID());
		assertEquals("dk001", newMatchRecord.getPlayer1CharacterID());
		assertEquals("sp001", newMatchRecord.getPlayer1ItemID());
		assertEquals("player2", newMatchRecord.getPlayer2ID());
		assertEquals("p001", newMatchRecord.getPlayer2CharacterID());
		assertEquals("b001", newMatchRecord.getPlayer2ItemID());
		assertFalse(newMatchRecord.isPlayer2IsBot());
		assertEquals("player1", newMatchRecord.getWinnerID());
		assertEquals(Timestamp.class, newMatchRecord.getTimeOfMatch().getClass());
	}
	@Test
	public void testEqualsOverride() {
		MatchRecord matchRecord1 = new MatchRecord("mr001", "player1", 
				"dk001", "sp001", "player2", "p001", "b001", false, "player1");
		MatchRecord matchRecord2 = new MatchRecord("mr001", "player1", 
				"dk001", "sp001", "player2", "p001", "b001", false, "player1");
		assertTrue(matchRecord1.equals(matchRecord2));
	}

}
