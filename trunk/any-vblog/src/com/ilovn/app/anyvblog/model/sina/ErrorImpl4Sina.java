package com.ilovn.app.anyvblog.model.sina;

import com.ilovn.app.anyvblog.model.Error;

public class ErrorImpl4Sina implements Error {
	private int error_code;
	private String request;
	private String error;
	@Override
	public String getMsg() {
		return error;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
