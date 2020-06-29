package com.revature.mariokartfighter_v2.dao;

import java.util.List;

import com.revature.mariokartfighter_v2.models.Item;

public interface IItemRepo {
	public Item addItem(Item item);
	
	public List<Item> getAllItems();
	public List<Item> getSomeItems(int level);
	public Item getItemInfo(String itemName);
	public String getItemImageURL(String itemID);

	public void setItemImageURL(String itemID, String url);
	
	public void removeItems(String name);
	public void deleteItemImageURL(String itemID);
}
