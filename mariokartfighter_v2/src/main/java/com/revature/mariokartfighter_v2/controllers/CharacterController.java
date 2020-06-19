package com.revature.mariokartfighter_v2.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mariokartfighter_v2.dao.IPlayableCharacterRepo;
import com.revature.mariokartfighter_v2.dao.PlayableCharacterRepoDB;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.service.ConnectionService;

public class CharacterController {
	private static IPlayableCharacterRepo repo = new PlayableCharacterRepoDB(new ConnectionService());

	public static void getCharacters(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("getting characters");
		List<PlayableCharacter> allCharacters = repo.getAllCharacters();
		
		
		
//		req.getSession().setAttribute("characters", allCharacters);
//		return "character_get.html";
	}
}
