package com.revature.mariokartfighter_v2.web;

import java.util.LinkedHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.CharacterRepoDB;
import com.revature.mariokartfighter_v2.dao.ICharacterRepo;
import com.revature.mariokartfighter_v2.dao.IItemRepo;
import com.revature.mariokartfighter_v2.dao.IPlayerRepo;
import com.revature.mariokartfighter_v2.dao.ItemRepoDB;
import com.revature.mariokartfighter_v2.dao.PlayerRepoDB;
import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.models.Player;
import com.revature.mariokartfighter_v2.service.PlayerService;

@Path("/player")
public class PlayerController {
	private static final Logger logger = LogManager.getLogger(PlayerController.class); 
	private static IPlayerRepo repo = new PlayerRepoDB();
	private static ICharacterRepo characterRepo = new CharacterRepoDB();
	private static IItemRepo itemRepo = new ItemRepoDB();
	private static PlayerService playerService = new PlayerService(repo);
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response login(LinkedHashMap<String, String> params) {
		boolean newPlayer = params.get("newPlayer").equals("true");
		String playerID = params.get("playerID");
		String password = params.get("password");
		System.out.println(playerID);
		
		//check if right length
		if (playerID.length() < 4 || playerID.length() > 24 
				|| password.length() < 4 || password.length() > 24) {
			return Response.status(401).build();
		}
		
		if (newPlayer) {
			Player player = new Player(playerID);
			Player addedPlayer = repo.addPlayer(player, password);
			if (addedPlayer == null) {
				logger.warn("user entered existing playerID");
				return Response.status(409).build();
			}
			logger.info("player " + player.getPlayerID() + " created an account");
			return Response.ok(player).build();
		} else {
			Player player = playerService.getPlayerObject(playerID);
			boolean validLogin = playerService.checkPassword(playerID, password);
			if(validLogin) {
				logger.info("player " + playerID + " logged in");
				return Response.ok(player).build();
			} else {
//				System.out.println("invalid login");
				logger.warn("incorrect login for username " + playerID);
				return Response.status(401).build();	//401 = unauthorized
			}
		}
	}
	
	@GET
	@Path("/profile/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getProfile(@PathParam("username") String playerID) {
		logger.info("getting player info for player " + playerID);
		
		System.out.println(playerID);
		Player requestedPlayer = repo.getPlayerInfo(playerID);
		PlayableCharacter playerCharacter = requestedPlayer.getSelectedCharacter();
		Item playerItem = requestedPlayer.getSelectedItem();
		int rank = repo.getPlayerRank(playerID);
		
		if (playerCharacter != null) {			
			playerCharacter.setCharacterImage(characterRepo.getCharacterImageURL(playerCharacter.getCharacterID()));
		}
		if (playerItem != null) {			
			playerItem.setItemImage(itemRepo.getItemImageURL(playerItem.getItemID()));
		}
		if (rank == -1) {
			requestedPlayer.setRank(0);
		} else {			
			requestedPlayer.setRank(rank);
		}
		return Response.ok(requestedPlayer).build();
	}
	
	@POST
	@Path("/setcharacter")
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response setCharacter(PlayableCharacter character, @PathParam("username") String playerID) {
		logger.info("setting character for player " + playerID);
		repo.assignCharacterToPlayer(character, playerID);
		//TODO check if item type still allowed
		return Response.status(201).build();
	}
	
	@POST
	@Path("/setitem")
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response setitem(Item item, @PathParam("username") String playerID) {
		logger.info("setting character for player " + playerID);
		//TODO check if item type allowed for character
		repo.assignItemToPlayer(item, playerID);
		return Response.status(201).build();
	}
	
	@GET
	@Path("/getavailable")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getAvailableOpponents() {
		logger.info("getting all players available to fight");
		return Response.ok(repo.getAvailablePlayers()).build();
	}

}
