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
import com.revature.mariokartfighter_v2.service.ItemService;
import com.revature.mariokartfighter_v2.service.PlayerService;
import com.revature.mariokartfighter_v2.service.ValidationService;

public class ItemMenu implements IMenu {
	
	private static final Logger logger = LogManager.getLogger(ItemMenu.class); 
	
	IPlayerRepo playerRepo;
	IPlayableCharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	private PlayerService playerService;
	private ItemService itemService;
	private GameService gameService;
	private ValidationService validationService = new ValidationService();

	@Override
	public void start(ConnectionService connectionService, String currPlayerID) {
		logger.info("player entered item menu");
		
		playerRepo = new PlayerRepoDB(connectionService);
		characterRepo = new PlayableCharacterRepoDB(connectionService);
		itemRepo = new ItemRepoDB(connectionService);
		matchRecordRepo = new MatchRecordRepoDB(connectionService);

		playerService = new PlayerService(playerRepo);
		itemService = new ItemService(itemRepo);
		gameService = new GameService(playerRepo, characterRepo, itemRepo,
				matchRecordRepo);
		validationService = new ValidationService();
		
		int itemOption = -1;
		do {
			System.out.println("---ITEM MENU---");
			System.out.println("[1] List All Items");
			System.out.println("[2] List Unlocked Items");
			System.out.println("[3] Get Item Info");
			System.out.println("[4] Set My Item");
			System.out.println("[5] Create Custom Item");
			System.out.println("[6] Back to Main Menu");
			
			itemOption = validationService.getValidInt();
			
			switch (itemOption) {
			case 1:
				itemService.printAllItems();
				break;
			case 2:
				itemService.printSomeItems(
						playerService.getPlayerObject(currPlayerID).getLevel());
				break;
			case 3:
				System.out.println("Enter item's name:");
				String nameInput = validationService.getValidString();
				itemService.printItemInfo(nameInput);
				break;
			case 4:
				boolean created = false;
				do {
					System.out.println("Enter item's name:");
					String itemNameInput = validationService.getValidString();					
					created = gameService.setItem(itemNameInput, currPlayerID);
				} while (!created);
				break;
			case 5:
				itemService.createNewItem();
				break;
			case 6:
				logger.info("player exited item menu");
				return;
			default:
				System.out.println("Invalid option...Redirecting to Main Menu");
			}
			System.out.println(" ");
		} while (itemOption != 6);
		return;
	}
}
