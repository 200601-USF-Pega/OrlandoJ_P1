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
import com.revature.mariokartfighter_v2.service.ItemService;
import com.revature.mariokartfighter_v2.service.PlayableCharacterService;
import com.revature.mariokartfighter_v2.service.PlayerService;
import com.revature.mariokartfighter_v2.service.ValidationService;

public class MainMenu {
	private static final Logger logger = LogManager.getLogger(MainMenu.class); 
	
	ConnectionService connectionService;
	
	IPlayerRepo playerRepo;
	IPlayableCharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	
	private PlayerService playerService;
	private ValidationService validationService;
	private GameService gameService;
	private String currPlayerID;
	
	private void setUp() {
		connectionService = new ConnectionService();
		logger.info("created new connection service");
		
		playerRepo = new PlayerRepoDB(connectionService);
		characterRepo = new PlayableCharacterRepoDB(connectionService);
		itemRepo = new ItemRepoDB(connectionService);
		matchRecordRepo = new MatchRecordRepoDB(connectionService);
		logger.info("created new repo objects");
		
		playerService = new PlayerService(playerRepo);
		validationService = new ValidationService();
		gameService = new GameService(playerRepo, characterRepo, itemRepo,
				matchRecordRepo);
		logger.info("created new service objects");
	}
	
	public void mainMenu() {	
		setUp();
		
		System.out.println("WELCOME TO MARIO KART FIGHTER!");
		System.out.println("Please choose an option:");
		System.out.println("[1] New Player");
		System.out.println("[2] Returning Player");
		System.out.println("[0] Exit the program");
		
		int optionNumber = validationService.getValidInt();
		
		boolean loggedIn = false;
		do {
			if (optionNumber == 1) {
				System.out.println("Enter a username (4-24 characters):");
				String inputtedID = validationService.getValidString();
				while (!loggedIn) {
					if (inputtedID.length() > 24 || inputtedID.length() < 4) {
						System.out.println("username wrong length...try again");												
					} else if(playerService.checkPlayerExists(inputtedID)) {
						inputtedID = validationService.getValidString();
						System.out.println("ID already taken...try again");
					} else {
						String inputtedPassword = "";
						do {
							System.out.println("Enter a password (4-24 characters):");
							inputtedPassword = validationService.getValidString();
						} while(inputtedPassword.length() < 4 || inputtedPassword.length() > 24); 					
						currPlayerID = playerService.createNewPlayer(inputtedID, inputtedPassword);
						loggedIn = true;
						break;
					}
				}
			} else if (optionNumber == 2) {
				//find player in database
				System.out.println("Enter your player ID to login:");
				String inputID = validationService.getValidString();
				if (playerService.checkPlayerExists(inputID)) {
					String inputtedPassword = "";
					do {
						System.out.println("Enter a password (4-24 characters):");
						inputtedPassword = validationService.getValidString();
					} while(!playerService.checkPassword(inputID, inputtedPassword));
					loggedIn = true;
					currPlayerID = inputID;
					logger.info("player successfully logged in");
				} else {
					System.out.println("ID does not exist...try again "
							+ "(type 'exit' to go back to main menu)");
				}
			} else if (optionNumber == 0) {
				logger.info("player exited game");
				System.exit(0);
			} else {
				System.out.println("Invalid option number");
			}
		} while (!loggedIn);
				
		SubMenuFactory menuFactory = new SubMenuFactory();
		
		System.out.println("Welcome Player " + currPlayerID);
		int optionNumber2 = -1;
		do {
			System.out.println("What would you like to do?");
			System.out.println("[1] View my Level and Rank");
			System.out.println("[2] Character Menu");
			System.out.println("[3] Item Menu");
			System.out.println("[4] Fight Menu");
			System.out.println("[5] Matches Menu");
			System.out.println("[0] Exit the program");
			
			optionNumber2 = validationService.getValidInt();		
			
			if (optionNumber2 == 1) {
				//print player level and rank
				playerService.printPlayerInfo(currPlayerID);
			} else if (optionNumber2 == 2) {
				IMenu characterMenu = menuFactory.getMenu("character");
				characterMenu.start(connectionService, currPlayerID);
			} else if (optionNumber2 == 3) {
				IMenu itemMenu = menuFactory.getMenu("item");
				itemMenu.start(connectionService, currPlayerID);
			} else if (optionNumber2 == 4) {
				IMenu fightMenu = menuFactory.getMenu("fight");
				fightMenu.start(connectionService, currPlayerID);	
			} else if (optionNumber2 == 5)  {
				logger.info("player entered matches menu");
				int printMatchesOption = -1;
				do {
					System.out.println("---MATCHES MENU---");
					System.out.println("[1] List All Matches");
					System.out.println("[2] List My Matches");
					System.out.println("[3] Back to Main Menu");
					
					printMatchesOption = validationService.getValidInt();
					
					switch (printMatchesOption) {
					case 1:
						gameService.printAllMatches();
						break;
					case 2:
						gameService.printPlayerMatches(currPlayerID);
						break;
					case 3:
						logger.info("player exited matches menu");
						break;
					default:
						System.out.println("Invalid option...Redirecting to Main Menu");
					}
					System.out.println(" ");
				} while (printMatchesOption != 3);
			} else if (optionNumber2 == 0) {
				System.out.println("Thanks for playing!");
				logger.info("player exited game");
				System.exit(0);
			} else {
				System.out.println("Invalid option number");
			}
		} while (optionNumber2 != 0);
	}
}
