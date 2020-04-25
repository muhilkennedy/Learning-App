package com.miniproject.service;

import java.io.File;
import java.util.List;

import com.miniproject.model.OrderDetail;
import com.miniproject.model.Orders;
import com.miniproject.model.User;

public interface OrdersService {

	Orders findOrderById(int id);

	List<Orders> findOrderByUserId(int id) throws Exception;

	byte[] getDocumentContentByOrderId(int id) throws Exception;

	void save(Orders order);

	Orders createOrder(User user, List<OrderDetail> orderDetails) throws Exception;

}
