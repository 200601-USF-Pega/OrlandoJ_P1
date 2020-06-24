package com.revature.mariokartfighter_v2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.mariokartfighter_v2.models.Bot;
import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.models.Player;
import com.revature.mariokartfighter_v2.web.ConnectionService;

public class PlayerRepoDB implements IPlayerRepo {
	
	@Override
	public Player addPlayer(Player player, String password) {
		try {			
			PreparedStatement playerInsert = ConnectionService.getConnection().prepareStatement(
					"INSERT INTO player VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			playerInsert.setString(1, player.getPlayerID());
			playerInsert.setString(2, password);
			playerInsert.setInt(3, player.getLevel());
			playerInsert.setInt(4, player.getXpEarned());
			playerInsert.setInt(5, player.getNumberOfMatches());
			playerInsert.setInt(6, player.getNumberOfWins());
			
			if(player.getSelectedCharacter() != null) {				
				playerInsert.setString(7, player.getSelectedCharacter().getCharacterID());
			} else {
				playerInsert.setString(7, null);
			}
			if(player.getSelectedItem() != null) {				
				playerInsert.setString(8, player.getSelectedItem().getItemID());
			} else { 
				playerInsert.setString(8, null);
			}				
			
			playerInsert.execute();
			
			return player;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Bot addBot(Bot bot) {
		try {			
			PreparedStatement botInsert = ConnectionService.getConnection().prepareStatement(
					"INSERT INTO player VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			botInsert.setString(1, bot.getBotID());
			botInsert.setString(2, "bot_password");
			botInsert.setInt(3, bot.getLevel());
			botInsert.setInt(4, 0);
			botInsert.setInt(5, 0);
			botInsert.setInt(6, 0);
			
			if(bot.getSelectedCharacter() != null) {				
				botInsert.setString(7, bot.getSelectedCharacter().getCharacterID());
			} else {
				botInsert.setString(7, null);
			}
			if(bot.getSelectedItem() != null) {				
				botInsert.setString(8, bot.getSelectedItem().getItemID());
			} else { 
				botInsert.setString(8, null);
			}				
			
			botInsert.execute();
			
			return bot;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Player> getAllPlayers() {
		try {
			//get all players that are not bots
			PreparedStatement getPlayers = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM player WHERE playerID NOT LIKE ?;");
			getPlayers.setString(1, "bot_%");
			ResultSet playersRS = getPlayers.executeQuery();
			
			PreparedStatement getPlayersCharacter = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM playablecharacter WHERE characterID = ?;");
			PreparedStatement getPlayersItem = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM item WHERE itemID = ?;");
			
			List<Player> retrievedPlayers = new ArrayList<Player>();
			
			while(playersRS.next()) {
				getPlayersCharacter.setString(1, playersRS.getString("selectedCharacterID"));
				ResultSet playerCharRS = getPlayersCharacter.executeQuery();
				
				getPlayersItem.setString(1, playersRS.getString("selectedItemID"));
				ResultSet playerItemRS = getPlayersItem.executeQuery();
				
				PlayableCharacter playerCharacter = null;
				Item playerItem = null;
				while(playerCharRS.next()) {
					playerCharacter = new PlayableCharacter(playersRS.getString("selectedCharacterID"), 
						playerCharRS.getString("characterType"),
						playerCharRS.getString("name"),	
						playerCharRS.getInt("maxHealth"), 
						playerCharRS.getDouble("attackStat"), 
						playerCharRS.getDouble("defenseStat"), 
						playerCharRS.getInt("unlockAtLevel"));
				}
				while(playerItemRS.next()) {
					playerItem = new Item(playersRS.getString("selectedItemID"), 
						playerItemRS.getString("name"), 
						playerItemRS.getString("typeThatCanUse"), 
						playerItemRS.getInt("unlockAtLevel"), 
						playerItemRS.getInt("bonusToHealth"), 
						playerItemRS.getDouble("bonusToAttack"), 
						playerItemRS.getDouble("bonusToDefense"));
				}
				
				Player newPlayer;
				if (playerCharacter != null && playerItem != null) {
					newPlayer = new Player(
						playersRS.getString("playerID"),
						playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
						playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
						playerCharacter, playerItem);	
				} else if(playerCharacter != null) {
					newPlayer = new Player(
							playersRS.getString("playerID"),
							playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
							playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
							playerCharacter, null);	
				} else {
					newPlayer = new Player(
							playersRS.getString("playerID"),
							playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
							playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
							null, null);	
				}
				retrievedPlayers.add(newPlayer);
			}	
			return retrievedPlayers;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Player>();
	}

	@Override
	public void assignCharacterToPlayer(PlayableCharacter character, String playerID) {
		try {			
			PreparedStatement getPlayers = ConnectionService.getConnection().prepareStatement(
					"UPDATE player "
					+ "SET selectedCharacterID = ? "
					+ "WHERE playerID = ?;");
			getPlayers.setString(1, character.getCharacterID());
			getPlayers.setString(2, playerID);
			
			getPlayers.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void assignItemToPlayer(Item item, String playerID) {
		try {			
			PreparedStatement getPlayers = ConnectionService.getConnection().prepareStatement(
					"UPDATE player "
					+ "SET selectedItemID = ? "
					+ "WHERE playerID = ?;");
			if (item == null) {
				getPlayers.setString(1, null);
			} else {
				getPlayers.setString(1, item.getItemID());
			}
			getPlayers.setString(2, playerID);
			
			getPlayers.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateAfterFight(boolean wonMatch, String playerID) {
		boolean leveledUp = false;
		try {
			List<Player> allPlayers = this.getAllPlayers();
			Player currentPlayer = null;
			for (Player p : allPlayers) {
				if (p.getPlayerID().equals(playerID)) {
					if (wonMatch) {
						p.setXpEarned(p.getXpEarned() + 100);					
					} else {
						p.setXpEarned(p.getXpEarned() + 50);	
					}
					//check for level up
					if(p.getXpEarned() >= (p.getLevel()*100)+1) {
						p.setLevel(p.getLevel()+1);
						leveledUp = true;
					}
					currentPlayer = p;
					break;
				}
			}
			if(currentPlayer == null) {
				throw new SQLException("player does not exist");
			}
			
			PreparedStatement updatePlayer = ConnectionService.getConnection().prepareStatement(
					"UPDATE player "
					+ "SET xpEarned = ?, xpLevel = ?"
					+ "WHERE playerID = ?;");
			
			updatePlayer.setInt(1, currentPlayer.getXpEarned());
			updatePlayer.setInt(2, currentPlayer.getLevel());
			updatePlayer.setString(3, currentPlayer.getPlayerID());
			
			updatePlayer.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return leveledUp;
	}

	@Override
	public int getPlayerRank(String playerID) {
		try {
			List<Player> allPlayers = this.getAllPlayers();
			Player currentPlayer = null;
			for (Player p : allPlayers) {
				if (p.getPlayerID().equals(playerID)) {
					currentPlayer = p;
					break;
				}
			}
			if(currentPlayer == null) {
				throw new SQLException("player does not exist");
			}
			
			PreparedStatement getPlayersSorted = ConnectionService.getConnection().prepareStatement(
					"SELECT * "
					+ "FROM player "
					+ "WHERE playerid NOT LIKE ? "
					+ "ORDER BY xpLevel DESC, xpEarned DESC;");
			getPlayersSorted.setString(1, "bot_%");
			ResultSet playerSortedRS = getPlayersSorted.executeQuery();
			int rank = 1;
			while (playerSortedRS.next()) {
				//look for player and count how many people theyre below
				if(playerSortedRS.getString("playerID").equals(playerID)) {
					return rank;
				}
				rank++;
			}
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void removePlayers(String name) {
		try {
			PreparedStatement removePlayers = ConnectionService.getConnection().prepareStatement(
					"DELETE FROM player "
					+ "WHERE playerID LIKE ?");
			removePlayers.setString(1, name+'%');
			removePlayers.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Map<String,String> getAllPlayersWithPasswords() {
		try {
			//get all players that are not bots
			PreparedStatement getPlayers = ConnectionService.getConnection().prepareStatement(
					"SELECT playerID, password FROM player WHERE playerID NOT LIKE ?;");
			getPlayers.setString(1, "bot_%");
			ResultSet playersRS = getPlayers.executeQuery();
			
			
			Map<String,String> retrievedPlayers = new HashMap<String,String>();
			
			while(playersRS.next()) {
				retrievedPlayers.put(playersRS.getString("playerID"), 
						playersRS.getString("password"));
			}
			return retrievedPlayers;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new HashMap<String,String>();
	}

	@Override
	public Player getPlayerInfo(String playerID) {
		try {
			PreparedStatement getPlayer = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM player WHERE playerID = ?;");
			getPlayer.setString(1, playerID);
			ResultSet playersRS = getPlayer.executeQuery();
			
			PreparedStatement getPlayersCharacter = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM playablecharacter WHERE characterID = ?;");
			PreparedStatement getPlayersItem = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM item WHERE itemID = ?;");
			
			while(playersRS.next()) {
				getPlayersCharacter.setString(1, playersRS.getString("selectedCharacterID"));
				ResultSet playerCharRS = getPlayersCharacter.executeQuery();
				
				getPlayersItem.setString(1, playersRS.getString("selectedItemID"));
				ResultSet playerItemRS = getPlayersItem.executeQuery();
				
				PlayableCharacter playerCharacter = null;
				Item playerItem = null;
				while(playerCharRS.next()) {
					playerCharacter = new PlayableCharacter(playersRS.getString("selectedCharacterID"), 
						playerCharRS.getString("characterType"),
						playerCharRS.getString("name"),	
						playerCharRS.getInt("maxHealth"), 
						playerCharRS.getDouble("attackStat"), 
						playerCharRS.getDouble("defenseStat"), 
						playerCharRS.getInt("unlockAtLevel"));
				}
				while(playerItemRS.next()) {
					playerItem = new Item(playersRS.getString("selectedItemID"), 
						playerItemRS.getString("name"), 
						playerItemRS.getString("typeThatCanUse"), 
						playerItemRS.getInt("unlockAtLevel"), 
						playerItemRS.getInt("bonusToHealth"), 
						playerItemRS.getDouble("bonusToAttack"), 
						playerItemRS.getDouble("bonusToDefense"));
				}
				
				Player newPlayer;
				if (playerCharacter != null && playerItem != null) {
					newPlayer = new Player(
						playersRS.getString("playerID"),
						playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
						playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
						playerCharacter, playerItem);	
				} else if(playerCharacter != null) {
					newPlayer = new Player(
							playersRS.getString("playerID"),
							playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
							playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
							playerCharacter, null);	
				} else {
					newPlayer = new Player(
							playersRS.getString("playerID"),
							playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
							playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
							null, null);	
				}
				return newPlayer;
			}	
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new Player();
	}

}
