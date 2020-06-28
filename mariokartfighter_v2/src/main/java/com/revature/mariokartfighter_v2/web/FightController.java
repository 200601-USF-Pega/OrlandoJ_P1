package com.revature.mariokartfighter_v2.web;

import javax.ws.rs.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.CharacterRepoDB;
import com.revature.mariokartfighter_v2.dao.ICharacterRepo;
import com.revature.mariokartfighter_v2.dao.IItemRepo;
import com.revature.mariokartfighter_v2.dao.IMatchRecordRepo;
import com.revature.mariokartfighter_v2.dao.IPlayerRepo;
import com.revature.mariokartfighter_v2.dao.ItemRepoDB;
import com.revature.mariokartfighter_v2.dao.MatchRecordRepoDB;
import com.revature.mariokartfighter_v2.dao.PlayerRepoDB;
import com.revature.mariokartfighter_v2.service.GameService;
import com.revature.mariokartfighter_v2.service.PlayerService;

@Path("/fight")
public class FightController {
	private static final Logger logger = LogManager.getLogger(FightController.class);
	private static IMatchRecordRepo matchRepo = new MatchRecordRepoDB();
	private static IItemRepo itemRepo = new ItemRepoDB();
	private static ICharacterRepo characterRepo = new CharacterRepoDB();
	private static IPlayerRepo playerRepo = new PlayerRepoDB();
	private static PlayerService playerService = new PlayerService(playerRepo);
	private static GameService gameService = new GameService(playerRepo, characterRepo, itemRepo, matchRepo);

//	@POST
//	@Path("/bot")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public static Response fightBot(int level, String playerID) {
//		// TODO Auto-generated method stub
//		logger.info("player " + playerID + " fighting a level " + level + " bot");
//		
//		PlayableCharacter botCharacter = gameService.chooseRandomCharacter(level);
//		Item botItem = gameService.chooseRandomItem(level, botCharacter.getType());
//		Bot newBot = gameService.createNewBot(level, botCharacter, botItem);
//		
//		gameService.botFight(newBot, playerID);
//		
//		return Response.status(200).build();
//	}
//	
//	@POST
//	@Path("/random")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public static Response fightRandom(String playerID) {
//		logger.info("player " + playerID + " fighting a random player");
//		Player player1 = playerService.getPlayerObject(playerID);O
//		Player player2 = playerService.chooseClosestPlayer(player1);
//		gameService.playerFight(player1, player2);
//		return Response.status(200).build();
//	}
//	
//	@POST
//	@Path("/otherplayer")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public static Response fightPlayer(String playerID, String opponentID) {
//		logger.info("player " + playerID + " fighting player " + opponentID);
//		Player player1 = playerService.getPlayerObject(playerID);
//		Player player2 = playerService.getPlayerObject(opponentID);
//		gameService.playerFight(player1, player2);
//		return Response.status(200).build();
//	}

}
