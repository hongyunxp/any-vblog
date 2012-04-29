package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendForward4Tencent implements SendData {
	private String format;
	private String content;
	private String clientip;
	private String jing;
	private String wei;
	private String reid;
	
	public SendForward4Tencent(String content, String reid) {
		this(content, "0", "0", reid);
	}

	public SendForward4Tencent(String content, String jing, String wei,
			String reid) {
		this("json", content, "127.0.0.1", jing, wei, reid);
	}

	public SendForward4Tencent(String format, String content, String clientip,
			String jing, String wei, String reid) {
		this.format = format;
		this.content = content;
		this.clientip = clientip;
		this.jing = jing;
		this.wei = wei;
		this.reid = reid;
	}

	@Override
	public Parameters conver() {
		Parameters data = new Parameters();
		data.addParameter("format", format);
		if (content == null || "".equals(content)) {
			content = "转发微博";
		}
		data.addParameter("content", content);
		data.addParameter("clientip", clientip);
		data.addParameter("jing", jing);
		data.addParameter("wei", wei);
		data.addParameter("reid", reid);
		return data;
	}

}
