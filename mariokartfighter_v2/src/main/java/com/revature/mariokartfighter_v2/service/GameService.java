package com.revature.mariokartfighter_v2.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.IItemRepo;
import com.revature.mariokartfighter_v2.dao.IMatchRecordRepo;
import com.revature.mariokartfighter_v2.dao.ICharacterRepo;
import com.revature.mariokartfighter_v2.dao.IPlayerRepo;
import com.revature.mariokartfighter_v2.models.Bot;
import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.models.MatchRecord;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.models.Player;

public class GameService {
	IPlayerRepo playerRepo;
	ICharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	private static final Logger logger = LogManager.getLogger(GameService.class);
	
	public GameService (IPlayerRepo playerRepo, ICharacterRepo characterRepo, IItemRepo itemRepo,
			IMatchRecordRepo matchRecordRepo) {
		this.playerRepo = playerRepo;
		this.characterRepo = characterRepo;
		this.itemRepo = itemRepo;
		this.matchRecordRepo = matchRecordRepo;
	}
	
	public boolean setCharacter(String characterName, String playerID) {
		//check if character exists
		List<PlayableCharacter> retrievedCharacters = characterRepo.getAllCharacters();
		for (PlayableCharacter c : retrievedCharacters) {
			if (characterName.equals(c.getCharacterName())) {
				//check if player has unlocked
				Player player = null;
				List<Player> retrievedPlayers = playerRepo.getAllPlayers();
				for (Player p : retrievedPlayers) {
					if (playerID.equals(p.getPlayerID())) {
						player = p;
						break;
					}
				}
				if (player != null && player.getLevel() >= c.getUnlockAtLevel()) {
					playerRepo.assignCharacterToPlayer(c, playerID);		
					logger.info("character " + c.getCharacterID() + " assigned to player " + playerID);
					
					if (player.getSelectedItem() != null && !player.getSelectedCharacter().getType().equals(
							player.getSelectedItem().getTypeThatCanUse())) {		
						System.out.println("Item type no longer compatible with character type!!");
						System.out.println("Unsetting selected item...");
						logger.warn("item " + player.getSelectedItem().getItemID() 
								+ " unset because type " + player.getSelectedItem().getTypeThatCanUse()
								+ " != " + c.getType());
						player.setSelectedItem(null);
						playerRepo.assignItemToPlayer(null, playerID);
						
					}
				} else if (player != null) {
					System.out.println("You haven't unlocked this character yet.");	
					System.out.println("**Earn more XP by playing matches**");
					return false;
				} else {
					System.out.println("player does not exist");
					logger.warn("player " + playerID + " does not exist");
					return false;
				}
				return true;
			}
		}
		logger.warn("set character " + characterName + " for player " + playerID + " failed");
		return false;
	}
	
	public boolean setItem(String itemName, String playerID) {
		//check if character exists
		List<Item> retrievedItems = itemRepo.getAllItems();
		for (Item i : retrievedItems) {
			if (itemName.equals(i.getItemName())) {
				//check if player has unlocked
				Player player = null;
				List<Player> retrievedPlayers = playerRepo.getAllPlayers();
				for (Player p : retrievedPlayers) {
					if (playerID.equals(p.getPlayerID())) {
						player = p;
						break;
					}
				}

				if (player != null) {
					//check if selectedCharacter can use
					 if (player.getLevel() < i.getUnlockAtLevel()) {
						System.out.println("You haven't unlocked this item yet.");	
						System.out.println("**Earn more XP by playing matches**");
						return false;
					} else if (!player.getSelectedCharacter().getType().equals(i.getTypeThatCanUse())) {		
						System.out.println("Item type not compatible with character type!!");
						System.out.println("\titem type: " + i.getTypeThatCanUse());
						System.out.println("\tcharacter type: " + player.getSelectedCharacter().getType());
						return false;
					} else {
						playerRepo.assignItemToPlayer(i, playerID);
						logger.info("item " + i.getItemID() + " assigned to player " + playerID);
					}
				} else {
					System.out.println("player does not exist");
					logger.warn("player " + playerID + " does not exist");
					return false;
				}
				return true;
			}
		}
		logger.warn("set item " + itemName + " for player " + playerID + " failed");
		return false;
	}
	
