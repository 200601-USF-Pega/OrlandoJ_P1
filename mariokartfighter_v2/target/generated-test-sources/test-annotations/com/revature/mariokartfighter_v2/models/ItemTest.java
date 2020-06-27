package com.revature.mariokartfighter_v2.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItemTest {

	@Test
	public void constructorShouldInitializeValuesProperly() {
		Item newItem = new Item("abcde", "alphabet", "speed", 1, 0, 3.0, 1.0);
		assertEquals("abcde", newItem.getItemID());
		assertEquals("alphabet", newItem.getItemName());
		assertEquals("speed", newItem.getTypeThatCanUse());
		assertEquals(3.0, newItem.getBonusToAttack(), 0);
		assertEquals(1.0, newItem.getBonusToDefense(), 0);
		assertEquals(0, newItem.getBonusToHealth());
		assertEquals(1, newItem.getUnlockAtLevel());
	}
	@Test
	public void testEqualsOverride() {
		Item item1 = new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0);
		Item item2 = new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0);
		assertTrue(item1.equals(item2));
	}
	
	@Test
	public void getInfoStringShouldReturnString() {
		Item item1 = new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0);
		assertEquals(String.class, item1.getInfoString().getClass());
	}

}
