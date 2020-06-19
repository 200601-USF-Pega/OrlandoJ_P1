package com.revature.mariokartfighter_v2.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mariokartfighter_v2.controllers.CharacterController;

public class Router {
	public static String routeTo(HttpServletRequest req, HttpServletResponse res) {
		System.out.println(req.getRequestURI());
		switch(req.getRequestURI()) {
		case "/mariokartfighter_v2/character.get":
			//route to logic here 
			return CharacterController.getCharacters(req, res);
		default:
			//route to home page
			return "index.html";
		}
	}
}