	public PlayableCharacter chooseRandomCharacter(int level) {
		List<PlayableCharacter> retrievedCharacters = characterRepo.getSomeCharacters(level);
		Random random = new Random();
		int n = random.nextInt(retrievedCharacters.size()-1);
		logger.info("chose character " + retrievedCharacters.get(n).getCharacterID());
		return retrievedCharacters.get(n);
	}
	
	public Item chooseRandomItem(int level, String characterType) {
		logger.info("looking for item that is compatible with " + characterType);
		List<Item> retrievedItems = itemRepo.getSomeItems(level);
		Random random = new Random();
		int n = random.nextInt(retrievedItems.size()-1);
		Item chosenItem = retrievedItems.get(n);
		//if incompatible type, choose again
		while(!chosenItem.getTypeThatCanUse().equals(characterType)) {
			logger.info("item " + chosenItem.getItemID() + " is wrong type ("
					+ chosenItem.getTypeThatCanUse() + ")...choosing again");
			n = random.nextInt(retrievedItems.size()-1);
			chosenItem = retrievedItems.get(n);
		}
		logger.info("chose item " + chosenItem.getItemID());
		return chosenItem;
	}
	
	public Bot createNewBot(int botLevel, PlayableCharacter randomCharacter, Item randomItem ) {
		Bot newBot = new Bot(botLevel, randomCharacter, randomItem);
		playerRepo.addBot(newBot);
		logger.info("new bot created with ID " + newBot.getBotID());
		return newBot;
	}
	
