package com.huawei.iot.util;

public class RestResult {

	public static final int SUCCESS = 1;
	public static final int FAILED = 0;
	
	private int code;
	private Object data;
	private String description;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
/*	@Override
	public String toString() {
		return "RestResult [code=" + code + ", data=" + data + ", description=" + description + "]";
	}*/
	
}
