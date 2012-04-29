package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendFollowers4Tencent implements SendData {
	private String format;//	 返回数据的格式（json或xml）
	 private int reqnum;//	 请求个数(1-30)
	 private int startindex;//	 起始位置（第一页填0，继续向下翻页：填【reqnum*（page-1）】）
	 private int mode;// 获取模式，默认为0	mode=0，旧模式，新粉丝在前，只能拉取1000个	mode=1，新模式，拉取全量粉丝，老粉丝在前
	 private String name;
	 

	public SendFollowers4Tencent(String name, int reqnum, int startindex,
			int mode) {
		this(name, "json", reqnum, startindex, mode);
	}


	public SendFollowers4Tencent(String name, String format, int reqnum,
			int startindex, int mode) {
		this.name = name;
		this.format = format;
		this.reqnum = reqnum;
		this.startindex = startindex;
		this.mode = mode;
	}


	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("reqnum", reqnum + "");
		parameters.addParameter("startindex", startindex + "");
		parameters.addParameter("mode", mode + "");
		parameters.addParameter("name", name);
		return parameters;
	}

}
