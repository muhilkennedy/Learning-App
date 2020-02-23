package com.miniproject.service;

import org.springframework.social.google.api.userinfo.GoogleUserInfo;

public interface GoogleService {

	String googleLogin();

	String getGoogleAccessToken(String code);

	GoogleUserInfo getGoogleUserProfile(String accesstoken);

}
