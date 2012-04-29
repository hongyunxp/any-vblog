package com.ilovn.app.anyvblog.model.sohu;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendSearchStatus4Sohu implements SendData {
	private String q;// 查询关键字，返回此关键字的查询列表(需要urlencode，编码为utf-8)
	private String nick_name;// 昵称
	private String short_domain;// 短域名
	private int page;// 当前的页数
	private int rpp;// 每页条数，不大于20
	// callback
	// 仅JSON方式支持，用于JSONP callback作用。

	public SendSearchStatus4Sohu(String q, int page, int count) {
		this(q, null, null, page, count);
	}

	public SendSearchStatus4Sohu(String q, String nick_name, String short_domain,
			int page, int count) {
		this.q = q;
		this.nick_name = nick_name;
		this.short_domain = short_domain;
		this.page = page;
		this.rpp = count;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("q", q);
		if (nick_name != null) {
			parameters.addParameter("nick_name", nick_name);
		} else if (short_domain != null) {
			parameters.addParameter("short_domain", short_domain);
		}
		parameters.addParameter("page", page + "");
		parameters.addParameter("rpp", rpp + "");
		return parameters;
	}

}
