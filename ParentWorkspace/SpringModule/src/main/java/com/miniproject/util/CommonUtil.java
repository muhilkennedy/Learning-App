package com.miniproject.util;

import java.io.File;

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
	public static final int maxCategoryRemovalTime = 6;
	private static final int randomCodeLength = 8;
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
	public static final String verificationPath = "/verifyUser";
	public static final String userCreationPath = "/createUser";
	public static final String Key_code = "code";
	public static final String Key_email = "email";
	public static final String Key_PendingStatus = "Pending";
	public static final String Key_AcceptedStatus = "Accepted";
	public static final String Key_DeliveredStatus = "Delivered";
	public static final String Key_OutForDeliveryStatus = "Out Fot Delivery";
	public static final String Key_Rejected = "Rejected"; 
	
	public static final String Header_Limit = "Limit";
	public static final String Header_Offset = "Offset";
	public static final String Header_Scope = "Scope";

	public static boolean isNullOrEmptyString(String value) {
		if (value != null && !(value.length() <= 0))
			return false;
		else
			return true;
	}

	/**
	 * @return randome code wiht pre-defined length and Alpha numeric characters.
	 */
	public static String generateRandomCode() {
		StringBuilder builder = new StringBuilder();
		int count = randomCodeLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * @see This methos needs to be called everytime after a temp file/Dir is
	 *      created in order keep the memory optimized.
	 * @param file to be deleted
	 * @return true if successfully removed.
	 */
	public static boolean deleteDirectoryOrFile(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectoryOrFile(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		LogUtil.getLogger().info("Removing Dir - " + dir.getPath());
		return dir.delete();
	}
}