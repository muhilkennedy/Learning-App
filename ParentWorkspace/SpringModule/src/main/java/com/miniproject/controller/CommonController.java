package com.miniproject.controller;

import java.io.File;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.ItemResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.Category;
import com.miniproject.model.Item;
import com.miniproject.service.CategoryService;
import com.miniproject.service.ItemService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;
import com.miniproject.util.ResponseUtil;

/**
 * @author Muhil Kennedy
 * contains endpoints that does not require user authentication.
 */
@RestController
@RequestMapping("common")
public class CommonController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ItemService itemService;
	
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
	public GenericResponse<ItemResponse> getInvoice(
			@RequestParam(value = "itemId", required = true) String itemId, HttpServletRequest request) {
		GenericResponse<ItemResponse> response = new GenericResponse<>();
		try {
			Item item = itemService.findItem(Integer.parseInt(itemId));
			if(item != null) {
				response.setData(ResponseUtil.convertItemModelToItemResponse(item));
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
	public GenericResponse<ItemResponse> getItemsForCategory(
			@RequestParam(value = "categoryId", required = true) String categoryId, HttpServletRequest request) {
		GenericResponse<ItemResponse> response = new GenericResponse<>();
		try {
			Category cat = categoryService.find(Integer.parseInt(categoryId));
			if(cat != null) {
				List<Integer> catagories  = categoryService.findChildrenIdRecursive(cat);
				String limit = request.getHeader(CommonUtil.Header_Limit);
				String offset = request.getHeader(CommonUtil.Header_Offset);
				List<Item> items = itemService.getItemsForCategory(catagories, limit, offset, false);
				response.setDataList(ResponseUtil.convertItemModelToItemResponse(items));
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
	
	@RequestMapping(value = "/getAllItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ItemResponse> getAllItems(HttpServletRequest request) {
		GenericResponse<ItemResponse> response = new GenericResponse<>();
		try {
				String limit = request.getHeader(CommonUtil.Header_Limit);
				String offset = request.getHeader(CommonUtil.Header_Offset);
				List<Item> items = itemService.getAllItems(limit, offset);
				response.setDataList(ResponseUtil.convertItemModelToItemResponse(items));
				response.setStatus(Response.Status.OK);
		} catch (Exception ex) {
			LogUtil.getLogger().error("getInvoice : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

//	Remove this method before commit
	@RequestMapping(value = "/setItem", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ItemResponse> testAddItem(
			@RequestParam(value = "itemId", required = true) String itemId, HttpServletRequest request) {
		GenericResponse<ItemResponse> response = new GenericResponse<>();
		Item item = itemService.findItem(Integer.parseInt(itemId));
		File file = new File("/Users/i339628/Desktop/m30s.jpg");
		try {
			Blob blob = new SerialBlob(FileUtils.readFileToByteArray(file));
			item.setImage(blob);
			itemService.save(item);
			
			byte[] bytes= FileUtils.readFileToByteArray(file);
			response.setStatus(Response.Status.OK);
			response.setData(ResponseUtil.convertItemModelToItemResponse(item));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	
}
