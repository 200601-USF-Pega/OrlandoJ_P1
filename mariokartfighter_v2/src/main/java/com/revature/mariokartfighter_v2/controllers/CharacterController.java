package com.revature.mariokartfighter_v2.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mariokartfighter_v2.dao.IPlayableCharacterRepo;
import com.revature.mariokartfighter_v2.dao.PlayableCharacterRepoDB;
import com.revature.mariokartfighter_v2.models.PlayableCharacter;
import com.revature.mariokartfighter_v2.web.ConnectionService;

public class CharacterController {
	private static IPlayableCharacterRepo repo = new PlayableCharacterRepoDB(new ConnectionService());

	public static void getCharacters(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("getting characters");
		
		List<PlayableCharacter> allCharacters = repo.getAllCharacters();
		String html = "<html><body><h1>List of Characters:</h1>";
		
        for (PlayableCharacter c : allCharacters) {
            html += "<p>" + c + "/<p>";
        }
        html +="</body></html>";
        res.setContentType("text/html");
        res.getWriter().write(html);
		
//		req.getSession().setAttribute("characters", allCharacters);
//		return "character_get.html";
	}

	public static void getCharacterInfo(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	public static void createCharacter(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}
}
