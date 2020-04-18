package com.miniproject.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.model.Orders;
import com.miniproject.repository.OrdersRepository;
import com.miniproject.service.OrdersService;


@Service
public class OrdersServiceImpl implements OrdersService {
	
	@Autowired OrdersRepository ordersRepo;

	@Override
	public void save(Orders order) {
		ordersRepo.save(order);
	}

	@Override
	public Orders find(int orderId) {
		return ordersRepo.findOrders(orderId);
	}

	@Override
	public List<Orders> findOrderByUsers(int userId) {
		return ordersRepo.findOrderBySUser(userId);
	}

	@Override
	public List<Orders> findOrderByStatus(String status) {
		return ordersRepo.findOrderByStatus(status);
	}
	
	

}
