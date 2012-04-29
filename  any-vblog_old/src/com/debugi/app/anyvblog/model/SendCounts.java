package com.debugi.app.anyvblog.model;

import com.debugi.open.oauth.model.Parameters;

public class SendCounts implements SendData {
	private String ids;
	
	public SendCounts(String ids) {
		this.ids = ids;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("ids", ids);
		return parameters;
	}

}
