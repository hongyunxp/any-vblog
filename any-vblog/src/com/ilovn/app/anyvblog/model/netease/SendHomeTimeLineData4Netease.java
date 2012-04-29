package com.ilovn.app.anyvblog.model.netease;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendHomeTimeLineData4Netease implements SendData {
	private int count;	//可选参数	 数量，默认为30条，最大为200条
	private String since_id;	//可选参数	 该参数需传cursor_id,返回此条索引之前发的微博列表,不包含此条
	private String max_id;	//可选参数	 该参数需传cursor_id,返回此条索引之后发的微博列表,包含此条
	private boolean trim_user;	//可选参数	 值为true时返回的user对象只包含id属性，该属性能在一定程度上减少返回的数据量
	private int filter_type;	//可选参数	 需要返回微博的过滤类型，取值范围0-7，分别代表全部、原创、图片、视频、音乐、跟帖、新闻、话题，默认为0全部，如果输入超出取值范围则返回全部。
	

	public SendHomeTimeLineData4Netease(int count) {
		this(count, null);
	}

	public SendHomeTimeLineData4Netease(int count, String max_id) {
		this(count, null, max_id);
	}

	public SendHomeTimeLineData4Netease(int count, String since_id,
			String max_id) {
		this(count, since_id, max_id, false, 0);
	}



	public SendHomeTimeLineData4Netease(int count, String since_id,
			String max_id, boolean trim_user, int filter_type) {
		this.count = count;
		this.since_id = since_id;
		this.max_id = max_id;
		this.trim_user = trim_user;
		this.filter_type = filter_type;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (since_id != null) {
			parameters.addParameter("since_id", since_id);
		}
		if (max_id != null) {
			parameters.addParameter("max_id", max_id);
		}
		if (trim_user) {
			parameters.addParameter("trim_user", "true");
		}
		parameters.addParameter("filter_type", filter_type + "");
		parameters.addParameter("count", count + "");
		return parameters;
	}

}
