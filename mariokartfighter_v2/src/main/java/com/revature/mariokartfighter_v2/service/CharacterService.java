package com.revature.mariokartfighter_v2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.ICharacterRepo;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;

public class CharacterService {
	ValidationService validation;
	ICharacterRepo repo;
	private static final Logger logger = LogManager.getLogger(CharacterService.class);
	
	public CharacterService (ICharacterRepo repo) {
		this.repo = repo;
		this.validation = new ValidationService();
	}
	
	public String createNewCharacter() {
		String type;
		String name;
		int maxHealth;
		double attackStat;
		double defenseStat;
		int unlockAtLevel;
		
		//TODO validate input
		System.out.println("Name:");
		name = validation.getValidString();
		
		//check type is allowed
		boolean gotAllowedType = false;
		List<String> allowedTypes = new ArrayList<String>();
		allowedTypes.add("skill");
		allowedTypes.add("all-around");
		allowedTypes.add("speed");
		allowedTypes.add("power");
		
		System.out.println("Type (choose from skill, all-around, speed, power):");
		do {
			type = validation.getValidString();
			if (allowedTypes.contains(type.toLowerCase())) {
				gotAllowedType = true;
				break;
			} else {
				System.out.println("Not a valid character type...try again");
			}
		} while (gotAllowedType);
		
		System.out.println("Max Health:");
		maxHealth = validation.getValidInt();
		
		System.out.println("Attack Stat:");
		attackStat = validation.getValidDouble();
		
		System.out.println("Defense Stat:");
		defenseStat = validation.getValidDouble();
		
		//choose unlock level based on stats
		unlockAtLevel = (int)(maxHealth%10 + attackStat + defenseStat)/3;
		
		String characterID = generateCharacterID();
		PlayableCharacter newCharacter = new PlayableCharacter(characterID, type, name, maxHealth, attackStat, defenseStat, unlockAtLevel);
		repo.addCharacter(newCharacter);
		
		logger.info("new character created with ID " + characterID);
		return characterID;
	}
	
	public String generateCharacterID() {
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
		} while(checkCharacterExists(sb.toString()));
		
		return sb.toString();
	}
	
	public void printAllCharacters() {
		System.out.println(String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s\n", 
				"characterID", "characterName", "type", "unlockAtLevel", "maxHealth", 
				"attackStat", "defenseStat"));
		List<PlayableCharacter> retrievedCharacters = repo.getAllCharacters();
		for(PlayableCharacter c : retrievedCharacters) {
			System.out.println(c);
		}
		logger.info("retrieved all characters");
	}
	
	public void printSomeCharacters(int level) {
		System.out.println(String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s\n", 
				"characterID", "characterName", "type", "unlockAtLevel", "maxHealth", 
				"attackStat", "defenseStat"));
		List<PlayableCharacter> retrievedCharacters = repo.getSomeCharacters(level);
		for(PlayableCharacter c : retrievedCharacters) {
			System.out.println(c);
		}
		logger.info("retrieved unlocked characters");
	}
	
	public boolean checkCharacterExists(String characterID) {
		List<PlayableCharacter> retrievedCharacters = repo.getAllCharacters();
		for(PlayableCharacter c : retrievedCharacters) {
			if (c.getCharacterID().equals(characterID)) {
				logger.info("checked that character " + characterID + " exists");
				return true;
			}
		}
		logger.warn("character " + characterID + "does not exist");
		return false;
	}
	
	public void printCharacterInfo(String characterName) {
		List<PlayableCharacter> retrievedCharacters = repo.getAllCharacters();
		for(PlayableCharacter c : retrievedCharacters) {
			if (c.getCharacterName().equals(characterName)) {
				System.out.println(c.getInfoString());
				logger.info("retrieved info for character " + characterName);
				return;
			}
		}
		logger.warn("character with name " + characterName + " not found");
	}
	
	public void removeTestCharacters(String testName) {
		logger.info("removed all players with characterID starting with " + testName + " from repo");
		repo.removeCharacters(testName);
	}
}

