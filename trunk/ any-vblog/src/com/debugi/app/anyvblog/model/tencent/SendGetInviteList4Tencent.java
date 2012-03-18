package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.open.oauth.model.Parameters;

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
