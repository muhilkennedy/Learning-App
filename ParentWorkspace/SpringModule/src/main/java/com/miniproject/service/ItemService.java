package com.miniproject.service;

import java.util.List;

import com.miniproject.model.Category;
import com.miniproject.model.Item;

public interface ItemService {

	Item findItem(int id);

	void save(Item item);

	Item createItem(Item item, Category category) throws Exception;

	List<Item> getItems(List<Integer> cIds) throws Exception;

	Item findActiveItem(int id);

	Item updateItem(Item item, Item newItem, Category category) throws Exception;

	void deleteItem(Item item) throws Exception;

	void deactivateItem(Item item) throws Exception;

	List<Item> getItemsForCategory(List<Integer> cIds, String limit, String offset, boolean includeInactive) throws Exception;

	List<Item> findAllActiveItems();

}
