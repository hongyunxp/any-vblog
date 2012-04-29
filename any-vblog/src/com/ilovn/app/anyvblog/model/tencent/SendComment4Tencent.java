package com.ilovn.app.anyvblog.model.tencent;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendComment4Tencent implements SendData {
	private String format;
	private String content;
	private String clientip;
	private String jing;
	private String wei;
	private String reid;	//回复的父结点微博id / 点评根结点（非父结点）微博id
	
	public SendComment4Tencent(String content, String reid) {
		this(content, reid, "0", "0");
	}

	public SendComment4Tencent(String content, String reid, String jing,
			String wei) {
		this(content, reid, "127.0.0.1", jing, wei);
	}

	public SendComment4Tencent(String content, String reid, String clientip,
			String jing, String wei) {
		this("json", content, reid, clientip, jing, wei);
	}

	public SendComment4Tencent(String format, String content, String reid,
			String clientip, String jing, String wei) {
		this.format = format;
		this.content = content;
		this.reid = reid;
		this.clientip = clientip;
		this.jing = jing;
		this.wei = wei;
	}

	@Override
	public Parameters conver() {
		Parameters data = new Parameters();
		data.addParameter("format", format);
		data.addParameter("content", content);
		data.addParameter("clientip", clientip);
		data.addParameter("jing", jing);
		data.addParameter("wei", wei);
		data.addParameter("reid", reid);
		return data;
	}

}
