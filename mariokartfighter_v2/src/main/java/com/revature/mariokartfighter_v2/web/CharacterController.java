package com.revature.mariokartfighter_v2.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
	@Path("/getinfo/{charactername}")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getCharacterInfo(@PathParam("charactername") String name) {
		logger.info("getting character info");
		PlayableCharacter retrievedCharacter = repo.getCharacterInfo(name);
		if(retrievedCharacter == null) {
			return Response.status(404).build();
		}
		String characterImage = repo.getCharacterImageURL(retrievedCharacter.getCharacterID());
		if(characterImage != "") {
			retrievedCharacter.setCharacterImage(characterImage);
		}
		return Response.ok(retrievedCharacter).build();
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response createCharacter(PlayableCharacter character) {
		logger.info("creating new character");
		character.setCharacterID(charService.generateCharacterID());
		character.setUnlockAtLevel((int)(character.getMaxHealth()%10 + character.getAttackStat() + character.getDefenseStat()/3));
		repo.addCharacter(character);
		return Response.status(201).build();
	}
}
