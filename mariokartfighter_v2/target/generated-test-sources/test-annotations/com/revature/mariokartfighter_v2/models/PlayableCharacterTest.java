package com.revature.mariokartfighter_v2.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayableCharacterTest {

	@Test
	public void constructorShouldInitializeValuesProperly() {
		PlayableCharacter newCharacter = new PlayableCharacter(
				"abcde", "speed", "alphabet", 100, 5.0, 5.0, 1);
		assertEquals("abcde", newCharacter.getCharacterID());
		assertEquals("speed", newCharacter.getType());
		assertEquals("alphabet", newCharacter.getCharacterName());
		assertEquals(5.0, newCharacter.getAttackStat(), 0);
		assertEquals(5.0, newCharacter.getDefenseStat(), 0);
		assertEquals(100, newCharacter.getMaxHealth());
		assertEquals(1, newCharacter.getUnlockAtLevel());
	}
	@Test
	public void testEqualsOverride() {
		PlayableCharacter character1 = new PlayableCharacter(
				"abcde", "speed", "alphabet", 100, 5.0, 5.0, 1);
		PlayableCharacter character2 = new PlayableCharacter(
				"abcde", "speed", "alphabet", 100, 5.0, 5.0, 1);
		assertTrue(character1.equals(character2));
	}
	
	@Test
	public void getInfoStringShouldReturnString() {
		PlayableCharacter newCharacter = new PlayableCharacter(
				"abcde", "speed", "alphabet", 100, 5.0, 5.0, 1);
		assertEquals(String.class, newCharacter.getInfoString().getClass());
	}

}
