package com.miniproject.util;

public class CommonUtil {

	public static String userPermission = "USER";
	public static String adminPermission = "ADMIN";
	public static boolean active = true;
	public static boolean inactive = false;
	public static String googleUser = "GOOGLE";
	public static String facebookUser = "FACEBOOK";
	public static String internalUser = "INTERNAL";
	public static int saltRounds = 5;
	
	public static boolean isNullOrEmptyString(String value) {
		if(value != null && !(value.length() <= 0))
			return false;
		else
			return true;
	}
}