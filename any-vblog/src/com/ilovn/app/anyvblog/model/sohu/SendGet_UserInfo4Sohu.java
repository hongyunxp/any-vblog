package com.ilovn.app.anyvblog.model.sohu;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendGet_UserInfo4Sohu implements SendData {
	private String id;
	private String nick_name;

	public SendGet_UserInfo4Sohu() {
		super();
	}

	public SendGet_UserInfo4Sohu(String id) {
		this.id = id;
	}

	public SendGet_UserInfo4Sohu(String id, String nick_name) {
		this.id = id;
		this.nick_name = nick_name;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (id != null) {
			parameters.addParameter("id", id);
		} 
		parameters.addParameter("anyweibo", "anyweibo");
		return parameters;
	}

}
