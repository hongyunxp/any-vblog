package com.debugi.app.anyvblog.model.sohu;

import java.util.HashMap;
import java.util.Map;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.model.Parameters;

public class SendOneData4Sohu implements SendData {
	private String status;
	private String in_reply_to_status_id;
	

	public SendOneData4Sohu(String status) {
		this(status, null);
	}

	public SendOneData4Sohu(String status, String in_reply_to_status_id) {
		this.status = status;
		this.in_reply_to_status_id = in_reply_to_status_id;
	}

	@Override
	public Parameters conver() {
		init();
		Map<String, String> data = new HashMap<String, String>();
		data.put("status", status);
		if (in_reply_to_status_id != null && !"".equals(in_reply_to_status_id)) {
			data.put("in_reply_to_status_id", in_reply_to_status_id);
		}
		return new Parameters(data);
	}
	
	private void init() {
		if (status == null || "".equals(status)) {
			status = Constants.DEFAULT_MSG;
		}
	}

}
