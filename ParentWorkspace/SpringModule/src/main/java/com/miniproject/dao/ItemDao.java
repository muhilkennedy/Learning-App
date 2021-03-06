package com.miniproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miniproject.messages.SQLQueryHandler;
import com.miniproject.util.DbUtil;

@Component
public class ItemDao {

	@Autowired
	private DbUtil dbUtil;

	public List<Integer> getItemIds(List<Integer> cIds, String limit, String offset, boolean includeInactive) throws Exception {
		List<Integer> items = new ArrayList<>();
		try (Connection con = dbUtil.getConnectionInstance()) {
			SQLQueryHandler sql = null;
			if(includeInactive) {
				sql = new SQLQueryHandler.SQLQueryBuilder()
						  .setQuery("select itemid from item")
						  .setWhereClause()
						  .setStartBrace()
						  .setOrConditionForInt("cid", cIds)
						  .setEndBrace()
						  .setLimit(limit)
						  .setOffset(offset)
						  .build();
			}
			else {
				sql = new SQLQueryHandler.SQLQueryBuilder()
						  .setQuery("select itemid from item")
						  .setWhereClause()
						  .setStartBrace()
						  .setOrConditionForInt("cid", cIds)
						  .setEndBrace()
						  .setAndCondition("active", "true", true)
						  .setLimit(limit)
						  .setOffset(offset)
						  .build();
			}
			PreparedStatement stmt = con.prepareStatement(sql.getQuery());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				items.add(rs.getInt(1));
			}
		}
		return items;
	}
	
	public List<Integer> getItemIds(String limit, String offset) throws Exception {
		List<Integer> items = new ArrayList<>();
		try (Connection con = dbUtil.getConnectionInstance()) {
			SQLQueryHandler sql = new SQLQueryHandler.SQLQueryBuilder()
						  .setQuery("select itemid from item")
						  .setLimit(limit)
						  .setOffset(offset)
						  .build();
			PreparedStatement stmt = con.prepareStatement(sql.getQuery());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				items.add(rs.getInt(1));
			}
		}
		return items;
	}
}
