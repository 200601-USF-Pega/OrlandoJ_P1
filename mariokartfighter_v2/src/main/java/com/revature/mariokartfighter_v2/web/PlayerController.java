package com.revature.mariokartfighter_v2.web;

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

import com.revature.mariokartfighter_v2.dao.IPlayerRepo;
import com.revature.mariokartfighter_v2.dao.PlayerRepoDB;
import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.service.PlayerService;

@Path("/player")
public class PlayerController {
	private static final Logger logger = LogManager.getLogger(PlayerController.class); 
	private static IPlayerRepo repo = new PlayerRepoDB();
	private static PlayerService playerService = new PlayerService(repo);
	
//	@POST
//	@Path("/login")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public static Response login(Player player) {
//		// boolean newPlayer, String username, String password
//		if (newPlayer) {
//			repo.addPlayer(player, password);
//			logger.info("player " + username + " created an account");
//			return Response.ok(player).build();
//		} else {
//			boolean validLogin = playerService.checkPassword(username, password);
//			if(validLogin) {
//				logger.info("player " + username + " logged in");
//				return Response.ok().build();
//			} else {
//				logger.warn("incorrect login for username " + username);
//				return Response.status(401).build();	//401 = unauthorized
//			}
//		}
//	}
//	
	
//	@Path("/logout")
//	public static void logout(String username) {
//		logger.info("player " + username + " logged out");
//	}
	
	@GET
	@Path("/profile/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getProfile(@PathParam("username") String playerID) {
		System.out.println("getting profile");
		logger.info("getting player info for player " + playerID);
		return Response.ok(repo.getPlayerInfo(playerID)).build();
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
