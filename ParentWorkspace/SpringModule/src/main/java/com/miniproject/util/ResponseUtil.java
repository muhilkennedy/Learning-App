package com.miniproject.util;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.miniproject.model.OrderDetail;
import com.miniproject.model.Orders;
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
			
			/* Remove cart user object to avoid json StackOverflowError */
			if (!CollectionUtils.isEmpty(user.getCart())) {
				user.getCart().stream().forEach(item -> {
					item.setUserId(null);
				});
			}
		}
		return user;
	}
	
	public static List<Orders> cleanUpOrderResponse(List<Orders> orders) {
		orders.parallelStream().forEach(order -> {
			cleanUpOrderResponse(order);
		});
		return orders;
	}

	public static Orders cleanUpOrderResponse(Orders order) {
		if (order != null) {
			List<OrderDetail> det = order.getOrderDetail();
			order.getOrderDetail().parallelStream().forEach(detail -> {
				detail.setOrderId(null);
			});
		}
		order.setUserId(null);
		return order;
	}

}
