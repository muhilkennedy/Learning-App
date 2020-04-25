package com.miniproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miniproject.messages.SQLQueryHandler;
import com.miniproject.util.DbUtil;

@Component
public class CartDao {

	@Autowired
	private DbUtil dbUtil;

	public void insertIntoCart(int userId, int itemId, int quantity) throws Exception {
		try (Connection con = dbUtil.getConnectionInstance()) {
			PreparedStatement stmt = con.prepareStatement("insert into cart values(?, ?, ?)");
			stmt.setInt(1, userId);
			stmt.setInt(2, itemId);
			stmt.setInt(3, quantity);
			stmt.executeUpdate();
		}
	}
	
	public void updateQuantityInCart(int userId, int itemId, int quantity) throws Exception {
		try (Connection con = dbUtil.getConnectionInstance()) {
			SQLQueryHandler sql = new SQLQueryHandler.SQLQueryBuilder()
					    		  .setQuery("update cart set quantity=?")
					    		  .setWhereClause()
					    		  .setAndCondition(" userid ", userId, false)
					    		  .setAndCondition(" itemid ", itemId, true)
					    		  .build();
			PreparedStatement stmt = con.prepareStatement(sql.getQuery());
			stmt.setInt(1, quantity);
			stmt.executeUpdate();
		}
	}
	
	public List<Integer> getItemIds(int userId) throws Exception {
		List<Integer> items = new ArrayList<>();
		try (Connection con = dbUtil.getConnectionInstance()) {
			PreparedStatement stmt = con.prepareStatement("select itemid from cart where userid=?");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				items.add(rs.getInt(1));
			}
		}
		return items;
	}
	
	public Map<Integer, Integer> getCartItemsWithQuantity(int userId) throws Exception {
		Map<Integer, Integer> map = new HashMap<>();
		try (Connection con = dbUtil.getConnectionInstance()) {
			PreparedStatement stmt = con.prepareStatement("select itemid,quantity from cart where userid=?");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt(1),rs.getInt(2));
			}
		}
		return map;
	}
	
}
