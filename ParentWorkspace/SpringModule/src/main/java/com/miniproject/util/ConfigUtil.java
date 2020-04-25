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
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	@Value("${spring.datasource.username}")
	private String dbUser;
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	
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

	public String getDbUrl() {
		return dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

}