	public void botFight(Bot bot, String playerID) {
		//find player info
		List<Player> retrievedPlayers = playerRepo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if (playerID.equals(p.getPlayerID())) {		
				logger.info("player " + playerID + " is fighting " + bot.getBotID());
				PlayableCharacter playerChar = p.getSelectedCharacter();
				PlayableCharacter botChar = bot.getSelectedCharacter();
				Item playerItem = p.getSelectedItem();
				Item botItem = bot.getSelectedItem();
				int playerHealth = p.getSelectedCharacter().getMaxHealth();
				int botHealth = bot.getSelectedCharacter().getMaxHealth();
				
				System.out.println("Player 1 Character:");
				System.out.println(playerChar.getInfoString());
				System.out.println("Player 1 Item:");
				System.out.println(playerItem.getInfoString());
				
				System.out.println("Bot Character:");
				System.out.println(botChar.getInfoString());
				System.out.println("Bot Item:");
				System.out.println(botItem.getInfoString());
				
				while(botHealth > 0 && playerHealth > 0) {
					//strength is player attack - opponent defense
					double botStrength = botChar.getAttackStat() + botItem.getBonusToAttack()
						- (playerChar.getDefenseStat() + playerItem.getBonusToDefense());
					double playerStrength = playerChar.getAttackStat() + playerItem.getBonusToAttack()
						- (botChar.getDefenseStat() + botItem.getBonusToDefense());
					
					botHealth -= playerStrength;
					if(botHealth <= 0) {
						break;
					}
					playerHealth -= botStrength;
				}
				
				String winnerID;
				if(botHealth < playerHealth) {
					winnerID = p.getPlayerID();
					playerRepo.updateAfterFight(true, p.getPlayerID());
					System.out.println("Player 1 wins!!");
				} else {
					winnerID = bot.getBotID();
					System.out.println("Bot wins!!");
				}
				logger.info("player " + winnerID + " won the match");
				
				//save to repo
				MatchRecord newMatch = new MatchRecord(this.generateMatchID(), playerID, 
						p.getSelectedCharacter().getCharacterID(), 
						p.getSelectedItem().getItemID(),
						bot.getBotID(), bot.getSelectedCharacter().getCharacterID(),
						bot.getSelectedItem().getItemID(), true, winnerID);
				matchRecordRepo.addMatchRecord(newMatch);
				logger.info("match " + newMatch.getMatchID() + "added to repo");
				break;
			}
		}
	}
	
	public void playerFight(Player player1, Player player2) {	
		logger.info("player " + player1.getPlayerID() + " is fighting " + player2.getPlayerID());
		PlayableCharacter player1Char = player1.getSelectedCharacter();
		PlayableCharacter player2Char = player2.getSelectedCharacter();
		Item player1Item = player1.getSelectedItem();
		Item player2Item = player2.getSelectedItem();
		int player1Health = player1.getSelectedCharacter().getMaxHealth();
		int player2Health = player2.getSelectedCharacter().getMaxHealth();
		
		System.out.println("Player 1 Character:");
		System.out.println(player1Char.getInfoString());
		System.out.println("Player 1 Item:");
		System.out.println(player1Item.getInfoString());
		
		System.out.println("Player 2 Character:");
		System.out.println(player2Char.getInfoString());
		System.out.println("Player 2 Item:");
		System.out.println(player2Item.getInfoString());
		
		while(player2Health > 0 && player1Health > 0) {
			//strength is player attack - opponent defense
			double player1Strength = player2Char.getAttackStat() + player2Item.getBonusToAttack()
				- (player1Char.getDefenseStat() + player1Item.getBonusToDefense());
			double player2Strength = player1Char.getAttackStat() + player1Item.getBonusToAttack()
				- (player2Char.getDefenseStat() + player2Item.getBonusToDefense());
			
			player2Health -= player1Strength;
			if(player2Health <= 0) {
				break;
			}
			player1Health -= player2Strength;
		}
		
		String winnerID;
		boolean levelUp1 = false;
		if(player2Health < player1Health) {
			winnerID = player1.getPlayerID();
			levelUp1 = playerRepo.updateAfterFight(true, player1.getPlayerID());
			playerRepo.updateAfterFight(false, player2.getPlayerID());
			System.out.println("Player 1 wins!!");
		} else {
			winnerID = player2.getPlayerID();
			levelUp1 = playerRepo.updateAfterFight(false, player1.getPlayerID());
			playerRepo.updateAfterFight(true, player2.getPlayerID());
			System.out.println("Player 2 wins!!");
		}
		logger.info("player " + winnerID + " won the match");
		
		if (levelUp1) {
			System.out.println("Congratulations! You leveled up!");
			System.out.println("You are now level " + player1.getLevel() + ".");
			logger.info("player " + player1.getPlayerID() + " leveled up to level " + player1.getLevel());
		}
		
		//save to repo
		MatchRecord newMatch = new MatchRecord(this.generateMatchID(), player1.getPlayerID(), 
				player1.getSelectedCharacter().getCharacterID(), 
				player1.getSelectedItem().getItemID(),
				player2.getPlayerID(), player2.getSelectedCharacter().getCharacterID(),
				player2.getSelectedItem().getItemID(), false, winnerID);
		matchRecordRepo.addMatchRecord(newMatch);
		logger.info("match " + newMatch.getMatchID() + "added to repo");
	}
	
	public void printAllMatches() {
		System.out.println(String.format("%-25s|%-20s|%-20s|%-20s|%-25s|%-20s|%-20s|%-20s\n", 
				"timeOfMatch", "player1ID", "player1CharacterID", "player1ItemID", "player2ID", 
				"player2CharacterID", "player2ItemID", "winnerID"));
		List<MatchRecord> allMatches = matchRecordRepo.getAllMatches();
		for (MatchRecord mr : allMatches) {
			System.out.println(mr);
		}
		logger.info("printed all matches played");
	}
	
	public void printPlayerMatches(String playerID) {
		System.out.println(String.format("%-25s|%-20s|%-20s|%-20s|%-25s|%-20s|%-20s|%-20s\n", 
				"timeOfMatch", "player1ID", "player1CharacterID", "player1ItemID", "player2ID", 
				"player2CharacterID", "player2ItemID", "winnerID"));
		List<MatchRecord> allMatches = matchRecordRepo.getPlayerMatches(playerID);
		for (MatchRecord mr : allMatches) {
			System.out.println(mr);
		}
		logger.info("printed matches that player " + playerID + " participated in");
	}
	
	public String generateMatchID() {
		return UUID.randomUUID().toString();
	}
}
