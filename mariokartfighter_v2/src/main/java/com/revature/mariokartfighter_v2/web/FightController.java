package com.revature.mariokartfighter_v2.web;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import com.revature.mariokartfighter_v2.models.Bot;
import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.models.Player;
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

	@POST
	@Path("/bot/{playerID}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response fightBot(Bot bot, @PathParam("playerID") String playerID) {
		// TODO Auto-generated method stub
		logger.info("player " + playerID + " fighting a level " + bot.getLevel() + " bot");
		
		PlayableCharacter botCharacter = gameService.chooseRandomCharacter(bot.getLevel());
		botCharacter.setCharacterImage(characterRepo.getCharacterImageURL(botCharacter.getCharacterID()));
		Item botItem = gameService.chooseRandomItem(bot.getLevel(), botCharacter.getType());
		botItem.setItemImage(itemRepo.getItemImageURL(botItem.getItemID()));
		Bot newBot = gameService.createNewBot(bot.getLevel(), botCharacter, botItem);
		
		Map<String, Boolean> result = gameService.botFight(newBot, playerID);
		String winner = null;
		boolean leveledUp = false;
		for(Map.Entry<String, Boolean> entry : result.entrySet()) {
			winner = entry.getKey();
			leveledUp = entry.getValue();
		}
		
		if(winner == null) {
			return Response.status(400).build();
		}
		
		if (winner.equals(playerID)) {			
			//player wins
			logger.info("player " + playerID + " wins!");
			if(leveledUp) {
				return Response.ok(newBot).status(202).build();
			} else {				
				return Response.ok(newBot).status(200).build();
			}
		} else {
			//bot wins
			logger.info("bot wins!");
			if(leveledUp) {				
				return Response.ok(newBot).status(203).build();
			} else {				
				return Response.ok(newBot).status(201).build();
			}
		}
	}
	
	@POST
	@Path("/random")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response fightRandom(Player player) {
		logger.info("player " + player.getPlayerID() + " fighting a random player");
		Player player1 = playerService.getPlayerObject(player.getPlayerID());
		Player player2 = playerService.chooseRandomPlayer(player1);
		if(player2.getPlayerID().equals("")) {
			//no available players
			return Response.status(404).build();
		}
		
		player2.getSelectedCharacter().setCharacterImage(characterRepo.getCharacterImageURL(player2.getSelectedCharacter().getCharacterID()));
		player2.getSelectedItem().setItemImage(itemRepo.getItemImageURL(player2.getSelectedItem().getItemID()));;
		
		Map<String, Boolean> result = gameService.playerFight(player1, player2);
		String winner = null;
		boolean leveledUp = false;
		for(Map.Entry<String, Boolean> entry : result.entrySet()) {
			winner = entry.getKey();
			leveledUp = entry.getValue();
		}
		
		if(winner == null) {
			return Response.status(400).build();
		}
		
		if (winner.equals(player1.getPlayerID())) {			
			//player wins
			logger.info("player1 wins!");
			if(leveledUp) {
				return Response.ok(player2).status(202).build();
			} else {				
				return Response.ok(player2).status(200).build();
			}
		} else {
			//bot wins
			logger.info("player2 wins!");
			if(leveledUp) {				
				return Response.ok(player2).status(203).build();
			} else {				
				return Response.ok(player2).status(201).build();
			}
		}
	}
	
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
