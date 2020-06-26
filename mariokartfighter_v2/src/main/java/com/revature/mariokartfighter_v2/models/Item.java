package com.revature.mariokartfighter_v2.models;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private String itemID;
	private String itemName;
	private String typeThatCanUse;
	private int unlockAtLevel;
	private int bonusToHealth;
	private double bonusToAttack;
	private double bonusToDefense;
	private String itemImage;
	
	public Item(String itemName, String typeThatCanUse, int unlockAtLevel, int bonusToHealth, double bonusToAttack, double bonusToDefense) {
		this.itemID = "";
		this.itemName = itemName;
		this.typeThatCanUse = typeThatCanUse;
		this.unlockAtLevel = unlockAtLevel;
		this.bonusToHealth = bonusToHealth;
		this.bonusToAttack = bonusToAttack;
		this.bonusToDefense = bonusToDefense;
	}

	public Item(String itemName, String typeThatCanUse, int unlockAtLevel, int bonusToHealth, double bonusToAttack, 
			double bonusToDefense, String itemImage) {
		this.itemID = "";
		this.itemName = itemName;
		this.typeThatCanUse = typeThatCanUse;
		this.unlockAtLevel = unlockAtLevel;
		this.bonusToHealth = bonusToHealth;
		this.bonusToAttack = bonusToAttack;
		this.bonusToDefense = bonusToDefense;
		this.itemImage = itemImage;
	}
	
	public Item(String id, String itemName, String typeThatCanUse, int unlockAtLevel, int bonusToHealth, double bonusToAttack, double bonusToDefense) {
		this.itemID = id;
		this.itemName = itemName;
		this.typeThatCanUse = typeThatCanUse;
		this.unlockAtLevel = unlockAtLevel;
		this.bonusToHealth = bonusToHealth;
		this.bonusToAttack = bonusToAttack;
		this.bonusToDefense = bonusToDefense;
	}

	public Item() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bonusToAttack);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(bonusToDefense);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + bonusToHealth;
		result = prime * result + ((itemID == null) ? 0 : itemID.hashCode());
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((typeThatCanUse == null) ? 0 : typeThatCanUse.hashCode());
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
		Item other = (Item) obj;
		if (Double.doubleToLongBits(bonusToAttack) != Double.doubleToLongBits(other.bonusToAttack))
			return false;
		if (Double.doubleToLongBits(bonusToDefense) != Double.doubleToLongBits(other.bonusToDefense))
			return false;
		if (bonusToHealth != other.bonusToHealth)
			return false;
		if (itemID == null) {
			if (other.itemID != null)
				return false;
		} else if (!itemID.equals(other.itemID))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (typeThatCanUse == null) {
			if (other.typeThatCanUse != null)
				return false;
		} else if (!typeThatCanUse.equals(other.typeThatCanUse))
			return false;
		if (unlockAtLevel != other.unlockAtLevel)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s", 
				itemID, itemName, typeThatCanUse, unlockAtLevel, bonusToHealth, bonusToAttack, bonusToDefense);
	}
	
	public String getInfoString() {
		String printString = "";
		printString += "\tItem ID: " + this.itemID + "\n";
		printString += "\tName: " + this.itemName + "\n";
		printString += "\tType That Can Use: " + this.typeThatCanUse + "\n";
		printString += "\tBonus to Attack: " + this.bonusToAttack + "\n";
		printString += "\tBonus to Defense: " + this.bonusToDefense + "\n";
		printString += "\tBonus to Health: " + this.bonusToHealth + "\n";
		printString += "\tUnlock at level: " + this.unlockAtLevel;
		return printString;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setName(String name) {
		this.itemName = name;
	}

	public String getTypeThatCanUse() {
		return typeThatCanUse;
	}

	public void setTypeThatCanUse(String typeThatCanUse) {
		this.typeThatCanUse = typeThatCanUse;
	}

	public int getUnlockAtLevel() {
		return unlockAtLevel;
	}

	public void setUnlockAtLevel(int unlockAtLevel) {
		this.unlockAtLevel = unlockAtLevel;
	}

	public int getBonusToHealth() {
		return bonusToHealth;
	}

	public void setBonusToHealth(int bonusToHealth) {
		this.bonusToHealth = bonusToHealth;
	}

	public double getBonusToAttack() {
		return bonusToAttack;
	}

	public void setBonusToAttack(double bonusToAttack) {
		this.bonusToAttack = bonusToAttack;
	}

	public double getBonusToDefense() {
		return bonusToDefense;
	}

	public void setBonusToDefense(double bonusToDefense) {
		this.bonusToDefense = bonusToDefense;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
