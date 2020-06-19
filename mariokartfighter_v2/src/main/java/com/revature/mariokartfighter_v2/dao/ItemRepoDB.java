package com.revature.mariokartfighter_v2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter_v2.models.Item;
import com.revature.mariokartfighter_v2.service.ConnectionService;

public class ItemRepoDB implements IItemRepo {
	ConnectionService connectionService;
	
	public ItemRepoDB(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	
	@Override
	public Item addItem(Item item) {
		try {			
			PreparedStatement itemInsert = connectionService.getConnection().prepareStatement(
					"INSERT INTO item VALUES (?, ?, ?, ?, ?, ?, ?)");
			itemInsert.setString(1, item.getItemID());
			itemInsert.setString(2, item.getItemName());
			itemInsert.setString(3, item.getTypeThatCanUse());
			itemInsert.setInt(4, item.getBonusToHealth());
			itemInsert.setDouble(5, item.getBonusToAttack());
			itemInsert.setDouble(6, item.getBonusToDefense());
			itemInsert.setInt(7, item.getUnlockAtLevel());
			
			itemInsert.executeUpdate();
			
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
			PreparedStatement getItems = connectionService.getConnection().prepareStatement(
					"SELECT * FROM item;");
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
			PreparedStatement getItems = connectionService.getConnection().prepareStatement(
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
			PreparedStatement removeItems = connectionService.getConnection().prepareStatement(
					"DELETE FROM item "
					+ "WHERE itemID LIKE ?");
			removeItems.setString(1, name+'%');
			removeItems.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
