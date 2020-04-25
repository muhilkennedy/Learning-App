package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.OrderDetail;
import com.miniproject.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	
	String findOrderQuery = "select order from Orders order where order.orderId = :orderId";
	String findOrderByUserQuery = "select od from Orders od where od.userId = :userId order by orderDate desc";
	
	@Query(findOrderQuery)
	Orders findOrderById(@Param("orderId") int orderId);
	
	@Query(findOrderByUserQuery)
	List<Orders> findOrderByUserId(@Param("userId") int userId);
	
}
