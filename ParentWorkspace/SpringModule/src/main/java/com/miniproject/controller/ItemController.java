package com.miniproject.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.Category;
import com.miniproject.model.Item;
import com.miniproject.service.CategoryService;
import com.miniproject.service.ItemService;
import com.miniproject.util.LogUtil;

/**
 * @author Muhil Kennedy
 * contains endpoints related to CATEGORY and ITEM.
 *
 */
@RestController
@RequestMapping("item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/createCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> createCategory(@RequestBody Category category){
		GenericResponse<Category> response = new GenericResponse<>();
		try {
			category = categoryService.createCategory(category);
			response.setData(category);
			response.setStatus(Response.Status.OK);
		}catch (Exception ex) {
			LogUtil.getLogger().error("createCategory : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<String> deleteCategory(@RequestParam(value = "categoryId", required = true) String catId){
		GenericResponse<String> response = new GenericResponse<>();
		try {
			Category cat = categoryService.find(Integer.parseInt(catId));
			if(cat != null) {
				//Child deltion will be taken care in the Scheduled task.
				categoryService.markCategoryForDeletion(cat, false);
				response.setStatus(Response.Status.OK);
			}
			else {
				response.setStatus(Response.Status.NOT_FOUND);
				response.setErrorMessages(Arrays.asList("Item not found"));
			}
		}catch (Exception ex) {
			LogUtil.getLogger().error("createItem : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/createItem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Item> createItem(@RequestParam(value = "categoryId", required = true) String catId,
											@RequestBody Item item){
		GenericResponse<Item> response = new GenericResponse<>();
		try {
			Category category = categoryService.find(Integer.parseInt(catId));
			if(category != null) {
				itemService.createItem(item, category);
				response.setStatus(Response.Status.OK);
			}
			else {
				response.setErrorMessages(Arrays.asList("Category not found"));
				response.setStatus(Response.Status.NOT_FOUND);
			}
		}catch (Exception ex) {
			LogUtil.getLogger().error("createItem : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/deleteItem", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<String> createItem(@RequestParam(value = "itemId", required = true) String itemId){
		GenericResponse<String> response = new GenericResponse<>();
		try {
			Item item = itemService.findActiveItem(Integer.parseInt(itemId));
			if(item != null) {
				itemService.deleteItem(item);
				response.setStatus(Response.Status.OK);
			}
			else {
				response.setStatus(Response.Status.NOT_FOUND);
				response.setErrorMessages(Arrays.asList("Item not found"));
			}
		}catch (Exception ex) {
			LogUtil.getLogger().error("createItem : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/updateItem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Item> updateItem(@RequestParam(value = "itemId", required = true) String itemId,
											@RequestParam(value = "categoryId", required = true) String catId,
											@RequestBody Item newItem){
		GenericResponse<Item> response = new GenericResponse<>();
		try {
			Item item = itemService.findActiveItem(Integer.parseInt(itemId));
			if (item != null) {
				itemService.deleteItem(item);
				response.setData(itemService.updateItem(item, newItem, categoryService.find(Integer.parseInt(catId))));
				response.setStatus(Response.Status.OK);
			}
			else {
				response.setStatus(Response.Status.NOT_FOUND);
				response.setErrorMessages(Arrays.asList("Item not found"));
			}
		}catch (Exception ex) {
			LogUtil.getLogger().error("createItem : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
}
