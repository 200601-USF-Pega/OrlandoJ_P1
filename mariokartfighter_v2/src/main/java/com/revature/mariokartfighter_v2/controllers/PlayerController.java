package com.revature.mariokartfighter_v2.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.IPlayerRepo;
import com.revature.mariokartfighter_v2.dao.PlayerRepoDB;
import com.revature.mariokartfighter_v2.service.PlayerService;
import com.revature.mariokartfighter_v2.web.ConnectionService;

public class PlayerController {
	private static final Logger logger = LogManager.getLogger(PlayerController.class); 
	private static IPlayerRepo repo = new PlayerRepoDB(new ConnectionService());
	private static PlayerService playerService = new PlayerService(repo);

	public static void login(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("logging in player");
		
		String optionNumber = req.getParameter("inlineRadioOptions");
		if (optionNumber.equals("option1")) {
			//new player
			String inputtedID = req.getParameter("new_username");
			if (inputtedID.length() > 24 || inputtedID.length() < 4) {
				System.out.println("username wrong length");
				//send back alert
			} else if(playerService.checkPlayerExists(inputtedID)) {
				System.out.println("ID already taken");
				//send back alert
			} else {
				String inputtedPassword = req.getParameter("new_password");
				if(inputtedPassword.length() < 4 || inputtedPassword.length() > 24) {
					System.out.println("password wrong length");
					//send back alert
				}
				String currPlayerID = playerService.createNewPlayer(inputtedID, inputtedPassword);
				req.getSession().setAttribute("currPlayerID", currPlayerID);
				logger.info("new player " + currPlayerID + " successfully created");
			}
		} else {
			//returning player
			//find player in database
			String inputID = req.getParameter("old_username");
			if (playerService.checkPlayerExists(inputID)) {
				String inputtedPassword = req.getParameter("old_password");
				if (!playerService.checkPassword(inputID, inputtedPassword)) {
					System.out.println("incorrect password");
					//send back alert
				} else {
					req.getSession().setAttribute("currPlayerID", inputID);					
				}
				logger.info("player " + inputID + " successfully logged in");
			} else {
				System.out.println("ID does not exist");
				//send back alert
			}
		}
	}

	public static void logout(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	public static void getProfile(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	public static void setCharacter(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
	}

}
