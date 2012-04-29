package com.ilovn.app.anyvblog.model.tencent;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendSearchUser4Tencent implements SendData {
	 private String format;//	 返回数据的格式（json或xml）
	 private String keyword;//	 搜索关键字
	 private int pagesize;//	 本次请求的记录条数（1-20个）
	 private int page;//	 请求的页码，从1开始
	 
	public SendSearchUser4Tencent(String keyword, int pagesize, int page) {
		this("json", keyword, pagesize, page);
	}

	public SendSearchUser4Tencent(String format, String keyword, int pagesize,
			int page) {
		this.format = format;
		this.keyword = keyword;
		this.pagesize = pagesize;
		this.page = page;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("keyword", keyword);
		parameters.addParameter("pagesize", pagesize + "");
		parameters.addParameter("page", page + "");
		return parameters;
	}

}
