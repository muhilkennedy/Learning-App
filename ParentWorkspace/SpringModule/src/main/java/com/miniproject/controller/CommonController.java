package com.miniproject.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.Response;
import com.miniproject.messages.SQLQueryHandler.SQLQueryBuilder;
import com.miniproject.model.Category;
import com.miniproject.model.Item;
import com.miniproject.service.CategoryService;
import com.miniproject.service.ItemService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;

/**
 * @author Muhil Kennedy
 * contains endpoints that does not require user authentication.
 */
@RestController
@RequestMapping("common")
public class CommonController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping(value = "/getCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> getUserData(@RequestParam(value = "categoryName", required = false) String catName,
											 HttpServletRequest request) {
		GenericResponse<Category> response = new GenericResponse<>();
		try {
			Category cat = categoryService.findByName(catName);
			if(cat != null) {
				Map child = categoryService.findChildrenRecursive(cat.getcId());
				response.setData(cat);
				response.setDataList(Arrays.asList(child));
				response.setStatus(Response.Status.OK);
			}
			else {
				response.setErrorMessages(Arrays.asList("Category not found"));
				response.setStatus(Response.Status.NO_CONTENT);
			}
		}catch (Exception ex) {
			LogUtil.getLogger().error("getUserData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/getCategoryHierarchy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Map> getAllCategories() {
		GenericResponse<Map> response = new GenericResponse<>();
		try {
			Map map = categoryService.buildTreeMap();
			response.setData(map);
		}catch (Exception ex) {
			LogUtil.getLogger().error("getUserData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/getItem", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Item> getInvoice(
			@RequestParam(value = "itemId", required = true) String itemId, HttpServletRequest request) {
		GenericResponse<Item> response = new GenericResponse<>();
		try {
			Item item = itemService.findItem(Integer.parseInt(itemId));
			if(item != null) {
				response.setData(item);
				response.setStatus(Response.Status.OK);
			}
			else {
				response.setErrorMessages(Arrays.asList("Item not found"));
				response.setStatus(Response.Status.NO_CONTENT);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().error("getInvoice : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/getItemsForCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Item> getItemsForCategory(
			@RequestParam(value = "categoryId", required = true) String categoryId, HttpServletRequest request) {
		GenericResponse<Item> response = new GenericResponse<>();
		try {
			Category cat = categoryService.find(Integer.parseInt(categoryId));
			if(cat != null) {
				List<Integer> catagories  = categoryService.findChildrenIdRecursive(cat);
				String limit = request.getHeader(CommonUtil.Header_Limit);
				String offset = request.getHeader(CommonUtil.Header_Offset);
				List<Item> items = itemService.getItems(catagories, limit, offset);
				response.setDataList(items);
				response.setStatus(Response.Status.OK);
			}
			else {
				response.setErrorMessages(Arrays.asList("Category not found"));
				response.setStatus(Response.Status.NO_CONTENT);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().error("getInvoice : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	
}
