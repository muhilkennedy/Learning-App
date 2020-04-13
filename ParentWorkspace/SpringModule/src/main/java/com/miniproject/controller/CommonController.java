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
import com.miniproject.model.Category;
import com.miniproject.model.User;
import com.miniproject.service.CategoryService;
import com.miniproject.service.LoginService;
import com.miniproject.util.InvoiceUtil;
import com.miniproject.util.LogUtil;

@RestController
@RequestMapping("common")
public class CommonController {
	
	@Autowired
	CategoryService category;
	
	@Autowired
	private LoginService login;
	
	@RequestMapping(value = "/getCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> getUserData(@RequestParam(value = "categoryName", required = false) String catName,
											 HttpServletRequest request) {
		GenericResponse<Category> response = new GenericResponse<>();
		try {
			Category cat = category.findByName(catName);
			Map child = category.findChildrenRecursive(cat.getcId());
			response.setData(cat);
			response.setDataList(Arrays.asList(child));
			response.setStatus(Response.Status.OK);
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
			Map map = category.buildTreeMap();
			response.setData(map);
		}catch (Exception ex) {
			LogUtil.getLogger().error("getUserData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/generateInvoice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> getInvoice() {
		GenericResponse<User> response = new GenericResponse<>();
		try {
			User user = login.findUser("muhil1");
			InvoiceUtil.generateInvoice(user);
			user = null ;
			response.setData(user);
		}catch (Exception ex) {
			LogUtil.getLogger().error("getUserData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
