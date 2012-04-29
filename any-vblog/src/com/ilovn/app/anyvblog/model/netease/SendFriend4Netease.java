package com.ilovn.app.anyvblog.model.netease;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendFriend4Netease implements SendData {
	private String user_id;
	
	public SendFriend4Netease(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("user_id", user_id);
		return parameters;
	}

}
