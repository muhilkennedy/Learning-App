package com.miniproject.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.dao.ItemDao;
import com.miniproject.model.Category;
import com.miniproject.model.Item;
import com.miniproject.repository.ItemRepository;
import com.miniproject.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private ItemDao itemDao;

	@Override
	public Item findItem(int id) {
		return itemRepo.findItem(id);
	}
	
	@Override
	public Item findActiveItem(int id) {
		return itemRepo.findActiveItem(id);
	}
	
	@Override
	public List<Item> findAllActiveItems() {
		return itemRepo.findAllActiveItems();
	}

	@Override
	@Transactional
	public void save(Item item) {
		itemRepo.save(item);
	}
	
	@Override
	@Transactional
	public Item createItem(Item item, Category category) throws Exception {
		item.setcId(category);
		item.setActive(true);
		save(item);
		return item;
	}
	
	@Override
	public List<Item> getItems(List<Integer> itemIds) throws Exception {
		List<Item> items = new ArrayList<>();
		itemIds.parallelStream().forEach(itemId -> {
			Item it = findActiveItem(itemId);
			if(it!=null) {
				items.add(it);
			}
		});
		return items;
	}
	
	@Override
	public List<Item> getItemsForCategory(List<Integer> cIds, String limit, String offset, boolean includeInactive) throws Exception{
		List<Integer> cIdToFetch = itemDao.getItemIds(cIds, limit, offset, includeInactive);
		List<Item> items = new ArrayList<>();
		cIdToFetch.parallelStream().forEach(cid -> {
			Item tempItem = itemRepo.findItem(cid);
			if(tempItem != null)
				items.add(tempItem);
		});
		return items;
	}
	
	@Override
	@Transactional
	public void deactivateItem (Item item) throws Exception {
		item.setActive(false);
		save(item);
	}
	
	/*
	 * Always soft delete item.
	 */
	@Override
	@Transactional
	public void deleteItem (Item item) throws Exception {
		item.setActive(false);
		item.setDeleted(true);
		save(item);
	}
	
	@Override
	@Transactional
	public Item updateItem (Item item, Item newItem, Category category) throws Exception {
		item.setActive(false);
		save(item);
		newItem.setActive(true);
		newItem.setcId(category);
		save(newItem);
		return newItem;
	}
}
