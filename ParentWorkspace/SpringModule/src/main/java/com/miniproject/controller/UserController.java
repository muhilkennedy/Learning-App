package com.miniproject.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.User;
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
	private LoginService login;
	
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
			User user = login.findUser(email);
			if (user != null) {
				response.setData(ResponseUtil.cleanUpUserResponse(user));
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
	
}
