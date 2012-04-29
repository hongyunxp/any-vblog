package com.debugi.app.anyvblog.model.netease;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendSearch4Netease implements SendData {
	private String q;//必选参数，关键字,最大长度25
	private int page;//可选参数，当前页数，默认为第一页
	private int per_page;//可选参数，返回数量,最大20
	
	public SendSearch4Netease(String q, int page, int per_page) {
		this.q = q;
		this.page = page;
		this.per_page = per_page;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("q", q);
		parameters.addParameter("page", page + "");
		parameters.addParameter("per_page", per_page + "");
		return parameters;
	}

}
