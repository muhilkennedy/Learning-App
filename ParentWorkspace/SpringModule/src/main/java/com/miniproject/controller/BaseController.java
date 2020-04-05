package com.miniproject.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.JWTResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.User;
import com.miniproject.service.LoginService;
import com.miniproject.util.JWTUtil;
import com.miniproject.util.LogUtil;

/**
 * @author muhilkennedy
 *
 */
@CrossOrigin
@RestController
@RequestMapping("base")
public class BaseController {
	
	@Autowired
	private LoginService login;
	
	@Autowired
	private JWTUtil jwtTokenUtil;
	
	@RequestMapping("/ping")
	public Response init() {
		Response response = new Response();
		response.setStatus(Response.Status.OK);
		return response;
	}
	
	/**
	 * @param userObj user details.
	 * @param request
	 * @return JWT token for authenticated user.
	 */

	@RequestMapping(value = "/userAuthentication", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<JWTResponse> getLoginData(@RequestBody User userObj, HttpServletRequest request) {
		GenericResponse<JWTResponse> response = new GenericResponse<>();
		try {
			User user = login.loginUser(userObj.getEmailId(), userObj.getPassword());
			if (user != null) {
				JWTResponse token = new JWTResponse();
				token.setToken(jwtTokenUtil.generateToken(user));
				token.setExpiry(jwtTokenUtil.getExpirationDateFromToken(token.getToken()).getTime());
				response.setData(token);
				response.setDataList(Arrays.asList(user));
				response.setStatus(Response.Status.OK);
			} else {
				response.setErrorMessages(Arrays.asList("Invalid User Credentials"));
				response.setStatus(Response.Status.FORBIDDEN);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().error("getLoginData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}
	
	/**
	 * @param userObj user details.
	 * @param request
	 * @return user details if successfully inserted.
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> insertUser(@RequestBody User userObj, HttpServletRequest request) {
		GenericResponse<User> response = new GenericResponse<>();
		boolean value = login.createUser(userObj);
		if (value) {
			login.sendVerification(userObj.getEmailId(), userObj.getVerification().getCode(), request.getRequestURL().toString());
			response.setStatus(Response.Status.OK);
		} else {
			response.setStatus(Response.Status.ERROR);
			response.setErrorMessages(Arrays.asList("Error Inserting User"));
		}
		return response;
	}

	/**
	 * @param email
	 * @param code
	 * @return verification status message.
	 */
	@RequestMapping(value = "/verifyUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 public String verifyUser(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "code", required = true) String code) {
		LogUtil.getLogger().debug("verifyUser :: Verication for " + email);
		try {
			if (login.verifyUser(email, code)) {
				return "Account verification Successfull...Please Login again";
			} else {
				return "Account verification failed";
			}
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	/**
	 * @param userObj user details.
	 * @param request
	 * @return JWT token for authenticated user.
	 */
	@RequestMapping(value = "/userAuthentication", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<JWTResponse> getLoginData(@RequestBody User userObj, HttpServletRequest request) {
		GenericResponse<JWTResponse> response = new GenericResponse<>();
		try {
			User user = login.loginUser(userObj.getEmailId(), userObj.getPassword());
			if (user != null) {
				JWTResponse token = new JWTResponse();
				token.setToken(jwtTokenUtil.generateToken(user));
				token.setExpiry(jwtTokenUtil.getExpirationDateFromToken(token.getToken()).getTime());
				response.setData(token);
				response.setDataList(Arrays.asList(user));
				response.setStatus(Response.Status.OK);
			} else {
				response.setErrorMessages(Arrays.asList("Invalid User Credentials"));
				response.setStatus(Response.Status.FORBIDDEN);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().error("getLoginData : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			return response;
		}
	}
	
	/**
	 * @param userObj user details.
	 * @param request
	 * @return user details if successfully inserted.
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> insertUser(@RequestBody User userObj, HttpServletRequest request) {
		GenericResponse<User> response = new GenericResponse<>();
		boolean value = login.createUser(userObj);
		if (value) {
			login.sendVerification(userObj.getEmailId(), userObj.getVerification().getCode(), request.getRequestURL().toString());
			response.setStatus(Response.Status.OK);
		} else {
			response.setStatus(Response.Status.ERROR);
			response.setErrorMessages(Arrays.asList("Error Inserting User"));
		}
		return response;
	}


	/**
	 * @param email
	 * @param code
	 * @return verification status message.
	 */
	@RequestMapping(value = "/verifyUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 public String verifyUser(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "code", required = true) String code) {
		LogUtil.getLogger().debug("verifyUser :: Verication for " + email);
		try {
			if (login.verifyUser(email, code)) {
				return "Account verification Successfull...Please Login again";
			} else {
				return "Account verification failed";
			}
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

}
