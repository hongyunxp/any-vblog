package com.ilovn.app.anyvblog.model.sina;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendGetOne4Sina implements SendData {
	private String id;
	
	public SendGetOne4Sina(String id) {
		this.id = id;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		return parameters;
	}

}
