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
import com.miniproject.service.VerificationService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.JWTUtil;
import com.miniproject.util.LogUtil;

/**
 * @author muhilkennedy
 *
 */
@RestController
@RequestMapping("base")
public class BaseController {
	
	@Autowired
	private LoginService login;
	
	@Autowired
	VerificationService verificationService;
	
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
			login.sendVerification(userObj.getEmailId(), userObj.getVerification().getCode(),
					request.getRequestURL().toString(), false);
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
	public String verifyUser(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "code", required = true) String code) {
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
	 * @param email
	 * @return verification code status
	 */
	@RequestMapping(value = "/sendVerification", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<String> sendVerification(@RequestParam(value = "emailId", required = true) String email) {
		GenericResponse<String> response = new GenericResponse<>();
		LogUtil.getLogger().debug("sendVerification :: Re-Verication for " + email);
		try {
			User user = login.findUser(email);
			if(user != null) {
				login.createUserVerification(user);
				response.setStatus(Response.Status.OK);
				response.setData("Email Sent Successfully");
			}
			else {
				response.setStatus(Response.Status.NOT_FOUND);
				response.setData("User Does not Exists");
			}
			
		} catch (Exception ex) {
			LogUtil.getLogger().debug("sendVerification :: Exception - " + ex);
			response.setStatus(Response.Status.ERROR);
			response.setErrorMessages(Arrays.asList("Error Sending Verification Code"));
		}
		return response;
	}
	
	/**
	 * @param userObj
	 * @return status for user password update
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<String> updatePassword(@RequestBody User userObj) {
		GenericResponse<String> response = new GenericResponse<>();
		LogUtil.getLogger().debug("verifyUser :: Verication for " + userObj.getEmailId());
		try {
			if (login.verifyUser(userObj.getEmailId(), userObj.getVerification().getCode())) {
				login.updateUserPassword(userObj, userObj.getPassword());
				response.setData("Account verification Successfull...Please Login again");
				response.setStatus(Response.Status.OK);
			} else {
				response.setData("Account verification failed");
				response.setStatus(Response.Status.ERROR);
			}
		} catch (Exception ex) {
			LogUtil.getLogger().debug("sendVerification :: Exception - " + ex);
			response.setStatus(Response.Status.ERROR);
			response.setErrorMessages(Arrays.asList("Error Sending Verification Code"));
		}
		return response;
	}

}
