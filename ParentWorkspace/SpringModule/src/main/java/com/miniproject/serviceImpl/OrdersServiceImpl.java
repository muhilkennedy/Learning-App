package com.miniproject.serviceImpl;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.dao.OrdersDao;
import com.miniproject.model.Invoice;
import com.miniproject.model.OrderDetail;
import com.miniproject.model.Orders;
import com.miniproject.model.User;
import com.miniproject.repository.OrdersRepository;
import com.miniproject.service.OrdersService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.InvoiceUtil;

@Service
public class OrdersServiceImpl implements OrdersService {

	private static Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);
	
	@Autowired
	private OrdersRepository ordersRepo;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private InvoiceUtil invoiceUtil;
	
	@Override
	@Transactional
	public void save(Orders order) {
		ordersRepo.save(order);
	}
	
	@Override
	public Orders findOrderById(int id) {
		return ordersRepo.findOrderById(id);
	}
	
	@Override
	public List<Orders> findOrderByUserId(int id) throws Exception {
		List<Integer> orderIds = ordersDao.getOrdersByUserId(id);
		List<Orders> orders = new ArrayList<>();
		orderIds.stream().forEach(orderId -> {
			Orders order = findOrderById(orderId);
			if (order != null) {
				// just to lazy load the data and have it as java object.
				Hibernate.initialize(order);
				orders.add(order);
			}
		});
		return orders;
	}
	
	@Override
	public byte[] getDocumentContentByOrderId(int id) throws Exception {
		logger.debug("getDocumentContentByOrderId :: getting invoice content for id : " + id);
		Invoice inv = findOrderById(id).getInvoice();
		File file = invoiceUtil.getInvoiceDocument(inv);
		byte[] docBytes = Files.readAllBytes(file.toPath());
		CommonUtil.deleteDirectoryOrFile(file);
		return docBytes;
	}

	@Override
	@Transactional
	public Orders createOrder(User user, List<OrderDetail> orderDetails) throws Exception {
		Orders order = createBaseorder(user);
		orderDetails.parallelStream().forEach(detail -> {
			detail.setOrderId(order);
		});
		order.setOrderDetail(orderDetails);
		save(order);
		return order;
	}

	private Orders createBaseorder(User user) throws Exception {
		Orders order = new Orders(CommonUtil.Key_PendingStatus, new Date(), user);
		save(order);
		return order;
	}
}
