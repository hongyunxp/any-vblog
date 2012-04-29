package com.ilovn.app.anyvblog.model.netease;

import java.util.Map;

import com.ilovn.app.anyvblog.model.Data;
import com.ilovn.app.anyvblog.model.DataAdapter;

public class GetForward4Netease implements Data {
	private Status4Netease retweeted_status;
	public Status4Netease getRetweeted_status() {
		return retweeted_status;
	}

	public void setRetweeted_status(Status4Netease retweeted_status) {
		this.retweeted_status = retweeted_status;
	}

	@Override
	public DataAdapter conver2Data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}

}
