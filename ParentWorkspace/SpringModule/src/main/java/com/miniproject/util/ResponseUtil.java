package com.miniproject.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.io.ByteStreams;
import com.miniproject.messages.CartResponse;
import com.miniproject.messages.ItemResponse;
import com.miniproject.model.Item;
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
			order.getOrderDetail().parallelStream().forEach(detail -> {
				detail.setOrderId(null);
			});
		}
		order.setUserId(null);
		return order;
	}
	
	public static ItemResponse convertItemModelToItemResponse(Item item) throws Exception {
		ItemResponse itemResponse = new ItemResponse();
		itemResponse.setActive(item.isActive());
		itemResponse.setBrandName(item.getBrandName());
		itemResponse.setCost(item.getCost());
		itemResponse.setItemId(item.getItemId());
		itemResponse.setItemName(item.getItemName());
		itemResponse.setMeasure(item.getMeasure());
		itemResponse.setOffer(item.getOffer());
		itemResponse.setcId(item.getcId().getcId());
		itemResponse.setCategoryName(item.getcId().getCategory());
		if(item.getImage() != null) {
			InputStream in = item.getImage().getBinaryStream();
			StringBuilder base64 = new StringBuilder("data:image/jpeg;base64,");
			base64.append(Base64.getEncoder().encodeToString(ByteStreams.toByteArray(in)));
			itemResponse.setImage(base64.toString());
		}
		return itemResponse;
	}
	
	public static List<ItemResponse> convertItemModelToItemResponse(List<Item> items) {
		List<ItemResponse> itemResponse = new ArrayList<>();
		items.parallelStream().forEach(item -> {
			try {
				itemResponse.add(convertItemModelToItemResponse(item));
			} catch (Exception e) {
				LogUtil.getLogger()
						.error("convertItemModelToItemResponse :: Error Converting Item : " + item.getItemId());
			}
		});
		return itemResponse;
	}
	
	public static List<CartResponse> convertToCartResponse(List<Item> items, Map<Integer,Integer> itemQuantityMap){
		List<CartResponse> cartResponse = new ArrayList<>();
		items.parallelStream().forEach(item -> {
			CartResponse cart = new CartResponse();
			cart.setItemId(item.getItemId());
			cart.setItemName(item.getItemName());
			cart.setQuantity(itemQuantityMap.get(item.getItemId()));
			cartResponse.add(cart);
		});
		return cartResponse;
	}

}
