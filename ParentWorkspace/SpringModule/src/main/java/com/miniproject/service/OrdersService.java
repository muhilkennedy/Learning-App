package com.miniproject.service;

import java.util.List;

import com.miniproject.model.Orders;

public interface OrdersService {
	
	public void save(Orders order);
	
	public Orders find(int orderid);
	
	public List<Orders> findOrderByUsers(int userId);
	
	public List<Orders> findOrderByStatus(String status);

}
