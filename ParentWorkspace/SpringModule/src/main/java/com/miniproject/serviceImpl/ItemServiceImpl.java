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
	public List<Item> getItems(List<Integer> cIds, String limit, String offset) throws Exception{
		List<Integer> cIdToFetch = itemDao.getItemIds(cIds, limit, offset);
		List<Item> items = new ArrayList<>();
		cIdToFetch.parallelStream().forEach(cid -> {
			Item tempItem = itemRepo.findItem(cid);
			if(tempItem != null)
				items.add(tempItem);
		});
		return items;
	}
}
