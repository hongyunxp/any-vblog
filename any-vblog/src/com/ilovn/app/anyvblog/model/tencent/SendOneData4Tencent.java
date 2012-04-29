package com.ilovn.app.anyvblog.model.tencent;

import java.util.HashMap;
import java.util.Map;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.open.oauth.model.Parameters;

public class SendOneData4Tencent implements SendData {
	private String format;
	private String content;
	private String clientip;
	private float jing;
	private float wei;
	private int syncflag;
	//图片

	public SendOneData4Tencent(String content) {
		this(content, "127.0.0.1");
	}
	public SendOneData4Tencent(String content, String clientip) {
		this(content, clientip, 0, 0);
	}
	public SendOneData4Tencent(String content, float jing, float wei) {
		this(content, "127.0.0.1", jing, wei);
	}
	public SendOneData4Tencent(String content, String clientip, float jing,
			float wei) {
		this(content, clientip, jing, wei, 0);
	}
	public SendOneData4Tencent(String content, String clientip, float jing,
			float wei, int syncflag) {
		this("json", content, clientip, jing, wei, syncflag);
	}
	public SendOneData4Tencent(String format, String content, String clientip,
			float jing, float wei, int syncflag) {
		this.format = format;
		this.content = content;
		this.clientip = clientip;
		this.jing = jing;
		this.wei = wei;
		this.syncflag = syncflag;
	}
	@Override
	public Parameters conver() {
		init();
		Map<String, String> data = new HashMap<String, String>();
		data.put("format", format);
		data.put("content", content);
		data.put("clientip", clientip);
		data.put("jing", jing + "");
		data.put("wei", wei + "");
		data.put("syncflag", syncflag + "");
		return new Parameters(data);
	}
	/**
	 * 初始化一些判定
	 */
	private void init() {
		if (format == null || "".equals(format)) {
			format = "json";
		} else if (content == null || "".equals(content)) {
			content = Constants.DEFAULT_MSG;
		} else if (clientip == null || "".equals(clientip)) {
			clientip = "127.0.0.1";
		} else if (jing > 180 || jing < -180  || wei > 90 || wei < -90) {
			jing = 0;
			wei = 0;
		} else if (syncflag > 2 || syncflag < 0) {
			syncflag = 0;
		}
	}
}
