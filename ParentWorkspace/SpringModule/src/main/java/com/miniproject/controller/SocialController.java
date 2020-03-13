package com.miniproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.miniproject.messages.Response;
import com.miniproject.messages.SocialResponse;
import com.miniproject.model.User;
import com.miniproject.service.GoogleService;
import com.miniproject.service.LoginService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;
import com.miniproject.util.SocialUtil;

/**
 * @author MuhilKennedy
 *
 */
@RestController
@RequestMapping("social")
public class SocialController {

	@Autowired
	private GoogleService googleService;
	
	@Autowired
	private LoginService loginService;

	/**
	 * @return redirect URL for goole login
	 */
	@GetMapping(value = "/googleLogin")
	public SocialResponse googleLogin() {
		String url = googleService.googleLogin();
		SocialResponse response = new SocialResponse();
		if (url != null) {
			response.setStatus(Response.Status.OK);
			response.setURL(url);
		} else {
			List<String> msg = new ArrayList<>();
			msg.add("Google Redirect URL is NOT GENERATED");
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.ERROR);
		}
		return response;
	}

	/**
	 * @param code sent from google based on the redirect url given in configuration
	 * @return	redirects to index page with auth details.
	 */
	@GetMapping(value = "/google")
	public RedirectView googleData(@RequestParam("code") String code) {
		RedirectView redirectURL = new RedirectView();
		String url = null;
		try {
			JSONObject jsonObj = new JSONObject();
			String accessToken = googleService.getGoogleAccessToken(code);
			GoogleUserInfo googleUser = googleService.getGoogleUserProfile(accessToken);
			jsonObj.put(SocialUtil.googleData.firstName.toString(), googleUser.getFirstName());
			jsonObj.put(SocialUtil.googleData.lastName.toString(), googleUser.getLastName());
			jsonObj.put(SocialUtil.googleData.imageUrl.toString(), googleUser.getProfilePictureUrl());
			jsonObj.put(SocialUtil.googleData.email.toString(), googleUser.getEmail());
			jsonObj.put(SocialUtil.googleData.accessToken.toString(), accessToken);
			url = "http://localhost:8080/index.html?status=1&email=" + googleUser.getEmail();
			//persist user details in db if logging in for first time.
			User user = loginService.findActiveUser(googleUser.getEmail());
			if(user == null) {
				loginService.saveUser(new User(googleUser.getEmail(), null, null, CommonUtil.userPermission,
						googleUser.getFirstName(), googleUser.getLastName(), CommonUtil.active, CommonUtil.googleUser));
			}
		} catch (Exception ex) {
			url = "http://localhost:8080/index.html?status=2&msg=" + ex.getMessage();
			LogUtil.getLogger(SocialController.class).error(ex.getMessage());
		} finally {
			redirectURL.setUrl(url);
			return redirectURL;
		}
	}
}
