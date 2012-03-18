package com.debugi.app.anyvblog.model.sina;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendGet_UserInfo4Sina implements SendData {
	private String user_id;	//	false	int64	需要查询的用户ID。user_id=1920270334
	private String screen_name;	//	false	string	需要查询的用户昵称。
	
	public SendGet_UserInfo4Sina(String uid) {
		this.user_id = uid;
	}

	public SendGet_UserInfo4Sina(String data, String type) {
		if (type == Type.uid.name()) {
			this.user_id = data;
		} else {
			this.screen_name = data;
		}
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (user_id != null && !"".equals(user_id)) {
			parameters.addParameter("user_id", user_id);
		} else {
			parameters.addParameter("screen_name", screen_name);
		}
		return parameters;
	}
	private enum Type {
		uid, name
	}
}
