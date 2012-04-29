package com.ilovn.app.anyvblog.model.tencent;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendGet_UserInfo4Tencent implements SendData {
	private String format;
	private String name;
	private String fopenid;
	
	
	/**
	 * 获取自己的信息
	 */
	public SendGet_UserInfo4Tencent() {
		this.format = "json";
	}
	/**
	 * 获取name指定的用户信息
	 * @param name 用户
	 */
	public SendGet_UserInfo4Tencent(String name) {
		this(name, "json");
	}
	public SendGet_UserInfo4Tencent(String name, String format) {
		this.name = name;
		this.format = format;
	}
	public SendGet_UserInfo4Tencent(String name, String fopenid, String format) {
		this.name = name;
		this.fopenid = fopenid;
		this.format = format;
	}
	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		if (name != null && !"".equals(name)) {
			parameters.addParameter("name", name);
		} else if (fopenid != null && !"".equals(fopenid)) {
			parameters.addParameter("fopenid", fopenid);
		}
		return parameters;
	}
}
