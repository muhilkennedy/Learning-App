package com.miniproject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miniproject.project.ProjectApplication;

/**
 * @author muhilkennedy
 *
 * Util function for logger factory object.
 */
public class LogUtil {

	private static final Logger logger = LoggerFactory.getLogger(ProjectApplication.class);

	public static Logger getLogger() {
		return logger;
	}

	public static Logger getLogger(Class<?> class1) {
		return LoggerFactory.getLogger(class1);
	}
}