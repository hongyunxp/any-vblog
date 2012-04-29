package com.ilovn.app.anyvblog.model.tencent;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendFriends4Tencent implements SendData {
	private String format;// 返回数据的格式（json或xml）
	private int reqnum;// 请求个数(1-30)
	private int startindex;// 起始位置（第一页:填0，继续向下翻页：填【reqnum*（page-1）】）
	private String name;
	
	

	public SendFriends4Tencent(String name, int reqnum, int startindex) {
		this("json", reqnum, startindex, name);
	}



	public SendFriends4Tencent(String format, int reqnum, int startindex,
			String name) {
		this.format = format;
		this.reqnum = reqnum;
		this.startindex = startindex;
		this.name = name;
	}



	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("reqnum", reqnum + "");
		parameters.addParameter("startindex", startindex + "");
		parameters.addParameter("name", name);
		return parameters;
	}
}
