package com.revature.mariokartfighter_v2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.web.ConnectionService;

public class ItemRepoDB implements IItemRepo {
	
	@Override
	public Item addItem(Item item) {
		try {			
			PreparedStatement itemInsert = ConnectionService.getConnection().prepareStatement(
					"INSERT INTO item VALUES (?, ?, ?, ?, ?, ?, ?)");
			itemInsert.setString(1, item.getItemID());
			itemInsert.setString(2, item.getItemName());
			itemInsert.setString(3, item.getTypeThatCanUse());
			itemInsert.setInt(4, item.getBonusToHealth());
			itemInsert.setDouble(5, item.getBonusToAttack());
			itemInsert.setDouble(6, item.getBonusToDefense());
			itemInsert.setInt(7, item.getUnlockAtLevel());
			
			itemInsert.executeUpdate();
			
			//check if image url exists
			if (item.getItemImage() != null) {
				PreparedStatement itemImageInsert = ConnectionService.getConnection().prepareStatement(
						"INSERT INTO itemImages VALUES (?, ?);");
				itemImageInsert.setString(1, item.getItemID());
				itemImageInsert.setString(2, item.getItemImage());
				
				itemImageInsert.executeUpdate();
			}
			
			return item;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Item> getAllItems() {
		try {			
			PreparedStatement getItems = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM item ORDER BY unlockAtLevel, typeThatCanUse, name;");
			ResultSet itemsRS = getItems.executeQuery();
			
			List<Item> retrievedItems = new ArrayList<Item>();
			
			while(itemsRS.next()) {				
				Item newItem = new Item(
					itemsRS.getString("itemID"),
					itemsRS.getString("name"),
					itemsRS.getString("typeThatCanUse"),
					itemsRS.getInt("unlockAtLevel"),
					itemsRS.getInt("bonusToHealth"),
					itemsRS.getDouble("bonusToAttack"), 
					itemsRS.getDouble("bonusToDefense"));	
				
				retrievedItems.add(newItem);
			}	
			return retrievedItems;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Item>();
	}

	@Override
	public List<Item> getSomeItems(int level) {
		try {			
			PreparedStatement getItems = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM item WHERE unlockAtLevel <= ?;");
			getItems.setInt(1, level);
			
			ResultSet itemsRS = getItems.executeQuery();
			
			List<Item> retrievedItems = new ArrayList<Item>();
			
			while(itemsRS.next()) {				
				Item newItem = new Item(
					itemsRS.getString("itemID"),
					itemsRS.getString("name"),
					itemsRS.getString("typeThatCanUse"),
					itemsRS.getInt("unlockAtLevel"),
					itemsRS.getInt("bonusToHealth"),
					itemsRS.getDouble("bonusToAttack"), 
					itemsRS.getDouble("bonusToDefense"));	
				
				retrievedItems.add(newItem);
			}	
			return retrievedItems;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Item>();
	}

	@Override
	public void removeItems(String name) {
		try {
			PreparedStatement removeItems = ConnectionService.getConnection().prepareStatement(
					"DELETE FROM item "
					+ "WHERE itemID LIKE ?");
			removeItems.setString(1, name+'%');
			removeItems.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Item getItemInfo(String itemName) {
		try {			
			PreparedStatement getItem = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM item WHERE name = ?;");
			getItem.setString(1, itemName);
			ResultSet itemsRS = getItem.executeQuery();
			
			while(itemsRS.next()) {				
				Item newItem = new Item(
					itemsRS.getString("itemID"),
					itemsRS.getString("name"),
					itemsRS.getString("typeThatCanUse"),
					itemsRS.getInt("unlockAtLevel"),
					itemsRS.getInt("bonusToHealth"),
					itemsRS.getDouble("bonusToAttack"), 
					itemsRS.getDouble("bonusToDefense"));	
				
				return newItem;
			}	
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemImageURL(String itemID) {
		try {			
			PreparedStatement getItemURL = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM itemImages WHERE itemID = ?;");
			getItemURL.setString(1, itemID);
			ResultSet itemsRS = getItemURL.executeQuery();
			
			while(itemsRS.next()) {				
				return itemsRS.getString("url");
			}
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

}
