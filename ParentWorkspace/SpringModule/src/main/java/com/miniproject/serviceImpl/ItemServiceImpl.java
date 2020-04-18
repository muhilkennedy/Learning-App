package com.miniproject.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.model.Item;
import com.miniproject.repository.ItemRepository;
import com.miniproject.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired ItemRepository itemRepo;

	@Override
	public void save(Item item) {
		itemRepo.save(item);
	}

	@Override
	public Item find(int itemId) {
		return itemRepo.findItem(itemId);
	}

	@Override
	public List<Item> findItemByCategory(int cId) {
		return itemRepo.findItemByCategory(cId);
	}

	@Override
	public List<Item> findItemByName(String name) {
		return itemRepo.findItembyItemName(name);
	}

}
