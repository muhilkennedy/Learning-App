package com.miniproject.service;

import org.springframework.social.facebook.api.User;

public interface FacebookService {

	String facebookLogin();

	String getFacebookAccessToken(String code);

	User getFacebookUserProfile(String accesstoken);
	
}
