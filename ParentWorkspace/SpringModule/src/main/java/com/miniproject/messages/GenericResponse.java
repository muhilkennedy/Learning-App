package com.miniproject.messages;

import java.util.List;

/**
 * @author MuhilKennedy
 *
 * @param <T> can be used for any object/List of Objects as generic response type.
 */
public class GenericResponse<T> extends Response {

	private T data;
	private List<T> dataList;
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public List<T> getDataList() {
		return dataList;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

}
