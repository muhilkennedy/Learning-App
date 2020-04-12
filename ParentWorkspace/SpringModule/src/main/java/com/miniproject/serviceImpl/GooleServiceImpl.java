package com.miniproject.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.miniproject.service.GoogleService;

@Service
public class GooleServiceImpl implements GoogleService {

	@Value("${spring.social.google.app-id}")
	private String googleID;

	@Value("${spring.social.google.app-secret}")
	private String googleSecret;

	@Value("${spring.social.google.redirectUri}")
	private String googleRedirectUri;

	@Value("${spring.social.google.scope}")
	private String googleScope;

	private GoogleConnectionFactory createGoogleConnection() {
		return new GoogleConnectionFactory(googleID, googleSecret);
	}

	@Override
	public String googleLogin() {
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri(googleRedirectUri);
		parameters.setScope(googleScope);
		return createGoogleConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
	}

	@Override
	public String getGoogleAccessToken(String code) {
		return createGoogleConnection().getOAuthOperations().exchangeForAccess(code, googleRedirectUri, null)
				.getAccessToken();
	}

	@Override
	public GoogleUserInfo getGoogleUserProfile(String accesstoken) {
		Google google = new GoogleTemplate(accesstoken);
		return google.userOperations().getUserInfo();
	}

}
