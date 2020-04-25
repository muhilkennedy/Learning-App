package com.miniproject.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.Cart;
import com.miniproject.model.User;
import com.miniproject.service.ItemService;
import com.miniproject.service.LoginService;
import com.miniproject.util.JWTUtil;
import com.miniproject.util.LogUtil;
import com.miniproject.util.ResponseUtil;

/**
 * @author MuhilKennedy
 *
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private LoginService userService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private JWTUtil jwtTokenUtil;
	
	@RequestMapping(value = "/userDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> getUserData(@RequestParam(value = "email", required = false) String email,
											 HttpServletRequest request) {
		GenericResponse<User> response = new GenericResponse<>();
		try {
			if (email == null) {
				email = jwtTokenUtil.getUserEmailFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
			}
			User user = userService.findUser(email);
			if (user != null) {
				response.setData(ResponseUtil.cleanUpUserResponse(user));
				response.setStatus(Response.Status.OK);
			} else {
				List<String> msg = Arrays.asList("User Does Not Exist");
				response.setErrorMessages(msg);
				response.setStatus(Response.Status.NO_CONTENT);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().error("getUserData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}
	
	@RequestMapping(value = "/getCartItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Map> getCartItems(@RequestParam(value = "email", required = false) String email,
											 HttpServletRequest request) {
		GenericResponse<Map> response = new GenericResponse<>();
		try {
			if (email == null) {
				email = jwtTokenUtil.getUserEmailFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
			}
			User user = userService.findUser(email);
			if (user != null) {
				Map<Integer, Integer> itemQuantityMap = userService.getCartForUser(user.getUserId());
				response.setData(itemQuantityMap);
				response.setDataList(itemService.getItems(Lists.newArrayList(itemQuantityMap.keySet()), null, null));
				response.setStatus(Response.Status.OK);
			} else {
				List<String> msg = Arrays.asList("User Does Not Exist");
				response.setErrorMessages(msg);
				response.setStatus(Response.Status.NO_CONTENT);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().error("getUserData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	@RequestMapping(value = "/insertItemInCart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> insertItemInCart(@RequestParam(value = "email", required = false) String email,
			@RequestBody List<Cart> cartItems,
			HttpServletRequest request) {
		GenericResponse<User> response = new GenericResponse<>();
		try {
			if (email == null) {
				email = jwtTokenUtil.getUserEmailFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
			}
			User user = userService.findUser(email);
			if (user != null) {
				user = userService.insertCartToUser(user, cartItems);
				response.setData(ResponseUtil.cleanUpUserResponse(user));
				response.setStatus(Response.Status.OK);
			} else {
				List<String> msg = Arrays.asList("User Does Not Exist");
				response.setErrorMessages(msg);
				response.setStatus(Response.Status.NO_CONTENT);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().error("getUserData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	
}
