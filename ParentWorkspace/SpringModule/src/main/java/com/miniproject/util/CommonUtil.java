package com.miniproject.util;

public class CommonUtil {

	public static String userPermission = "user";
	public static String adminPermission = "admin";
	public static boolean active = true;
	public static boolean inactive = false;
	
	public static boolean isNullOrEmptyString(String value) {
		if(value != null && !(value.length() <= 0))
			return false;
		else
			return true;
	}
}
