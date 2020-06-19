package com.revature.mariokartfighter_v2.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.IItemRepo;
import com.revature.mariokartfighter_v2.dao.IMatchRecordRepo;
import com.revature.mariokartfighter_v2.dao.IPlayableCharacterRepo;
import com.revature.mariokartfighter_v2.dao.IPlayerRepo;
import com.revature.mariokartfighter_v2.dao.ItemRepoDB;
import com.revature.mariokartfighter_v2.dao.MatchRecordRepoDB;
import com.revature.mariokartfighter_v2.dao.PlayableCharacterRepoDB;
import com.revature.mariokartfighter_v2.dao.PlayerRepoDB;
import com.revature.mariokartfighter_v2.models.Bot;
import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.models.Player;
import com.revature.mariokartfighter_v2.service.ConnectionService;
import com.revature.mariokartfighter_v2.service.GameService;
import com.revature.mariokartfighter_v2.service.PlayerService;
import com.revature.mariokartfighter_v2.service.ValidationService;

public class FightMenu implements IMenu {
	
	private static final Logger logger = LogManager.getLogger(FightMenu.class); 
	
	IPlayerRepo playerRepo;
	IPlayableCharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	private PlayerService playerService;
	private GameService gameService;
	private ValidationService validationService = new ValidationService();

	@Override
	public void start(ConnectionService connectionService, String currPlayerID) {
		// TODO Auto-generated method stub
		logger.info("player entered fight menu");
		
		playerRepo = new PlayerRepoDB(connectionService);
		characterRepo = new PlayableCharacterRepoDB(connectionService);
		itemRepo = new ItemRepoDB(connectionService);
		matchRecordRepo = new MatchRecordRepoDB(connectionService);

		playerService = new PlayerService(playerRepo);			
		gameService = new GameService(playerRepo, characterRepo, itemRepo,
				matchRecordRepo);
		validationService = new ValidationService();
		
		int fightOption = -1;
		Player thisPlayer;
		Player player1;
		Player player2;
		do {
			System.out.println("---FIGHT MENU---");
			System.out.println("[1] Fight a Bot");
			System.out.println("[2] Fight a Random Player");
			System.out.println("[3] Choose a Player to Fight");
			System.out.println("[4] Back to Main Menu");
			
			fightOption = validationService.getValidInt();
			
			switch(fightOption) {
			case 1:
				//check if player has selected an item and character
				thisPlayer = playerService.getPlayerObject(currPlayerID);
				if (thisPlayer.getSelectedCharacter() == null 
						|| thisPlayer.getSelectedItem() == null) {
					System.out.println("You can't fight yet because you haven't selected "
							+ "a character and item.");
					System.out.println("Redirecting to main menu...");
					continue;
				}
				
				//ask for level of bot
				System.out.println("What level bot would you like to fight?");
				int botLevel = validationService.getValidInt();
				PlayableCharacter randomCharacter = gameService.chooseRandomCharacter(botLevel);
				Item randomItem = gameService.chooseRandomItem(botLevel, randomCharacter.getType());
				Bot newBot = gameService.createNewBot(botLevel, randomCharacter, randomItem);
				
				gameService.botFight(newBot, currPlayerID);
				break;
			case 2:
				//check if player has selected an item and character
				thisPlayer = playerService.getPlayerObject(currPlayerID);
				if (thisPlayer.getSelectedCharacter() == null 
						|| thisPlayer.getSelectedItem() == null) {
					System.out.println("You can't fight yet because you haven't selected "
							+ "a character and item.");
					System.out.println("Redirecting to main menu...");
					continue;
				}
				
				System.out.println("Selecting opponent...");
				player1 = playerService.getPlayerObject(currPlayerID);
				player2 = playerService.chooseClosestPlayer(player1);
				
				//make sure a player to fight was chosen
				if (player2.getSelectedCharacter() == null)  {
					System.out.println("No players available to fight.");
					System.out.println("Redirecting to main menu...");
				} else {
					System.out.println("Opponent is " + player2.getPlayerID());
					playerService.printPlayerInfo(player2.getPlayerID());
					
					gameService.playerFight(player1, player2);
				}
				break;
			case 3:
				//check if player has selected an item and character
				thisPlayer = playerService.getPlayerObject(currPlayerID);
				if (thisPlayer.getSelectedCharacter() == null 
						|| thisPlayer.getSelectedItem() == null) {
					System.out.println("You can't fight yet because you haven't selected "
							+ "a character and item.");
					System.out.println("Redirecting to main menu...");
					continue;
				}

				player1 = playerService.getPlayerObject(currPlayerID);
				
				System.out.println("Players available to fight:");
				playerService.printPlayersToFight(currPlayerID);
				
				System.out.println("Enter the playerID of the player you want to fight:");
				String player2ID = validationService.getValidString();
				do {
					if(playerService.checkPlayerExists(player2ID)) {
						break;
					}
					player2ID = validationService.getValidString();
				} while (!playerService.checkPlayerExists(player2ID));
				
				player2 = playerService.getPlayerObject(player2ID);
				
				System.out.println("Opponent is " + player2.getPlayerID());
				playerService.printPlayerInfo(player2.getPlayerID());
				
				gameService.playerFight(player1, player2);
				break;
			case 4:
				logger.info("player exited fight menu");
				break;
			default:
				System.out.println("Invalid option...Redirecting to Main Menu");
			}
		} while (fightOption != 4);
	}

}
