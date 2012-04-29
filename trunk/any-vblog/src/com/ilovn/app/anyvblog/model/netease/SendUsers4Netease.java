package com.ilovn.app.anyvblog.model.netease;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendUsers4Netease implements SendData {
	private String user_id;// 可选参数. 用户ID，主要是用来区分用户ID跟微博昵称相同产生歧义的情况。
	private String screen_name;//可选参数，该用户的个性网址，也可以传user_id；
	private int cursor;//可选参数，分页参数，单页只能包含30个关注列表
	
	public SendUsers4Netease(String user_id, int cursor) {
		this(user_id, null, cursor);
	}

	public SendUsers4Netease(String user_id, String screen_name, int cursor) {
		this.user_id = user_id;
		this.screen_name = screen_name;
		this.cursor = cursor;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("user_id", user_id);
		if (screen_name != null) {
			parameters.addParameter("screen_name", screen_name);
		}
		parameters.addParameter("cursor", cursor + "");
		return parameters;
	}

}
