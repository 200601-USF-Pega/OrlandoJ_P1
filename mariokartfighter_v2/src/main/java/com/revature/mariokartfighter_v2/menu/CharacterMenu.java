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
import com.revature.mariokartfighter_v2.service.ConnectionService;
import com.revature.mariokartfighter_v2.service.GameService;
import com.revature.mariokartfighter_v2.service.PlayableCharacterService;
import com.revature.mariokartfighter_v2.service.PlayerService;
import com.revature.mariokartfighter_v2.service.ValidationService;

public class CharacterMenu implements IMenu {
	
	private static final Logger logger = LogManager.getLogger(CharacterMenu.class); 
	
	IPlayerRepo playerRepo;
	IPlayableCharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	private PlayerService playerService;
	private PlayableCharacterService characterService;
	private GameService gameService;
	private ValidationService validationService = new ValidationService();
	
	@Override
	public void start(ConnectionService connectionService, String currPlayerID) {
		logger.info("player entered character menu");

		playerRepo = new PlayerRepoDB(connectionService);
		characterRepo = new PlayableCharacterRepoDB(connectionService);
		itemRepo = new ItemRepoDB(connectionService);
		matchRecordRepo = new MatchRecordRepoDB(connectionService);

		playerService = new PlayerService(playerRepo);		
		characterService = new PlayableCharacterService(characterRepo);				
		gameService = new GameService(playerRepo, characterRepo, itemRepo,
				matchRecordRepo);
		validationService = new ValidationService();
		
		int characterOption = -1;
		do {
			System.out.println("---CHARACTER MENU---");
			System.out.println("[1] List All Characters");
			System.out.println("[2] List Unlocked Characters");
			System.out.println("[3] Get Character Info");
			System.out.println("[4] Set My Character");
			System.out.println("[5] Create Custom Character");
			System.out.println("[6] Back to Main Menu");
			
			characterOption = validationService.getValidInt();
			
			switch (characterOption) {
			case 1:
				characterService.printAllCharacters();
				break;
			case 2:
				characterService.printSomeCharacters(
						playerService.getPlayerObject(currPlayerID).getLevel());
				break;
			case 3:
				System.out.println("Enter character's name:");
				String nameInput = validationService.getValidString();
				characterService.printCharacterInfo(nameInput);
				break;
			case 4:
				boolean created = false;
				do {
					System.out.println("Enter character's name:");
					String charNameInput = validationService.getValidString();
					created = gameService.setCharacter(charNameInput, currPlayerID);
				} while (!created);
				break;
			case 5:
				characterService.createNewCharacter();
				break;
			case 6:
				logger.info("player exited character menu");
				return;
			default:
				System.out.println("Invalid option...try again");
			}
			System.out.println(" ");
		} while (characterOption != 6);
		return;
	}

}
