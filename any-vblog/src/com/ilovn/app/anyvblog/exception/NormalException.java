package com.ilovn.app.anyvblog.exception;


public class NormalException extends RuntimeException {

	private static final long serialVersionUID = -791503140531568707L;

	public NormalException(String detailMessage, Throwable throwable) {
		//super(detailMessage, throwable);
	}

	public NormalException(String detailMessage) {
	}

	public NormalException() {
		//super();
		// TODO Auto-generated constructor stub
	}

	public NormalException(Throwable throwable) {
		//super(throwable);
		// TODO Auto-generated constructor stub
	}
}
