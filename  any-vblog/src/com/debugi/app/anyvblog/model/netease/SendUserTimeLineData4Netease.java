package com.debugi.app.anyvblog.model.netease;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendUserTimeLineData4Netease implements SendData {
	private String user_id;
	private String screen_name;// 可选参数	 可以传user_id或screen_name；
	private String name;	//可选参数	 用户昵称；
	
	private int count;	//可选参数	 数量，默认为30条，最大为200条
	private String since_id;	//可选参数	 该参数需传cursor_id,返回此条索引之前发的微博列表,不包含此条
	private String max_id;	//可选参数	 该参数需传cursor_id,返回此条索引之后发的微博列表,包含此条
	private boolean trim_user;	//可选参数	 值为true时返回的user对象只包含id属性，该属性能在一定程度上减少返回的数据量
	private int filter_type;	//可选参数	 需要返回微博的过滤类型，取值范围0-7，分别代表全部、原创、图片、视频、音乐、跟帖、新闻、话题，默认为0全部，如果输入超出取值范围则返回全部。
	

	public SendUserTimeLineData4Netease(String user_id, int count) {
		this(user_id, count, null);
	}
	/**
	 * 请求数据
	 * @param user_id
	 * @param count
	 * @param since_id
	 */
	public SendUserTimeLineData4Netease(String user_id, int count, String since_id) {
		this(user_id, null, null, count, since_id, null, false, 0);
	}



	public SendUserTimeLineData4Netease(String user_id, int count, 
			boolean trim_user, int filter_type) {
		this(user_id, null, null, count, null, null, trim_user, filter_type);
	}

	

	public SendUserTimeLineData4Netease(String user_id, String screen_name,
			String name, int count, String since_id, String max_id,
			boolean trim_user, int filter_type) {
		this.user_id = user_id;
		this.screen_name = screen_name;
		this.name = name;
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
		} else if (max_id != null) {
			parameters.addParameter("max_id", max_id);
		} else if (trim_user) {
			parameters.addParameter("trim_user", "true");
		} else if (screen_name != null) {
			parameters.addParameter("screen_name", screen_name);
		} else if(name != null) {
			parameters.addParameter("name", name);
		}
		parameters.addParameter("user_id", user_id);
		parameters.addParameter("filter_type", filter_type + "");
		parameters.addParameter("count", count + "");
		return parameters;
	}

}
