package com.ilovn.app.anyvblog.model.tencent;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.open.oauth.model.Parameters;

public class SendGetInviteList4Tencent implements SendData {
	private String format;
	
	public SendGetInviteList4Tencent() {
		this("json");
	}

	public SendGetInviteList4Tencent(String format) {
		this.format = format;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("appid", Config.currentUser.getToken());
		return parameters;
	}

}
