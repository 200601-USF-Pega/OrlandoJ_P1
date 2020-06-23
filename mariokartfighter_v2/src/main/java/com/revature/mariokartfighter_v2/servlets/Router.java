package com.revature.mariokartfighter_v2.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mariokartfighter_v2.controllers.CharacterController;
import com.revature.mariokartfighter_v2.controllers.GameController;
import com.revature.mariokartfighter_v2.controllers.ItemController;
import com.revature.mariokartfighter_v2.controllers.PlayerController;

public class Router {
	public static void routeTo(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println(req.getRequestURI());
		switch(req.getRequestURI()) {
		case "/mariokartfighter_v2/player.login":
			PlayerController.login(req, res);
			break;
		case "/mariokartfighter_v2/player.logout":
			PlayerController.logout(req, res);
			break;
		case "/mariokartfighter_v2/player.profile":
			PlayerController.getProfile(req, res);
			break;
		case "/mariokartfighter_v2/player.setcharacter":
			PlayerController.setCharacter(req, res);
			break;
		case "/mariokartfighter_v2/character.get":
			CharacterController.getCharacters(req, res);
			break;
		case "/mariokartfighter_v2/character.getinfo":
			CharacterController.getCharacterInfo(req, res);
			break;
		case "/mariokartfighter_v2/character.create":
			CharacterController.createCharacter(req, res);
			break;
		case "/mariokartfighter_v2/item.get":
			ItemController.getItems(req, res);
			break;
		case "/mariokartfighter_v2/item.getinfo":
			ItemController.getItemInfo(req, res);
			break;
		case "/mariokartfighter_v2/item.create":
			ItemController.createItem(req, res);
			break;
		case "/mariokartfighter_v2/fight.bot":
			GameController.fightBot(req, res);
			break;
		case "/mariokartfighter_v2/fight.random":
			GameController.fightRandom(req, res);
			break;
		case "/mariokartfighter_v2/fight.player":
			GameController.fightPlayer(req, res);
			break;
		default:
			//route to home page
			req.getRequestDispatcher("index.html").forward(req, res);
			break;
		}
	}
}
