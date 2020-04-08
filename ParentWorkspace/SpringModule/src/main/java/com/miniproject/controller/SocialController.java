package com.miniproject.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
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
import com.miniproject.util.ConfigUtil;
import com.miniproject.util.JWTUtil;
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
	
	@Autowired
	private JWTUtil jwtTokenUtil;
	
	@Autowired
	private ConfigUtil configUtil;

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
			//persist user details in db if logging in for first time.
			User user = loginService.findActiveUser(googleUser.getEmail());
			if(user == null) {
				user = new User(googleUser.getEmail(), null, null, CommonUtil.Key_userPermission,
						googleUser.getFirstName(), googleUser.getLastName(), CommonUtil.Key_active, CommonUtil.Key_googleUser);
				loginService.saveUser(user);
			}
			String token = jwtTokenUtil.generateToken(user);
			url = addParamsToUrl(configUtil.uiHost(), token);
		} catch (Exception ex) {
			url = configUtil.uiHost()+"/error?msg=" + ex.getMessage();
			LogUtil.getLogger(SocialController.class).error(ex.getMessage());
		} finally {
			redirectURL.setUrl(url);
			return redirectURL;
		}
	}
	
	public String addParamsToUrl(String url, String token) throws URISyntaxException {
		URIBuilder urlBuilder = new URIBuilder(url);
		urlBuilder.setPath("/redirect");
		urlBuilder.addParameter("token", token);
		return urlBuilder.toString();
	}
}
