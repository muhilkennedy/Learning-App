package com.miniproject.messages;

import java.util.List;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;

/**
 * @author Muhil kennedy
 * Builder pattern to handle query builder actions.
 *
 */
public class SQLQueryHandler {

	private String query;	

	public String getQuery() {
		return query;
	}

	private SQLQueryHandler(SQLQueryBuilder builder) {
		this.query = builder.query;
	}

	public static class SQLQueryBuilder {
		
		private String query;
		
		public static final String Key_WhereClause = " where ";
		public static final String Key_OrCondition = " or ";
		public static final String Key_AndCondition = " and ";
		public static final String Key_LimitCondition = " limit ";
		public static final String Key_OffsetCondition = " offset ";
		public static final String Key_Equals = "=";

		public SQLQueryHandler build() {
			return new SQLQueryHandler(this);
		}

		public SQLQueryBuilder setQuery(String query) {
			this.query = query;
			return this;
		}

		public SQLQueryBuilder setWhereClause() {
			if (this.query.contains(Key_WhereClause.trim())) {
				return this;
			}
			this.query = query.concat(Key_WhereClause);
			return this;
		}
		
		public SQLQueryBuilder setAndConditionForInt(String fieldName, List<Integer> values) {
			return setAndCondition(fieldName, Lists.transform(values, Functions.toStringFunction()));
		}

		public SQLQueryBuilder setAndCondition(String fieldName, List<String> values) {
			int count = 1;
			for (String value : values) {
				if (count == values.size()) {
					this.query = this.query.concat(fieldName.concat(Key_Equals).concat(value));
				} else {
					this.query = this.query.concat(fieldName.concat(Key_Equals).concat(value).concat(Key_AndCondition));
				}
				count++;
			}
			return this;
		}

		public SQLQueryBuilder setOrConditionForInt(String fieldName, List<Integer> values) {
			return setOrCondition(fieldName, Lists.transform(values, Functions.toStringFunction()));
		}
		
		public SQLQueryBuilder setOrCondition(String fieldName, List<String> values) {
			int count = 1;
			for (String value : values) {
				if (count == values.size()) {
					this.query = this.query.concat(fieldName.concat(Key_Equals).concat(value));
				} else {
					this.query = this.query.concat(fieldName.concat(Key_Equals).concat(value).concat(Key_OrCondition));
				}
				count++;
			}
			return this;
		}
		
		public SQLQueryBuilder setLimit(String value) {
			if (value != null) {
				if (this.query.contains(Key_LimitCondition.trim())) {
					return this;
				}
				this.query = query.concat(Key_LimitCondition.concat(value));
			}
			return this;
		}

		public SQLQueryBuilder setOffset(String value) {
			if (value != null) {
				if (this.query.contains(Key_OffsetCondition.trim())) {
					return this;
				}
				this.query = query.concat(Key_OffsetCondition.concat(value));
			}
			return this;
		}
	}
}
