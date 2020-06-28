package com.revature.mariokartfighter_v2.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.IPlayerRepo;
import com.revature.mariokartfighter_v2.models.Player;

public class PlayerService {
	IPlayerRepo repo;
	private static final Logger logger = LogManager.getLogger(PlayerService.class);
	
	public PlayerService (IPlayerRepo repo) {
		this.repo = repo;
	}
	
	public boolean checkPassword(String playerID, String password) {
		Map<String,String> retrievedPlayers = repo.getAllPlayersWithPasswords();
		for(Map.Entry<String, String> p : retrievedPlayers.entrySet()) {
			if (p.getKey().equals(playerID)) {
				if (p.getValue().equals(password)) {
					logger.info("player " + playerID + "'s password matched");
					return true;
				} else {
					System.out.println("Incorrect password...try again");
					return false;
				}
			} 
		}
		logger.warn("player " + playerID + " does not exist");
		return false;
	}
	
	public String createNewPlayer(String inputtedID, String inputtedPassword) {		
		//check if player exists in main menu
		Player newPlayer = new Player(inputtedID);
		Player addedPlayer = repo.addPlayer(newPlayer, inputtedPassword);
		logger.info("new player created with ID " + inputtedID);
		return addedPlayer.getPlayerID();
	}

	public void printPlayers() {
		System.out.println(String.format("%-20s|%-15s|%-15s|%-15s|%-15s|%-20s|%-20s\n", 
				"playerID", "level", "xpEarned", "numberOfWins", "numberOfMatches", 
				"selectedCharacterName", "selectedItemName"));
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			System.out.println(p);
		}
		logger.info("retrieved all players");
	}
	
	public void printPlayersToFight(String playerID) {
		System.out.println(String.format("%-20s|%-15s|%-15s|%-15s|%-15s|%-20s|%-20s\n", 
				"playerID", "level", "xpEarned", "numberOfWins", "numberOfMatches", 
				"CharacterName", "ItemName"));
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				continue;
			} else if (p.getSelectedCharacter() == null || p.getSelectedItem() == null)  {
				continue;
			}
			System.out.println(p);
		}
		logger.info("displayed players with characters and items selected");
	}
	
	public void printPlayerInfo(String playerID) {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				System.out.println("\tLevel: " + p.getLevel());
				System.out.println("\tXP: " + p.getXpEarned());
				int rank  = repo.getPlayerRank(playerID);
				System.out.println("\tRank: " + rank + "/" + retrievedPlayers.size());
				if (p.getSelectedCharacter() == null) {					
					System.out.println("\tSelected Character: <None selected>");
				} else {
					System.out.println("\tSelected Character: " + p.getSelectedCharacter().getCharacterName());
				}
				if (p.getSelectedItem() == null ) {
					System.out.println("\tSelected Item: <None selected>");					
				} else {
					System.out.println("\tSelected Item: " + p.getSelectedItem().getItemName());
				}
				logger.info("retrieved info for player " + playerID);
				return;
			}
		}
		logger.warn("player with ID " + playerID + " not found");
	}
	
	public Player chooseClosestPlayer(Player player1) {
		//choose player with closest level
		int xpDiff = 1000000;
		Player player2 = new Player("");
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(Math.abs(p.getXpEarned() - player1.getXpEarned()) < xpDiff) {
				//check if player2 has selected an item and character
				if (p.getSelectedCharacter() == null || p.getSelectedItem() == null 
						|| p.getPlayerID().equals(player1.getPlayerID())) {
					continue;
				}
				player2 = p;
				xpDiff = Math.abs(p.getXpEarned() - player1.getXpEarned());
			} 
		}
		logger.info("chose " + player2.getPlayerID() + " as closest player with xpDiff of " + xpDiff);
		return player2;
	}
	
	public Player getPlayerObject(String playerID) throws RuntimeException {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				logger.info("retrieved player object for " + playerID);
				return p;
			}
		}
		logger.warn("player " + playerID + "does not exist");
		throw new RuntimeException("player does not exist");
	}
	
	public boolean checkPlayerExists(String playerID) {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				logger.info("checked that player " + playerID + " exists");
				return true;
			}
		}
		logger.warn("player " + playerID + " does not exist");
		return false;
	}
	
	public void removeTestPlayers(String testName) {
		logger.info("removed all players with playerID starting with " + testName + " from repo");
		repo.removePlayers(testName);
	}
}
