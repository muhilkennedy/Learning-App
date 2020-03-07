package com.miniproject.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.User;
import com.miniproject.service.LoginService;
import com.miniproject.util.LogUtil;

/**
 * @author MuhilKennedy
 *
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private LoginService login;
	
	@RequestMapping("/userDetail")
	public GenericResponse<User> getUserData(@RequestParam(value = "email", required = false) String email) {
		GenericResponse<User> response = new GenericResponse<>();
		try {
			User user = login.findActiveUser(email);
			if (user != null) {
				user.setPassword(null);
				response.setData(user);
				response.setStatus(Response.Status.OK);
			} else {
				List<String> msg = Arrays.asList("User Does Not Exist");
				response.setErrorMessages(msg);
				response.setStatus(Response.Status.NOT_FOUND);
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
	
	@PostMapping("/login")
	public GenericResponse<User> getLoginData(@RequestBody User userObj) {
		GenericResponse<User> response = new GenericResponse<>();
		try {
			response = login.loginUser(userObj.getEmailId(), userObj.getPassword());
		} catch (Exception ex) {
			LogUtil.getLogger().error("getLoginData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}

	@PostMapping("/create")
	public GenericResponse<User> insertUser(@RequestBody User userObj) {
		GenericResponse<User> response = new GenericResponse<>();
		boolean value = login.createUser(userObj);
		if (value) {
			response.setStatus(Response.Status.OK);
		} else {
			response.setStatus(Response.Status.ERROR);
			response.setErrorMessages(Arrays.asList("Error Inserting User"));
		}
		return response;
	}
	
}
