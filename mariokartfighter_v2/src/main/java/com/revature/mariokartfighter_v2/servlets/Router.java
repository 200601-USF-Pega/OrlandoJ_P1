package com.revature.mariokartfighter_v2.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mariokartfighter_v2.controllers.CharacterController;

public class Router {
	public static void routeTo(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println(req.getRequestURI());
		switch(req.getRequestURI()) {
		case "/mariokartfighter_v2/character.get":
			//route to logic here 
			CharacterController.getCharacters(req, res);
			break;
		default:
			//route to home page
			req.getRequestDispatcher("index.html").forward(req, res);
			break;
		}
	}
}
