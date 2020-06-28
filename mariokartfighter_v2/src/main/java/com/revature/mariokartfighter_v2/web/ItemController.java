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


import com.revature.mariokartfighter_v2.dao.IItemRepo;
import com.revature.mariokartfighter_v2.dao.ItemRepoDB;
import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.service.ItemService;

@Path("/item")
public class ItemController {
	private static final Logger logger = LogManager.getLogger(ItemController.class);
	private static IItemRepo repo = new ItemRepoDB();
	private static ItemService itemService = new ItemService(repo);
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getItems() {
		logger.info("getting all items");
		return Response.ok(repo.getAllItems()).build();
	}

	@GET
	@Path("/getinfo/{itemname}")
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getItemInfo(@PathParam("itemname") String itemName) {
		logger.info("getting item info for " + itemName);
		Item retrievedItem = repo.getItemInfo(itemName);
		if (retrievedItem == null) {
			return Response.status(404).build();
		}
		String itemImage = repo.getItemImageURL(retrievedItem.getItemID());
		if(itemImage != "") {
			retrievedItem.setItemImage(itemImage);
		}
		return Response.ok(retrievedItem).build();
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response createItem(Item item) {
		logger.info("getting all items");
		item.setItemID(itemService.generateItemID());
		repo.addItem(item);
		return Response.status(201).build();
	}

}
