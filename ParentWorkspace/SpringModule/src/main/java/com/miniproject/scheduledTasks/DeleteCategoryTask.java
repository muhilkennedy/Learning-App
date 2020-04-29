package com.miniproject.scheduledTasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.miniproject.model.Category;
import com.miniproject.model.Item;
import com.miniproject.service.CategoryService;
import com.miniproject.service.ItemService;
import com.miniproject.util.LogUtil;

/**
 * @author Muhil Kennedy
 * Soft deletes marked categorya and its child recursively and all the associated items every 1hr.
 */
@Component
public class DeleteCategoryTask implements ScheduledTask {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ItemService itemService;

	//cron = sec min hour day mon dayOfWeek year.
	@Scheduled(cron = " 0 0 0/1 * * * ")
	@Override
	public void execute() {
		LogUtil.getLogger().info("Scheduled Task Executed : " + new Date());
		List<Category> categoryList = categoryService.findAllCategoryMarkedForDeletion();
		List<Integer> cIds = new ArrayList<>();
		try {
			if (!CollectionUtils.isEmpty(categoryList)) {
				// Soft delete categories
				for (Category cat : categoryList) {
					LogUtil.getLogger().info("DeleteCategoryTask for : " + cat.getcId());
					cIds.addAll(categoryService.findChildrenIdRecursive(cat));
					categoryService.deleteCategory(cat, true);
				}
				// Soft delete items to the related categories
				List<Item> items = itemService.getItemsForCategory(cIds, null, null, true);
				items.stream().forEach(item -> {
					try {
						itemService.deleteItem(item);
					} catch (Exception e) {
						LogUtil.getLogger().info("DeleteCategoryTask :: Exception deleting item : " + e.getMessage());
					}
				});
			}
		} catch (Exception e) {
			LogUtil.getLogger().info("DeleteCategoryTask Exception : " + e.getMessage());
		}
	}

}
