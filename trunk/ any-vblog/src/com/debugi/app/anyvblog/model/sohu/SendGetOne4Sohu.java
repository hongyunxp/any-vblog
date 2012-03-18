package com.debugi.app.anyvblog.model.sohu;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendGetOne4Sohu implements SendData {
	private String id;
	
	public SendGetOne4Sohu(String id) {
		this.id = id;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		return parameters;
	}

}
