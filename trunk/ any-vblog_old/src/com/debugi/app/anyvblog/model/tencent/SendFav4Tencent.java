package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendFav4Tencent implements SendData {
	private String id;
	private String format;
	
	public SendFav4Tencent(String id) {
		this(id, "json");
	}
	

	public SendFav4Tencent(String id, String format) {
		this.id = id;
		this.format = format;
	}


	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		parameters.addParameter("format", format);
		return parameters;
	}

}
