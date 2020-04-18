package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Orders;

@Repository
public interface OrdersRepository  extends JpaRepository<Orders, Integer>{
	
	String findOrdersQuery = "select ord from Orders ord where ord.ORDERID = :orderId";
	String findOrderByStatusQuery = "select ord from Orders ord where ord.STATUS = :status";
	String findOrderByUserQuery = "select ord from Orders ord where ord.USERID = :userId";
	
	@Query(findOrdersQuery)
	Orders findOrders(@Param("orderId") int orderId);
	
	@Query(findOrderByStatusQuery)
	List<Orders> findOrderByStatus(@Param("status") String status);
	
	@Query(findOrderByUserQuery)
	List<Orders> findOrderBySUser(@Param("userId") int userId);

}
