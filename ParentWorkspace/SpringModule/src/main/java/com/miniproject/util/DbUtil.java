package com.miniproject.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbUtil {
	
	@Autowired
	ConfigUtil configUtil;

	public Connection getConnectionInstance() throws Exception {
		return DriverManager.getConnection(configUtil.getDbUrl(), configUtil.getDbUser(), configUtil.getDbPassword());
	}
	
}
