package com.miniproject.service;

import java.util.List;

import com.miniproject.model.Item;

public interface ItemService {
	
	public void save(Item item);
	
	public Item find(int itemId);
	
	public List<Item> findItemByCategory(int cId);

	public List<Item> findItemByName(String Name);

}
