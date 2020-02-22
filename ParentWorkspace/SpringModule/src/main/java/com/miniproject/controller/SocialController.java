package com.miniproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.miniproject.messages.Response;
import com.miniproject.messages.SocialResponse;
import com.miniproject.service.GoogleService;
import com.miniproject.util.SocialUtil;

/**
 * @author MuhilKennedy
 *
 */
@RestController
public class SocialController {

	@Autowired
	private GoogleService googleService;

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
			GoogleUserInfo user = googleService.getGoogleUserProfile(accessToken);
			jsonObj.put(SocialUtil.googleData.firstName.toString(), user.getFirstName());
			jsonObj.put(SocialUtil.googleData.lastName.toString(), user.getLastName());
			jsonObj.put(SocialUtil.googleData.imageUrl.toString(), user.getProfilePictureUrl());
			jsonObj.put(SocialUtil.googleData.email.toString(), user.getEmail());
			jsonObj.put(SocialUtil.googleData.accessToken.toString(), accessToken);
			url = "http://localhost:8080/index.html?status=1&username=" + user.getFirstName() + "&email="
					+ user.getEmail();
		} catch (Exception ex) {
			url = "http://localhost:8080/index.html?status=2&msg=" + ex.getMessage();
		} finally {
			redirectURL.setUrl(url);
			return redirectURL;
		}
	}
}
