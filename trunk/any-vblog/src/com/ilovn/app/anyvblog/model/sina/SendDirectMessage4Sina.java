package com.ilovn.app.anyvblog.model.sina;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendDirectMessage4Sina implements SendData {
	private String since_id; // 若指定此参数，则只返回ID比since_id大的微博消息（即比since_id发表时间晚的微博消息）。
	private String max_id; // 若指定此参数，则返回ID小于或等于max_id的微博消息
	private int count; // 默认值20，最大值200。 指定要返回的记录条数。
	private int page; // 默认值1。
						// 指定返回结果的页码。

	public SendDirectMessage4Sina(int count) {
		this(count, 1);
	}

	public SendDirectMessage4Sina(int count, int page) {
		this(null, null, count, page);
	}


	public SendDirectMessage4Sina(String since_id, String max_id,
			int count, int page) {
		this.since_id = since_id;
		this.max_id = max_id;
		this.count = count;
		this.page = page;
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
		return parameters;
	}

}
