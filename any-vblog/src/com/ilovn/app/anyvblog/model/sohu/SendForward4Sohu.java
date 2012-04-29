package com.ilovn.app.anyvblog.model.sohu;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendForward4Sohu implements SendData {
	private String id;
	private String status;
	
	public SendForward4Sohu(String id, String status) {
		this.id = id;
		this.status = status;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		if (status == null || "".equals(status)) {
			status = "转发微博";
		}
		parameters.addParameter("status", status);
		return parameters;
	}

}
