package com.debugi.app.anyvblog.model.netease;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendGetOne4Netease implements SendData {
	private String id;
	
	public SendGetOne4Netease(String id) {
		this.id = id;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		return parameters;
	}

}
