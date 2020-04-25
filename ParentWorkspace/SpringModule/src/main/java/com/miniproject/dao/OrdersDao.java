package com.miniproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miniproject.util.DbUtil;

/**
 * @author Muhil kennedy
 * DAO class to perfrom sql operations on order table.
 */
@Component
public class OrdersDao {

	@Autowired
	DbUtil dbUtil;

	public List<Integer> getOrdersByUserId(int userId) throws Exception {
		List<Integer> orders = new ArrayList<>();
		try (Connection con = dbUtil.getConnectionInstance()) {
			PreparedStatement stmt = con.prepareStatement("select orderid from orders where userid=?");
			stmt.setInt(1, userId);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				orders.add(rs.getInt(1));
			}
		}
		return orders;
	}
	
}
