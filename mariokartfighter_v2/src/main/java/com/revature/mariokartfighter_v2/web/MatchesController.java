package com.revature.mariokartfighter_v2.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import com.revature.mariokartfighter_v2.dao.IMatchRecordRepo;
import com.revature.mariokartfighter_v2.dao.MatchRecordRepoDB;

@Path("/matches")
public class MatchesController {
	private static final Logger logger = LogManager.getLogger(MatchesController.class);
	private static IMatchRecordRepo matchRepo = new MatchRecordRepoDB();
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getMatches() {
		logger.info("getting all matches");
		return Response.ok(matchRepo.getAllMatches()).build();
	}
}
