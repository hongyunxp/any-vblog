package com.ilovn.app.anyvblog.model.sohu;

import com.ilovn.app.anyvblog.model.Error;

public class ErrorImpl4Sohu implements Error {
	private int code;
	private String error;
	private String request;
	@Override
	public String getMsg() {
		return error;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}

}
