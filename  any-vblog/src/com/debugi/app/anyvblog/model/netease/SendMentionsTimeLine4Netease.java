package com.debugi.app.anyvblog.model.netease;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendMentionsTimeLine4Netease implements SendData {
	private int count;	//可选参数	 数量，默认为30条，最大为200条
	private String since_id;	//可选参数	 该参数需传cursor_id,返回此条索引之前发的微博列表,不包含此条
	private String max_id;	//可选参数	 该参数需传cursor_id,返回此条索引之后发的微博列表,包含此条
	private boolean trim_user;	//可选参数	 值为true时返回的user对象只包含id属性，该属性能在一定程度上减少返回的数据量
	

	public SendMentionsTimeLine4Netease(int count) {
		this(null, count);
	}

	public SendMentionsTimeLine4Netease(String since_id, int count) {
		this(count, since_id, null, false);
	}

	public SendMentionsTimeLine4Netease(int count, 
			boolean trim_user) {
		this(count, null, null, trim_user);
	}

	public SendMentionsTimeLine4Netease(int count, String since_id,
			String max_id, boolean trim_user) {
		this.count = count;
		this.since_id = since_id;
		this.max_id = max_id;
		this.trim_user = trim_user;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (since_id != null) {
			parameters.addParameter("since_id", since_id);
		} else if (max_id != null) {
			parameters.addParameter("max_id", max_id);
		} else if (trim_user) {
			parameters.addParameter("trim_user", "true");
		}
		parameters.addParameter("count", count + "");
		return parameters;
	}

}
