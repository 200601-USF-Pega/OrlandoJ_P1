package com.revature.mariokartfighter_v2.models;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String playerID;
	private int level;
	private int xpEarned;
	private int numberOfWins;
	private int numberOfMatches;
	private PlayableCharacter selectedCharacter;
	private Item selectedItem;
	private int rank;
	
	public Player(String playerID) {
		this.playerID = playerID;
		this.level = 1;
		this.xpEarned = 0;
		this.numberOfWins = 0;
		this.numberOfMatches = 0;
		this.selectedCharacter = null;
		this.selectedItem = null;
	}

	public Player(String playerID, int level, int xpEarned, int numberOfWins, int numberOfMatches,
			PlayableCharacter selectedCharacter, Item selectedItem) {
		super();
		this.playerID = playerID;
		this.level = level;
		this.xpEarned = xpEarned;
		this.numberOfWins = numberOfWins;
		this.numberOfMatches = numberOfMatches;
		this.selectedCharacter = selectedCharacter;
		this.selectedItem = selectedItem;
	}

	public Player() {
		// TODO Auto-generated constructor stub
		this.playerID = null;
	}

	public String getPlayerID() {
		return playerID;
	}

	@Override
	public String toString() {
		return String.format("%-20s|%-15s|%-15s|%-15s|%-15s|%-20s|%-20s", 
				playerID, level, xpEarned, numberOfWins, numberOfMatches, 
				selectedCharacter.getCharacterName(), selectedItem.getItemName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + level;
		result = prime * result + numberOfMatches;
		result = prime * result + numberOfWins;
		result = prime * result + ((playerID == null) ? 0 : playerID.hashCode());
		result = prime * result + ((selectedCharacter == null) ? 0 : selectedCharacter.hashCode());
		result = prime * result + ((selectedItem == null) ? 0 : selectedItem.hashCode());
		result = prime * result + xpEarned;
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
		Player other = (Player) obj;
		if (level != other.level)
			return false;
		if (numberOfMatches != other.numberOfMatches)
			return false;
		if (numberOfWins != other.numberOfWins)
			return false;
		if (playerID == null) {
			if (other.playerID != null)
				return false;
		} else if (!playerID.equals(other.playerID))
			return false;
		if (selectedCharacter == null) {
			if (other.selectedCharacter != null)
				return false;
		} else if (!selectedCharacter.equals(other.selectedCharacter))
			return false;
		if (selectedItem == null) {
			if (other.selectedItem != null)
				return false;
		} else if (!selectedItem.equals(other.selectedItem))
			return false;
		if (xpEarned != other.xpEarned)
			return false;
		return true;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXpEarned() {
		return xpEarned;
	}

	public void setXpEarned(int xpEarned) {
		this.xpEarned = xpEarned;
	}

	public int getNumberOfWins() {
		return numberOfWins;
	}

	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	public int getNumberOfMatches() {
		return numberOfMatches;
	}

	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}

	public PlayableCharacter getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(PlayableCharacter selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
