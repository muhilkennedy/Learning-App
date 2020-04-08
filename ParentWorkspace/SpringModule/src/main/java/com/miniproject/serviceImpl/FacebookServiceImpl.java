package com.miniproject.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.miniproject.service.FacebookService;

@Service
public class FacebookServiceImpl implements FacebookService {

	@Value("${spring.social.facebook.app-id}")
	private String facebookID;

	@Value("${spring.social.facebook.app-secret}")
	private String facebookSecret;

	@Value("${spring.social.facebook.redirectUri}")
	private String facebookRedirectUri;

	@Value("${spring.social.facebook.scope}")
	private String facebookScope;
	
	private FacebookConnectionFactory createFaceBookConnection() {
		return new FacebookConnectionFactory(facebookID, facebookSecret);
	}
	
	@Override
	public String facebookLogin() {
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri(facebookRedirectUri);
		parameters.setScope(facebookScope);
		return createFaceBookConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
	}
	
	@Override
	public String getFacebookAccessToken(String code) {
		return createFaceBookConnection().getOAuthOperations().exchangeForAccess(code, facebookRedirectUri, null)
				.getAccessToken();
	}
	
	@Override
	public org.springframework.social.facebook.api.User getFacebookUserProfile(String accesstoken) {
		Facebook facebook = new FacebookTemplate(accesstoken);
		return facebook.userOperations().getUserProfile();
	}
}
