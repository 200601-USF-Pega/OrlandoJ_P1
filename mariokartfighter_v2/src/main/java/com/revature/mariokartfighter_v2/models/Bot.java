package com.revature.mariokartfighter_v2.models;

public class Bot {
	private PlayableCharacter selectedCharacter;
	private Item selectedItem;
	private int level;
	private String id;
	
	public Bot(int level, PlayableCharacter selectedCharacter, Item selectedItem) {
		//choose random character and item for bot
		this.selectedCharacter = selectedCharacter;
		this.selectedItem = selectedItem;
		this.level = level;
		if (this.id == null) {			
			this.id = this.generateBotID();
		}
	}
	
	public Bot(int level) {
		this.level = level;
		this.id = this.generateBotID();
	}
	
	public Bot(){
		super();
	}
	
	@Override
	public String toString() {
		return "Bot [selectedCharacter=" + selectedCharacter + ", selectedItem=" + selectedItem + ", level=" + level
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result + ((selectedCharacter == null) ? 0 : selectedCharacter.hashCode());
		result = prime * result + ((selectedItem == null) ? 0 : selectedItem.hashCode());
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
		Bot other = (Bot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
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
		return true;
	}

	public String generateBotID() {
		String botID = "bot_" + Integer.toString(this.getLevel()) + "_" + System.currentTimeMillis();
		return botID;
	}

	public PlayableCharacter getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(PlayableCharacter selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}
	
	public String getBotID() {
		return this.id;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
