package com.miniproject.service;

import java.util.List;

import com.miniproject.model.Category;
import com.miniproject.model.Item;

public interface ItemService {

	Item findItem(int id);

	void save(Item item);

	Item createItem(Item item, Category category) throws Exception;

	List<Item> getItems(List<Integer> cIds, String limit, String offset) throws Exception;

}
