package com.revature.mariokartfighter_v2.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter_v2.dao.IItemRepo;
import com.revature.mariokartfighter_v2.dao.ItemRepoDB;
import com.revature.mariokartfighter_v2.models.Item;

@Path("/item")
public class ItemController {
	private static final Logger logger = LogManager.getLogger(ItemController.class);
	private static IItemRepo repo = new ItemRepoDB();
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getItems() {
		logger.info("getting all items");
		return Response.ok(repo.getAllItems()).build();
	}

	@GET
	@Path("/getinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getItemInfo(String itemName) {
		logger.info("getting item info for " + itemName);
		return Response.ok(repo.getItemInfo(itemName)).build();
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response createItem(Item item) {
		logger.info("getting all items");
		repo.addItem(item);
		return Response.status(201).build();
	}

}
