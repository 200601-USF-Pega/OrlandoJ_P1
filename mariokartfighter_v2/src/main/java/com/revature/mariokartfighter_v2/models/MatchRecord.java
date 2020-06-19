package com.revature.mariokartfighter_v2.models;

import java.sql.Timestamp;

public class MatchRecord {
	private String matchID;
	private Timestamp timeOfMatch;
	private String player1ID;
	private String player1CharacterID;
	private String player1ItemID;
	private String player2ID;
	private String player2CharacterID;
	private String player2ItemID;
	private boolean player2IsBot;
	private String winnerID;
	
	public MatchRecord(String matchID, String player1id, String player1CharacterID,
			String player1ItemID, String player2id, String player2CharacterID, String player2ItemID,
			boolean player2IsBot, String winnerID) {
		this.matchID = matchID;
		this.timeOfMatch = new Timestamp(System.currentTimeMillis());
		player1ID = player1id;
		this.player1CharacterID = player1CharacterID;
		this.player1ItemID = player1ItemID;
		player2ID = player2id;
		this.player2CharacterID = player2CharacterID;
		this.player2ItemID = player2ItemID;
		this.player2IsBot = player2IsBot;
		this.winnerID = winnerID;
	}

	public MatchRecord(String matchID, Timestamp timeOfMatch, String player1id, String player1CharacterID,
			String player1ItemID, String player2id, String player2CharacterID, String player2ItemID,
			boolean player2IsBot, String winnerID) {
		this.matchID = matchID;
		this.timeOfMatch = timeOfMatch;
		player1ID = player1id;
		this.player1CharacterID = player1CharacterID;
		this.player1ItemID = player1ItemID;
		player2ID = player2id;
		this.player2CharacterID = player2CharacterID;
		this.player2ItemID = player2ItemID;
		this.player2IsBot = player2IsBot;
		this.winnerID = winnerID;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchID == null) ? 0 : matchID.hashCode());
		result = prime * result + ((player1CharacterID == null) ? 0 : player1CharacterID.hashCode());
		result = prime * result + ((player1ID == null) ? 0 : player1ID.hashCode());
		result = prime * result + ((player1ItemID == null) ? 0 : player1ItemID.hashCode());
		result = prime * result + ((player2CharacterID == null) ? 0 : player2CharacterID.hashCode());
		result = prime * result + ((player2ID == null) ? 0 : player2ID.hashCode());
		result = prime * result + (player2IsBot ? 1231 : 1237);
		result = prime * result + ((player2ItemID == null) ? 0 : player2ItemID.hashCode());
		result = prime * result + ((timeOfMatch == null) ? 0 : timeOfMatch.hashCode());
		result = prime * result + ((winnerID == null) ? 0 : winnerID.hashCode());
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
		MatchRecord other = (MatchRecord) obj;
		if (matchID == null) {
			if (other.matchID != null)
				return false;
		} else if (!matchID.equals(other.matchID))
			return false;
		if (player1CharacterID == null) {
			if (other.player1CharacterID != null)
				return false;
		} else if (!player1CharacterID.equals(other.player1CharacterID))
			return false;
		if (player1ID == null) {
			if (other.player1ID != null)
				return false;
		} else if (!player1ID.equals(other.player1ID))
			return false;
		if (player1ItemID == null) {
			if (other.player1ItemID != null)
				return false;
		} else if (!player1ItemID.equals(other.player1ItemID))
			return false;
		if (player2CharacterID == null) {
			if (other.player2CharacterID != null)
				return false;
		} else if (!player2CharacterID.equals(other.player2CharacterID))
			return false;
		if (player2ID == null) {
			if (other.player2ID != null)
				return false;
		} else if (!player2ID.equals(other.player2ID))
			return false;
		if (player2IsBot != other.player2IsBot)
			return false;
		if (player2ItemID == null) {
			if (other.player2ItemID != null)
				return false;
		} else if (!player2ItemID.equals(other.player2ItemID))
			return false;
		if (timeOfMatch == null) {
			if (other.timeOfMatch != null)
				return false;
		} else if (!timeOfMatch.equals(other.timeOfMatch))
			return false;
		if (winnerID == null) {
			if (other.winnerID != null)
				return false;
		} else if (!winnerID.equals(other.winnerID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-25s|%-20s|%-20s|%-20s|%-25s|%-20s|%-20s|%-20s", 
				timeOfMatch, player1ID, player1CharacterID, player1ItemID, player2ID, player2CharacterID,
				player2ItemID, winnerID);
	}

	public String getMatchID() {
		return matchID;
	}

	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}

	public Timestamp getTimeOfMatch() {
		return timeOfMatch;
	}

	public void setTimeOfMatch(Timestamp timeOfMatch) {
		this.timeOfMatch = timeOfMatch;
	}

	public String getPlayer1ID() {
		return player1ID;
	}

	public void setPlayer1ID(String player1id) {
		player1ID = player1id;
	}

	public String getPlayer1CharacterID() {
		return player1CharacterID;
	}

	public void setPlayer1CharacterID(String player1CharacterID) {
		this.player1CharacterID = player1CharacterID;
	}

	public String getPlayer1ItemID() {
		return player1ItemID;
	}

	public void setPlayer1ItemID(String player1ItemID) {
		this.player1ItemID = player1ItemID;
	}

	public String getPlayer2ID() {
		return player2ID;
	}

	public void setPlayer2ID(String player2id) {
		player2ID = player2id;
	}

	public String getPlayer2CharacterID() {
		return player2CharacterID;
	}

	public void setPlayer2CharacterID(String player2CharacterID) {
		this.player2CharacterID = player2CharacterID;
	}

	public String getPlayer2ItemID() {
		return player2ItemID;
	}

	public void setPlayer2ItemID(String player2ItemID) {
		this.player2ItemID = player2ItemID;
	}

	public boolean isPlayer2IsBot() {
		return player2IsBot;
	}

	public void setPlayer2IsBot(boolean player2IsBot) {
		this.player2IsBot = player2IsBot;
	}

	public String getWinnerID() {
		return winnerID;
	}

	public void setWinnerID(String winnerID) {
		this.winnerID = winnerID;
	}
	
	
}
