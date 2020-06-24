package com.revature.mariokartfighter_v2.models;

import java.io.Serializable;

public class PlayableCharacter implements Serializable {
	private static final long serialVersionUID = 1L;
	private String characterID;
	private String type;
	private String characterName;
	private int maxHealth;
	private double attackStat;
	private double defenseStat;
	private int unlockAtLevel;
	
	public PlayableCharacter(String type, String name, int maxHealth, double attackStat, double defenseStat, int unlockAtLevel) {
		this.characterID = "";
		this.type = type;
		this.characterName = name;
		this.maxHealth = maxHealth;
		this.attackStat = attackStat;
		this.defenseStat = defenseStat;
		this.unlockAtLevel = unlockAtLevel;
	}
	
	public PlayableCharacter(String id, String type, String name, int maxHealth, double attackStat, double defenseStat, int unlockAtLevel) {
		this.characterID = id;
		this.type = type;
		this.characterName = name;
		this.maxHealth = maxHealth;
		this.attackStat = attackStat;
		this.defenseStat = defenseStat;
		this.unlockAtLevel = unlockAtLevel;
	}

	public PlayableCharacter() {
		// TODO Auto-generated constructor stub
		this.characterID = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(attackStat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((characterID == null) ? 0 : characterID.hashCode());
		result = prime * result + ((characterName == null) ? 0 : characterName.hashCode());
		temp = Double.doubleToLongBits(defenseStat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + maxHealth;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + unlockAtLevel;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayableCharacter other = (PlayableCharacter) obj;
		if (Double.doubleToLongBits(attackStat) != Double.doubleToLongBits(other.attackStat))
			return false;
		if (characterID == null) {
			if (other.characterID != null)
				return false;
		} else if (!characterID.equals(other.characterID))
			return false;
		if (characterName == null) {
			if (other.characterName != null)
				return false;
		} else if (!characterName.equals(other.characterName))
			return false;
		if (Double.doubleToLongBits(defenseStat) != Double.doubleToLongBits(other.defenseStat))
			return false;
		if (maxHealth != other.maxHealth)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unlockAtLevel != other.unlockAtLevel)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s", 
				characterID, characterName, type, unlockAtLevel, maxHealth, attackStat, defenseStat);
	}
	
	public String getInfoString() {
		String printString = "";
		printString += "\tCharacter ID: " + this.characterID + "\n";
		printString += "\tName: " + this.characterName + "\n";
		printString += "\tType: " + this.type + "\n";
		printString += "\tAttack Stat: " + this.attackStat + "\n";
		printString += "\tDefense Stat: " + this.defenseStat + "\n";
		printString += "\tMax Health: " + this.maxHealth + "\n";
		printString += "\tUnlock at level: " + this.unlockAtLevel;
		return printString;
	}


	public String getCharacterID() {
		return characterID;
	}

	public void setCharacterID(String characterID) {
		this.characterID = characterID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String name) {
		this.characterName = name;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getAttackStat() {
		return attackStat;
	}

	public void setAttackStat(double attackStat) {
		this.attackStat = attackStat;
	}

	public double getDefenseStat() {
		return defenseStat;
	}

	public void setDefenseStat(double defenseStat) {
		this.defenseStat = defenseStat;
	}

	public int getUnlockAtLevel() {
		return unlockAtLevel;
	}

	public void setUnlockAtLevel(int unlockAtLevel) {
		this.unlockAtLevel = unlockAtLevel;
	}
}
