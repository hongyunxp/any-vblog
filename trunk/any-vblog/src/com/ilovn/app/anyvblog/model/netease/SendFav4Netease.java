package com.ilovn.app.anyvblog.model.netease;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendFav4Netease implements SendData {
	private String id;
	
	public SendFav4Netease(String id) {
		this.id = id;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		return parameters;
	}

}
