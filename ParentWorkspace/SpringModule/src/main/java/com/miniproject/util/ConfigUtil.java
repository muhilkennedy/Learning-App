package com.miniproject.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Muhil Kennedy
 *
 */
@Component
public class ConfigUtil {

	@Value("${project.deployment.mode}")
	private String deploymentMode;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Value("${spring.flyway.enabled}")
	private boolean flywayEnabled;
	
	@Value("${spring.social.gmail.id}")
	private String senderGmailId;
	
	@Value("${spring.social.gmail.password}")
	private String senderGmailPassword;
	
	@Value("${angular.ui.host}")
	private String angularUiHost;
	
	@Value("${spring.social.gmail.enable-mailing}")
	private boolean mailingService;

	public boolean isProdDeploymentMode() {
		return ("prod".equals(deploymentMode));
	}
	
	public boolean isFlywayEnabled() {
		return flywayEnabled;
	}
	
	public String getSenderGmailId() {
		return senderGmailId;
	}
	
	public String getSenderGmailPassword() {
		return senderGmailPassword;
	}
	
	public String getApplicationName() {
		return appName;
	}
	
	public String uiHost() {
		return angularUiHost;
	}
	
	public boolean isMailingServiceEnabled() {
		return mailingService;
	}
}
