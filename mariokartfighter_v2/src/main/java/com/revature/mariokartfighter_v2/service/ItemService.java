package com.revature.mariokartfighter_v2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.IItemRepo;
import com.revature.mariokartfighter_v2.models.Item;

public class ItemService {
	ValidationService validation;
	IItemRepo repo;
	private static final Logger logger = LogManager.getLogger(ItemService.class);
	
	public ItemService (IItemRepo repo) {
		this.repo = repo;
		this.validation = new ValidationService();
	}
	
	public String createNewItem() {
		String name;
		String typeThatCanUse;
		int unlockAtLevel;
		int bonusToHealth; 
		double bonusToAttack;
		double bonusToDefense;
		
		System.out.println("Name: ");
		name = validation.getValidString();
		
		//check type is allowed
		boolean gotAllowedType = false;
		List<String> allowedTypes = new ArrayList<String>();
		allowedTypes.add("skill");
		allowedTypes.add("all-around");
		allowedTypes.add("speed");
		allowedTypes.add("power");
		
		System.out.println("Type that can use (choose from skill, all-around, speed, power):");
		do {
			typeThatCanUse = validation.getValidString();
			if (allowedTypes.contains(typeThatCanUse.toLowerCase())) {
				gotAllowedType = true;
				break;
			} else {
				System.out.println("Not a valid character type...try again");
			}
		} while (gotAllowedType);
		
		System.out.println("Bonus to Health: ");
		bonusToHealth = validation.getValidInt();
		
		System.out.println("Bonus to Attack: ");
		bonusToAttack = validation.getValidDouble();
		
		System.out.println("Bonus to Defense: ");
		bonusToDefense = validation.getValidDouble();
		
		//choose unlock level based on stats
		unlockAtLevel = (int)(bonusToHealth + bonusToAttack + bonusToDefense)/3;
		String itemID = generateItemID();
		Item newItem = new Item(itemID, name, typeThatCanUse, unlockAtLevel, bonusToHealth, bonusToAttack, bonusToDefense);
		repo.addItem(newItem);
		
		logger.info("new item created with ID " + itemID);
		return itemID;
	}
	
	public String generateItemID() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789" + "abcdefghijklmnopqrstuvxyz"; 
		
		//choose a random length up to 15 characters 
		Random random = new Random();
		int n = random.nextInt(14) + 1;
				
		StringBuilder sb;
		do {
			sb = new StringBuilder(n); 
	        for (int i = 0; i < n; i++) { 
	            int index = (int)(alphaNumericString.length()  * Math.random()); 
	            sb.append(alphaNumericString.charAt(index)); 
	        } 
		} while(checkItemExists(sb.toString()));
		
		return sb.toString();
	}
	
	public void printAllItems() {
		System.out.println(String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s\n", 
				"itemID", "itemName", "typeThatCanUse", "unlockAtLevel", "bonusToHealth", 
				"bonusToAttack", "bonusToDefense"));
		List<Item> retrievedItems = repo.getAllItems();
		for(Item i : retrievedItems) {
			System.out.println(i);
		}
		logger.info("retrieved all items");
	}
	
	public void printSomeItems(int level) {
		System.out.println(String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s\n", 
				"itemID", "itemName", "typeThatCanUse", "unlockAtLevel", "bonusToHealth", 
				"bonusToAttack", "bonusToDefense"));
		List<Item> retrievedItems = repo.getSomeItems(level);
		for(Item i : retrievedItems) {
			System.out.println(i);
		}
		logger.info("retrieved unlocked items");
	}
	
	public boolean checkItemExists(String itemID) {
		List<Item> retrievedItems = repo.getAllItems();
		for(Item i : retrievedItems) {
			if (i.getItemID().equals(itemID)) {
				logger.info("checked that item " + itemID + " exists");
				return true;
			}
		}
		logger.warn("item " + itemID + "does not exist");
		return false;
	}
	
	public void printItemInfo(String itemName) {
		List<Item> retrievedItems = repo.getAllItems();
		for(Item i : retrievedItems) {
			if (i.getItemName().equals(itemName)) {
				System.out.println(i.getInfoString());
				logger.info("retrieved info for item " + itemName);
				return;
			}
		}
		logger.warn("item with name " + itemName + " not found");
	}
	
	public void removeTestItems(String testName) {
		logger.info("removed all items with itemID starting with " + testName + " from repo");
		repo.removeItems(testName);
	}
}
