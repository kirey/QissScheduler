package com.kubris.qiss.data.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String message;
	//private int statusCode;
	private Object data;

	public ResponseDto(String message) {
		this.message = message;
	}
	
	public ResponseDto(Object data, String message) {
		this.message = message;
		this.data = data;
		//this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
