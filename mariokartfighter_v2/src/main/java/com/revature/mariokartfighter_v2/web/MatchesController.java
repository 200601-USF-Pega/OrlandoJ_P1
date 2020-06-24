package com.revature.mariokartfighter_v2.web;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.IMatchRecordRepo;
import com.revature.mariokartfighter_v2.dao.MatchRecordRepoDB;
import com.revature.mariokartfighter_v2.models.MatchRecord;

@Path("/matches")
public class MatchesController {
	private static final Logger logger = LogManager.getLogger(MatchesController.class);
	private static IMatchRecordRepo matchRepo = new MatchRecordRepoDB();
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getMatches(@FormParam("include-locked-items") boolean onlyPlayer, String playerID) {
		if(onlyPlayer) {
			logger.info("getting player " + playerID + "'s matches");
			List<MatchRecord> playerMatches = matchRepo.getPlayerMatches(playerID);
			GenericEntity<List<MatchRecord>> matchesEntity = new GenericEntity<List<MatchRecord>>(playerMatches) {};
			return Response.status(200).entity(matchesEntity).build();
//			return Response.ok(matchRepo.getPlayerMatches(playerID)).build();
		} else {
			logger.info("getting all matches");
			List<MatchRecord> allMatches = matchRepo.getAllMatches();
			GenericEntity<List<MatchRecord>> matchesEntity = new GenericEntity<List<MatchRecord>>(allMatches) {};
			return Response.status(200).entity(matchesEntity).build();
//			return Response.ok(matchRepo.getAllMatches()).build();
		}
	}
}
