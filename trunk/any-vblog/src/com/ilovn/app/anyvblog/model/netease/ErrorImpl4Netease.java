package com.ilovn.app.anyvblog.model.netease;

import com.ilovn.app.anyvblog.model.Error;

public class ErrorImpl4Netease implements Error {
	private String request;
	private String error;
	private int error_code;
	private int message_code;

	@Override
	public String getMsg() {
		return error;
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

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public int getMessage_code() {
		return message_code;
	}

	public void setMessage_code(int message_code) {
		this.message_code = message_code;
	}
}
