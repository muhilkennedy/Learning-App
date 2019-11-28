/**
 * @author muhilkennedy
 */
package com.miniproject.databaseScripts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Independent java class for creation of DB tables,
 * This class is for ease of development purpose only
 */
public class DbDev {

	private static final Logger logger = LoggerFactory.getLogger(DbDev.class);
	// DB credentials
	static String DB_URL;
	static String DB_USER;
	static String DB_PASSWORD;
	static Properties property = null;
	static {
		logger.debug("loading DB properties");
		InputStream is = null;
		try {
			is = DbDev.class.getResourceAsStream("/database.properties");
			property = new Properties();
			property.load(is);
		} catch (IOException e) {
			logger.error("exception reading db properties!");
			e.printStackTrace();
		}
		/* List of db properties loaded */
		DB_URL = property.getProperty("db_url");
		DB_USER = property.getProperty("db_user_name");
		DB_PASSWORD = property.getProperty("db_password");
	}

	public static void main(String[] args) {
		Statement stmt = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			logger.debug("Connecting to a selected database..." + DB_URL);
			logger.debug("Creating tables in given database...");
			stmt = conn.createStatement();
			/* List of tables to be created.
			 * Maintain the order to avoid key constraint errors */
			stmt.executeUpdate(property.getProperty("User"));
			stmt.executeUpdate(property.getProperty("ADDRESS"));
			stmt.executeUpdate(property.getProperty("MAINCATEGORY"));
			stmt.executeUpdate(property.getProperty("SUBCATEGORY"));
			stmt.executeUpdate(property.getProperty("CART"));
			stmt.executeUpdate(property.getProperty("ORDER"));
			stmt.executeUpdate(property.getProperty("INVOICE"));
			stmt.executeUpdate(property.getProperty("VERIFICATION"));

		} catch (Exception se) {
			logger.debug("exception during sql execution!");
			se.printStackTrace();
		}
		logger.debug("Table creation success!");
	}
}
