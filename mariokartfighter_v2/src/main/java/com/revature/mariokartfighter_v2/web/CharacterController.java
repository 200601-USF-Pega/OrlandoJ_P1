package com.revature.mariokartfighter_v2.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.CharacterRepoDB;
import com.revature.mariokartfighter_v2.dao.ICharacterRepo;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.service.CharacterService;

@Path("/character")
public class CharacterController {
	private static final Logger logger = LogManager.getLogger(CharacterController.class);
	private static ICharacterRepo repo = new CharacterRepoDB();
	private static CharacterService charService = new CharacterService(repo);
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getCharacters() {
		logger.info("getting characters");
		return Response.ok(repo.getAllCharacters()).build();
	}
	
	@GET
	@Path("/getinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getCharacterInfo(String name) {
		logger.info("getting character info");
		return Response.ok(repo.getCharacterInfo(name)).build();
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response createCharacter(PlayableCharacter character) {
		logger.info("creating new character");
		character.setCharacterID(charService.generateCharacterID());
		repo.addCharacter(character);
		return Response.status(201).build();
	}
}
