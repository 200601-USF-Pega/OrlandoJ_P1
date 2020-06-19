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
import com.revature.mariokartfighter_v2.service.ValidationService;

public class MatchesMenu implements IMenu {
	
	private static final Logger logger = LogManager.getLogger(FightMenu.class); 
	
	IPlayerRepo playerRepo;
	IPlayableCharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	private GameService gameService;
	private ValidationService validationService = new ValidationService();

	@Override
	public void start(ConnectionService connectionService, String currPlayerID) {
		// TODO Auto-generated method stub
		logger.info("player entered matches menu");
		
		playerRepo = new PlayerRepoDB(connectionService);
		characterRepo = new PlayableCharacterRepoDB(connectionService);
		itemRepo = new ItemRepoDB(connectionService);
		matchRecordRepo = new MatchRecordRepoDB(connectionService);
	
		gameService = new GameService(playerRepo, characterRepo, itemRepo,
				matchRecordRepo);
		validationService = new ValidationService();
		
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
	}

}
