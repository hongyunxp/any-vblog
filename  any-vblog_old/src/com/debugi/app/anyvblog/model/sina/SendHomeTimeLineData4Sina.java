package com.debugi.app.anyvblog.model.sina;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendHomeTimeLineData4Sina implements SendData {
	private String since_id; // 若指定此参数，则只返回ID比since_id大的微博消息（即比since_id发表时间晚的微博消息）。
	private String max_id; // 若指定此参数，则返回ID小于或等于max_id的微博消息
	private int count; // 默认值20，最大值200。 指定要返回的记录条数。
	private int page; // 默认值1。
						// 指定返回结果的页码。根据当前登录用户所关注的用户数及这些被关注用户发表的微博数，翻页功能最多能查看的总记录数会有所不同，通常最多能查看1000条左右。
	private int base_app; // 是否基于当前应用来获取数据。1为限制本应用微博，0为不做限制。
	private int feature; // 微博类型，0全部，1原创，2图片，3视频，4音乐. 返回指定类型的微博信息内容。

	public SendHomeTimeLineData4Sina(int count) {
		this(null, count);
	}

	public SendHomeTimeLineData4Sina(String max_id, int count) {
		this(null, max_id, count, 1);
	}

	public SendHomeTimeLineData4Sina(String since_id, int count, int page) {
		this(since_id, null, count, page);
	}

	public SendHomeTimeLineData4Sina(String since_id, String max_id, int count,
			int page) {
		this(since_id, count, page, max_id, 0, 0);
	}

	public SendHomeTimeLineData4Sina(String since_id, int count, int page,
			String max_id, int base_app, int feature) {
		super();
		this.since_id = since_id;
		this.count = count;
		this.page = page;
		this.max_id = max_id;
		this.base_app = base_app;
		this.feature = feature;
	}



	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (since_id != null) {
			parameters.addParameter("since_id", since_id);
		} else if (max_id != null) {
			parameters.addParameter("max_id", max_id);
		}
		parameters.addParameter("count", count + "");
		parameters.addParameter("page", page + "");
		parameters.addParameter("base_app", base_app + "");
		parameters.addParameter("feature", feature + "");
		return parameters;
	}

}
