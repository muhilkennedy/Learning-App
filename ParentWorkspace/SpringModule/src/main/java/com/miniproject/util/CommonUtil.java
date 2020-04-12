package com.miniproject.util;

public class CommonUtil {
	
	public static final String Key_userPermission = "USER";
	public static final String Key_adminPermission = "ADMIN";
	public static final boolean Key_active = true;
	public static final boolean Key_inactive = false;
	public static final String Key_googleUser = "GOOGLE";
	public static final String Key_facebookUser = "FACEBOOK";
	public static final String Key_internalUser = "INTERNAL";
	public static final int saltRounds = 5;
	public static final int maxVerificationTime = 1;
	private static final int randomCodeLength = 8;
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
	public static final String verificationPath = "/verifyUser";
	public static final String userCreationPath = "/createUser";
	public static final String Key_code = "code";
	public static final String Key_email = "email";
	 

	public static boolean isNullOrEmptyString(String value) {
		if (value != null && !(value.length() <= 0))
			return false;
		else
			return true;
	}

	public static String generateRandomCode() {
		StringBuilder builder = new StringBuilder();
		int count = randomCodeLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}