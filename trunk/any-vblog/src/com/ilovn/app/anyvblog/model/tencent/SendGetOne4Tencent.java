package com.ilovn.app.anyvblog.model.tencent;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendGetOne4Tencent implements SendData {
	private String format;
	private String id;
	
	public SendGetOne4Tencent(String id) {
		this(Format.json.name(), id);
	}
	public SendGetOne4Tencent(String format, String id) {
		this.format = format;
		this.id = id;
	}
	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("id", id);
		return parameters;
	}
	private enum Format {
		json, xml
	}
}
