package com.miniproject.util;

import org.springframework.util.CollectionUtils;

import com.miniproject.model.User;

/**
 * @author MuhilKennedy
 * Modify objects for limiting response data.
 *
 */
public class ResponseUtil {

	public static User cleanUpUserResponse(User user) {
		if (user != null) {
			/* Reset password to null to avoid it in api response */
			user.setPassword(null);
			/* Remove address user object to avoid json StackOverflowError */
			if (!CollectionUtils.isEmpty(user.getAddress())) {
				user.getAddress().stream().forEach(item -> {
					item.setUserId(null);
				});
			}
			/* Remove verification user object to avoid json StackOverflowError*/
			if (user.getVerification() != null) {
				user.getVerification().setUserId(null);
			}
		}
		return user;
	}

}
