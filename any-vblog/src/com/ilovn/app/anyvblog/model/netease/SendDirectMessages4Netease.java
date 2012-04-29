package com.ilovn.app.anyvblog.model.netease;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendDirectMessages4Netease implements SendData {
	private int count;	//可选参数	 数量，默认为30条，最大为200条
	private String since_id;	//可选参数	 该参数需传cursor_id,返回此条索引之前发的微博列表,不包含此条
	

	
	public SendDirectMessages4Netease(int count) {
		this(count, null);
	}



	public SendDirectMessages4Netease(int count, String since_id) {
		this.count = count;
		this.since_id = since_id;
	}



	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (since_id != null) {
			parameters.addParameter("since_id", since_id);
		} 
		parameters.addParameter("count", count + "");
		return parameters;
	}

}
